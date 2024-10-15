package com.kientran.order_service.service.impl;

import com.kientran.order_service.dto.DeliveryInformationDto;
import com.kientran.order_service.entity.DeliveryInformation;
import com.kientran.order_service.exception.ResourceNotFoundException;
import com.kientran.order_service.repository.DeliveryInformationRepository;
import com.kientran.order_service.service.DeliveryInformationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryInformationServiceImpl implements DeliveryInformationService {
    private final DeliveryInformationRepository deliveryInfoRepo;
    private final ModelMapper modelMapper;

    public DeliveryInformationServiceImpl(DeliveryInformationRepository deliveryInfoRepo, ModelMapper modelMapper) {
        this.deliveryInfoRepo = deliveryInfoRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public DeliveryInformationDto create(DeliveryInformationDto deliveryInfoDto) {
        DeliveryInformation deliveryInfo = this.modelMapper.map(deliveryInfoDto, DeliveryInformation.class);
        DeliveryInformation addDeliveryInfo = this.deliveryInfoRepo.save(deliveryInfo);
        return this.modelMapper.map(addDeliveryInfo, DeliveryInformationDto.class);
    }

    @Override
    public void delete(Integer deliveryInfoId) {
        DeliveryInformation deliveryInfo = this.deliveryInfoRepo.findById(deliveryInfoId)
                .orElseThrow(()-> new ResourceNotFoundException("DeliveryInfo","DeliveryInfoId", deliveryInfoId));
        this.deliveryInfoRepo.delete(deliveryInfo);
    }

    @Override
    public List<DeliveryInformationDto> getDeliveryInfos() {
        List<DeliveryInformation> deliveryInfos = this.deliveryInfoRepo.findAll();
        List<DeliveryInformationDto> deliveryInfoDtos = deliveryInfos.stream().map((deliveryInfo)-> this.modelMapper.map(deliveryInfo, DeliveryInformationDto.class)).collect(Collectors.toList());
        return deliveryInfoDtos;
    }
}
