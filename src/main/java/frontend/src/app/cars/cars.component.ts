// src/app/cars/cars.component.ts

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CarsService } from '../services/cars.service';
import { CarDTO } from '../models/car-dto.model';

@Component({
  selector: 'app-cars',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cars.component.html',
  styleUrls: ['./cars.component.css']
})
export class CarsComponent implements OnInit {
  cars: CarDTO[] = [];
  filteredCars: CarDTO[] = [];

  loading = false;
  errorMessage = '';

  activeMake: string | null = null;
  activeModel: string | null = null;

  constructor(private carService: CarsService) { }

  ngOnInit(): void {
    this.loadCars();
  }

  private loadCars(): void {
    this.loading = true;
    this.carService.getAllCars().subscribe({
      next: (data: CarDTO[]) => {
        this.cars = data;
        this.filteredCars = [...data];
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Nie udało się pobrać listy samochodów.';
        this.loading = false;
      }
    });
  }

  deleteCar(id: number): void {
    if (!confirm('Czy na pewno chcesz usunąć ten samochód?')) {
      return;
    }
    this.carService.deleteCar(id).subscribe({
      next: () => this.loadCars(),
      error: (err) => {
        console.error(err);
        alert('Błąd podczas usuwania samochodu.');
      }
    });
  }

  /**
   * Grupuje listę cars według pola carMake.
   */
  groupByMake(): { make: string; cars: CarDTO[] }[] {
    const map: Record<string, CarDTO[]> = {};
    this.cars.forEach(car => {
      const make = car.carMake;
      if (!map[make]) {
        map[make] = [];
      }
      map[make].push(car);
    });
    return Object.keys(map).map(make => ({
      make,
      cars: map[make]
    }));
  }

  /**
   * Kliknięcie w markę — filtr po samej marce.
   */
  selectByMake(make: string): void {
    if (this.activeMake === make && this.activeModel === null) {
      // Jeśli kliknięto tę samą markę ponownie, reset
      this.filteredCars = [...this.cars];
      this.activeMake = null;
      this.activeModel = null;
    } else {
      this.activeMake = make;
      this.activeModel = null;
      this.filteredCars = this.cars.filter(car => car.carMake === make);
    }
  }

  /**
   * Kliknięcie w model — filtr po marce i modelu.
   */
  selectMakeModel(make: string, model: string): void {
    if (this.activeMake === make && this.activeModel === model) {
      // Reset filtrów
      this.filteredCars = [...this.cars];
      this.activeMake = null;
      this.activeModel = null;
    } else {
      this.activeMake = make;
      this.activeModel = model;
      this.filteredCars = this.cars.filter(car =>
        car.carMake === make && car.carModel === model
      );
    }
  }
}
