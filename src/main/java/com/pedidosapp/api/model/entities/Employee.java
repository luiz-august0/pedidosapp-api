package com.pedidosapp.api.model.entities;

import com.pedidosapp.api.utils.Utils;
import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    @OneToOne
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
}