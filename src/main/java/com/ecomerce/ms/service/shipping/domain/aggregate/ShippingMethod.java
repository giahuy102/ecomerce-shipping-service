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
@Table(name = "shipping_method")
@Getter
@Setter
@NoArgsConstructor
public class ShippingMethod extends DomainEntity<UUID> {

    @Column(name = "method_name", nullable = false)
    private String methodName;

    @Column(name = "price_unit_volume", nullable = false)
    private double priceUnitVolume;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;
}
