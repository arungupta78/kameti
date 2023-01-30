package com.kameti.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @NotBlank(message = "Firstname shouldn't be empty")
  String firstname;

  String lastname;

  @Email(message = "Invalid email id")
  String email;

  @NotBlank(message = "Password cannot be empty")
  String password;
}
