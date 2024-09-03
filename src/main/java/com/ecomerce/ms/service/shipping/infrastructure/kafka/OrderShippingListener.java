package com.ecomerce.ms.service.shipping.infrastructure.kafka;

import com.ecomerce.ms.service.DeliveryProcessingCommand;
import com.ecomerce.ms.service.DeliveryProcessingReply;
import com.ecomerce.ms.service.OrderingSagaKey;
import com.huyle.ms.command.CommandGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderShippingListener {
    private final CommandGateway commandGateway;
    private final KafkaTemplate<OrderingSagaKey, DeliveryProcessingReply> deliveryProcessingTemplate;

    public void onDeliveryProcessingCommand(@Payload DeliveryProcessingCommand deliveryProcessingCommand,
                                            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) OrderingSagaKey sagaKey) {

    }
}
