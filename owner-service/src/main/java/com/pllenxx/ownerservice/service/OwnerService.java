package com.pllenxx.ownerservice.service;

import com.pllenxx.entities.Mapper;
import com.pllenxx.entities.dto.OwnerDto;
import com.pllenxx.entities.jpaEntities.Owner;
import com.pllenxx.ownerservice.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@EnableRabbit
public class OwnerService {
    private final OwnerRepository repository;
    private final Mapper mapper;
    private final RabbitTemplate template;

    @RabbitListener(queues = "owner_queue_get")
    public OwnerDto getOwnerById(Long id) {
        OwnerDto dto = mapper.mapOwnerToOwnerDto(repository.findById(id).get());
        template.convertAndSend("direct_owner_exchange", "owner_get", dto);
        return dto;
    }
    @RabbitListener(queues = "owner_queue_save")
    public void saveOwner(OwnerDto dto) {
        Owner owner = mapper.mapOwnerDtoToOwner(dto);
        repository.save(owner);
        template.convertAndSend("direct_owner_exchange", "owner_save", dto);
    }

    @RabbitListener(queues = "owner_queue_update")
    public void updateOwner(OwnerDto dto) {
        Owner ownerToUpdate = repository.findById(dto.getId()).orElse(null);
        ownerToUpdate.setId(dto.getId());
        ownerToUpdate.setName(dto.getName());
        ownerToUpdate.setBirthday(dto.getBirthday());

        Owner updatedOwner = repository.saveAndFlush(ownerToUpdate);
        template.convertAndSend("direct_owner_exchange", "owner_update", mapper.mapOwnerToOwnerDto(updatedOwner));
    }

    @RabbitListener(queues = "owner_queue_delete")
    public void deleteOwnerById(Long id) {
        repository.deleteById(id);
        template.convertAndSend("direct_owner_exchange", "owner_delete");
    }
}

