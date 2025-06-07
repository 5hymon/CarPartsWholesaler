import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  model = {
    firstName: '',
    lastName: '',
    emailAddress: '',
    password: '',
    phoneNumber: '',
    address: '',
    city: '',
    postalCode: '',
    country: ''
  };
  errorMessage = '';

  constructor(private http: HttpClient, private router: Router) {}

  register(form: NgForm) {
    if (form.invalid) {
      this.errorMessage = 'Proszę wypełnić wszystkie pola poprawnie.';
      return;
    }
    this.errorMessage = '';
    const params = {
      firstName: this.model.firstName,
      lastName: this.model.lastName,
      emailAddress: this.model.emailAddress,
      password: this.model.password,
      phoneNumber: this.model.phoneNumber,
      address: this.model.address,
      city: this.model.city,
      postalCode: this.model.postalCode,
      country: this.model.country,
    };

    this.http.post('http://localhost:8080/customers', null, { params })
        .subscribe({
          next: () => this.router.navigate(['/login']),
          error: () => this.errorMessage = 'Rejestracja nie powiodła się.'
        });
  }
}
