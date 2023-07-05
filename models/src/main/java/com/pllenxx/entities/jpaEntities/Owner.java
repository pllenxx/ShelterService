package com.pllenxx.entities.jpaEntities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "owner")
@Getter
@Setter
@NoArgsConstructor
public class Owner {
    @Id
    private Long id;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kitty> kitties = new ArrayList<>();

    public void setKitties(List<Kitty> kitties) {
        this.kitties.addAll(kitties);
    }

    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String strDate = dateFormat.format(birthday);
        return String.format("Owner [ownerId = %d, name = %s, date of birth = %s]", id, name, strDate);
    }
}
