package com.ecomerce.ms.service.shipping.domain.shared.external.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class Order {
    private UUID id;
    private List<OrderItem> items;
    private UUID shippingMethodId;

    public List<UUID> getItemIds() {
        return items.stream()
                .map(OrderItem::getId)
                .collect(Collectors.toList());
    }
}
