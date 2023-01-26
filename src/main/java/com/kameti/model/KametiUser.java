package com.kameti.model;

import static jakarta.persistence.GenerationType.AUTO;
import static java.util.Collections.singletonList;

import jakarta.persistence.*;
import java.util.Collection;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KametiUser implements UserDetails {
  @Id
  @GeneratedValue(strategy = AUTO)
  Long id;

  String firstname;
  String lastname;
  String email;
  String password;

  @Enumerated(EnumType.STRING)
  Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return singletonList(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
