package org.example.datahub.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * UserSignUpRequestDTO
 */

@JsonTypeName("UserSignUpRequest")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-04T12:21:25.408590772+08:00[Asia/Shanghai]")
public class UserSignUpRequestDTO {

  private String username;

  private String password;

  private String userEmail;

  private String role;

  public UserSignUpRequestDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UserSignUpRequestDTO(String username, String password, String userEmail, String role) {
    this.username = username;
    this.password = password;
    this.userEmail = userEmail;
    this.role = role;
  }

  public UserSignUpRequestDTO username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
  */
  @NotNull 
  @Schema(name = "username", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UserSignUpRequestDTO password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
  */
  @NotNull 
  @Schema(name = "password", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserSignUpRequestDTO userEmail(String userEmail) {
    this.userEmail = userEmail;
    return this;
  }

  /**
   * Get userEmail
   * @return userEmail
  */
  @NotNull 
  @Schema(name = "user_email", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("user_email")
  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public UserSignUpRequestDTO role(String role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   * @return role
  */
  @NotNull 
  @Schema(name = "role", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("role")
  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserSignUpRequestDTO userSignUpRequest = (UserSignUpRequestDTO) o;
    return Objects.equals(this.username, userSignUpRequest.username) &&
        Objects.equals(this.password, userSignUpRequest.password) &&
        Objects.equals(this.userEmail, userSignUpRequest.userEmail) &&
        Objects.equals(this.role, userSignUpRequest.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password, userEmail, role);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserSignUpRequestDTO {\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    userEmail: ").append(toIndentedString(userEmail)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

