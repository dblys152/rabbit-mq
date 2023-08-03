package com.ys.rental.application.port.in;

import com.ys.rental.domain.RentalLines;
import com.ys.rental.refs.user.domain.UserId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateRentalRequest {

    @NotNull
    private Integer userId;
    @NotNull
    private List<CreateRentalLineRequest> rentalLineList;
    @NotNull
    private LocalDateTime rentedAt;
    @NotNull
    private LocalDateTime scheduledReturnAt;

    @Data
    public static class CreateRentalLineRequest {
        private Integer productId;
        private int price;
        private int quantity;
    }
}
