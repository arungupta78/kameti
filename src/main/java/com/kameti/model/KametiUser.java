package com.kameti.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KametiUser {


    @Id
    @GeneratedValue(strategy = AUTO)
    Long id;
    String name;
    String email;
    String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
}
