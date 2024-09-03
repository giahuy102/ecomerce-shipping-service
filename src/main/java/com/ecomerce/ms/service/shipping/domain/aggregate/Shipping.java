package com.ecomerce.ms.service.shipping.domain.aggregate;

import com.huyle.ms.domain.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

import static com.ecomerce.ms.service.shipping.domain.aggregate.ShippingStatus.PROCESSING;

@Entity
@Table(name = "shippings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipping extends DomainEntity<UUID> {
    @Column(name = "order_id")
    private UUID orderId;

    @JoinColumn(name = "shipping_method_id")
    @ManyToOne
    private ShippingMethod shippingMethod;

    @Column(name = "shipping_charge")
    private double shippingCharge;

    @Enumerated(EnumType.STRING)
    private ShippingStatus status = PROCESSING;
}
