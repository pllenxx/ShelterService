package com.pllenxx.kittiesservice.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfig {
    public static final String QUEUE1 = "kitty_queue_save";
    public static final String ROUTING_KEY1 = "kitty_save";
    public static final String QUEUE2 = "kitty_queue_get";
    public static final String ROUTING_KEY2 = "kitty_get";
    public static final String QUEUE3 = "kitty_queue_update";
    public static final String ROUTING_KEY3 = "kitty_update";
    public static final String QUEUE4 = "kitty_queue_delete";
    public static final String ROUTING_KEY4 = "kitty_delete";
    public static final String EXCHANGE = "direct_kitty_exchange";

    @Bean
    public Queue firstQueue() {
        return new Queue(QUEUE1, true);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(QUEUE2, true);
    }

    @Bean
    public Queue thirdQueue() {
        return new Queue(QUEUE3, true);
    }

    @Bean
    public Queue fourthQueue() {
        return new Queue(QUEUE4, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingKittySave() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(ROUTING_KEY1);
    }

    @Bean
    public Binding bindingKittyrGet() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with(ROUTING_KEY2);
    }

    @Bean
    public Binding bindingKittyUpdate() {
        return BindingBuilder.bind(thirdQueue()).to(exchange()).with(ROUTING_KEY3);
    }

    @Bean
    public Binding bindingKittyDelete() {
        return BindingBuilder.bind(fourthQueue()).to(exchange()).with(ROUTING_KEY4);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
