import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';

import { CarsService } from '../services/cars.service';
import { CarDTO } from '../models/car-dto.model';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-cars',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
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

  editingCarId: number | null = null;
  editedCar: CarDTO | null = null;

  addingCar = false;
  newCar: CarDTO = {
    carId: 0,
    carMake: '',
    carModel: '',
    productionYears: '',
    bodyType: '',
    fuelType: '',
    engineType: '',
    compatibleParts: []
  };

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
}
