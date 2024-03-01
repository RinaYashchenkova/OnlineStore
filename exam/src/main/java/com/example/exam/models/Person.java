package com.example.exam.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Логин не может быть пустым.")
    @Size(min = 4,max = 100, message = "Логин должен составлять от 4 до 100 символов.")
    @Column(name = "username", unique = true)
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым.")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Email не может быть пустым")
    @Size(min = 4,max = 50, message = "Email должен быть от 4 до 50 символов.")
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "telephone_number")
    private long telephoneNumber;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "person_role",
            joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Column(name = "account_active")
    private boolean accountActive;

    @Column(name = "date_of_registration")
    private LocalDateTime dateOfRegistration;

    @OneToMany(mappedBy = "person")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Product> products;

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                '}';
    }
}
