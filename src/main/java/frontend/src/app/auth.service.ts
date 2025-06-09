import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { BehaviorSubject, catchError, map, throwError } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private loggedIn$ = new BehaviorSubject<boolean>(false);
  role$ = new BehaviorSubject<'user' | 'admin' | ''>('');

  private isBrowser: boolean;

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(platformId);

    if (this.isBrowser) {
      const storedRole = localStorage.getItem('role') as ('user'|'admin'|null);
      if (storedRole) {
        this.loggedIn$.next(true);
        this.role$.next(storedRole);
      }
    }
  }

  /** Obserwable czy zalogowany */
  get isLoggedIn$() {
    return this.loggedIn$.asObservable();
  }

  /** Logowanie: zwraca Observable<void> lub błąd */
  login(email: string, password: string) {
    const params = new HttpParams()
      .set('emailAddress', email)
      .set('password', password);

    return this.http
      .get<number>('http://localhost:8080/login/all', {
        params
      })
      .pipe(
        map(res => {
          console.log('Odpowiedź z backendu:', res); // 👈 DODAJ TO

          const code = Number(res);
          if (code === 1 || code === 2) {
            const role = code === 1 ? 'user' : 'admin';
            if (this.isBrowser) {
              localStorage.setItem('role', role);
            }
            this.role$.next(role);
            this.loggedIn$.next(true);
          } else {
            throw new Error('Nieprawidłowy email lub hasło');
          }
        }),
        catchError(err => throwError(() => err))
      );
  }

  logout() {
    if (this.isBrowser) {
      localStorage.removeItem('role');
    }
    this.role$.next('');
    this.loggedIn$.next(false);
  }
}

