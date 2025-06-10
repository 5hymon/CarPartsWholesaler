import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PartDTO } from '../models/part-dto.model';

@Injectable({
  providedIn: 'root'
})
export class PartsService {
  private baseUrl = 'http://localhost:8080/parts';

  constructor(private http: HttpClient) {}

  getAllParts(): Observable<PartDTO[]> {
    return this.http.get<PartDTO[]>(`${this.baseUrl}/all`);
  }

  getPartById(id: number): Observable<PartDTO> {
    return this.http.get<PartDTO>(`${this.baseUrl}/${id}`);
  }

  deletePart(partId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${partId}`);
  }
}
