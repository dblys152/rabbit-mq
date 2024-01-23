package com.ys.rental.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalEntityRepository extends JpaRepository<RentalEntity, Long> {
    
}
