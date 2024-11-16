package com.kientran.shipping_service.controller;

import com.kientran.shipping_service.dto.DeliveryOrderDto;
import com.kientran.shipping_service.dto.ResDeliveryOrderDto;
import com.kientran.shipping_service.imgur.ImgurService;
import com.kientran.shipping_service.response.ApiResponse;
import com.kientran.shipping_service.service.DeliveryOrderService;
import com.kientran.shipping_service.service.ShipperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/delivery_order")
public class DeliverOrderController {

    private final DeliveryOrderService deliveryOrderService;
    private final ShipperService shipperService;
    private final ImgurService imgurService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String UPDATE_STATUS_ORDER_TOPIC = "update_status_order_topic";
    private static final String UPDATE_CANCEL_ORDER_TOPIC = "update_cancel_order_topic";
    private static final String UPDATE_COMPLETE_ORDER_TOPIC = "update_complete_order_topic";

    public DeliverOrderController(DeliveryOrderService deliveryOrderService, ShipperService shipperService, ImgurService imgurService, KafkaTemplate<String, String> kafkaTemplate) {
        this.deliveryOrderService = deliveryOrderService;
        this.shipperService = shipperService;
        this.imgurService = imgurService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/add/{shipperId}")
    public ResponseEntity<ResDeliveryOrderDto> createDeliveryOrder(@RequestBody DeliveryOrderDto deliveryOrderDto,
                                                                   @PathVariable Integer shipperId) {
        ResDeliveryOrderDto resDeliveryOrderDto = this.deliveryOrderService.createDeliveryOrder(deliveryOrderDto, shipperId);
        String order_ID = String.valueOf(deliveryOrderDto.getOrderId());
        kafkaTemplate.send(UPDATE_STATUS_ORDER_TOPIC, order_ID);
        return new ResponseEntity<>(resDeliveryOrderDto, HttpStatus.CREATED);
    }

    @GetMapping("/{deliveryOrderId}")
    public ResponseEntity<ResDeliveryOrderDto> getDeliveryOrder(@PathVariable Integer deliveryOrderId) {
        ResDeliveryOrderDto resDeliveryOrderDto = this.deliveryOrderService.getDeliveryOrder(deliveryOrderId);
        return new ResponseEntity<>(resDeliveryOrderDto, HttpStatus.OK);
    }

    @DeleteMapping("/{deliveryOrderId}")
    public ResponseEntity<ApiResponse> deleteDeliveryOrderById(@PathVariable Integer deliveryOrderId) {
        Integer orderId = this.deliveryOrderService.deleteDeliveryOrder(deliveryOrderId);
        kafkaTemplate.send(UPDATE_CANCEL_ORDER_TOPIC, String.valueOf(orderId));
        return new ResponseEntity<>(new ApiResponse("DeliveryOrder is deleted successfully!", true),
                HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<List<ResDeliveryOrderDto>> getAllDeliveryCurrentOrder() {
        List<ResDeliveryOrderDto> deliveryOrderDtos = this.deliveryOrderService.getAllDeliveryOrderCurrentDto();
        return new ResponseEntity<>(deliveryOrderDtos, HttpStatus.OK);
    }

    @GetMapping("/complete")
    public ResponseEntity<List<ResDeliveryOrderDto>> getAllDeliveryCompleteOrder() {
        List<ResDeliveryOrderDto> deliveryOrderDtos = this.deliveryOrderService.getAllDeliveryOrderCompleteDto();
        return new ResponseEntity<>(deliveryOrderDtos, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResDeliveryOrderDto>> getAllDeliveryOrder() {
        List<ResDeliveryOrderDto> deliveryOrderDtos = this.deliveryOrderService.getAllDeliveryOrder();
        return new ResponseEntity<>(deliveryOrderDtos, HttpStatus.OK);
    }

    @PutMapping("/complete/{deliveryOrderId}")
    public ResponseEntity<ResDeliveryOrderDto> addImageConfirmation(@RequestParam MultipartFile image,
                                                                    @PathVariable Integer deliveryOrderId) {
        String imageUrl = this.imgurService.uploadImage(image);
        ResDeliveryOrderDto updateDeliveryOrder = this.deliveryOrderService.addImageConfirmation(deliveryOrderId, imageUrl);
        kafkaTemplate.send(UPDATE_COMPLETE_ORDER_TOPIC, String.valueOf(updateDeliveryOrder.getOrderId()));
//      this.shipperService.updateIncome(updateDeliveryOrder.getShipper().getId());
        ResDeliveryOrderDto newDeliveryOrder = this.deliveryOrderService.getDeliveryOrder(deliveryOrderId);
        return new ResponseEntity<>(newDeliveryOrder, HttpStatus.OK);
    }

    @PutMapping("/cancel/{deliveryOrderId}")
    public ResponseEntity<ResDeliveryOrderDto> cancelImageConfirmation(@PathVariable Integer deliveryOrderId) {
        ResDeliveryOrderDto updateDeliveryOrder = this.deliveryOrderService.cancelImageConfirmation(deliveryOrderId);
        return new ResponseEntity<>(updateDeliveryOrder, HttpStatus.OK);
    }

    @PutMapping("/complete/confirm/{deliveryOrderId}/{shipperId}")
    public ResponseEntity<ResDeliveryOrderDto> confirmShipperDeliverySuccess(@PathVariable Integer deliveryOrderId,
                                                                    @PathVariable Integer shipperId){
        ResDeliveryOrderDto updateDeliveryOrder = this.deliveryOrderService.confirmDeliveryOrderShipper(deliveryOrderId);
        this.shipperService.updateIncome(shipperId);
        return new ResponseEntity<>(updateDeliveryOrder, HttpStatus.OK);
    }

}
