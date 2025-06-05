// src/app/cars/cars.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarsService } from '../services/cars.service';
import { CarDTO } from '../models/car-dto.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-cars',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cars.component.html',
  styleUrls: ['./cars.component.css']
})
export class CarsComponent implements OnInit {
  cars: CarDTO[] = [];
  loading = false;
  error: string | null = null;

  constructor(private carsService: CarsService) { }

  ngOnInit(): void {
    this.loading = true;
    this.carsService.getAllCars().subscribe({
      next: (data: CarDTO[]) => {
        this.cars = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Błąd przy pobieraniu samochodów:', err);
        this.error = 'Nie udało się pobrać danych.';
        this.loading = false;
      }
    });
  }
}


