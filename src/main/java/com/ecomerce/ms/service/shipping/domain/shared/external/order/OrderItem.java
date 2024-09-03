package com.ecomerce.ms.service.shipping.domain.shared.external.order;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItem {
    private UUID id;
    private Integer quantity;
}