package com.junioroffers.infrastructure.loginandregister.offer.controller.error;

import org.springframework.http.HttpStatus;

public record OfferErrorResponse(
        String message,
        HttpStatus status) {
}
