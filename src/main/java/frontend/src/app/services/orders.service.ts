// src/app/services/orders.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderDTO } from '../models/order-dto.model';

@Injectable({
  providedIn: 'root',
})
export class OrdersService {
  private baseUrl = 'http://localhost:8080/orders';

  constructor(private http: HttpClient) {}

  /** Pobierz wszystkie zamówienia */
  getAllOrders(): Observable<OrderDTO[]> {
    return this.http.get<OrderDTO[]>(`${this.baseUrl}/all`);
  }

  /**
   * Zaktualizuj całe zamówienie (wysyłamy JSON Body).
   * Endpoint: PUT /orders/{orderId}
   * Przyjmuje pełny obiekt OrderDTO w JSON.
   */
  updateOrder(order: OrderDTO): Observable<OrderDTO> {
    return this.http.put<OrderDTO>(
      `${this.baseUrl}/${order.orderId}`,
      order
    );
  }
}
