package com.example.application.views.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.application.data.Gear;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ListViewTest {

    @Autowired
    private ListView listView;

    @Test
    public void formShownWhenContactSelected() {
        Grid<Gear> grid = listView.grid;
        Gear firstGear = getFirstItem(grid);

        GearForm form = listView.form;

        assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstGear);
        assertTrue(form.isVisible());
        assertEquals(firstGear.getBrand(), form.brand.getValue());
    }

    private Gear getFirstItem(Grid<Gear> grid) {
        return( (ListDataProvider<Gear>) grid.getDataProvider()).getItems().iterator().next();
    }
}