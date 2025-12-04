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
 * TaskSubmissionExportResponseDataDTO
 */

@JsonTypeName("TaskSubmissionExportResponse_data")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-04T12:21:25.408590772+08:00[Asia/Shanghai]")
public class TaskSubmissionExportResponseDataDTO {

  private Long fileId;

  public TaskSubmissionExportResponseDataDTO fileId(Long fileId) {
    this.fileId = fileId;
    return this;
  }

  /**
   * Get fileId
   * @return fileId
  */
  
  @Schema(name = "file_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("file_id")
  public Long getFileId() {
    return fileId;
  }

  public void setFileId(Long fileId) {
    this.fileId = fileId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskSubmissionExportResponseDataDTO taskSubmissionExportResponseData = (TaskSubmissionExportResponseDataDTO) o;
    return Objects.equals(this.fileId, taskSubmissionExportResponseData.fileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskSubmissionExportResponseDataDTO {\n");
    sb.append("    fileId: ").append(toIndentedString(fileId)).append("\n");
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

