package com.ecomerce.ms.service.shipping.domain.shared.external.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderItem {
    private UUID id;
    private Integer quantity;
}
