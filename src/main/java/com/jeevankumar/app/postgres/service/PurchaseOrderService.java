package com.jeevankumar.app.postgres.service;

import com.jeevankumar.app.postgres.model.PurchaseOrder;
import com.jeevankumar.app.postgres.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Transactional("postgresTransactionManager")
    public PurchaseOrder createOrder(String description) {
        return purchaseOrderRepository.save(new PurchaseOrder(description));
    }
}
