package com.app.drrim.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Document
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String name;
    private String email;
    private String cpf;
    private String telephone;
    private String password;

    @DBRef(lazy=true)
    private List<Post> posts = new ArrayList<>();

    @DBRef(lazy = true)
    private List<Gallery> gallery = new ArrayList<>();

    private List<Medication> medication = new ArrayList<>();
    private List<Scheduling> scheduling = new ArrayList<>();

    public User(String id, String name, String email, String cpf, String telephone, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.telephone = telephone;
        this.password = password;
    }
}
