package com.pedidosapp.api.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pedidosapp.api.utils.Utils;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "customer")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Customer extends AbstractEntity {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_customer", sequenceName = "gen_id_customer", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_customer")
    private Integer id;

    @Column(name = "name", nullable = false, length = 110)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "cnpj", length = 14, unique = true)
    private String cnpj;

    @Column(name = "cpf", length = 11, unique = true)
    private String cpf;

    @Column(name = "contact", length = 20)
    private String contact;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    List<Order> orders;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @PrePersist
    @PreUpdate
    protected void onPersist() {
        if (Utils.isEmpty(active)) this.active = true;
    }

    @Override
    public String getPortugueseClassName() {
        return "cliente";
    }

    @Override
    public String getObjectName() {
        return this.name;
    }
}