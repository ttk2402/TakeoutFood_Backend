package com.kientran.identity_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AccountStatusDto {
    private Integer id;
    private String status;
}
