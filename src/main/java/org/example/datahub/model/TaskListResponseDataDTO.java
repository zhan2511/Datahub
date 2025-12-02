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
import org.example.datahub.model.TaskItemDTO;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TaskListResponseDataDTO
 */

@JsonTypeName("TaskListResponse_data")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-02T11:19:17.400856484+08:00[Asia/Shanghai]")
public class TaskListResponseDataDTO {

  @Valid
  private List<@Valid TaskItemDTO> tasks;

  private PageInfoDTO page;

  public TaskListResponseDataDTO tasks(List<@Valid TaskItemDTO> tasks) {
    this.tasks = tasks;
    return this;
  }

  public TaskListResponseDataDTO addTasksItem(TaskItemDTO tasksItem) {
    if (this.tasks == null) {
      this.tasks = new ArrayList<>();
    }
    this.tasks.add(tasksItem);
    return this;
  }

  /**
   * Get tasks
   * @return tasks
  */
  @Valid 
  @Schema(name = "tasks", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tasks")
  public List<@Valid TaskItemDTO> getTasks() {
    return tasks;
  }

  public void setTasks(List<@Valid TaskItemDTO> tasks) {
    this.tasks = tasks;
  }

  public TaskListResponseDataDTO page(PageInfoDTO page) {
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
    TaskListResponseDataDTO taskListResponseData = (TaskListResponseDataDTO) o;
    return Objects.equals(this.tasks, taskListResponseData.tasks) &&
        Objects.equals(this.page, taskListResponseData.page);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tasks, page);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskListResponseDataDTO {\n");
    sb.append("    tasks: ").append(toIndentedString(tasks)).append("\n");
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

