package com.example.application.views.list;

import com.example.application.data.Category;
import com.example.application.data.Gear;
import com.example.application.data.Status;
//import com.example.application.data.entity.Category;
//import com.example.application.data.entity.Gear;
//import com.example.application.data.entity.Price;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.application.data.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GearFormTest {
    private List<Category> categories;
    private List<Status> statuses;
    private Gear zpacksDuplex;
    private Category category1;
    private Category category2;
    private Status price1;
    private Status price2;

    @BeforeEach
    public void setupData() {
        categories = new ArrayList<>();
        category1 = new Category();
        category1.setName("Tent");
        category2 = new Category();
        category2.setName("Backpack");
        categories.add(category1);
        categories.add(category2);

        statuses = new ArrayList<>();
        price1 = new Status();
        price1.setName("Price 1");
        price2 = new Status();
        price2.setName("Price 2");
        statuses.add(price1);
        statuses.add(price2);

        zpacksDuplex = new Gear();
        zpacksDuplex.setBrand("Zpacks");
        zpacksDuplex.setItem("Duplex");
        zpacksDuplex.setWeight("560");
        zpacksDuplex.setStatus(price1);
        zpacksDuplex.setCategory(category2);
    }

    @Test
    public void formFieldsPopulated() {
        GearForm form = new GearForm(categories, statuses);
        form.setGear(zpacksDuplex);
        assertEquals("Zpacks", form.brand.getValue());
        assertEquals("Duplex", form.item.getValue());
        assertEquals("560", form.weight.getValue());
        assertEquals(category2, form.category.getValue());
        assertEquals(price1, form.status.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        GearForm form = new GearForm(categories, statuses);
        Gear gear = new Gear();
        form.setGear(gear);
        form.brand.setValue("Hyperlite");
        form.item.setValue("Junction");
        form.category.setValue(category1);
        form.weight.setValue("700");
        form.status.setValue(price2);

        AtomicReference<Gear> savedGearRef = new AtomicReference<>(null);
        form.addSaveListener( e -> {
            savedGearRef.set(e.getGear());
        });
        form.save.click();
        Gear savedGear = savedGearRef.get();

        assertEquals("Hyperlite", savedGear.getBrand());
        assertEquals("Junction", savedGear.getItem());
        assertEquals("700", savedGear.getWeight());
        assertEquals(category1, savedGear.getCategory());
        assertEquals(price2, savedGear.getStatus());
    }
}