package com.pllenxx.entities.jpaEntities;

import com.pllenxx.entities.enums.ColorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "kitty")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kitty {
    @Id
    @Column(name = "id")
    private Long id;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private String breed;
    private ColorType color;
    @Column(name = "tail_length")
    private Integer tailLength;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id")
    private Owner owner;
}
