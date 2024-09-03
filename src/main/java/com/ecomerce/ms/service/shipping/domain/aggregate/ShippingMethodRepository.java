package com.ecomerce.ms.service.shipping.domain.aggregate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, UUID> {

    public Optional<ShippingMethod> findByIsDefault(boolean isDefault);
}
