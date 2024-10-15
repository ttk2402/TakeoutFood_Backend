package com.kientran.cart_service.dto;

import com.kientran.cart_service.entity.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ResCartDto {
    private Integer id;
    private Integer accountId;
    private List<Item> items;
}