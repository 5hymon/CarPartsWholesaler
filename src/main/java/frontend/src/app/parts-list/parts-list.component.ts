import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

import { PartsService } from '../services/parts.service';
import { PartDTO } from '../models/part-dto.model';

@Component({
  selector: 'app-parts-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './parts-list.component.html',
  styleUrls: ['./parts-list.component.css']
})
export class PartsListComponent implements OnInit {
  parts: PartDTO[] = [];
  filteredParts: PartDTO[] = [];

  loading = false;
  errorMessage = '';

  activeCategory: string | null = null;
  activePartName: string | null = null;

  private categoryFromUrl: string | null = null; // Store URL category

  constructor(
    private partsService: PartsService,
    private http: HttpClient,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.categoryFromUrl = params['category'] || null;
      this.activeCategory = this.categoryFromUrl;
      this.activePartName = null;
      this.applyFilter();
    });

    this.loadParts();
  }

  private loadParts(): void {
    this.loading = true;
    this.partsService.getAllParts().subscribe({
      next: (data: PartDTO[]) => {
        this.parts = data;
        this.filteredParts = [...data];

        if (this.categoryFromUrl) {
          this.activeCategory = this.categoryFromUrl;
          this.activePartName = null;
          this.applyFilter();
        }

        this.loading = false;
      },
      error: (err) => {
        console.error('Błąd przy pobieraniu części:', err);
        this.errorMessage = 'Nie udało się pobrać listy części.';
        this.loading = false;
      }
    });
  }

  // Ta metoda agreguje wszystkie warunki filtrowania:
  private applyFilter(): void {
    if (!this.parts || this.parts.length === 0) {
      return;
    }

    let result = [...this.parts];

    if (this.activeCategory) {
      result = result.filter(part => part.categoryName === this.activeCategory);
    }

    if (this.activePartName) {
      result = result.filter(part => part.partName === this.activePartName);
    }

    this.filteredParts = result;
  }

  groupByCategory(): { category: string; parts: PartDTO[] }[] {
    const map: Record<string, PartDTO[]> = {};
    this.parts.forEach(part => {
      const cat = part.categoryName;
      if (!map[cat]) {
        map[cat] = [];
      }
      map[cat].push(part);
    });
    return Object.keys(map).map(category => ({
      category,
      parts: map[category]
    }));
  }

  selectByCategory(category: string): void {
    this.activeCategory = category;
    this.activePartName = null;
    this.applyFilter();
  }

  selectCategoryPart(category: string, partName: string): void {
    if (this.activeCategory === category && this.activePartName === partName) {
      this.activeCategory = null;
      this.activePartName = null;
      this.filteredParts = [...this.parts];
    } else {
      this.activeCategory = category;
      this.activePartName = partName;
      this.applyFilter();
    }
  }
}
