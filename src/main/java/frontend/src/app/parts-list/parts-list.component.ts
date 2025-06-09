import {Component, Inject, OnInit, PLATFORM_ID} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';

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

  showToast = false;
  toastMessage = '';

  loading = false;
  errorMessage = '';

  activeCategory: string | null = null;
  activePartName: string | null = null;

  private categoryFromUrl: string | null = null;

  isBrowser: boolean;
  userRole: string = '';

  constructor(
    @Inject(PLATFORM_ID) platformId: Object,
    private partsService: PartsService,
    private http: HttpClient,
    private route: ActivatedRoute
  ) {
    this.isBrowser = isPlatformBrowser(platformId);
    if (this.isBrowser) {
      this.userRole = localStorage.getItem('role') || '';
    }
  }

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
        this.parts = data.map(part => ({ ...part, selectedQuantity: 1 }));
        this.filteredParts = [...this.parts];

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

  addToCart(part: PartDTO): void {
    if (!this.isBrowser) return;

    const wanted = part.selectedQuantity || 1;
    const cart: any[] = JSON.parse(localStorage.getItem('cart') || '[]');
    const existing = cart.find(i => i.partId === part.partId);

    const currentInCart = existing ? existing.quantity : 0;
    const newTotal = currentInCart + wanted;

    if (newTotal > part.leftOnStock) {
      // Nie można dodać więcej niż na stanie
      this.toastMessage = `Nie możesz dodać ${wanted} szt. "${part.partName}". Na stanie jest tylko ${part.leftOnStock - currentInCart}.`;
      this.showToast = true;
      setTimeout(() => this.showToast = false, 2000);
      return;
    }

    if (existing) {
      existing.quantity = newTotal;
    } else {
      cart.push({
        partId: part.partId,
        partName: part.partName,
        unitPrice: part.unitPrice,
        quantity: wanted
      });
    }

    localStorage.setItem('cart', JSON.stringify(cart));
    this.toastMessage = `Dodano ${wanted} × "${part.partName}" do koszyka.`;
    this.showToast = true;
    setTimeout(() => this.showToast = false, 2000);
  }
}
