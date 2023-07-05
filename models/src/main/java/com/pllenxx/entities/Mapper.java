package com.pllenxx.entities;

import com.pllenxx.entities.dto.OwnerDto;
import com.pllenxx.entities.jpaEntities.Owner;
import com.pllenxx.entities.dto.KittyDto;
import org.springframework.stereotype.Component;
import com.pllenxx.entities.jpaEntities.Kitty;

@Component
public class Mapper {
    public OwnerDto mapOwnerToOwnerDto(Owner owner) {
        OwnerDto dto = new OwnerDto();
        dto.setId(owner.getId());
        dto.setName(owner.getName());
        dto.setBirthday(owner.getBirthday());
        return dto;
    }

    public Owner mapOwnerDtoToOwner(OwnerDto dto) {
        Owner owner = new Owner();
        owner.setId(dto.getId());
        owner.setName(dto.getName());
        owner.setBirthday(dto.getBirthday());
        return owner;
    }

    public KittyDto mapKittyToKittyDto(Kitty kitty) {
        KittyDto dto = new KittyDto();
        dto.setId(kitty.getId());
        dto.setName(kitty.getName());
        dto.setBirthday(kitty.getBirthday());
        dto.setBreed(kitty.getBreed());
        dto.setColor(kitty.getColor());
        dto.setTailLength(kitty.getTailLength());
        dto.setOwnerId(kitty.getOwner().getId());
        return dto;
    }
    public Kitty mapKittyDtoToKitty(KittyDto dto) {
        Kitty kitty = new Kitty();
        kitty.setId(dto.getId());
        kitty.setName(dto.getName());
        kitty.setBirthday(dto.getBirthday());
        kitty.setBreed(dto.getBreed());
        kitty.setColor(dto.getColor());
        kitty.setTailLength(dto.getTailLength());
        return kitty;
    }
}
