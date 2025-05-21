import { Component, OnInit } from '@angular/core';

interface Part {
  name: string;
  manufacturer: string;
  price: number;
  image: string;
}

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {
  partsList: Part[] = [
    { name: 'Klocki hamulcowe', manufacturer: 'Brembo', price: 199, image: 'https://via.placeholder.com/300x200' },
    { name: 'Filtr powietrza', manufacturer: 'Mann', price: 49, image: 'https://via.placeholder.com/300x200' },
    // dodaj kolejne na sztywno lub później z serwisu
  ];

  constructor() {}

  ngOnInit(): void {}
}
