package com.demo.model;

import com.demo.validation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
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
  @Schema(description = "First name of the user")
  @NotBlank(message = "Firstname shouldn't be empty")
  String firstname;

  @Schema(description = "Last name of the user")
  String lastname;

  @Schema(description = "Email ID of the user")
  @Email(message = "Invalid email id")
  String email;

  @Schema(description = "Password")
  @ValidPassword
  String password;
}
