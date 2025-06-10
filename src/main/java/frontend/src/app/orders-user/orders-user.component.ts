import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {OrdersService} from '../services/orders.service';
import {OrderDTO} from '../models/order-dto.model';

interface OrderWithView extends OrderDTO {
  showDetails: boolean;
}

@Component({
  selector: 'app-orders-user',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './orders-user.component.html',
  styleUrls: ['./orders-user.component.css']
})
export class OrdersUserComponent implements OnInit {
  orders: OrderWithView[] = [];
  loading = false;
  errorMessage = '';

  constructor(private ordersService: OrdersService) {
  }

  ngOnInit(): void {
    this.loadUserOrders();
  }

  private loadUserOrders(): void {
    const email = localStorage.getItem('email');
    if (!email) {
      this.errorMessage = 'Nie jesteś zalogowany.';
      return;
    }

    this.loading = true;
    this.ordersService.getOrdersByUser(email).subscribe({
      next: (data: OrderDTO[]) => {
        this.orders = data.map(o => ({
          ...o,
          showDetails: false
        }));
        this.loading = false;
      },
      error: err => {
        console.error('Błąd przy pobieraniu zamówień:', err);
        this.errorMessage = 'Nie udało się pobrać Twoich zamówień.';
        this.loading = false;
      }
    });
  }

  toggleDetails(order: OrderWithView): void {
    order.showDetails = !order.showDetails;
  }
}
