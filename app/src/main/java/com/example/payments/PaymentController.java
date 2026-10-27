package com.example.payments;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    record PaymentRequest(long amountCents, String currency) {}

    record Payment(String id, long amountCents, String currency, String status) {}

    // In memory on purpose: the project is about the platform, not the database.
    private final Map<String, Payment> payments = new ConcurrentHashMap<>();

    @PostMapping("/payments")
    public Payment create(@RequestBody PaymentRequest request) {
        var payment = new Payment(UUID.randomUUID().toString(), request.amountCents(), request.currency(), "PAID");
        payments.put(payment.id(), payment);
        return payment;
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<Payment> get(@PathVariable String id) {
        var payment = payments.get(id);
        return payment == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(payment);
    }
}
