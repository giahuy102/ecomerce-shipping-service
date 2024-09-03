package com.ecomerce.ms.service.shipping.application.command;

import com.ecomerce.ms.service.shipping.domain.aggregate.Shipping;
import com.ecomerce.ms.service.shipping.domain.aggregate.ShippingMethodRepository;
import com.ecomerce.ms.service.shipping.domain.aggregate.ShippingRepository;
import com.ecomerce.ms.service.shipping.domain.service.ShippingService;
import com.ecomerce.ms.service.shipping.domain.shared.external.order.Order;
import com.huyle.ms.command.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateShippingCommandHandler implements CommandHandler<CreateShippingCommand, Void> {

    private final ShippingService shippingService;
    private final ShippingRepository shippingRepository;
    private final ShippingMethodRepository shippingMethodRepository;

    @Override
    @Transactional
    public Void handle(CreateShippingCommand command) {
        Order order = command.getOrder();
        UUID shippingMethodId = order.getShippingMethodId();
        Shipping shippingRecord = Shipping.builder()
                .orderId(order.getId())
                .shippingMethod(shippingMethodId != null ? shippingMethodRepository.getReferenceById(shippingMethodId) : null)
                .shippingCharge(shippingService.calculateShippingCost(order))
                .build();
        shippingRepository.save(shippingRecord);
        return null;
    }
}
