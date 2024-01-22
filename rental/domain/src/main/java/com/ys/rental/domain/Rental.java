package com.ys.rental.domain;

import com.ys.rental.refs.user.domain.UserId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Rental {
    @NotNull
    private RentalId rentalId;

    @NotNull
    private UserId userId;

    @NotNull
    private RentalStatus status;

    @Valid
    @NotNull
    private RentalLines rentalLines;

    @Valid
    @NotNull
    private RentalPeriod rentalPeriod;

    private LocalDateTime returnedAt;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime modifiedAt;

    private Long version;

    public static Rental of(
            RentalId rentalId,
            UserId userId,
            RentalStatus status,
            RentalLines rentalLines,
            RentalPeriod rentalPeriod,
            LocalDateTime returnedAt,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            Long version
    ) {
        return new Rental(rentalId, userId, status, rentalLines, rentalPeriod, returnedAt, createdAt, modifiedAt, version);
    }

    public Rental(
            RentalId rentalId,
            UserId userId,
            RentalStatus status,
            RentalLines rentalLines,
            RentalPeriod rentalPeriod
    ) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.status = status;
        this.rentalLines = rentalLines;
        this.rentalPeriod = rentalPeriod;
    }

    public static Rental create(RentalId rentalId, DoRentalCommand command) {
        return new Rental(
                rentalId,
                command.getUserId(),
                RentalStatus.RENTED,
                command.getRentalLines(),
                command.getRentalPeriod()
        );
    }

    public RentalLines changeRentalLines(List<RentalLine> rentalLineList) {
        this.rentalLines = RentalLines.of(rentalLineList);
        return this.rentalLines;
    }

    public void doReturn(DoReturnCommand command) {
        if (this.status != RentalStatus.RENTED) {
            throw new IllegalStateException("반납은 대여중 상태에서만 가능합니다.");
        }
        if (command.getReturnedAt().isBefore(this.rentalPeriod.getStartedAt())) {
            throw new IllegalArgumentException("반납일이 대여일보다 이전 일 수 없습니다.");
        }
        this.status = RentalStatus.RETURNED;
        this.returnedAt = command.getReturnedAt();
        this.modifiedAt = LocalDateTime.now();
    }

    public void doCancel() {
        if (this.status != RentalStatus.RENTED) {
            throw new IllegalStateException("취소는 대여중 상태에서만 가능합니다.");
        }
        this.status = RentalStatus.CANCELED;
        this.modifiedAt = LocalDateTime.now();
    }
}
