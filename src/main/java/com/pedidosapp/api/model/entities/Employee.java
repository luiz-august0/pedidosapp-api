package com.pedidosapp.api.model.entities;

import com.pedidosapp.api.service.AbstractService;
import com.pedidosapp.api.service.EmployeeService;
import com.pedidosapp.api.utils.Utils;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "employee")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Employee extends AbstractEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_employee", sequenceName = "gen_id_employee", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_employee")
    private Integer id;

    @Column(name = "name", length = 110, nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "cpf", length = 11, unique = true)
    private String cpf;

    @Column(name = "contact", length = 20)
    private String contact;

    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @PrePersist
    @PreUpdate
    protected void onPersist() {
        if (Utils.isEmpty(active)) this.active = true;
    }

    @Override
    public String getPortugueseClassName() {
        return "funcionário";
    }

    @Override
    public Class<? extends AbstractService> getServiceClass() {
        return EmployeeService.class;
    }

    @Override
    public String getObjectName() {
        return this.name;
    }

}