package com.ecomerce.ms.service.shipping.domain.aggregate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductShippingInfoRepository extends JpaRepository<ProductShippingInfo, UUID> {

    @Query(value = "SELECT SUM(product_volume) FROM product_shipping_infos WHERE product_id IN :product_ids", nativeQuery = true)
    public Double calculateTotalVolumeIn(@Param("product_ids") List<UUID> productIds);
}
