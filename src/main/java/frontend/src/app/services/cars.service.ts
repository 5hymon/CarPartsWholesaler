// src/app/services/cars.service.ts
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { CarDTO } from '../models/car-dto.model';
import { tap, map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CarsService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/cars';

  /** Pobiera listę wszystkich samochodów jako CarDTO[] */
  getAllCars(): Observable<CarDTO[]> {
    return this.http.get<CarDTO[]>(`${this.baseUrl}/all`, { observe: 'response' })
      .pipe(
        tap(response => {
          console.log('Status:', response.status);
          console.log('Data:', response.body);
        }),
        map(response => response.body as CarDTO[]),
        catchError(err => {
          console.error('HTTP error:', err);
          return throwError(() => new Error('Błąd HTTP: ' + err.message));
        })
      );
  }

  /** Opcjonalnie: pobierz pojedynczy samochód po ID */
  getCarById(carId: number): Observable<CarDTO> {
    return this.http.get<CarDTO>(`${this.baseUrl}/${carId}`);
  }

  /** Opcjonalnie: dodaj nowy samochód (przy użyciu encji Car, nie CarDTO) */
  addCar(car: Partial<CarDTO>) {
    return this.http.post<CarDTO>(`${this.baseUrl}`, car);
  }

  /** Opcjonalnie: edytuj samochód */
  updateCar(carId: number, car: Partial<CarDTO>) {
    return this.http.put<CarDTO>(`${this.baseUrl}/${carId}`, car);
  }

  /** Opcjonalnie: usuń samochód */
  deleteCar(carId: number) {
    return this.http.delete<void>(`${this.baseUrl}/${carId}`);
  }
}
