import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedIn = new BehaviorSubject<boolean>(false);
  loginStatus$ = this.loggedIn.asObservable();

  private isBrowser: boolean;

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {
    // Sprawdźmy, czy działamy w przeglądarce:
    this.isBrowser = isPlatformBrowser(this.platformId);

    if (this.isBrowser) {
      const user = localStorage.getItem('user');
      this.loggedIn.next(!!user);
    } else {
      // Gdy np. w trakcie SSR, zawsze ustawiamy false
      this.loggedIn.next(false);
    }
  }

  login(username: string, password: string): boolean {
    if (username === 'admin' && password === 'admin') {
      if (this.isBrowser) {
        localStorage.setItem('user', username);
      }
      this.loggedIn.next(true);
      return true;
    }
    return false;
  }

  logout(): void {
    if (this.isBrowser) {
      localStorage.removeItem('user');
    }
    this.loggedIn.next(false);
  }
}
