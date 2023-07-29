package com.ys.rental.domain;

import com.fasterxml.uuid.Generators;
import com.ys.rental.refs.user.domain.UserId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.LocalDateTime;

@Entity(name = "RENTAL_LIST")
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Rental extends AbstractAggregateRoot<Rental> {

    private static final LocalDateTime NOW = LocalDateTime.now();

    @EmbeddedId
    @Column(name = "RENTAL_ID")
    private RentalId rentalId;

    @Column(name = "USER_ID", nullable = false)
    private UserId userId;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    @Embedded
    private RentalLines rentalLines;

    @Column(name = "RENTED_AT", nullable = false)
    private LocalDateTime rentedAt;

    @Column(name = "SCHEDULED_RETURN_AT", nullable = false)
    private LocalDateTime scheduledReturnAt;

    @Column(name = "RETURNED_AT", nullable = false)
    private LocalDateTime returnedAt;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt = NOW;
    @Column(name = "MODIFIED_AT", nullable = false)
    private LocalDateTime modifiedAt = NOW;
    @Version
    @Column(name = "VERSION")
    private Long version;

    public static Rental of(
            RentalId rentalId,
            UserId userId,
            RentalStatus status,
            RentalLines rentalLines,
            LocalDateTime rentedAt,
            LocalDateTime scheduledReturnAt,
            LocalDateTime returnedAt,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            Long version
    ) {
        return new Rental(rentalId, userId, status, rentalLines, rentedAt, scheduledReturnAt, returnedAt, createdAt, modifiedAt, version);
    }

    public Rental(
            RentalId rentalId,
            UserId userId,
            RentalStatus status,
            RentalLines rentalLines,
            LocalDateTime rentedAt,
            LocalDateTime scheduledReturnAt
    ) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.status = status;
        this.rentalLines = rentalLines;
        this.rentedAt = rentedAt;
        this.scheduledReturnAt = scheduledReturnAt;
        scheduleValidation();
    }

    public static Rental create(CreateRentalCommand command) {
        return new Rental(
                command.getRentalId(),
                command.getUserId(),
                RentalStatus.RENTED,
                command.getRentalLines(),
                command.getRentedAt(),
                command.getScheduledReturnAt()
        );
    }

    private void scheduleValidation() {
        if (this.rentedAt.isAfter(this.scheduledReturnAt)) {
            throw new IllegalArgumentException("대여일이 반납 예정일보다 이후 일 수 없습니다.");
        }
    }

    public void doReturn(DoReturnCommand command) {
        if (this.status != RentalStatus.RENTED) {
            throw new IllegalStateException("반납은 대여중 상태에서만 가능합니다.");
        }
        if (command.getReturnedAt().isBefore(this.rentedAt)) {
            throw new IllegalArgumentException("반납일이 대여일보다 이전 일 수 없습니다.");
        }
        this.status = RentalStatus.RETURNED;
        this.returnedAt = command.getReturnedAt();
        this.modifiedAt = NOW;
    }

    public void doCancel() {
        if (this.status != RentalStatus.RENTED) {
            throw new IllegalStateException("취소는 대여중 상태에서만 가능합니다.");
        }
        this.status = RentalStatus.CANCELED;
        this.modifiedAt = NOW;
    }
}
