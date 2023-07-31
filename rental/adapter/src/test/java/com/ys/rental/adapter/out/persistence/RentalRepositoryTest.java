package com.ys.rental.adapter.out.persistence;

import com.ys.rental.adapter.config.DataJpaConfig;
import com.ys.rental.adapter.out.persistence.fixture.SupportRentalFixture;
import com.ys.rental.domain.Rental;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = DataJpaConfig.class)
class RentalRepositoryTest extends SupportRentalFixture {

    @Autowired
    private RentalEntityRepository repository;

    @Test
    void save() {
        Rental rental = Rental.create(CREATE_RENTAL_COMMAND);
        RentalEntity entity = RentalEntity.fromDomain(rental);

        RentalEntity actual = repository.save(entity);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getRentalId()).isNotNull(),
                () -> assertThat(actual.getRentalLineList().isEmpty()).isFalse()
        );
    }
}