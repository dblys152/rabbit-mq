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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = DataJpaConfig.class)
class RentalLineEntityRepositoryTest extends SupportRentalFixture {
    @Autowired
    private RentalEntityRepository rentalEntityRepository;
    @Autowired
    private RentalLineEntityRepository repository;

    @Test
    void saveAll() {
        Rental rental = Rental.create(RENTAL_ID, new DoRentalCommand(USER_ID, RENTAL_LINES, RENTAL_PERIOD));
        RentalEntity rentalEntity = RentalEntity.fromDomain(rental);
        RentalEntity savedRentalEntity= rentalEntityRepository.save(rentalEntity);

        List<RentalLineEntity> actual = repository.saveAll(savedRentalEntity.getRentalLineList());

        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(savedRentalEntity.getRentalLineList().size());
    }

    @Test
    void findAllByRentalEntity_rentalId() {
        List<RentalLineEntity> actual = repository.findAllByRentalEntity_rentalId(1L);

        assertThat(actual).isNotEmpty();
    }
}