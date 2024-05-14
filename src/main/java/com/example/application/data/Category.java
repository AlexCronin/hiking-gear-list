package com.example.application.data;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.Formula;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Category extends AbstractEntity {
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "category")
    @Nullable
    private List<Gear> gearItems = new LinkedList<>();

    @Formula("(select count(g.id) from Gear g where g.category_id = id)")
    private int gearItemCount;

    public int getGearItemCount() {
        return gearItemCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Gear> getGearItems() {
        return gearItems;
    }

    public void setItem(List<Gear> gearItems) {
        this.gearItems = gearItems;
    }
}
