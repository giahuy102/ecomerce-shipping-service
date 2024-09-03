package com.ecomerce.ms.service.shipping.domain.aggregate;

import com.huyle.ms.domain.DomainEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "product_shipping_infos")
@Getter
@Setter
@NoArgsConstructor
public class ProductShippingInfo extends DomainEntity<UUID> {

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "product_volume", nullable = false)
    private double productVolume;
}
