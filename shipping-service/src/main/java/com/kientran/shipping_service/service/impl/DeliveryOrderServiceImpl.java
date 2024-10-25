package com.kientran.shipping_service.service.impl;

import com.kientran.shipping_service.dto.DeliveryOrderDto;
import com.kientran.shipping_service.dto.ResDeliveryOrderDto;
import com.kientran.shipping_service.entity.DeliveryOrder;
import com.kientran.shipping_service.entity.Shipper;
import com.kientran.shipping_service.exception.ResourceNotFoundException;
import com.kientran.shipping_service.repository.DeliveryOrderRepository;
import com.kientran.shipping_service.repository.ShipperRepository;
import com.kientran.shipping_service.service.DeliveryOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DeliveryOrderServiceImpl implements DeliveryOrderService {

    private final DeliveryOrderRepository deliveryOrderRepository;
    private final ShipperRepository shipperRepository;
    private final ModelMapper modelMapper;
    //  private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir")).resolve("shipping-service");

    public DeliveryOrderServiceImpl(DeliveryOrderRepository deliveryOrderRepository, ShipperRepository shipperRepository, ModelMapper modelMapper) {
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.shipperRepository = shipperRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResDeliveryOrderDto createDeliveryOrder(DeliveryOrderDto deliveryOrderDto, Integer shipperId) {
        DeliveryOrder deliveryOrder = this.modelMapper.map(deliveryOrderDto, DeliveryOrder.class);
        Shipper shipper = this.shipperRepository.findById(shipperId).orElseThrow(() -> new ResourceNotFoundException("Shipper", "ShipperId", shipperId));
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(formatter);
        deliveryOrder.setReceiveDate(formattedDate);
        deliveryOrder.setIsCompleted(false);
        deliveryOrder.setShipper(shipper);
        DeliveryOrder addDeliveryOrder = this.deliveryOrderRepository.save(deliveryOrder);
        return this.modelMapper.map(addDeliveryOrder, ResDeliveryOrderDto.class);
    }

    @Override
    public ResDeliveryOrderDto getDeliveryOrder(Integer deliveryOrderId) {
        DeliveryOrder deliveryOrder = this.deliveryOrderRepository.findById(deliveryOrderId).orElseThrow(() -> new ResourceNotFoundException("DeliveryOrder", "DeliveryId", deliveryOrderId));
        return this.modelMapper.map(deliveryOrder, ResDeliveryOrderDto.class);
    }

    @Override
    public Integer deleteDeliveryOrder(Integer deliveryOrderId) {
        DeliveryOrder deliveryOrder = this.deliveryOrderRepository.findById(deliveryOrderId).orElseThrow(() -> new ResourceNotFoundException("DeliveryOrder", "DeliveryId", deliveryOrderId));
        Integer orderId = deliveryOrder.getOrderId();
        this.deliveryOrderRepository.delete(deliveryOrder);
        return orderId;
    }

    @Override
    public List<ResDeliveryOrderDto> getAllDeliveryOrderCurrentDto() {
        List<DeliveryOrder> deliveryOrders = this.deliveryOrderRepository.findAll();
        return deliveryOrders.stream()
                .filter(deliveryOrder -> !deliveryOrder.getIsCompleted()) // Lọc đơn hàng có isCompleted = false
                .map(deliveryOrder -> this.modelMapper.map(deliveryOrder, ResDeliveryOrderDto.class)) // Ánh xạ đối tượng
                .collect(Collectors.toList());
    }

    @Override
    public List<ResDeliveryOrderDto> getAllDeliveryOrderCompleteDto() {
        List<DeliveryOrder> deliveryOrders = this.deliveryOrderRepository.findAll();
        return deliveryOrders.stream()
                .filter(DeliveryOrder::getIsCompleted) // Lọc đơn hàng có isCompleted = false
                .map(deliveryOrder -> this.modelMapper.map(deliveryOrder, ResDeliveryOrderDto.class)) // Ánh xạ đối tượng
                .collect(Collectors.toList());
    }

    @Override
    public ResDeliveryOrderDto addImageConfirmation(Integer deliveryOrderId, MultipartFile image) throws IOException {
        DeliveryOrder deliveryOrder = this.deliveryOrderRepository.findById(deliveryOrderId).orElseThrow(() -> new ResourceNotFoundException("DeliveryOrder", "DeliveryId", deliveryOrderId));
//        Path staticPath = Paths.get("static");
        Path staticPath = Paths.get("src/main/resources/static");
        Path imagePath = Paths.get("images");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(Objects.requireNonNull(image.getOriginalFilename()));
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(image.getBytes());
        }
        // Tạo URL của ảnh
        String imageUrl = "/images/" + image.getOriginalFilename();
        // Lưu URL ảnh vào Delivery Order
        deliveryOrder.setImageConfirmation(imageUrl);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(formatter);
        deliveryOrder.setCompleteDate(formattedDate);
        deliveryOrder.setIsCompleted(true);
        DeliveryOrder updateDeliveryOrder = this.deliveryOrderRepository.save(deliveryOrder);
        return this.modelMapper.map(updateDeliveryOrder, ResDeliveryOrderDto.class);
    }

    @Override
    public ResDeliveryOrderDto cancelImageConfirmation(Integer deliveryOrderId) {
        DeliveryOrder deliveryOrder = this.deliveryOrderRepository.findById(deliveryOrderId).orElseThrow(() -> new ResourceNotFoundException("DeliveryOrder", "DeliveryId", deliveryOrderId));
        deliveryOrder.setImageConfirmation(null);
        deliveryOrder.setCompleteDate(null);
        deliveryOrder.setIsCompleted(false);
        DeliveryOrder updateDeliveryOrder = this.deliveryOrderRepository.save(deliveryOrder);
        return this.modelMapper.map(updateDeliveryOrder, ResDeliveryOrderDto.class);
    }
}
