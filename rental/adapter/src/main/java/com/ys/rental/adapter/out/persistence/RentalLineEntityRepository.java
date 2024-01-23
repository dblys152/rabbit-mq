package com.ys.rental.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalLineEntityRepository extends JpaRepository<RentalLineEntity, RentalLineEntity.RentalLinePk> {
    List<RentalLineEntity> findAllByRentalEntity_rentalId(Long rentalId);
}
