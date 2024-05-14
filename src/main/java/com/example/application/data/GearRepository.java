package com.example.application.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GearRepository extends JpaRepository<Gear, Long> {

    @Query("select c from Gear c " +
            "where lower(c.brand) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.item) like lower(concat('%', :searchTerm, '%'))")
    List<Gear> search(@Param("searchTerm") String searchTerm);
}
