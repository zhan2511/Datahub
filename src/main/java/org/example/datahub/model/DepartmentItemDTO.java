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
 * DepartmentItemDTO
 */

@JsonTypeName("DepartmentItem")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-04T09:26:02.924376840+08:00[Asia/Shanghai]")
public class DepartmentItemDTO {

  private Long deptId;

  private String deptName;

  public DepartmentItemDTO deptId(Long deptId) {
    this.deptId = deptId;
    return this;
  }

  /**
   * Get deptId
   * @return deptId
  */
  
  @Schema(name = "dept_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dept_id")
  public Long getDeptId() {
    return deptId;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }

  public DepartmentItemDTO deptName(String deptName) {
    this.deptName = deptName;
    return this;
  }

  /**
   * Get deptName
   * @return deptName
  */
  
  @Schema(name = "dept_name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dept_name")
  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DepartmentItemDTO departmentItem = (DepartmentItemDTO) o;
    return Objects.equals(this.deptId, departmentItem.deptId) &&
        Objects.equals(this.deptName, departmentItem.deptName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deptId, deptName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DepartmentItemDTO {\n");
    sb.append("    deptId: ").append(toIndentedString(deptId)).append("\n");
    sb.append("    deptName: ").append(toIndentedString(deptName)).append("\n");
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

