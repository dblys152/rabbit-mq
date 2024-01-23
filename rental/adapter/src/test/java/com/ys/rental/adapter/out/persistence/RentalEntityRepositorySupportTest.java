package com.ys.rental.adapter.out.persistence;

import com.ys.rental.adapter.config.DataJpaConfig;
import com.ys.rental.adapter.out.persistence.fixture.SupportRentalFixture;
import com.ys.rental.domain.DoRentalCommand;
import com.ys.rental.domain.Rental;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = DataJpaConfig.class)
class RentalEntityRepositorySupportTest extends SupportRentalFixture {
    @Autowired
    private EntityManager entityManager;
    private RentalEntityRepositorySupport repositorySupport;
    @Autowired
    private RentalEntityRepository repository;
    @Autowired
    private RentalLineEntityRepository rentalLineRepository;

    @BeforeEach
    void setUp() {
        this.repositorySupport = new RentalEntityRepositorySupport(entityManager);
    }

    @Test
    void findById() {
        Rental rental = Rental.create(RENTAL_ID, new DoRentalCommand(USER_ID, RENTAL_LINES, RENTAL_PERIOD));
        RentalEntity entity = RentalEntity.fromDomain(rental);
        RentalEntity savedEntity = repository.save(entity);
        rentalLineRepository.saveAll(savedEntity.getRentalLineList());

        Optional<RentalEntity> actual = repositorySupport.findById(savedEntity.getRentalId());

        assertThat(actual).isPresent();
    }
}