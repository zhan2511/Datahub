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
 * TaskCreateRequestDTO
 */

@JsonTypeName("TaskCreateRequest")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-28T04:05:34.377515333+08:00[Asia/Shanghai]")
public class TaskCreateRequestDTO {

  private String taskName;

  private String description;

  private String templatePath;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime deadline;

  private Long deptId;

  public TaskCreateRequestDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TaskCreateRequestDTO(String taskName, String templatePath, OffsetDateTime deadline, Long deptId) {
    this.taskName = taskName;
    this.templatePath = templatePath;
    this.deadline = deadline;
    this.deptId = deptId;
  }

  public TaskCreateRequestDTO taskName(String taskName) {
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

  public TaskCreateRequestDTO description(String description) {
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

  public TaskCreateRequestDTO templatePath(String templatePath) {
    this.templatePath = templatePath;
    return this;
  }

  /**
   * Get templatePath
   * @return templatePath
  */
  @NotNull 
  @Schema(name = "template_path", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("template_path")
  public String getTemplatePath() {
    return templatePath;
  }

  public void setTemplatePath(String templatePath) {
    this.templatePath = templatePath;
  }

  public TaskCreateRequestDTO deadline(OffsetDateTime deadline) {
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

  public TaskCreateRequestDTO deptId(Long deptId) {
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
    TaskCreateRequestDTO taskCreateRequest = (TaskCreateRequestDTO) o;
    return Objects.equals(this.taskName, taskCreateRequest.taskName) &&
        Objects.equals(this.description, taskCreateRequest.description) &&
        Objects.equals(this.templatePath, taskCreateRequest.templatePath) &&
        Objects.equals(this.deadline, taskCreateRequest.deadline) &&
        Objects.equals(this.deptId, taskCreateRequest.deptId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskName, description, templatePath, deadline, deptId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskCreateRequestDTO {\n");
    sb.append("    taskName: ").append(toIndentedString(taskName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    templatePath: ").append(toIndentedString(templatePath)).append("\n");
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

