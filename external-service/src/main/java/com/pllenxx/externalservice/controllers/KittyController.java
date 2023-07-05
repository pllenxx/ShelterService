package com.pllenxx.externalservice.controllers;

import com.pllenxx.entities.dto.KittyDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.pllenxx.externalservice.configuration.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/kitties")
@SecurityRequirement(name = "bearerAuth")
public class KittyController {
    private final RabbitTemplate template;

    @PostMapping()
    public void save(@RequestBody KittyDto kittyDto) {
        template.convertAndSend(RabbitConfig.EXCHANGE2, RabbitConfig.ROUTING_KEY5, kittyDto);
    }

    @GetMapping("{id}")
    public KittyDto get(@PathVariable Long id) {
        return (KittyDto) template.convertSendAndReceive(RabbitConfig.EXCHANGE2, RabbitConfig.ROUTING_KEY6, id);
    }

    @PutMapping()
    public void update(@RequestBody KittyDto kittyDto) {
        template.convertAndSend(RabbitConfig.EXCHANGE2, RabbitConfig.ROUTING_KEY7, kittyDto);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        template.convertAndSend(RabbitConfig.EXCHANGE2, RabbitConfig.ROUTING_KEY8, id);
    }
}
