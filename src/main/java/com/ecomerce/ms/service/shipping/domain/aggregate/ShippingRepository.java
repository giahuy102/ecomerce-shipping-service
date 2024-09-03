package com.ecomerce.ms.service.shipping.domain.aggregate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, UUID> {
}
