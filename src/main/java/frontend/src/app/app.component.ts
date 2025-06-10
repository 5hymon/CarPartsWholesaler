import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, RouterLink, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  isLoggedIn = false;
  role: 'user' | 'admin' | '' = '';
  showUserMenu = false;
  title: 'frontend' | undefined;

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit() {
    this.auth.isLoggedIn$.subscribe(v => this.isLoggedIn = v);
    this.auth.role$.subscribe(r => this.role = r as any);
  }

  toggleUserMenu() { this.showUserMenu = !this.showUserMenu; }
  logout() { this.auth.logout(); this.showUserMenu = false; this.router.navigate(['/main']) }
}
