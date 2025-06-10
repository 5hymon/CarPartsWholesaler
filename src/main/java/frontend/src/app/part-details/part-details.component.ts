import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { PartsService } from '../services/parts.service';
import { PartDTO } from '../models/part-dto.model';

@Component({
  selector: 'app-part-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './part-details.component.html',
  styleUrls: ['./part-details.component.css']
})
export class PartDetailsComponent implements OnInit {
  part: PartDTO | null = null;
  loading = false;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private partsService: PartsService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) this.loadPart(id);
    else this.errorMessage = 'Niepoprawny identyfikator części.';
  }

  private loadPart(id: number) {
    this.loading = true;
    this.partsService.getPartById(id).subscribe({
      next: p => {
        this.part = p;
        this.loading = false;
      },
      error: _ => {
        this.errorMessage = 'Nie udało się pobrać szczegółów części.';
        this.loading = false;
      }
    });
  }
}
