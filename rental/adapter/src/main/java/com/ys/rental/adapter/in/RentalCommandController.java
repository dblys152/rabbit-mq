package com.ys.rental.adapter.in;

import com.ys.shared.utils.ApiResponseModel;
import com.ys.shared.utils.CommandFactory;
import com.ys.rental.adapter.in.model.RentalModel;
import com.ys.rental.application.port.in.DoCancelUseCase;
import com.ys.rental.application.port.in.DoRentalRequest;
import com.ys.rental.application.port.in.DoRentalUseCase;
import com.ys.rental.application.port.in.DoReturnUseCase;
import com.ys.rental.domain.DoRentalCommand;
import com.ys.rental.domain.Rental;
import com.ys.rental.domain.RentalId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/rentals")
@RequiredArgsConstructor
public class RentalCommandController {
    private final CommandFactory<DoRentalRequest, DoRentalCommand> doRentalCommandFactory;
    private final DoRentalUseCase doRentalUseCase;
    private final DoCancelUseCase doCancelUseCase;
    private final DoReturnUseCase doReturnUseCase;

    @PostMapping("")
    public ResponseEntity<ApiResponseModel<RentalModel>> doRental(@Valid @RequestBody DoRentalRequest request) {
        DoRentalCommand command = doRentalCommandFactory.create(request);

        Rental rental = doRentalUseCase.doRental(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseModel.success(
                HttpStatus.CREATED.value(), RentalModel.fromDomain(rental)));
    }

    @PatchMapping("/{rentalId}/cancel")
    public ResponseEntity<ApiResponseModel<RentalModel>> doCancel(@PathVariable("rentalId") long rentalId) {
        Rental rental = doCancelUseCase.doCancel(RentalId.of(rentalId));

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseModel.success(
                HttpStatus.OK.value(), RentalModel.fromDomain(rental)));
    }

    @PatchMapping("/{rentalId}/return")
    public ResponseEntity<ApiResponseModel<RentalModel>> doReturn(@PathVariable("rentalId") long rentalId) {
        Rental rental = doReturnUseCase.doReturn(RentalId.of(rentalId));

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseModel.success(
                HttpStatus.OK.value(), RentalModel.fromDomain(rental)));
    }
}
