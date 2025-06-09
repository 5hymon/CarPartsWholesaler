import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../auth.service';

import { CustomerDTO } from '../models/customer-dto.model';

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  user: CustomerDTO | null = null;
  editedUser: CustomerDTO | null = null;
  loading = false;
  errorMessage = '';

  private baseUrl = 'http://localhost:8080/customers';

  constructor(private http: HttpClient, private authService: AuthService) {}

  ngOnInit(): void {
    const email = localStorage.getItem('email');
    if (!email) {
      this.errorMessage = 'Nie znaleziono e-maila w localStorage.';
      return;
    }

    this.loading = true;
    this.http.get<CustomerDTO>(`${this.baseUrl}/email/${email}`).subscribe({
      next: data => {
        this.user = data;
        this.loading = false;
      },
      error: err => {
        console.error('Błąd przy pobieraniu danych użytkownika:', err);
        this.errorMessage = 'Nie udało się pobrać danych.';
        this.loading = false;
      }
    });
  }

  startEdit(): void {
    if (this.user) {
      this.editedUser = { ...this.user };
    }
  }

  cancelEdit(): void {
    this.editedUser = null;
  }

  saveEdit(): void {
    if (!this.editedUser) return;

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
      `${this.baseUrl}/${this.editedUser.customerId}`,
      body.toString(),
      { headers }
    ).subscribe({
      next: res => {
        this.user = res;
        this.editedUser = null;
      },
      error: err => {
        console.error('Błąd podczas zapisu danych:', err);
        this.errorMessage = 'Aktualizacja nie powiodła się.';
      }
    });
  }
}
