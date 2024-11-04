package com.kientran.shipping_service.service;

import com.kientran.shipping_service.dto.*;

import java.util.List;

public interface ShipperService {
    ResShipperDto createShipper(ShipperDto shipperDto);
    ResShipperDto getShipper(Integer shipperId);
    ResShipperDto getShipperByAccountID(Integer accountId);
    ResShipperDto addPaymentInfo(Integer shipperId, Integer paymentId);
    ResShipperDto  updateShipper(UpdateShipperDto updateShipperDto, Integer shipperId);
    void resetIncome(Integer shipperId);
    void updateIncome(Integer shipperId);
    void downIncome(Integer shipperId);
    void deleteShipper(Integer shipperId);
    List<ResShipperDto> getAllShipper();
}
