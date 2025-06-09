package com.wholesaler.backend.service;

import com.wholesaler.backend.dto.OrderDTO;
import com.wholesaler.backend.dto.OrderDetailDTO;
import com.wholesaler.backend.model.Customer;
import com.wholesaler.backend.model.Employee;
import com.wholesaler.backend.model.Part;
import com.wholesaler.backend.model.Order;
import com.wholesaler.backend.model.OrderDetail;
import com.wholesaler.backend.repository.OrderRepository;
import com.wholesaler.backend.repository.EmployeeRepository;
import com.wholesaler.backend.repository.CustomerRepository;
import com.wholesaler.backend.repository.PartRepository;
import com.wholesaler.backend.repository.OrderDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final PartRepository partRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public OrderService(OrderRepository orderRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository, PartRepository partRepository, OrderDetailsRepository orderDetailsRepository) {
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.partRepository = partRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public OrderDTO convertOrderToDTO(Order order) {
        List<OrderDetailDTO> detailsDTO = order.getOrderDetails().stream()
                .map(this::convertOrderDetailToDTO)
                .collect(Collectors.toList());

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setPaymentMethod(order.getPaymentMethod());
        orderDTO.setCustomerId(order.getCustomer().getCustomerId());
        orderDTO.setEmployeeId(order.getEmployee().getEmployeeId());
        orderDTO.setOrderDetails(detailsDTO);

        return orderDTO;
    }

    private OrderDetailDTO convertOrderDetailToDTO(OrderDetail detail) {
        Optional<Part> partOptional = partRepository.findById(detail.getPartId());
        if (partOptional.isPresent()) {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setDetailId(detail.getDetailId());
            orderDetailDTO.setOrderId(detail.getOrderId());
            orderDetailDTO.setPartId(detail.getPartId());
            orderDetailDTO.setPartName(partOptional.get().getPartName());
            orderDetailDTO.setPartDescription(partOptional.get().getPartDescription());
            orderDetailDTO.setPartUnitPrice(partOptional.get().getUnitPrice());
            orderDetailDTO.setQuantity(detail.getQuantity());
            orderDetailDTO.setDiscount(detail.getDiscount());
            orderDetailDTO.setOrderValue(orderDetailDTO.getOrderValue());

            return orderDetailDTO;
        }
        return null;
    }

    // get all
    public List<OrderDTO> getAllOrdersAsDTO() {
        return orderRepository.findAll().stream()
                .map(this::convertOrderToDTO)
                .collect(Collectors.toList());
    }

    // get mapped by id
    public Optional<OrderDTO> getOrderByIdAsDTO(Integer id) {
        return orderRepository.findById(id).map(this::convertOrderToDTO);
    }

    public List<OrderDTO> getOrdersByEmailAsDTO(String email) {
        List<Order> orders = orderRepository.findByCustomerEmail(email);
        return orders.stream()
                .map(this::convertOrderToDTO)
                .collect(Collectors.toList());
    }

    // post new order
    public Order addOrder(String customerEmailAddress, String orderStatus, String paymentMethod, List<Integer> partsId, List<Integer> quantities, Double discount) {
        Random random = new Random();
        Integer employeesCount = employeeRepository.getLastEmployeeId();
        Integer randomEmployeeId = random.nextInt(employeesCount - 1) + 1;

        Optional<Employee> optionalEmployee = employeeRepository.findById(randomEmployeeId);
        Optional<Customer> optionalCustomer = customerRepository.findByEmailAddress(customerEmailAddress);

        if (optionalEmployee.isPresent() && optionalCustomer.isPresent() && partsId.size() == quantities.size()) {
            Employee employee = optionalEmployee.get();
            Customer customer = optionalCustomer.get();

            Order order = new Order();
            order.setEmployee(employee);
            order.setCustomer(customer);
            order.setOrderDate(new Date());
            order.setOrderStatus(orderStatus);
            order.setPaymentMethod(paymentMethod);

            List<OrderDetail> orderDetails = new ArrayList<>();
            for (Integer i : partsId) {
                Optional<Part> optionalPart = partRepository.findById(i);
                if (optionalPart.isPresent()) {
                    Part part = optionalPart.get();
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderId(orderRepository.getLastOrderId() + 1);
                    orderDetail.setPartId(part.getPartId());
                    orderDetail.setUnitPrice(part.getUnitPrice());
                    orderDetail.setQuantity(quantities.get(i));
                    orderDetail.setDiscount(discount);
                    orderDetails.add(orderDetail);
                }
            }
            order.setOrderDetails(orderDetails);

            return orderRepository.save(order);
        } else {
            return null;
        }
    }

    // post updated order
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // put
    public Optional<Order> updateOrder(Integer id, String orderStatus, String paymentMethod, Integer quantity, Double discount) {
        Optional<OrderDetail> optionalOrderDetail = orderDetailsRepository.findById(id);
        if (optionalOrderDetail.isPresent()) {
            OrderDetail orderDetail = optionalOrderDetail.get();

            return orderRepository.findById(id).map(order -> {
                order.setOrderStatus(orderStatus);
                order.setPaymentMethod(paymentMethod);
                orderDetail.setQuantity(quantity);
                orderDetail.setDiscount(discount);
                return orderRepository.save(order);
            });
        } else {
            return Optional.empty();
        }
    }

    // delete
    public void deleteOrder(Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        }
    }
}
