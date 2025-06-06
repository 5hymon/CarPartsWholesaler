import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { CarDTO } from '../models/car-dto.model';
import { tap, map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CarsService {
  private baseUrl = 'http://localhost:8080/cars';

  constructor(private http: HttpClient) {}

  getAllCars(): Observable<CarDTO[]> {
    return this.http.get<CarDTO[]>(`${this.baseUrl}/all`);
  }

  getCarById(carId: number): Observable<CarDTO> {
    return this.http.get<CarDTO>(`${this.baseUrl}/${carId}`);
  }

  addCar(car: CarDTO): Observable<CarDTO> {
    return this.http.post<CarDTO>(`${this.baseUrl}`, car);
  }

  updateCar(carId: number, car: CarDTO): Observable<CarDTO> {
    return this.http.put<CarDTO>(`${this.baseUrl}/${carId}`, car);
  }

  deleteCar(carId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${carId}`);
  }
}
