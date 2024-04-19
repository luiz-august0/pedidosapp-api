package com.pedidosapp.api.model.entities;

import com.pedidosapp.api.utils.Utils;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "supplier")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Supplier extends AbstractEntity {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_supplier", sequenceName = "gen_id_supplier", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_supplier")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "social_reason")
    private String socialReason;

    @Column(name = "cnpj", length = 14, unique = true)
    private String cnpj;

    @Column(name = "cpf", length = 11, unique = true)
    private String cpf;

    @Column(name = "contact", length = 20)
    private String contact;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @PrePersist
    @PreUpdate
    protected void onPersist() {
        if (Utils.isEmpty(active)) this.active = true;
    }

    @Override
    public String getPortugueseClassName() {
        return "fornecedor";
    }
}