package com.pllenxx.entities.dto;

import com.pllenxx.entities.enums.ColorType;
import lombok.Data;


import java.util.Date;

@Data
public class KittyDto {
    private Long id;
    private String name;
    private Date birthday;
    private String breed;
    private ColorType color;
    private Integer tailLength;
    private Long ownerId;
}

