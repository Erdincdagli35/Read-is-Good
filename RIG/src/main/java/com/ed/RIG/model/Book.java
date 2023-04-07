package com.ed.RIG.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer pageNumber;

    private Integer stock;

    private Double price;

    private Integer piece;

    @ManyToMany(mappedBy = "books")
    Set<OnlineOrder> onlineOrders;
}