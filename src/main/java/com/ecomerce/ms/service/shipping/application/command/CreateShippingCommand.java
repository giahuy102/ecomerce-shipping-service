package com.ecomerce.ms.service.shipping.application.command;

import com.ecomerce.ms.service.shipping.domain.shared.external.order.Order;
import com.huyle.ms.command.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateShippingCommand implements Command {
    private Order order;
}
