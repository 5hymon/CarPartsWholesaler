// src/app/services/parts.service.ts

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

  /** GET /parts/all → zwraca listę PartDTO */
  getAllParts(): Observable<PartDTO[]> {
    return this.http.get<PartDTO[]>(`${this.baseUrl}/all`);
  }

  /** DELETE /parts/{id} → usuwa część o podanym ID */
  deletePart(partId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${partId}`);
  }

  // Jeśli kiedykolwiek potrzebujesz GET /parts/{id}, POST czy PUT, możesz je dodać:
  // getPartById(id: number): Observable<PartDTO> { ... }
  // createPart(part: PartDTO): Observable<PartDTO> { ... }
  // updatePart(id: number, part: PartDTO): Observable<PartDTO> { ... }
}
