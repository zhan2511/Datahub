package org.example.datahub.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TaskMetadataDTO
 */

@JsonTypeName("TaskMetadata")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-02T11:19:17.400856484+08:00[Asia/Shanghai]")
public class TaskMetadataDTO {

  private String taskName;

  private String description;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime deadline;

  private Long deptId;

  public TaskMetadataDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TaskMetadataDTO(String taskName, OffsetDateTime deadline, Long deptId) {
    this.taskName = taskName;
    this.deadline = deadline;
    this.deptId = deptId;
  }

  public TaskMetadataDTO taskName(String taskName) {
    this.taskName = taskName;
    return this;
  }

  /**
   * Get taskName
   * @return taskName
  */
  @NotNull 
  @Schema(name = "task_name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("task_name")
  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public TaskMetadataDTO description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TaskMetadataDTO deadline(OffsetDateTime deadline) {
    this.deadline = deadline;
    return this;
  }

  /**
   * Get deadline
   * @return deadline
  */
  @NotNull @Valid 
  @Schema(name = "deadline", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("deadline")
  public OffsetDateTime getDeadline() {
    return deadline;
  }

  public void setDeadline(OffsetDateTime deadline) {
    this.deadline = deadline;
  }

  public TaskMetadataDTO deptId(Long deptId) {
    this.deptId = deptId;
    return this;
  }

  /**
   * Get deptId
   * @return deptId
  */
  @NotNull 
  @Schema(name = "dept_id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("dept_id")
  public Long getDeptId() {
    return deptId;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskMetadataDTO taskMetadata = (TaskMetadataDTO) o;
    return Objects.equals(this.taskName, taskMetadata.taskName) &&
        Objects.equals(this.description, taskMetadata.description) &&
        Objects.equals(this.deadline, taskMetadata.deadline) &&
        Objects.equals(this.deptId, taskMetadata.deptId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskName, description, deadline, deptId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskMetadataDTO {\n");
    sb.append("    taskName: ").append(toIndentedString(taskName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    deadline: ").append(toIndentedString(deadline)).append("\n");
    sb.append("    deptId: ").append(toIndentedString(deptId)).append("\n");
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

