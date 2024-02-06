package com.ys.rental.adapter.out.persistence;

import com.ys.rental.domain.*;
import com.ys.rental.refs.user.domain.UserId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "RENTALS")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class RentalEntity {
    @Id
    @Column(name = "RENTAL_ID")
    private Long rentalId;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    @OneToMany(mappedBy = "rentalEntity", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<RentalLineEntity> rentalLineList;

    @Column(name = "STARTED_AT", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ENDED_AT", nullable = false)
    private LocalDateTime endedAt;

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

    private void setRentalLineList(List<RentalLineEntity> rentalLineList) {
        this.rentalLineList = rentalLineList;
    }

    public static RentalEntity fromDomain(Rental rental) {
        RentalLines rentalLines = rental.getRentalLines();
        RentalPeriod rentalPeriod = rental.getRentalPeriod();
        RentalEntity rentalEntity = new RentalEntity(
                rental.getRentalId().get(),
                rental.getUserId().get(),
                rental.getStatus(),
                null,
                rentalPeriod.getStartedAt(),
                rentalPeriod.getEndedAt(),
                rental.getReturnedAt(),
                rental.getCreatedAt(),
                rental.getModifiedAt(),
                rental.getVersion()
        );

        rentalEntity.setRentalLineList(rentalLines.getItems().stream()
                .map(rentalLine -> RentalLineEntity.fromDomain(rentalEntity, rentalLine))
                .toList());

        return rentalEntity;
    }

    public Rental toDomain() {
        return Rental.of(
                RentalId.of(this.rentalId),
                UserId.of(this.userId),
                this.status,
                RentalLines.of(this.rentalLineList.stream()
                        .map(rentalLineEntity -> rentalLineEntity.toDomain())
                        .toList()),
                RentalPeriod.of(this.startedAt, this.endedAt),
                this.returnedAt,
                this.createdAt,
                this.modifiedAt,
                this.version
        );
    }
}
