package com.jeevankumar.app.postgres.controller;

import com.jeevankumar.app.postgres.model.PurchaseOrder;
import com.jeevankumar.app.postgres.service.PurchaseOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    public ResponseEntity<PurchaseOrder> createOrder(@RequestBody CreateOrderRequest request) {
        if (request.description() == null || request.description().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order description must not be blank");
        }

        PurchaseOrder order = purchaseOrderService.createOrder(request.description().trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    public record CreateOrderRequest(String description) {
    }
}
