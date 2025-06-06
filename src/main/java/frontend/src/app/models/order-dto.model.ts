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
