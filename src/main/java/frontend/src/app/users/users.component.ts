import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';

import { CustomerDTO } from '../models/customer-dto.model';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users: CustomerDTO[] = [];
  loading = false;
  errorMessage = '';

  editingUserId: number | null = null;
  editedUser: CustomerDTO | null = null;

  addingUser = false;

  private baseUrl = 'http://localhost:8080/customers';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  private loadUsers(): void {
    this.loading = true;
    this.http.get<CustomerDTO[]>(`${this.baseUrl}/all`).subscribe({
      next: data => {
        this.users = data;
        this.loading = false;
      },
      error: err => {
        console.error('Błąd przy pobieraniu klientów:', err);
        this.errorMessage = 'Nie udało się pobrać listy klientów.';
        this.loading = false;
      }
    });
  }

  deleteUser(id: number): void {
    if (!confirm('Czy na pewno chcesz usunąć tego klienta?')) {
      return;
    }
    this.http.delete<void>(`${this.baseUrl}/${id}`).subscribe({
      next: () => this.loadUsers(),
      error: err => {
        console.error('Błąd podczas usuwania klienta:', err);
        alert('Nie udało się usunąć klienta.');
      }
    });
  }

  startEdit(user: CustomerDTO): void {
    this.editingUserId = user.customerId;
    this.editedUser = { ...user };
  }

  cancelEdit(): void {
    this.editingUserId = null;
    this.editedUser = null;
  }

  saveEdit(): void {
    if (!this.editedUser || this.editingUserId == null) {
      return;
    }

    const body = new HttpParams()
      .set('firstName', this.editedUser.firstName.trim())
      .set('lastName', this.editedUser.lastName.trim())
      .set('emailAddress', this.editedUser.emailAddress.trim())
      .set('phoneNumber', this.editedUser.phoneNumber.trim())
      .set('address', this.editedUser.address.trim())
      .set('city', this.editedUser.city.trim())
      .set('postalCode', this.editedUser.postalCode.trim())
      .set('country', this.editedUser.country.trim());

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    this.http.put<CustomerDTO>(
      `${this.baseUrl}/${this.editingUserId}`,
      body.toString(),
      { headers }
    ).subscribe({
      next: _ => {
        this.editingUserId = null;
        this.editedUser = null;
        this.loadUsers();
      },
      error: err => {
        console.error('Błąd podczas aktualizacji klienta:', err);
        this.editingUserId = null;
        this.editedUser = null;
        this.loadUsers();
      }
    });
  }
}
