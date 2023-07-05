package com.pllenxx.externalservice.controllers;

import com.pllenxx.entities.dto.OwnerDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.pllenxx.externalservice.configuration.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/owners")
@SecurityRequirement(name = "bearerAuth")
public class OwnerController {
    private final RabbitTemplate template;
    @PostMapping()
    public void save(@RequestBody OwnerDto ownerDto) {
        template.convertAndSend(RabbitConfig.EXCHANGE1, RabbitConfig.ROUTING_KEY1, ownerDto);
    }

    @GetMapping("{id}")
    public OwnerDto get(@PathVariable Long id) {
        return (OwnerDto) template.convertSendAndReceive(RabbitConfig.EXCHANGE1, RabbitConfig.ROUTING_KEY2, id);
    }

    @PutMapping()
    public void update(@RequestBody OwnerDto ownerDto) {
        template.convertAndSend(RabbitConfig.EXCHANGE1, RabbitConfig.ROUTING_KEY3, ownerDto);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        template.convertAndSend(RabbitConfig.EXCHANGE1, RabbitConfig.ROUTING_KEY4, id);
    }
}
