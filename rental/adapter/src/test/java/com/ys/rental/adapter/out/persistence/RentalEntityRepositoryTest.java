package com.ys.rental.adapter.out.persistence;

import com.ys.rental.adapter.config.DataJpaConfig;
import com.ys.rental.adapter.out.persistence.fixture.SupportRentalFixture;
import com.ys.rental.domain.DoRentalCommand;
import com.ys.rental.domain.Rental;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = DataJpaConfig.class)
class RentalEntityRepositoryTest extends SupportRentalFixture {
    @Autowired
    private RentalEntityRepository repository;

    @Test
    void save() {
        Rental rental = Rental.create(RENTAL_ID, new DoRentalCommand(USER_ID, RENTAL_LINES, RENTAL_PERIOD));
        RentalEntity entity = RentalEntity.fromDomain(rental);

        RentalEntity actual = repository.save(entity);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getRentalId()).isNotNull()
        );
    }

    @Test
    void 영속성_컨텍스트_1차_캐시로_인한_findById_생략() {
        Rental rental = Rental.create(RENTAL_ID, new DoRentalCommand(USER_ID, RENTAL_LINES, RENTAL_PERIOD));
        RentalEntity entity = RentalEntity.fromDomain(rental);
        RentalEntity savedEntity = repository.save(entity);

        Optional<RentalEntity> actual = repository.findById(savedEntity.getRentalId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(savedEntity);
    }

    @Test
    void findById() {
        Optional<RentalEntity> actual = repository.findById(1L);

        assertThat(actual).isNotEmpty();
    }
}