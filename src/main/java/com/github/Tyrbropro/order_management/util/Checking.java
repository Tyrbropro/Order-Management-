package com.github.Tyrbropro.order_management.util;

import com.github.Tyrbropro.order_management.entity.Customer;
import com.github.Tyrbropro.order_management.entity.Order;
import org.springframework.security.access.AccessDeniedException;

public class Checking {
    public static void checkCurrentCustomer(Customer currentCustomer, Order order) {
        if (!order.getCustomer().getId().equals(currentCustomer.getId())) {
            throw new AccessDeniedException("You can cancel only your own orders");
        }
    }
}
