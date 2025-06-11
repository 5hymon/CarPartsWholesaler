export interface PartCompatibilityDTO {
  compatibilityId: number;
  partId: number;
  carId: number;
  carMake: string;
  carModel: string;
  productionYears: string;
  bodyType: string;
  fuelType: string;
  engineType: string;
  partName: string;
  unitPrice: number;
  available: boolean;
}
