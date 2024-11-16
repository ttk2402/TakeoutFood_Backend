package com.kientran.order_service.service.impl;

import com.kientran.order_service.dto.*;
import com.kientran.order_service.entity.*;
import com.kientran.order_service.exception.ResourceNotFoundException;
import com.kientran.order_service.repository.*;
import com.kientran.order_service.service.BillService;
import com.kientran.order_service.service.ItemOrderedService;
import com.kientran.order_service.service.OrderService;
import com.kientran.order_service.webclient.ResItemDto;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final DeliveryInformationRepository deliveryInfoRepository;
    private final CheckoutRepository checkOutRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderRepository orderRepository;
    private final BillRepository billRepository;
    private final ModelMapper modelMapper;
    private final ItemOrderedService itemOrderedService;
    private final BillService billService;
    private final WebClient webClient;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String DELETE_ITEM_TOPIC = "delete_item_topic";

    @KafkaListener(topics = "update_status_order_topic")
    public void consume_to_update_status(String message) {
        System.out.println("Message received from shipping-service to update status order: " + message);
        Integer orderId = Integer.parseInt(message);
        changeStatusOfOrder(orderId, 3);
    }

    @KafkaListener(topics = "update_cancel_order_topic")
    public void consume_to_cancel_order(String message) {
        System.out.println("Message received from shipping-service to update re-status order: " + message);
        Integer orderId = Integer.parseInt(message);
        changeStatusOfOrder(orderId, 2);
    }

    @KafkaListener(topics = "update_complete_order_topic")
    public void consume_to_complete_order(String message) {
        System.out.println("Message received from shipping-service to update complete order: " + message);
        Integer orderId = Integer.parseInt(message);
        changeStatusOfOrder(orderId, 4);
    }

    public OrderServiceImpl(DeliveryInformationRepository deliveryInfoRepository, CheckoutRepository checkOutRepository, OrderStatusRepository orderStatusRepository, OrderRepository orderRepository, BillRepository billRepository, ModelMapper modelMapper, ItemOrderedService itemOrderedService, BillService billService, WebClient webClient, KafkaTemplate<String, String> kafkaTemplate) {
        this.deliveryInfoRepository = deliveryInfoRepository;
        this.checkOutRepository = checkOutRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderRepository = orderRepository;
        this.billRepository = billRepository;
        this.modelMapper = modelMapper;
        this.itemOrderedService = itemOrderedService;
        this.billService = billService;
        this.webClient = webClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public ResOrderDto createOrderByStaff(OrderDto orderDto, Integer checkoutId) {
        Checkout checkout = this.checkOutRepository.findById(checkoutId).orElseThrow(()-> new ResourceNotFoundException("CheckOut","CheckOutId", checkoutId));
        OrderStatus orStatus = this.orderStatusRepository.findOrderStatusByStatus("Đã giao hàng");
        Order order = this.modelMapper.map(orderDto, Order.class);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(formatter);
        order.setDate(formattedDate);
        order.setCheckout(checkout);
        order.setOrderStatus(orStatus);
        Order addOrder = this.orderRepository.save(order);
        List<ResItemDto> items = getItemByAccountID(orderDto.getAccountId());
        Double total_price = 0.0;
        for (ResItemDto itemDto : items) {
            ItemOrderedDto ordered = modelMapper.map(itemDto, ItemOrderedDto.class);
            this.itemOrderedService.createItem(ordered, addOrder.getId());
            total_price += itemDto.getPrice();
            kafkaTemplate.send(DELETE_ITEM_TOPIC, String.valueOf(itemDto.getId()));
        }
        Integer orderId = addOrder.getId();
        Order orderUpdate = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
        orderUpdate.setTotalprice(total_price);
        BillDto billDto = new BillDto();
        billDto.setIssuedate(orderUpdate.getDate());
        billDto.setTotalprice(orderUpdate.getTotalprice());
        BillDto responseBill = this.billService.createBill(billDto);
        Bill bill = this.modelMapper.map(responseBill, Bill.class);
        orderUpdate.setBill(bill);
        Order orderSucceed = this.orderRepository.save(orderUpdate);
        return this.modelMapper.map(orderSucceed, ResOrderDto.class);
    }

    @Override
    public ResOrderDto createOrderByCustomer(OrderDto orderDto, Integer checkoutId, Integer deliveryInfoId) {
        Checkout checkout = this.checkOutRepository.findById(checkoutId).orElseThrow(()-> new ResourceNotFoundException("CheckOut","CheckOutId", checkoutId));
        DeliveryInformation deliveryInfo = this.deliveryInfoRepository.findById(deliveryInfoId).orElseThrow(()-> new ResourceNotFoundException("DeliveryInfo","DeliveryInfoId", deliveryInfoId));
        OrderStatus orStatus = this.orderStatusRepository.findOrderStatusByStatus("Chờ xác nhận");
        Order order = this.modelMapper.map(orderDto, Order.class);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(formatter);
        order.setDate(formattedDate);
        order.setCheckout(checkout);
        order.setOrderStatus(orStatus);
        order.setDeliveryInformation(deliveryInfo);
        Order addOrder = this.orderRepository.save(order);
        List<ResItemDto> items = getItemByAccountID(orderDto.getAccountId());
        Double total_price = 0.0;
        for (ResItemDto itemDto : items) {
            ItemOrderedDto ordered = modelMapper.map(itemDto, ItemOrderedDto.class);
            this.itemOrderedService.createItem(ordered, addOrder.getId());
            total_price += itemDto.getPrice();
            kafkaTemplate.send(DELETE_ITEM_TOPIC, String.valueOf(itemDto.getId()));
        }
        Integer orderId = addOrder.getId();
        Order orderUpdate = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
        orderUpdate.setTotalprice(total_price+15000.0);
        BillDto billDto = new BillDto();
        billDto.setIssuedate(orderUpdate.getDate());
        billDto.setTotalprice(orderUpdate.getTotalprice());
        BillDto responseBill = this.billService.createBill(billDto);
        Bill bill = this.modelMapper.map(responseBill, Bill.class);
        orderUpdate.setBill(bill);
        Order orderSucceed = this.orderRepository.save(orderUpdate);
        return this.modelMapper.map(orderSucceed, ResOrderDto.class);
    }

    @Override
    public ResOrderDto getOrder(Integer orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
        return this.modelMapper.map(order, ResOrderDto.class);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
        Bill bill = this.billRepository.findByOrder(order);
        this.orderRepository.delete(order);
        this.billRepository.delete(bill);
    }

    @Override
    public ResOrderDto changeStatusOfOrder(Integer orderId, Integer orderStatusId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
        OrderStatus orderStatus = this.orderStatusRepository.findById(orderStatusId).orElseThrow(()-> new ResourceNotFoundException("OrderStatus","OrderStatusId", orderStatusId));
        order.setOrderStatus(orderStatus);
        Order updateOrder = this.orderRepository.save(order);
        return this.modelMapper.map(updateOrder, ResOrderDto.class);
    }

    @Override
    public List<ResOrderDto> getOrdersByOrderStatus(Integer orderStatusId) {
        OrderStatus order_status = this.orderStatusRepository.findById(orderStatusId).orElseThrow(()-> new ResourceNotFoundException("OrderStatus","OrderStatusId", orderStatusId));
        List<Order> orders = this.orderRepository.findByOrderStatus(order_status);
        return orders.stream().map((order) -> this.modelMapper.map(order, ResOrderDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<ResOrderDto> getAllOrder() {
        List<Order> orders = this.orderRepository.findAll();
        return orders.stream().map((order) -> this.modelMapper.map(order, ResOrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResOrderDto> getAllOrderOfAccountID(Integer accountId) {
        List<Order> orders = this.orderRepository.findByAccountId(accountId);
        return orders.stream().map((order) -> this.modelMapper.map(order, ResOrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RevenueDto getRevenueOfStore() {
        RevenueDto revenueDto = new RevenueDto();
        revenueDto.setRevenue(this.orderRepository.calculateRevenue());
        return revenueDto;
    }

    @Override
    public TotalOrderDto getTotalOrderInStore() {
        TotalOrderDto orderDto = new TotalOrderDto();
        orderDto.setTotal(this.orderRepository.getTotalOrder());
        return orderDto;
    }

    public List<ResItemDto> getItemByAccountID(Integer accountId) {
        String url_Endpoint = "http://localhost:8083/api/item/all/account/" + accountId;
        Mono<List<ResItemDto>> responseMono = webClient.get()
                .uri(url_Endpoint)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });  // Chờ kết quả và lấy giá trị trực tiếp từ Mono
        return responseMono.block();  // block() sẽ chờ cho tới khi Mono hoàn tất và trả về dữ liệu
    }
}
