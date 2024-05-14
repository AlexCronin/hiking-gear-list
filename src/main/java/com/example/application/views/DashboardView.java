package com.example.application.views;

import com.example.application.services.GearListService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Pie Chart | Gear List")
@PermitAll
public class DashboardView extends VerticalLayout {

    private GearListService service;

    public DashboardView(GearListService service) {
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        String getGearStats;
        add(getGearStats(), getCatgegoriesChart());
    }

    private Component getGearStats() {
        Span stats = new Span(service.countGear() + " Gear Items");
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Component getCatgegoriesChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        /*
        service.findAllCategories().forEach(category -> {
            dataSeries.add(
                    new DataSeriesItem(category.getName(), category.getGearItemCount())
            )
        });
        */
        service.findAllCategories().forEach(category -> dataSeries.add(new DataSeriesItem(category.getName(), category.getGearItemCount())));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }



}
