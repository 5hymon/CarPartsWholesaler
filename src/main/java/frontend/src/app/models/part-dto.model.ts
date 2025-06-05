import {CarDTO} from './car-dto.model';

export interface PartDTO {
  partId?: number;
  partName: string;
  unitPrice: number;
  quantityPerUnit: string;
  leftOnStock: number;
  partDescription: string;
  available: boolean;
  compatibleParts?: CarDTO[];
}
