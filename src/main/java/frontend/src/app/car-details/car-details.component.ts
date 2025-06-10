import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarsService } from '../services/cars.service';
import { CarDTO } from '../models/car-dto.model';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute} from '@angular/router';
import { isPlatformBrowser } from '@angular/common';

import { PartDTO } from '../models/part-dto.model';

@Component({
  selector: 'app-car-details',
  imports: [CommonModule, FormsModule],
  templateUrl: './car-details.component.html',
  styleUrl: './car-details.component.css'
})
export class CarDetailsComponent {
  car: CarDTO | null = null;
  loading = false;
  errorMessage = '';

  part: PartDTO | null = null;

  parts: PartDTO[] = [];

  showToast = false;
  toastMessage = '';

  isBrowser: boolean;
  userRole: string = '';

  constructor(
    @Inject(PLATFORM_ID) platformId: Object,
    private route: ActivatedRoute,
    private http: HttpClient,
    private carsService: CarsService
  ) {
    this.isBrowser = isPlatformBrowser(platformId);
    if (this.isBrowser) {
      this.userRole = localStorage.getItem('role') || '';
    }
  }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) this.loadCar(id);
    else this.errorMessage = 'Niepoprawny identyfikator samochodu.';
  }

  private loadCar(id: number) {
    this.loading = true;
    this.carsService.getCarById(id).subscribe({
      next: p => {
        this.car = p;
        this.loading = false;
      },
      error: _ => {
        this.errorMessage = 'Nie udało się pobrać szczegółów samochodu.';
        this.loading = false;
      }
    });
  }

  addToCart(part: PartDTO): void {
    if (!this.isBrowser) return;

    const wanted = part.selectedQuantity || 1;
    const cart: any[] = JSON.parse(localStorage.getItem('cart') || '[]');
    const existing = cart.find(i => i.partId === part.partId);

    const currentInCart = existing ? existing.quantity : 0;
    const newTotal = currentInCart + wanted;

    if (newTotal > part.leftOnStock) {
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
