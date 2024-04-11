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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(length = 11, unique = true)
    private String cpf;

    @Column(length = 20)
    private String contact;

    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @OneToOne
    private User user;

    @Column(nullable = false)
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