// src/app/cars/cars.component.ts

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';

import { CarsService } from '../services/cars.service';
import { CarDTO } from '../models/car-dto.model';

@Component({
  selector: 'app-cars',
  standalone: true,
  imports: [CommonModule, FormsModule],
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

  // Używane do edycji
  editingCarId: number | null = null;
  editedCar: CarDTO | null = null;

  constructor(private carService: CarsService, private http: HttpClient) { }

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
        console.error('Błąd przy pobieraniu samochodów:', err);
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
        console.error('Błąd podczas usuwania samochodu:', err);
        alert('Nie udało się usunąć samochodu.');
      }
    });
  }

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

  selectByMake(make: string): void {
    if (this.activeMake === make && this.activeModel === null) {
      this.filteredCars = [...this.cars];
      this.activeMake = null;
      this.activeModel = null;
    } else {
      this.activeMake = make;
      this.activeModel = null;
      this.filteredCars = this.cars.filter(car => car.carMake === make);
    }
  }

  selectMakeModel(make: string, model: string): void {
    if (this.activeMake === make && this.activeModel === model) {
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

  /** Rozpoczynamy edycję: ustawiamy editedCar na kopię obiektu */
  startEdit(car: CarDTO): void {
    this.editingCarId = car.carId!;
    // Płytka kopia – żeby formularz nie nadpisywał od razu oryginału
    this.editedCar = { ...car };
  }

  /** Anulujemy edycję i ukrywamy formularz */
  cancelEdit(): void {
    this.editingCarId = null;
    this.editedCar = null;
  }

  /** Zapisujemy zmiany: wysyłamy PUT, a po sukcesie odświeżamy listę */
  saveEdit(): void {
    if (!this.editedCar || this.editingCarId == null) {
      return;
    }
    // Budujemy body jako x-www-form-urlencoded
    let body = new HttpParams()
      .set('carMake', this.editedCar.carMake)
      .set('carModel', this.editedCar.carModel)
      .set('productionYears', this.editedCar.productionYears)
      .set('bodyType', this.editedCar.bodyType)
      .set('fuelType', this.editedCar.fuelType)
      .set('engineType', this.editedCar.engineType);

    // Ustawiamy nagłówek
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    // Wysyłamy PUT do Springa
    this.http.put<CarDTO>(
      `http://localhost:8080/cars/${this.editingCarId}`,
      body.toString(),
      { headers }
    ).subscribe({
      next: updated => {
        // Po sukcesie odświeżamy listę i zamykamy formularz
        this.editingCarId = null;
        this.editedCar = null;
        this.loadCars();
      },
      error: err => {
        console.error('Błąd przy aktualizacji:', err);
        alert('Nie udało się zaktualizować.');
      }
    });
  }
}
