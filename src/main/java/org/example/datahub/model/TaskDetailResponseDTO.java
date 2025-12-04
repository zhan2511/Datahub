package org.example.datahub.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.example.datahub.model.TaskItemDTO;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TaskDetailResponseDTO
 */

@JsonTypeName("TaskDetailResponse")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-04T12:21:25.408590772+08:00[Asia/Shanghai]")
public class TaskDetailResponseDTO {

  private Boolean success;

  private TaskItemDTO data;

  private String message;

  public TaskDetailResponseDTO success(Boolean success) {
    this.success = success;
    return this;
  }

  /**
   * Get success
   * @return success
  */
  
  @Schema(name = "success", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("success")
  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public TaskDetailResponseDTO data(TaskItemDTO data) {
    this.data = data;
    return this;
  }

  /**
   * Get data
   * @return data
  */
  @Valid 
  @Schema(name = "data", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("data")
  public TaskItemDTO getData() {
    return data;
  }

  public void setData(TaskItemDTO data) {
    this.data = data;
  }

  public TaskDetailResponseDTO message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  */
  
  @Schema(name = "message", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskDetailResponseDTO taskDetailResponse = (TaskDetailResponseDTO) o;
    return Objects.equals(this.success, taskDetailResponse.success) &&
        Objects.equals(this.data, taskDetailResponse.data) &&
        Objects.equals(this.message, taskDetailResponse.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, data, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskDetailResponseDTO {\n");
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

