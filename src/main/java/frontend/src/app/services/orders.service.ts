import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderDTO } from '../models/order-dto.model';

@Injectable({
  providedIn: 'root',
})
export class OrdersService {
  private baseUrl = 'http://localhost:8080/orders';

  constructor(private http: HttpClient) {}

  getAllOrders(): Observable<OrderDTO[]> {
    return this.http.get<OrderDTO[]>(`${this.baseUrl}/all`);
  }

  getOrdersByUser(email: string): Observable<OrderDTO[]> {
    return this.http.get<OrderDTO[]>(`${this.baseUrl}/byemail/${email}`);
  }

  updateOrderFormUrlEncoded(
    orderId: number,
    body: string,
    headers: HttpHeaders
  ): Observable<OrderDTO> {
    return this.http.put<OrderDTO>(
      `${this.baseUrl}/${orderId}`,
      body,
      { headers }
    );
  }
}
