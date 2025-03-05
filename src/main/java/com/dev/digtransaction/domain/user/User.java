package com.dev.digtransaction.domain.user;

import com.dev.digtransaction.domain.account.Account;
import com.dev.digtransaction.dto.UserRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "users")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToOne(mappedBy = "owner")
    @JsonBackReference
    private Account account;

    /**
     * IMPORTANT:
     * This constructor is used for test purposes.
     * It should not be used to any other reason.
     */
    public User(Long id, String firstName, String lastName, String document, String email,
                String password, UserType userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.document = document;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public User(UserRequest userDTO) {
        this.firstName = userDTO.firstName();
        this.lastName = userDTO.lastName();
        this.document = userDTO.document();
        this.email = userDTO.email();
        this.password = userDTO.password();
        this.userType = userDTO.userType();
    }
}
