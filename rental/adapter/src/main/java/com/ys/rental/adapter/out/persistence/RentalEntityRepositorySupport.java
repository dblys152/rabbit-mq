package com.ys.rental.adapter.out.persistence;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RentalEntityRepositorySupport {
    @PersistenceContext
    private EntityManager entityManager;
    private final JPAQueryFactory queryFactory;
    private final QRentalEntity rentalEntity;
    private final QRentalLineEntity rentalLineEntity;

    public RentalEntityRepositorySupport(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.rentalEntity = QRentalEntity.rentalEntity;
        this.rentalLineEntity = QRentalLineEntity.rentalLineEntity;
    }

    public Optional<RentalEntity> findById(Long rentalId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(rentalEntity.rentalId.eq(rentalId));

        return Optional.ofNullable(queryFactory.selectFrom(rentalEntity)
                .innerJoin(rentalLineEntity)
                .on(rentalEntity.rentalId.eq(rentalLineEntity.rentalEntity.rentalId))
                .fetchJoin()
                .where(builder)
                .fetchOne());
    }
}
