import { Component } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';
import {CarDTO} from '../models/car-dto.model';
import {CarsService} from '../services/cars.service';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

@Component({
  selector: 'app-garage',
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './garage.component.html',
  styleUrl: './garage.component.css'
})
export class GarageComponent {
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

  startEdit(car: CarDTO): void {
    this.editingCarId = car.carId!;
    this.editedCar = { ...car };
  }

  cancelEdit(): void {
    this.editingCarId = null;
    this.editedCar = null;
  }

  saveEdit(): void {
    if (!this.editedCar || this.editingCarId == null) {
      return;
    }
    let body = new HttpParams()
      .set('carMake', this.editedCar.carMake)
      .set('carModel', this.editedCar.carModel)
      .set('productionYears', this.editedCar.productionYears)
      .set('bodyType', this.editedCar.bodyType)
      .set('fuelType', this.editedCar.fuelType)
      .set('engineType', this.editedCar.engineType);

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    this.http.put<CarDTO>(
      `http://localhost:8080/cars/${this.editingCarId}`,
      body.toString(),
      { headers }
    ).subscribe({
      next: updated => {
        this.editingCarId = null;
        this.editedCar = null;
        this.loadCars();
      },
      error: err => {
        console.error('Błąd przy aktualizacji:', err);
        this.editingCarId = null;
        this.editedCar = null;
        this.loadCars();
      }
    });
  }

  startAdd(): void {
    this.addingCar = true;
    this.newCar = {
      carId: 0,
      carMake: '',
      carModel: '',
      productionYears: '',
      bodyType: '',
      fuelType: '',
      engineType: '',
      compatibleParts: []
    };
  }

  cancelAdd(): void {
    this.addingCar = false;
  }

  saveAdd(): void {
    if (!this.newCar.carMake ||
      !this.newCar.carModel ||
      !this.newCar.productionYears ||
      !this.newCar.bodyType ||
      !this.newCar.fuelType ||
      !this.newCar.engineType) {
      return;
    }

    const body = new HttpParams()
      .set('carMake', this.newCar.carMake)
      .set('carModel', this.newCar.carModel)
      .set('productionYears', this.newCar.productionYears)
      .set('bodyType', this.newCar.bodyType)
      .set('fuelType', this.newCar.fuelType)
      .set('engineType', this.newCar.engineType);

    const headers = new HttpHeaders({
    });

    this.http.post<CarDTO>(
      'http://localhost:8080/cars',
      body.toString(),
      { headers }
    ).subscribe({
      next: created => {
        this.addingCar = false;
        this.loadCars();
      },
      error: err => {
        console.error('Błąd przy tworzeniu nowego samochodu:', err);
        alert('Nie udało się dodać nowego samochodu.');
      }
    });
  }
}
