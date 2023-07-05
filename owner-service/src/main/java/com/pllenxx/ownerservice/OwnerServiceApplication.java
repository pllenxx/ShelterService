package com.pllenxx.ownerservice;

import com.pllenxx.entities.Mapper;
import com.pllenxx.entities.jpaEntities.Kitty;
import com.pllenxx.entities.jpaEntities.Owner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackageClasses = {Owner.class, Kitty.class})
@ComponentScan(basePackageClasses = {Mapper.class})
public class OwnerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OwnerServiceApplication.class, args);
    }
}
