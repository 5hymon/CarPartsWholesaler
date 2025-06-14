import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

import { PartCompatibilityService } from '../services/part-compatibility.service';
import { PartsService } from '../services/parts.service';
import { PartDTO } from '../models/part-dto.model';
import { CarDTO } from '../models/car-dto.model';

@Component({
  selector: 'app-warehouse',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './warehouse.component.html',
  styleUrls: ['./warehouse.component.css']
})
export class WarehouseComponent implements OnInit {
  parts: PartDTO[] = [];
  filteredParts: PartDTO[] = [];
  cars: CarDTO[] = [];

  compatFormPartId: number | null = null;
  newCompatCarId!: number;

  loading = false;
  errorMessage = '';

  activeCategory: string | null = null;
  activePartName: string | null = null;

  editingPartId: number | null = null;
  editedPart: PartDTO | null = null;

  addingPart = false;
  newPart: PartDTO = {
    partId: 0,
    partName: '',
    unitPrice: 0,
    quantityPerUnit: '',
    leftOnStock: 0,
    isAvailable: true,
    partDescription: '',
    categoryName: '',
    compatibleCars: []
  };

  private categoryFromUrl: string | null = null; // Store URL category

  constructor(
    private partsService: PartsService,
    private compatService: PartCompatibilityService,
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
    this.loadCars();
  }

  private loadCars() {
    this.compatService.getAllCars().subscribe({
      next: list => this.cars = list,
      error: err => console.error('Błąd pobierania aut:', err)
    });
  }

  toggleCompatForm(partId: number | null) {
    this.compatFormPartId = this.compatFormPartId === partId ? null : partId;
    this.newCompatCarId = NaN;
  }

  addCompatibility(partId: number) {
    if (!this.newCompatCarId) { return; }
    this.compatService.addCompatibility(this.newCompatCarId, partId)
      .subscribe({
        next: () => {
          this.toggleCompatForm(partId);
        },
        error: err => {
          console.error(err);
        }
      });
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
      .set('isAvailable', (this.editedPart.isAvailable = this.editedPart.leftOnStock > 0).toString())
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
        this.editingPartId = null;
        this.editedPart = null;
        this.loadParts();
      }
    });
  }

  startAdd(): void {
    this.addingPart = true;
    this.newPart = {
      partId: 0,
      partName: '',
      unitPrice: 0,
      quantityPerUnit: '',
      leftOnStock: 0,
      isAvailable: true,
      partDescription: '',
      categoryName: '',
      compatibleCars: []
    };
  }

  cancelAdd(): void {
    this.addingPart = false;
  }

  saveAdd(): void {
    if (!this.newPart.partName ||
      !this.newPart.unitPrice ||
      !this.newPart.quantityPerUnit ||
      !this.newPart.leftOnStock ||
      !this.newPart.partDescription ||
      !this.newPart.categoryName) {
      return;
    }

    const body = new HttpParams()
      .set('partName', this.newPart.partName)
      .set('unitPrice', this.newPart.unitPrice.toString())
      .set('quantityPerUnit', this.newPart.quantityPerUnit)
      .set('leftOnStock', this.newPart.leftOnStock.toString())
      .set('partDescription', this.newPart.partDescription)
      .set('categoryName', this.newPart.categoryName)
      .set('isAvailable', true.toString());

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    this.http.post<PartDTO>(
      'http://localhost:8080/parts',
      body.toString(),
      { headers }
    ).subscribe({
      next: created => {
        this.addingPart = false;
        this.loadParts();
      },
      error: err => {
        console.error('Błąd przy tworzeniu nowej części:', err);
        alert('Nie udało się dodać nowej części.');
      }
    });
  }
}
