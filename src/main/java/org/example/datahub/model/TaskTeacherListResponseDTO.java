package org.example.datahub.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.example.datahub.model.TaskTeacherListResponseDataDTO;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TaskTeacherListResponseDTO
 */

@JsonTypeName("TaskTeacherListResponse")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-28T04:05:34.377515333+08:00[Asia/Shanghai]")
public class TaskTeacherListResponseDTO {

  private Boolean success;

  private TaskTeacherListResponseDataDTO data;

  public TaskTeacherListResponseDTO success(Boolean success) {
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

  public TaskTeacherListResponseDTO data(TaskTeacherListResponseDataDTO data) {
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
  public TaskTeacherListResponseDataDTO getData() {
    return data;
  }

  public void setData(TaskTeacherListResponseDataDTO data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskTeacherListResponseDTO taskTeacherListResponse = (TaskTeacherListResponseDTO) o;
    return Objects.equals(this.success, taskTeacherListResponse.success) &&
        Objects.equals(this.data, taskTeacherListResponse.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskTeacherListResponseDTO {\n");
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

