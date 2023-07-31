package com.ys.rental.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalEntityRepository extends JpaRepository<RentalEntity, Integer> {
    
}
