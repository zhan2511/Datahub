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
 * UserVerifyRoleRequestDTO
 */

@JsonTypeName("UserVerifyRoleRequest")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-05T18:33:26.059321132+08:00[Asia/Shanghai]")
public class UserVerifyRoleRequestDTO {

  private String role;

  private String employeeId;

  private String assistantName;

  private String assistantEmail;

  private String emailAppPassword;

  public UserVerifyRoleRequestDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UserVerifyRoleRequestDTO(String role, String employeeId, String assistantName, String assistantEmail, String emailAppPassword) {
    this.role = role;
    this.employeeId = employeeId;
    this.assistantName = assistantName;
    this.assistantEmail = assistantEmail;
    this.emailAppPassword = emailAppPassword;
  }

  public UserVerifyRoleRequestDTO role(String role) {
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

  public UserVerifyRoleRequestDTO employeeId(String employeeId) {
    this.employeeId = employeeId;
    return this;
  }

  /**
   * Get employeeId
   * @return employeeId
  */
  @NotNull 
  @Schema(name = "employee_id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("employee_id")
  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public UserVerifyRoleRequestDTO assistantName(String assistantName) {
    this.assistantName = assistantName;
    return this;
  }

  /**
   * Get assistantName
   * @return assistantName
  */
  @NotNull 
  @Schema(name = "assistant_name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("assistant_name")
  public String getAssistantName() {
    return assistantName;
  }

  public void setAssistantName(String assistantName) {
    this.assistantName = assistantName;
  }

  public UserVerifyRoleRequestDTO assistantEmail(String assistantEmail) {
    this.assistantEmail = assistantEmail;
    return this;
  }

  /**
   * Get assistantEmail
   * @return assistantEmail
  */
  @NotNull 
  @Schema(name = "assistant_email", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("assistant_email")
  public String getAssistantEmail() {
    return assistantEmail;
  }

  public void setAssistantEmail(String assistantEmail) {
    this.assistantEmail = assistantEmail;
  }

  public UserVerifyRoleRequestDTO emailAppPassword(String emailAppPassword) {
    this.emailAppPassword = emailAppPassword;
    return this;
  }

  /**
   * Get emailAppPassword
   * @return emailAppPassword
  */
  @NotNull 
  @Schema(name = "email_app_password", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email_app_password")
  public String getEmailAppPassword() {
    return emailAppPassword;
  }

  public void setEmailAppPassword(String emailAppPassword) {
    this.emailAppPassword = emailAppPassword;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserVerifyRoleRequestDTO userVerifyRoleRequest = (UserVerifyRoleRequestDTO) o;
    return Objects.equals(this.role, userVerifyRoleRequest.role) &&
        Objects.equals(this.employeeId, userVerifyRoleRequest.employeeId) &&
        Objects.equals(this.assistantName, userVerifyRoleRequest.assistantName) &&
        Objects.equals(this.assistantEmail, userVerifyRoleRequest.assistantEmail) &&
        Objects.equals(this.emailAppPassword, userVerifyRoleRequest.emailAppPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, employeeId, assistantName, assistantEmail, emailAppPassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserVerifyRoleRequestDTO {\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    employeeId: ").append(toIndentedString(employeeId)).append("\n");
    sb.append("    assistantName: ").append(toIndentedString(assistantName)).append("\n");
    sb.append("    assistantEmail: ").append(toIndentedString(assistantEmail)).append("\n");
    sb.append("    emailAppPassword: ").append(toIndentedString(emailAppPassword)).append("\n");
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

