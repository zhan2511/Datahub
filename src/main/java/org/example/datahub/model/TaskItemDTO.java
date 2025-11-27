package org.example.datahub.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.OffsetDateTime;
import org.example.datahub.model.DepartmentItemDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TaskItemDTO
 */

@JsonTypeName("TaskItem")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-28T04:05:34.377515333+08:00[Asia/Shanghai]")
public class TaskItemDTO {

  private Long taskId;

  private String taskName;

  private String description;

  private String templatePath;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime deadline;

  private String status;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createTime;

  private DepartmentItemDTO department;

  public TaskItemDTO taskId(Long taskId) {
    this.taskId = taskId;
    return this;
  }

  /**
   * Get taskId
   * @return taskId
  */
  
  @Schema(name = "task_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("task_id")
  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }

  public TaskItemDTO taskName(String taskName) {
    this.taskName = taskName;
    return this;
  }

  /**
   * Get taskName
   * @return taskName
  */
  
  @Schema(name = "task_name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("task_name")
  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public TaskItemDTO description(String description) {
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

  public TaskItemDTO templatePath(String templatePath) {
    this.templatePath = templatePath;
    return this;
  }

  /**
   * Get templatePath
   * @return templatePath
  */
  
  @Schema(name = "template_path", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("template_path")
  public String getTemplatePath() {
    return templatePath;
  }

  public void setTemplatePath(String templatePath) {
    this.templatePath = templatePath;
  }

  public TaskItemDTO deadline(OffsetDateTime deadline) {
    this.deadline = deadline;
    return this;
  }

  /**
   * Get deadline
   * @return deadline
  */
  @Valid 
  @Schema(name = "deadline", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("deadline")
  public OffsetDateTime getDeadline() {
    return deadline;
  }

  public void setDeadline(OffsetDateTime deadline) {
    this.deadline = deadline;
  }

  public TaskItemDTO status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  
  @Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public TaskItemDTO createTime(OffsetDateTime createTime) {
    this.createTime = createTime;
    return this;
  }

  /**
   * Get createTime
   * @return createTime
  */
  @Valid 
  @Schema(name = "create_time", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("create_time")
  public OffsetDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(OffsetDateTime createTime) {
    this.createTime = createTime;
  }

  public TaskItemDTO department(DepartmentItemDTO department) {
    this.department = department;
    return this;
  }

  /**
   * Get department
   * @return department
  */
  @Valid 
  @Schema(name = "department", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("department")
  public DepartmentItemDTO getDepartment() {
    return department;
  }

  public void setDepartment(DepartmentItemDTO department) {
    this.department = department;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskItemDTO taskItem = (TaskItemDTO) o;
    return Objects.equals(this.taskId, taskItem.taskId) &&
        Objects.equals(this.taskName, taskItem.taskName) &&
        Objects.equals(this.description, taskItem.description) &&
        Objects.equals(this.templatePath, taskItem.templatePath) &&
        Objects.equals(this.deadline, taskItem.deadline) &&
        Objects.equals(this.status, taskItem.status) &&
        Objects.equals(this.createTime, taskItem.createTime) &&
        Objects.equals(this.department, taskItem.department);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskId, taskName, description, templatePath, deadline, status, createTime, department);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskItemDTO {\n");
    sb.append("    taskId: ").append(toIndentedString(taskId)).append("\n");
    sb.append("    taskName: ").append(toIndentedString(taskName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    templatePath: ").append(toIndentedString(templatePath)).append("\n");
    sb.append("    deadline: ").append(toIndentedString(deadline)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
    sb.append("    department: ").append(toIndentedString(department)).append("\n");
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

