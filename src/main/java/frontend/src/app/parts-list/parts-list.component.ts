import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

interface Part {
  name: string;
  manufacturer: string;
  price: number;
  image: string;
}

@Component({
  selector: 'app-parts-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './parts-list.component.html',
  styleUrl: './parts-list.component.css'
})
export class PartsListComponent implements OnInit {
  partsList: Part[] = [
    { name: 'Klocki hamulcowe', manufacturer: 'Brembo', price: 199, image: 'https://via.placeholder.com/300x200' },
    { name: 'Filtr powietrza', manufacturer: 'Mann', price: 49, image: 'https://via.placeholder.com/300x200' },
    // dodaj kolejne na sztywno lub później z serwisu
  ];

  constructor() {}

  ngOnInit(): void {}
}
