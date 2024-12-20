package com.dacs2_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Nationalized
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 100)
    @Nationalized
    @Column(name = "password", length = 100)
    private String password;

    @Size(max = 50)
    @Nationalized
    @Column(name = "name", length = 50)
    private String name;

    @Size(max = 50)
    @Nationalized
    @Column(name = "firstname", length = 50)
    private String firstname;

    @Size(max = 50)
    @Nationalized
    @Column(name = "lastname", length = 50)
    private String lastname;

    @Size(max = 10)
    @Nationalized
    @Column(name = "gender", length = 10)
    private String gender;

    @Size(max = 255)
    @Nationalized
    @Column(name = "address")
    private String address;

    @Size(max = 15)
    @Nationalized
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Size(max = 100)
    @Nationalized
    @Column(name = "activation_code", length = 100)
    private String activationCode;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private Set<Feedback> feedbacks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews = new LinkedHashSet<>();

}