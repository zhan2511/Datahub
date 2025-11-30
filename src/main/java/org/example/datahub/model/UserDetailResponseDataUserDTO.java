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
 * UserDetailResponseDataUserDTO
 */

@JsonTypeName("UserDetailResponse_data_user")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-30T19:20:05.560758775+08:00[Asia/Shanghai]")
public class UserDetailResponseDataUserDTO {

  private Long userId;

  private String username;

  private String userEmail;

  private String role;

  private Long assistantId;

  public UserDetailResponseDataUserDTO userId(Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  */
  
  @Schema(name = "user_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("user_id")
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public UserDetailResponseDataUserDTO username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
  */
  
  @Schema(name = "username", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UserDetailResponseDataUserDTO userEmail(String userEmail) {
    this.userEmail = userEmail;
    return this;
  }

  /**
   * Get userEmail
   * @return userEmail
  */
  
  @Schema(name = "user_email", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("user_email")
  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public UserDetailResponseDataUserDTO role(String role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   * @return role
  */
  
  @Schema(name = "role", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("role")
  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public UserDetailResponseDataUserDTO assistantId(Long assistantId) {
    this.assistantId = assistantId;
    return this;
  }

  /**
   * Get assistantId
   * @return assistantId
  */
  
  @Schema(name = "assistant_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("assistant_id")
  public Long getAssistantId() {
    return assistantId;
  }

  public void setAssistantId(Long assistantId) {
    this.assistantId = assistantId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDetailResponseDataUserDTO userDetailResponseDataUser = (UserDetailResponseDataUserDTO) o;
    return Objects.equals(this.userId, userDetailResponseDataUser.userId) &&
        Objects.equals(this.username, userDetailResponseDataUser.username) &&
        Objects.equals(this.userEmail, userDetailResponseDataUser.userEmail) &&
        Objects.equals(this.role, userDetailResponseDataUser.role) &&
        Objects.equals(this.assistantId, userDetailResponseDataUser.assistantId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, username, userEmail, role, assistantId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserDetailResponseDataUserDTO {\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    userEmail: ").append(toIndentedString(userEmail)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    assistantId: ").append(toIndentedString(assistantId)).append("\n");
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

