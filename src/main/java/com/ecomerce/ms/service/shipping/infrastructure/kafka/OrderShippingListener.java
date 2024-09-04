package com.ecomerce.ms.service.shipping.infrastructure.kafka;

import com.ecomerce.ms.service.DeliveryProcessingCommand;
import com.ecomerce.ms.service.DeliveryProcessingReply;
import com.ecomerce.ms.service.OrderMessage;
import com.ecomerce.ms.service.OrderingSagaKey;
import com.ecomerce.ms.service.SagaStepStatusMessage;
import com.ecomerce.ms.service.shipping.application.command.CreateShippingCommand;
import com.ecomerce.ms.service.shipping.domain.shared.external.order.Order;
import com.ecomerce.ms.service.shipping.domain.shared.external.order.OrderItem;
import com.huyle.ms.command.CommandGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.stream.Collectors;

import static com.ecomerce.ms.service.shipping.domain.shared.Constants.DELIVERY_PROCESSING_REPLY_TOPIC;
import static com.ecomerce.ms.service.shipping.domain.shared.Constants.DELIVERY_PROCESSING_TOPIC;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderShippingListener {
    private final CommandGateway commandGateway;
    private final KafkaTemplate<OrderingSagaKey, DeliveryProcessingReply> deliveryProcessingTemplate;

    @KafkaListener(topics = DELIVERY_PROCESSING_TOPIC)
    public void onDeliveryProcessingCommand(@Payload DeliveryProcessingCommand deliveryProcessingCommand,
                                            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) OrderingSagaKey sagaKey) {
        OrderMessage orderMessage = deliveryProcessingCommand.getOrder();
        List<OrderItem> orderItems = orderMessage.getOrderItems().stream()
                .map(item -> OrderItem.builder()
                        .id(item.getProductId())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());
        Order order = Order.builder()
                .id(orderMessage.getOrderId())
                .items(orderItems)
                .build();

        SagaStepStatusMessage sagaStepStatusMessage;
        try {
            commandGateway.handle(CreateShippingCommand.builder()
                    .order(order)
                    .build());
            sagaStepStatusMessage = SagaStepStatusMessage.SUCCEEDED;
        } catch (RuntimeException e) {
            sagaStepStatusMessage = SagaStepStatusMessage.FAILED;
        }
        var reply = DeliveryProcessingReply.newBuilder()
                .setSagaMetadata(deliveryProcessingCommand.getSagaMetadata())
                .setSagaStepStatus(sagaStepStatusMessage)
                .build();
        deliveryProcessingTemplate.send(DELIVERY_PROCESSING_REPLY_TOPIC, sagaKey, reply)
                .addCallback(new ListenableFutureCallback<SendResult<OrderingSagaKey, DeliveryProcessingReply>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        log.info("Unable to send message=["
                                + reply + "] due to : " + ex.getMessage());
                    }

                    @Override
                    public void onSuccess(SendResult<OrderingSagaKey, DeliveryProcessingReply> result) {
                        log.info("Sent message=[" + reply +
                                "] with offset=[" + result.getRecordMetadata().offset() + "]");
                    }
                });
    }
}
