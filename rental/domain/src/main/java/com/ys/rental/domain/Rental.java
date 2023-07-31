package com.ys.rental.domain;

import com.ys.rental.refs.user.domain.UserId;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Rental {

    private static final LocalDateTime NOW = LocalDateTime.now();

    private RentalId rentalId;
    private UserId userId;
    private RentalStatus status;
    private RentalLines rentalLines;
    private LocalDateTime rentedAt;
    private LocalDateTime scheduledReturnAt;
    private LocalDateTime returnedAt;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
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
            UserId userId,
            RentalStatus status,
            RentalLines rentalLines,
            LocalDateTime rentedAt,
            LocalDateTime scheduledReturnAt
    ) {
        this.userId = userId;
        this.status = status;
        this.rentalLines = rentalLines;
        this.rentedAt = rentedAt;
        this.scheduledReturnAt = scheduledReturnAt;
        scheduleValidation();
    }

    public static Rental create(CreateRentalCommand command) {
        return new Rental(
                command.getUserId(),
                RentalStatus.RENTED,
                command.getRentalLines(),
                command.getRentedAt(),
                command.getScheduledReturnAt()
        );
    }

    public RentalLines changeRentalLines(List<RentalLine> rentalLineList) {
        this.rentalLines = RentalLines.of(rentalLineList);
        return this.rentalLines;
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
