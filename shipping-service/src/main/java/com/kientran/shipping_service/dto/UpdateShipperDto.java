package com.kientran.shipping_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShipperDto {
    private Integer id;
    private String phoneNumber;
    private String credentialCode;
}
