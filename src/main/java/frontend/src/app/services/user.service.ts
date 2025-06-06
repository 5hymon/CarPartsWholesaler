import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CustomerDTO } from '../models/customer-dto.model';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  // Zakładamy, że backend nasłuchuje na porcie 8080
  // i obsługuje ścieżki /customers
  private baseUrl = 'http://localhost:8080/customers';

  constructor(private http: HttpClient) { }

  /** GET /customers/all → pobiera wszystkich klientów */
  getAllUsers(): Observable<CustomerDTO[]> {
    return this.http.get<CustomerDTO[]>(`${this.baseUrl}/all`);
  }

  /** POST /customers → tworzy nowego klienta */
  createUser(user: CustomerDTO): Observable<CustomerDTO> {
    return this.http.post<CustomerDTO>(`${this.baseUrl}`, user);
  }

  /** DELETE /customers/{id} → usuwa klienta o podanym ID */
  deleteUser(customerId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${customerId}`);
  }
}
