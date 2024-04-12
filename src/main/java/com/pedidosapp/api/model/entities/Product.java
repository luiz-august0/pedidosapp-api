package com.pedidosapp.api.model.entities;

import com.pedidosapp.api.model.enums.EnumUnitProduct;
import com.pedidosapp.api.utils.Utils;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "product")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Product extends AbstractEntity {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_product", sequenceName = "gen_id_product", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_product")
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false, length = 50)
    private EnumUnitProduct unit;

    @Column(nullable = false, name = "unitary_value")
    private BigDecimal unitaryValue;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductSupplier> productSuppliers;

    @PrePersist
    @PreUpdate
    protected void onPersist() {
        if (Utils.isEmpty(active)) this.active = true;
    }

    @Override
    public String getPortugueseClassName() {
        return "produto";
    }
}