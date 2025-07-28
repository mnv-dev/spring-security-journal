package com.spring.security.repository;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ReceiptManager {
    private Set<String> receiptList;

    public ReceiptManager() {
        receiptList = new HashSet<>();
    }

    public void add(String receipt) {
        receiptList.add(receipt);
    }

    public boolean contains(String receipt) {
        return receiptList.contains(receipt);
    }
}
