package com.pllenxx.kittiesservice.service;

import com.pllenxx.entities.Mapper;
import com.pllenxx.entities.dto.KittyDto;
import com.pllenxx.entities.jpaEntities.Kitty;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.pllenxx.kittiesservice.repository.KittyRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@EnableRabbit
public class KittyService {
    private final KittyRepository repository;
    private final Mapper mapper;
    private final RabbitTemplate template;

    @RabbitListener(queues = "kitty_queue_save")
    public KittyDto getKittyById(Long id) {
        KittyDto dto = mapper.mapKittyToKittyDto(Objects.requireNonNull(repository.findById(id).orElse(null)));
        template.convertAndSend("direct_kitty_exchange", "kitty_queue_save", dto);
        return dto;
    }
    public void saveKitty(KittyDto dto) {
        Kitty kitty = mapper.mapKittyDtoToKitty(dto);
        repository.save(kitty);
        template.convertAndSend("direct_kitty_exchange", "kitty_queue_save", dto);
    }
    public void updateKitty(KittyDto dto) {
        Kitty kittyToUpdate = repository.findById(dto.getId()).orElse(null);
        kittyToUpdate.setId(dto.getId());
        kittyToUpdate.setName(dto.getName());
        kittyToUpdate.setBirthday(dto.getBirthday());
        kittyToUpdate.setColor(dto.getColor());
        kittyToUpdate.setTailLength(dto.getTailLength());
        kittyToUpdate.setBreed(dto.getBreed());

        Kitty updatedKitty = repository.saveAndFlush(kittyToUpdate);
        template.convertAndSend("direct_kitty_exchange", "kitty_queue_update", mapper.mapKittyToKittyDto(updatedKitty));
    }

    public void deleteKittyById(Long id) {
        repository.deleteById(id);
        template.convertAndSend("direct_kitty_exchange", "kitty_queue_delete");
    }
}

