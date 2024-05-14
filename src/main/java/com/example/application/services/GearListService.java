package com.example.application.services;

import com.example.application.data.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GearListService {

    private final GearRepository gearRepository;
    private final CategoryRepository categoryRepository;
    private final StatusRepository statusRepository;

    public GearListService(GearRepository gearRepository,
                           CategoryRepository categoryRepository,
                           StatusRepository statusRepository) {

        this.gearRepository = gearRepository;
        this.categoryRepository = categoryRepository;
        this.statusRepository = statusRepository;
    }

    public List<Gear> findAllGear(String filterText) {
        if(filterText == null || filterText.isEmpty()) {
            return gearRepository.findAll();
        } else {
            return gearRepository.search(filterText);
        }
    }

    public long countGear() {
        return gearRepository.count();
    }

    public void deleteGear(Gear gear) {
        gearRepository.delete(gear);
    }

    public void saveGear(Gear gear) {
        if(gear == null) {
            System.err.println("Contact is null");
            return;
        }

        gearRepository.save(gear);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Status> findAllStatuses() {
        return statusRepository.findAll();
    }


}
