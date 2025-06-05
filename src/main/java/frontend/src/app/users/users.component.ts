// src/app/users/users.component.ts

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }  from '@angular/forms';

import { UsersService } from '../services/user.service';
import { UserDTO }      from '../models/user-dto.model';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  // Pełna lista klientów pobrana z backendu
  users: UserDTO[] = [];
  // Lista widoczna w tabeli/kafelkach (po ewentualnym filtrowaniu – tu niewykorzystywane)
  filteredUsers: UserDTO[] = [];

  loading = false;
  errorMessage = '';

  // Dane dla formularza dodawania nowego klienta
  newUser: UserDTO = {
    firstName: '',
    lastName: '',
    emailAddress: '',
    phoneNumber: '',
    address: '',
    city: '',
    postalCode: '',
    country: ''
  };

  constructor(private usersService: UsersService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  private loadUsers(): void {
    this.loading = true;
    this.usersService.getAllUsers().subscribe({
      next: (data: UserDTO[]) => {
        this.users = data;
        this.filteredUsers = [...data];
        this.loading = false;
      },
      error: (err) => {
        console.error('Błąd przy pobieraniu klientów:', err);
        this.errorMessage = 'Nie udało się pobrać listy klientów.';
        this.loading = false;
      }
    });
  }

  addUser(): void {
    // Prosta walidacja: wszystkie pola muszą być wypełnione
    const u = this.newUser;
    if (!u.firstName.trim() ||
      !u.lastName.trim() ||
      !u.emailAddress.trim() ||
      !u.phoneNumber.trim() ||
      !u.address.trim() ||
      !u.city.trim() ||
      !u.postalCode.trim() ||
      !u.country.trim()) {
      alert('Wszystkie pola są wymagane.');
      return;
    }

    this.usersService.createUser(this.newUser).subscribe({
      next: (created: UserDTO) => {
        // Wyczyść formularz po dodaniu
        this.newUser = {
          firstName: '',
          lastName: '',
          emailAddress: '',
          phoneNumber: '',
          address: '',
          city: '',
          postalCode: '',
          country: ''
        };
        // Odśwież listę
        this.loadUsers();
      },
      error: (err) => {
        console.error('Błąd podczas dodawania klienta:', err);
        alert('Nie udało się dodać klienta.');
      }
    });
  }

  deleteUser(customerId: number): void {
    if (!confirm('Czy na pewno chcesz usunąć tego klienta?')) {
      return;
    }
    this.usersService.deleteUser(customerId).subscribe({
      next: () => this.loadUsers(),
      error: (err) => {
        console.error('Błąd podczas usuwania klienta:', err);
        alert('Nie udało się usunąć klienta.');
      }
    });
  }
}
