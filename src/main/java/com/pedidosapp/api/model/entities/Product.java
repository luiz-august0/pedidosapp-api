package com.pedidosapp.api.model.entities;

import com.pedidosapp.api.model.enums.EnumUnitProduct;
import com.pedidosapp.api.service.AbstractService;
import com.pedidosapp.api.service.ProductService;
import com.pedidosapp.api.utils.Utils;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Data
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

    @JoinTable(
            name = "product_supplier",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "supplier_id", referencedColumnName = "id")}
    )
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private List<Supplier> suppliers;

    @PrePersist
    @PreUpdate
    protected void onPersist() {
        if (Utils.isEmpty(active)) this.active = true;
    }

    @Override
    public String getPortugueseClassName() {
        return "produto";
    }

    @Override
    public Class<? extends AbstractService> getServiceClass() {
        return ProductService.class;
    }

    @Override
    public String getObjectName() {
        return this.description;
    }

}