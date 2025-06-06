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

  editingPartId: number | null = null;
  editedPart: PartDTO | null = null;

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
    // Jeśli nie ma części załadowanych, nic nie rób
    if (!this.parts || this.parts.length === 0) {
      return;
    }

    let result = [...this.parts];

    // 1) Najpierw ograniczamy po kategorii, jeżeli jest ustawiona
    if (this.activeCategory) {
      result = result.filter(part => part.categoryName === this.activeCategory);
    }

    // 2) Następnie, jeżeli ktoś wybrał konkretną nazwę części,
    //    możemy jeszcze dodatkowo filtrować:
    if (this.activePartName) {
      result = result.filter(part => part.partName === this.activePartName);
    }

    this.filteredParts = result;
  }

  deletePart(id: number): void {
    if (!confirm('Czy na pewno chcesz usunąć tę część?')) {
      return;
    }
    this.partsService.deletePart(id).subscribe({
      next: () => this.loadParts(),
      error: (err) => {
        console.error('Błąd podczas usuwania części:', err);
        alert('Błąd podczas usuwania części.');
      }
    });
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

  startEdit(part: PartDTO): void {
    this.editingPartId = part.partId;
    this.editedPart = { ...part };
  }

  cancelEdit(): void {
    this.editingPartId = null;
    this.editedPart = null;
  }

  saveEdit(): void {
    if (!this.editedPart || this.editingPartId == null) {
      return;
    }
    let body = new HttpParams()
      .set('partName', this.editedPart.partName)
      .set('unitPrice', this.editedPart.unitPrice.toString())
      .set('quantityPerUnit', this.editedPart.quantityPerUnit)
      .set('leftOnStock', this.editedPart.leftOnStock.toString())
      .set('available', this.editedPart.available.toString())
      .set('partDescription', this.editedPart.partDescription)
      .set('categoryName', this.editedPart.categoryName);

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    this.http.put<PartDTO>(
      `http://localhost:8080/parts/${this.editingPartId}`,
      body.toString(),
      { headers }
    ).subscribe({
      next: updated => {
        this.editingPartId = null;
        this.editedPart = null;
        this.loadParts();
      },
      error: err => {
        console.error('Błąd podczas aktualizacji części:', err);
        alert('Nie udało się zaktualizować części.');
      }
    });
  }
}
