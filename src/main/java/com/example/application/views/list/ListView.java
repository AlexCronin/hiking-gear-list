package com.example.application.views.list;

import com.example.application.data.Gear;
import com.example.application.services.GearListService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;

import java.util.Collections;


@SpringComponent
@Scope("prototype")
@PageTitle("Hiking Gear List")
@Route(value = "listview", layout = MainLayout.class)
//@RouteAlias(value = "")
@PermitAll
public class ListView extends VerticalLayout {
    Grid<Gear> grid = new Grid<>(Gear.class);
    TextField filterText = new TextField();
    GearForm form;
    GearListService service;

    public ListView(GearListService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();  // Uses full size grid regardless of how many elements inside

        configureGrid();
        configureForm();

        add(
          getToolbar(),
          getContent()
        );

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setGear(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllGear(filterText.getValue()));
    }

    private Component getContent() {

        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }
    private void configureForm() {
        form = new GearForm(service.findAllCategories(), service.findAllStatuses());
        form.setWidth("25em");

        form.addSaveListener(this::saveGear);
        form.addDeleteListener(this::deleteGear);
        form.addCloseListener(e -> closeEditor());
    }

    private void saveGear(GearForm.SaveEvent event) {
        service.saveGear(event.getGear());
        updateList();
        closeEditor();
    }

    private void deleteGear(GearForm.DeleteEvent event) {
        service.deleteGear(event.getGear());
        updateList();
        closeEditor();
    }
    private Component getToolbar() {
        filterText.setPlaceholder("Search..."); // not working
        filterText.setClearButtonVisible(true); // not working
        filterText.setValueChangeMode(ValueChangeMode.LAZY); // not working
        filterText.addValueChangeListener(e -> updateList());

        Button addGearButton = new Button("Add Gear");
        addGearButton.addClickListener(e -> addGear());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addGearButton);
        toolbar.addClassName("toolbar");
        return toolbar;

    }

    private void addGear() {
        grid.asSingleSelect().clear();
        editGear(new Gear());
    }

    private void configureGrid() {
        grid.addClassName("gear-grid");
        grid.setSizeFull();
        grid.setColumns("brand", "item", "weight");
        grid.addColumn(gear -> gear.getStatus().getName()).setHeader("Status");
        grid.addColumn(gear -> gear.getCategory().getName()).setHeader("Category");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editGear(e.getValue()));

    }

    private void editGear(Gear gear) {
        if(gear == null) {
            closeEditor();
        } else {
            form.setGear(gear);
            form.setVisible(true);
            addClassName("editing");
        }
    }

}
