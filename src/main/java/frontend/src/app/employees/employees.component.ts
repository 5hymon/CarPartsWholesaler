import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';

export interface EmployeeDTO {
  employeeId: number;
  firstName: string;
  lastName: string;
  password: string;
  phoneNumber: string;
  address: string;
  city: string;
  postalCode: string;
  country: string;
}

@Component({
  selector: 'app-employees',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.css']
})
export class EmployeesComponent implements OnInit {
  employees: EmployeeDTO[] = [];
  loading = false;
  errorMessage = '';

  // Edycja
  editingEmployeeId: number | null = null;
  editedEmployee: EmployeeDTO | null = null;

  // Dodawanie
  addingEmployee = false;
  newEmployee: EmployeeDTO = {
    employeeId: 0,
    firstName: '',
    lastName: '',
    password: '',
    phoneNumber: '',
    address: '',
    city: '',
    postalCode: '',
    country: ''
  };

  private readonly baseUrl = 'http://localhost:8080/employees';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  private loadEmployees(): void {
    this.loading = true;
    this.http.get<EmployeeDTO[]>(`${this.baseUrl}/all`).subscribe({
      next: (data) => {
        this.employees = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Nie udało się pobrać pracowników.';
        this.loading = false;
      }
    });
  }

  deleteEmployee(id: number): void {
    if (!confirm('Czy na pewno chcesz usunąć tego pracownika?')) {
      return;
    }
    this.http.delete<void>(`${this.baseUrl}/${id}`).subscribe({
      next: () => this.loadEmployees(),
      error: (err) => {
        console.error(err);
        alert('Nie udało się usunąć pracownika.');
      }
    });
  }

  startEdit(emp: EmployeeDTO): void {
    this.editingEmployeeId = emp.employeeId!;
    this.editedEmployee = { ...emp };
  }

  cancelEdit(): void {
    this.editingEmployeeId = null;
    this.editedEmployee = null;
  }

  saveEdit(): void {
    if (!this.editedEmployee || this.editingEmployeeId == null) {
      return;
    }

    // Tworzymy body w formacie application/x-www-form-urlencoded
    const body = new HttpParams()
      .set('firstName', this.editedEmployee.firstName)
      .set('lastName', this.editedEmployee.lastName)
      .set('phoneNumber', this.editedEmployee.phoneNumber)
      .set('address', this.editedEmployee.address)
      .set('city', this.editedEmployee.city)
      .set('postalCode', this.editedEmployee.postalCode)
      .set('country', this.editedEmployee.country);

    const headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });

    this.http.put<EmployeeDTO>(
      `${this.baseUrl}/${this.editingEmployeeId}`,
      body.toString(),
      { headers }
    ).subscribe({
      next: () => {
        this.editingEmployeeId = null;
        this.editedEmployee = null;
        this.loadEmployees();
      },
      error: (err) => {
        console.error(err);
        alert('Nie udało się zaktualizować pracownika.');
        this.editingEmployeeId = null;
        this.editedEmployee = null;
      }
    });
  }

  startAdd(): void {
    this.addingEmployee = true;
    this.newEmployee = {
      employeeId: 0,
      firstName: '',
      lastName: '',
      password: '',
      phoneNumber: '',
      address: '',
      city: '',
      postalCode: '',
      country: ''
    };
  }

  cancelAdd(): void {
    this.addingEmployee = false;
  }

  saveAdd(): void {
    // Walidacja wymaganych pól
    if (
      !this.newEmployee.firstName ||
      !this.newEmployee.lastName ||
      !this.newEmployee.password ||
      !this.newEmployee.phoneNumber ||
      !this.newEmployee.address ||
      !this.newEmployee.city ||
      !this.newEmployee.postalCode ||
      !this.newEmployee.country
    ) {
      return;
    }

    const body = new HttpParams()
      .set('firstName', this.newEmployee.firstName)
      .set('lastName', this.newEmployee.lastName)
      .set('password', this.newEmployee.password)
      .set('phoneNumber', this.newEmployee.phoneNumber)
      .set('address', this.newEmployee.address)
      .set('city', this.newEmployee.city)
      .set('postalCode', this.newEmployee.postalCode)
      .set('country', this.newEmployee.country);

    const headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });

    this.http.post<EmployeeDTO>(
      `${this.baseUrl}`,
      body.toString(),
      { headers }
    ).subscribe({
      next: () => {
        this.addingEmployee = false;
        this.loadEmployees();
      },
      error: (err) => {
        console.error(err);
        alert('Nie udało się dodać pracownika.');
      }
    });
  }
}
