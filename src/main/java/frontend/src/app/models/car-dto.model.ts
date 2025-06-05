import {PartDTO} from './part-dto.model';

export interface CarDTO {
  carId: number;
  carMake: string;
  carModel: string;
  productionYears: string;
  bodyType: string;
  fuelType: string;
  engineType: string;
  compatibleParts: PartDTO[];
}
