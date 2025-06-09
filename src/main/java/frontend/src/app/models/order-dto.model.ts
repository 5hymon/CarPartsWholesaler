/*
import {EmployeeDTO} from './employee-dto.model';
import {CustomerDTO} from './customer-dto.model';
import {OrderDetailsDTO} from './order-details-dto.model';

export interface OrderDTO {
  orderId: number;
  employee: EmployeeDTO;
  customer: CustomerDTO;
  orderDate: string;
  orderStatus: string;
  paymentMethod: string;
  orderDetails: OrderDetailsDTO[];
}
*/

// src/app/models/order-dto.model.ts

export interface OrderDetailsDTO {
  partId: number;
  partName: string;
  partDescription: string;
  partUnitPrice: number;
  quantity: number;
  discount: number;
  orderValue: number;
}

export interface OrderDTO {
  orderId: number;
  orderDate: string;
  orderStatus: string;
  paymentMethod: string;
  employeeId: number;   // zamiast obiektu EmployeeDTO
  customerId: number;   // zamiast obiektu CustomerDTO
  orderDetails: OrderDetailsDTO[];
}
