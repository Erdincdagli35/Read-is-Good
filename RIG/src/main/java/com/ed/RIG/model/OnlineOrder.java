package com.ed.RIG.model;

import com.ed.RIG.constant.Month;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class OnlineOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String detail;

    private Instant startDate;

    private Instant endDate;

    @Enumerated(EnumType.STRING)
    private Month month;

    @JsonIgnore
    @ManyToOne(targetEntity = Customer.class, cascade = {CascadeType.ALL})
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "online_order_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "online_order_id"))
    Set<Book> books = new HashSet<>();
}
