import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedIn = new BehaviorSubject<boolean>(false);
  loginStatus$ = this.loggedIn.asObservable();

  private roleSubject = new BehaviorSubject<string>('');
  role$ = this.roleSubject.asObservable();

  private isBrowser: boolean;

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {
    this.isBrowser = isPlatformBrowser(this.platformId);

    if (this.isBrowser) {
      const user = localStorage.getItem('user');
      const role = localStorage.getItem('role');
      this.loggedIn.next(!!user);
      this.roleSubject.next(role ?? '');
    } else {
      this.loggedIn.next(false);
      this.roleSubject.next('');
    }
  }

  login(username: string, password: string): boolean {
    if (username === 'admin' && password === 'admin') {
      if (this.isBrowser) {
        localStorage.setItem('user', username);
        localStorage.setItem('role', 'admin');
      }
      this.loggedIn.next(true);
      this.roleSubject.next('admin');
      return true;
    }
    if (username === 'user' && password === 'user') {
      if (this.isBrowser) {
        localStorage.setItem('user', username);
        localStorage.setItem('role', 'user');
      }
      this.loggedIn.next(true);
      this.roleSubject.next('user');
      return true;
    }
    return false;
  }

  logout(): void {
    if (this.isBrowser) {
      localStorage.removeItem('user');
      localStorage.removeItem('role');
    }
    this.loggedIn.next(false);
    this.roleSubject.next('');
  }

  getRole(): string {
    return this.roleSubject.value;
  }
}
