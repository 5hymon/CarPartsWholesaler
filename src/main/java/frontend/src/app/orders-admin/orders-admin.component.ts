import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { OrdersService } from '../services/orders.service';
import { OrderDTO, OrderDetailsDTO } from '../models/order-dto.model';
import {HttpHeaders, HttpParams} from '@angular/common/http';

interface OrderWithFlags extends OrderDTO {
  isEditing: boolean;
  showDetails: boolean;
  editableStatus: string;
  editablePayment: string;
  editableDetails: OrderDetailsDTO[];
}

@Component({
  selector: 'app-orders-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './orders-admin.component.html',
  styleUrls: ['./orders-admin.component.css']
})
export class OrdersAdminComponent implements OnInit {
  public orders: OrderWithFlags[] = [];
  public loading = false;
  public errorMessage = '';

  constructor(private ordersService: OrdersService) { }

  ngOnInit(): void {
    this.loadOrders();
  }

  calculateTotalValue(order: OrderDTO): number {
    return order.orderDetails.reduce((total, detail) => {
      const itemValue = detail.quantity * detail.partUnitPrice * (1 - detail.discount);
      return total + itemValue;
    }, 0);
  }

  private loadOrders(): void {
    this.loading = true;
    this.ordersService.getAllOrders().subscribe({
      next: (data: OrderDTO[]) => {
        this.orders = data.map(o => ({
          ...o,
          isEditing: false,
          showDetails: false,
          editableStatus: o.orderStatus,
          editablePayment: o.paymentMethod,
          editableDetails: o.orderDetails.map(d => ({ ...d }))
        }));
        this.loading = false;
      },
      error: err => {
        console.error('Błąd przy pobieraniu zamówień:', err);
        this.errorMessage = 'Nie udało się pobrać listy zamówień.';
        this.loading = false;
      }
    });
  }

  startEdit(order: OrderWithFlags): void {
    order.isEditing = true;
    order.editableStatus = order.orderStatus;
    order.editablePayment = order.paymentMethod;
    order.editableDetails = order.orderDetails.map(d => ({ ...d }));
  }

  cancelEdit(order: OrderWithFlags): void {
    order.isEditing = false;
  }

  saveEdit(order: OrderWithFlags): void {
    let params = new HttpParams()
      .set('orderStatus', order.editableStatus)
      .set('paymentMethod', order.editablePayment);

    order.editableDetails.forEach(d => {
      params = params
        .append('partId', d.partId.toString())
        .append('quantity', d.quantity.toString())
        .append('discount', d.discount.toString());
    });

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    this.ordersService.updateOrderFormUrlEncoded(order.orderId, params.toString(), headers)
      .subscribe({
        next: updated => {
          order.orderStatus = updated.orderStatus;
          order.paymentMethod = updated.paymentMethod;
          order.orderDetails = updated.orderDetails.map(d => ({ ...d }));
          order.isEditing = false;
        }
      });
  }

  toggleDetails(order: OrderWithFlags): void {
    order.showDetails = !order.showDetails;
  }
}
