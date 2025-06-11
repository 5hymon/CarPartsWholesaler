import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CarDTO } from '../models/car-dto.model';
import { PartCompatibilityDTO } from '../models/part-compatibility-dto.model';

@Injectable({ providedIn: 'root' })
export class PartCompatibilityService {
  private readonly baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getAllCars(): Observable<CarDTO[]> {
    return this.http.get<CarDTO[]>(`${this.baseUrl}/cars/all`);
  }

  addCompatibility(carId: number, partId: number): Observable<PartCompatibilityDTO> {
    const body = new HttpParams()
      .set('carId', carId.toString())
      .set('partId', partId.toString());
    const headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });
    return this.http.post<PartCompatibilityDTO>(
      `${this.baseUrl}/compatibilities`,
      body.toString(),
      { headers }
    );
  }
}
