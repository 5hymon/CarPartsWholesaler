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
  employeeId: number;
  customerId: number;
  orderDetails: OrderDetailsDTO[];
}
