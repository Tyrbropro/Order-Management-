package com.github.Tyrbropro.order_management.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Table(name = "customers")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "address")
    String address;

    @Column(name = "password")
    String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Order> orders = new ArrayList<>();

    public enum Role { CUSTOMER, ADMIN }
}