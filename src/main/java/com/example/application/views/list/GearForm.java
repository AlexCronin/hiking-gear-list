package com.example.application.views.list;

import com.example.application.data.Category;
import com.example.application.data.Gear;
import com.example.application.data.Status;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class GearForm extends FormLayout {
        Binder<Gear> binder = new BeanValidationBinder<>(Gear.class);

        TextField brand = new TextField("Brand");
        TextField item = new TextField("Item");
        TextField weight = new TextField("Weight");
        ComboBox<Status> status = new ComboBox<>("Status");
        ComboBox<Category> category = new ComboBox<>("Category");

        Button save = new Button("Save");
        Button delete = new Button("Delete");
        Button cancel = new Button("Cancel");
        private Gear gear;

    public GearForm(List<Category> categories, List<Status> statuses) {
            addClassName("gear-form");

            binder.bindInstanceFields(this);

            category.setItems(categories);
            category.setItemLabelGenerator(Category::getName);

            status.setItems(statuses);
            status.setItemLabelGenerator(Status::getName);

            add(
                    brand,
                    item,
                    weight,
                    category,
                    status,
                    createButtonLayout()
            );
        }

        public void setGear(Gear gear) {
            this.gear = gear;
            binder.readBean(gear);
        }

        private Component createButtonLayout() {
            save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
            cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            save.addClickListener(event -> validateAndSave());
            delete.addClickListener(event -> fireEvent(new DeleteEvent(this, gear)));
            cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

            save.addClickShortcut(Key.ENTER);
            cancel.addClickShortcut(Key.ESCAPE);

            return new HorizontalLayout(save, delete, cancel);
        }

    private void validateAndSave() {
        try {
            binder.writeBean(gear);
            fireEvent(new SaveEvent(this, gear));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    // Events
    public static abstract class GearFormEvent extends ComponentEvent<GearForm> {
        private Gear gear;

        protected GearFormEvent(GearForm source, Gear gear) {
            super(source, false);
            this.gear = gear;
        }

        public Gear getGear() {
            return gear;
        }
    }

    public static class SaveEvent extends GearFormEvent {
        SaveEvent(GearForm source, Gear gear) {
            super(source, gear);
        }
    }

    public static class DeleteEvent extends GearFormEvent {
        DeleteEvent(GearForm source, Gear contact) {
            super(source, contact);
        }

    }

    public static class CloseEvent extends GearFormEvent {
        CloseEvent(GearForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }

}
