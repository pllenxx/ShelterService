package com.pllenxx.entities.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OwnerDto {
    private Long id;
    private String name;
    private Date birthday;
}
