package com.pedidosapp.api.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "product_supplier")
@EqualsAndHashCode(of = "id", callSuper = false)
public class ProductSupplier extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne
    private Product product;

    @JoinColumn(name = "supplier_id", nullable = false)
    @ManyToOne
    private Supplier supplier;

    @Override
    public String getPortugueseClassName() {
        return "produto/fornecedor";
    }
}