import {CarDTO} from './car-dto.model';

export interface PartDTO {
  partId: number;
  partName: string;
  unitPrice: number;
  quantityPerUnit: string;
  leftOnStock: number;
  available: boolean;
  partDescription: string;
  categoryName: string;
  compatibleParts?: CarDTO[];
}
