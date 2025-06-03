import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  login() {
    const success = this.authService.login(this.username, this.password);
    if (success) {
      // Po zalogowaniu przekieruj na stronę główną
      this.router.navigate(['/']);
    } else {
      this.errorMessage = 'Nieprawidłowy login lub hasło!';
    }
  }
}
