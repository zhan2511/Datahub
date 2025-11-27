package org.example.datahub.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.example.datahub.model.DepartmentItemDTO;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * DepartmentListResponseDataDTO
 */

@JsonTypeName("DepartmentListResponse_data")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-28T04:05:34.377515333+08:00[Asia/Shanghai]")
public class DepartmentListResponseDataDTO {

  @Valid
  private List<@Valid DepartmentItemDTO> departments;

  public DepartmentListResponseDataDTO departments(List<@Valid DepartmentItemDTO> departments) {
    this.departments = departments;
    return this;
  }

  public DepartmentListResponseDataDTO addDepartmentsItem(DepartmentItemDTO departmentsItem) {
    if (this.departments == null) {
      this.departments = new ArrayList<>();
    }
    this.departments.add(departmentsItem);
    return this;
  }

  /**
   * Get departments
   * @return departments
  */
  @Valid 
  @Schema(name = "departments", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("departments")
  public List<@Valid DepartmentItemDTO> getDepartments() {
    return departments;
  }

  public void setDepartments(List<@Valid DepartmentItemDTO> departments) {
    this.departments = departments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DepartmentListResponseDataDTO departmentListResponseData = (DepartmentListResponseDataDTO) o;
    return Objects.equals(this.departments, departmentListResponseData.departments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(departments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DepartmentListResponseDataDTO {\n");
    sb.append("    departments: ").append(toIndentedString(departments)).append("\n");
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

