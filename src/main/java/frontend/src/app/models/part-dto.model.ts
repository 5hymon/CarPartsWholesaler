import {CarDTO} from './car-dto.model';

export interface PartDTO {
  selectedQuantity?: number;
  partId: number;
  partName: string;
  unitPrice: number;
  quantityPerUnit: string;
  leftOnStock: number;
  isAvailable: boolean;
  partDescription: string;
  categoryName: string;
  compatibleCars?: CarDTO[];
}
