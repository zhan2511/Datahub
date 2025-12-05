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
 * SubmissionStatusItemDTO
 */

@JsonTypeName("SubmissionStatusItem")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-05T18:33:26.059321132+08:00[Asia/Shanghai]")
public class SubmissionStatusItemDTO {

  private String status;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime submittedAt;

  private Long attachmentFileId;

  private Long submissionId;

  public SubmissionStatusItemDTO status(String status) {
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

  public SubmissionStatusItemDTO submittedAt(OffsetDateTime submittedAt) {
    this.submittedAt = submittedAt;
    return this;
  }

  /**
   * Get submittedAt
   * @return submittedAt
  */
  @Valid 
  @Schema(name = "submitted_at", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("submitted_at")
  public OffsetDateTime getSubmittedAt() {
    return submittedAt;
  }

  public void setSubmittedAt(OffsetDateTime submittedAt) {
    this.submittedAt = submittedAt;
  }

  public SubmissionStatusItemDTO attachmentFileId(Long attachmentFileId) {
    this.attachmentFileId = attachmentFileId;
    return this;
  }

  /**
   * Get attachmentFileId
   * @return attachmentFileId
  */
  
  @Schema(name = "attachment_file_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("attachment_file_id")
  public Long getAttachmentFileId() {
    return attachmentFileId;
  }

  public void setAttachmentFileId(Long attachmentFileId) {
    this.attachmentFileId = attachmentFileId;
  }

  public SubmissionStatusItemDTO submissionId(Long submissionId) {
    this.submissionId = submissionId;
    return this;
  }

  /**
   * Get submissionId
   * @return submissionId
  */
  
  @Schema(name = "submission_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("submission_id")
  public Long getSubmissionId() {
    return submissionId;
  }

  public void setSubmissionId(Long submissionId) {
    this.submissionId = submissionId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubmissionStatusItemDTO submissionStatusItem = (SubmissionStatusItemDTO) o;
    return Objects.equals(this.status, submissionStatusItem.status) &&
        Objects.equals(this.submittedAt, submissionStatusItem.submittedAt) &&
        Objects.equals(this.attachmentFileId, submissionStatusItem.attachmentFileId) &&
        Objects.equals(this.submissionId, submissionStatusItem.submissionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, submittedAt, attachmentFileId, submissionId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubmissionStatusItemDTO {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    submittedAt: ").append(toIndentedString(submittedAt)).append("\n");
    sb.append("    attachmentFileId: ").append(toIndentedString(attachmentFileId)).append("\n");
    sb.append("    submissionId: ").append(toIndentedString(submissionId)).append("\n");
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

