package com.ecomerce.ms.service.shipping.domain.service;

import com.ecomerce.ms.service.shipping.domain.aggregate.ProductShippingInfoRepository;
import com.ecomerce.ms.service.shipping.domain.aggregate.ShippingMethod;
import com.ecomerce.ms.service.shipping.domain.aggregate.ShippingMethodRepository;
import com.ecomerce.ms.service.shipping.domain.shared.external.order.Order;
import com.huyle.ms.domain.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShippingService implements DomainService {

    private final ProductShippingInfoRepository productShippingInfoRepository;
    private final ShippingMethodRepository shippingMethodRepository;

    public Double calculateShippingCost(Order order) {
        UUID shippingMethodId = order.getShippingMethodId();
        Double totalVolume = productShippingInfoRepository.calculateTotalVolumeIn(order.getItemIds());
        ShippingMethod method;
        if (shippingMethodId == null) {
            method = shippingMethodRepository.findByIsDefault(true)
                    .orElseThrow(() -> new RuntimeException("Can not find shipping method"));
        } else {
            method = shippingMethodRepository.findById(shippingMethodId)
                    .orElseThrow(() -> new RuntimeException("Can not find shipping method"));
        }
        return totalVolume * method.getPriceUnitVolume();
    }
}
