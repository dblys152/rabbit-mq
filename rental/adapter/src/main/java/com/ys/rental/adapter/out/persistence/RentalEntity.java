package com.ys.rental.adapter.out.persistence;

import com.ys.rental.domain.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity(name = "RENTAL_LIST")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class RentalEntity extends AbstractAggregateRoot<RentalEntity> {

    private static final LocalDateTime NOW = LocalDateTime.now();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RENTAL_ID")
    private Integer rentalId;

    @Column(name = "USER_ID", nullable = false)
    private Integer userId;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    @OneToMany(mappedBy = "rentalEntity", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<RentalLineEntity> rentalLineList;

    @Column(name = "RENTED_AT", nullable = false)
    private LocalDateTime rentedAt;

    @Column(name = "SCHEDULED_RETURN_AT", nullable = false)
    private LocalDateTime scheduledReturnAt;

    @Column(name = "RETURNED_AT")
    private LocalDateTime returnedAt;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "MODIFIED_AT", nullable = false)
    private LocalDateTime modifiedAt;
    @Version
    @Column(name = "VERSION")
    private Long version;

    public static RentalEntity fromDomain(Rental rental) {
        return new RentalEntity(
                Optional.ofNullable(rental.getRentalId())
                        .map(RentalId::getId)
                        .orElse(null),
                rental.getUserId().getId(),
                rental.getStatus(),
                rental.getRentalLines().getItems().stream()
                        .map(RentalLineEntity::fromDomain)
                        .toList(),
                rental.getRentedAt(),
                rental.getScheduledReturnAt(),
                rental.getReturnedAt(),
                rental.getCreatedAt(),
                rental.getModifiedAt(),
                rental.getVersion()
        );
    }
}
