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
 * UserVerifyRoleResponseDataDTO
 */

@JsonTypeName("UserVerifyRoleResponse_data")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-04T12:21:25.408590772+08:00[Asia/Shanghai]")
public class UserVerifyRoleResponseDataDTO {

  private String role;

  private Long assistantId;

  public UserVerifyRoleResponseDataDTO role(String role) {
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

  public UserVerifyRoleResponseDataDTO assistantId(Long assistantId) {
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
    UserVerifyRoleResponseDataDTO userVerifyRoleResponseData = (UserVerifyRoleResponseDataDTO) o;
    return Objects.equals(this.role, userVerifyRoleResponseData.role) &&
        Objects.equals(this.assistantId, userVerifyRoleResponseData.assistantId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, assistantId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserVerifyRoleResponseDataDTO {\n");
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

