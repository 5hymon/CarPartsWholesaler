import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

//import { PartsService } from '../services/parts.service';
import { PartDTO } from '../models/part-dto.model';

@Component({
  selector: 'app-parts-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './parts-list.component.html',
  styleUrls: ['./parts-list.component.css']
})
export class PartsListComponent{}

/*
export class PartsListComponent implements OnInit {
  // Pełna lista części pobrana z backendu
  parts: PartDTO[] = [];
  // Lista, którą faktycznie wyświetlamy (po filtrowaniu)
  filteredParts: PartDTO[] = [];

  loading = false;
  errorMessage = '';

  // Aktualnie wybrana kategoria lub kategoria i nazwa części
  activeCategory: string | null = null;
  activePartName: string | null = null;

  constructor(private partsService: PartsService) { }

  ngOnInit(): void {
    this.loadParts();
  }

  private loadParts(): void {
    this.loading = true;
    this.partsService.getAllParts().subscribe({
      next: (data: PartDTO[]) => {
        this.parts = data;
        this.filteredParts = [...data];
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Nie udało się pobrać listy części.';
        this.loading = false;
      }
    });
  }

  deletePart(id: number): void {
    if (!confirm('Czy na pewno chcesz usunąć tę część?')) {
      return;
    }
    this.partsService.deletePart(id).subscribe({
      next: () => this.loadParts(),
      error: (err) => {
        console.error(err);
        alert('Błąd podczas usuwania części.');
      }
    });
  }

  /!**
   * Grupuje listę parts według pola categoryName.
   * Zwraca tablicę obiektów: { category: string; parts: PartDTO[] }
   *!/
  groupByCategory(): { category: string; parts: PartDTO[] }[] {
    const map: Record<string, PartDTO[]> = {};
    this.parts.forEach(part => {
      const cat = part.categoryName;
      if (!map[cat]) {
        map[cat] = [];
      }
      map[cat].push(part);
    });
    return Object.keys(map).map(category => ({
      category,
      parts: map[category]
    }));
  }

  /!**
   * Kliknięcie w samą nazwę kategorii → pokazuje wszystkie części danej kategorii.
   *!/
  selectByCategory(category: string): void {
    if (this.activeCategory === category && this.activePartName === null) {
      // Resetuj filtr – pokaż wszystko
      this.filteredParts = [...this.parts];
      this.activeCategory = null;
      this.activePartName = null;
    } else {
      this.activeCategory = category;
      this.activePartName = null;
      this.filteredParts = this.parts.filter(part => part.categoryName === category);
    }
  }

  /!**
   * Kliknięcie w konkretną nazwę części w menu → filtr po kategorii + nazwie części.
   *!/
  selectCategoryPart(category: string, partName: string): void {
    if (this.activeCategory === category && this.activePartName === partName) {
      // Reset filtrów
      this.filteredParts = [...this.parts];
      this.activeCategory = null;
      this.activePartName = null;
    } else {
      this.activeCategory = category;
      this.activePartName = partName;
      this.filteredParts = this.parts.filter(part =>
        part.categoryName === category && part.partName === partName
      );
    }
  }
}
*/
