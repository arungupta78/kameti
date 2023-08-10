package com.demo.entity;

import static jakarta.persistence.GenerationType.AUTO;
import static java.util.Collections.singletonList;

import com.demo.model.Role;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
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
  @ToString.Exclude
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    DemoUser demoUser = (DemoUser) o;
    return id != null && Objects.equals(id, demoUser.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
