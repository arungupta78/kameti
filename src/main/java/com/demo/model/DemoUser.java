package com.demo.model;

import static jakarta.persistence.GenerationType.AUTO;
import static java.util.Collections.singletonList;

import com.demo.token.Token;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
public class DemoUser implements UserDetails {
  @Id
  @GeneratedValue(strategy = AUTO)
  Long id;

  String firstname;
  String lastname;

  @Column(unique = true)
  String email;

  String password;
  Date creationDate;

  @Enumerated(EnumType.STRING)
  Role role;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

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
