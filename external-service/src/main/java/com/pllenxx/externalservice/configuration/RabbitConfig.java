package com.pllenxx.externalservice.configuration;

import org.springframework.amqp.core.*;
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
    public static final String QUEUE1 = "owner_queue_save";
    public static final String ROUTING_KEY1 = "owner_save";
    public static final String QUEUE2 = "owner_queue_get";
    public static final String ROUTING_KEY2 = "owner_get";
    public static final String QUEUE3 = "owner_queue_update";
    public static final String ROUTING_KEY3 = "owner_update";
    public static final String QUEUE4 = "owner_queue_delete";
    public static final String ROUTING_KEY4 = "owner_delete";
    public static final String EXCHANGE1 = "direct_owner_exchange";
    public static final String QUEUE5 = "kitty_queue_save";
    public static final String ROUTING_KEY5 = "kitty_save";
    public static final String QUEUE6 = "kitty_queue_get";
    public static final String ROUTING_KEY6 = "kitty_get";
    public static final String QUEUE7 = "kitty_queue_update";
    public static final String ROUTING_KEY7 = "kitty_update";
    public static final String QUEUE8 = "kitty_queue_delete";
    public static final String ROUTING_KEY8 = "kitty_delete";
    public static final String EXCHANGE2 = "direct_kitty_exchange";
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
    public DirectExchange ownerExchange() {
        return new DirectExchange(EXCHANGE1);
    }

    @Bean
    public Queue fifthQueue() {
        return new Queue(QUEUE5, true);
    }

    @Bean
    public Queue sixthQueue() {
        return new Queue(QUEUE6, true);
    }

    @Bean
    public Queue seventhQueue() {
        return new Queue(QUEUE7, true);
    }

    @Bean
    public Queue eighththQueue() {
        return new Queue(QUEUE8, true);
    }

    @Bean
    public DirectExchange kittyExchange() {
        return new DirectExchange(EXCHANGE2);
    }

    @Bean
    public Binding bindingOwnerSave() {
        return BindingBuilder.bind(firstQueue()).to(ownerExchange()).with(ROUTING_KEY1);
    }

    @Bean
    public Binding bindingOwnerGet() {
        return BindingBuilder.bind(secondQueue()).to(ownerExchange()).with(ROUTING_KEY2);
    }

    @Bean
    public Binding bindingOwnerUpdate() {
        return BindingBuilder.bind(thirdQueue()).to(ownerExchange()).with(ROUTING_KEY3);
    }

    @Bean
    public Binding bindingOwnerDelete() {
        return BindingBuilder.bind(fourthQueue()).to(ownerExchange()).with(ROUTING_KEY4);
    }

    @Bean
    public Binding bindingKittySave() {
        return BindingBuilder.bind(fifthQueue()).to(kittyExchange()).with(ROUTING_KEY5);
    }

    @Bean
    public Binding bindingKittyGet() {
        return BindingBuilder.bind(sixthQueue()).to(kittyExchange()).with(ROUTING_KEY6);
    }

    @Bean
    public Binding bindingKittyUpdate() {
        return BindingBuilder.bind(seventhQueue()).to(kittyExchange()).with(ROUTING_KEY7);
    }

    @Bean
    public Binding bindingKittyDelete() {
        return BindingBuilder.bind(eighththQueue()).to(kittyExchange()).with(ROUTING_KEY8);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
