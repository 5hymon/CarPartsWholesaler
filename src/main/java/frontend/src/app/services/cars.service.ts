import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CarDTO } from '../models/car-dto.model';

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

  deleteCar(carId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${carId}`);
  }
}
