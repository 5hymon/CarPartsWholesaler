import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="orders-container">
      <h2>Twoje zamówienia</h2>
      <p>– tutaj będzie lista Twoich zamówień (na razie pusto)</p>
    </div>
  `,
  styles: [`
    .orders-container {
      max-width: 600px;
      margin: 50px auto;
      text-align: center;
    }
  `]
})
export class OrdersComponent {}
