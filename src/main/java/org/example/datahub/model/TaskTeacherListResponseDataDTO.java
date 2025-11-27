package org.example.datahub.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.example.datahub.model.PageInfoDTO;
import org.example.datahub.model.TaskTeacherItemDTO;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TaskTeacherListResponseDataDTO
 */

@JsonTypeName("TaskTeacherListResponse_data")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-28T04:05:34.377515333+08:00[Asia/Shanghai]")
public class TaskTeacherListResponseDataDTO {

  private Long taskId;

  @Valid
  private List<@Valid TaskTeacherItemDTO> teachers;

  private PageInfoDTO page;

  public TaskTeacherListResponseDataDTO taskId(Long taskId) {
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

  public TaskTeacherListResponseDataDTO teachers(List<@Valid TaskTeacherItemDTO> teachers) {
    this.teachers = teachers;
    return this;
  }

  public TaskTeacherListResponseDataDTO addTeachersItem(TaskTeacherItemDTO teachersItem) {
    if (this.teachers == null) {
      this.teachers = new ArrayList<>();
    }
    this.teachers.add(teachersItem);
    return this;
  }

  /**
   * Get teachers
   * @return teachers
  */
  @Valid 
  @Schema(name = "teachers", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("teachers")
  public List<@Valid TaskTeacherItemDTO> getTeachers() {
    return teachers;
  }

  public void setTeachers(List<@Valid TaskTeacherItemDTO> teachers) {
    this.teachers = teachers;
  }

  public TaskTeacherListResponseDataDTO page(PageInfoDTO page) {
    this.page = page;
    return this;
  }

  /**
   * Get page
   * @return page
  */
  @Valid 
  @Schema(name = "page", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("page")
  public PageInfoDTO getPage() {
    return page;
  }

  public void setPage(PageInfoDTO page) {
    this.page = page;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskTeacherListResponseDataDTO taskTeacherListResponseData = (TaskTeacherListResponseDataDTO) o;
    return Objects.equals(this.taskId, taskTeacherListResponseData.taskId) &&
        Objects.equals(this.teachers, taskTeacherListResponseData.teachers) &&
        Objects.equals(this.page, taskTeacherListResponseData.page);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskId, teachers, page);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskTeacherListResponseDataDTO {\n");
    sb.append("    taskId: ").append(toIndentedString(taskId)).append("\n");
    sb.append("    teachers: ").append(toIndentedString(teachers)).append("\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
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

