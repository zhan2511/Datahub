package org.example.datahub.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.example.datahub.model.SubmissionStatusItemDTO;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TaskTeacherItemDTO
 */

@JsonTypeName("TaskTeacherItem")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-04T12:21:25.408590772+08:00[Asia/Shanghai]")
public class TaskTeacherItemDTO {

  private Long teacherId;

  private String teacherName;

  private String teacherEmail;

  private SubmissionStatusItemDTO submission;

  public TaskTeacherItemDTO teacherId(Long teacherId) {
    this.teacherId = teacherId;
    return this;
  }

  /**
   * Get teacherId
   * @return teacherId
  */
  
  @Schema(name = "teacher_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("teacher_id")
  public Long getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(Long teacherId) {
    this.teacherId = teacherId;
  }

  public TaskTeacherItemDTO teacherName(String teacherName) {
    this.teacherName = teacherName;
    return this;
  }

  /**
   * Get teacherName
   * @return teacherName
  */
  
  @Schema(name = "teacher_name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("teacher_name")
  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public TaskTeacherItemDTO teacherEmail(String teacherEmail) {
    this.teacherEmail = teacherEmail;
    return this;
  }

  /**
   * Get teacherEmail
   * @return teacherEmail
  */
  
  @Schema(name = "teacher_email", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("teacher_email")
  public String getTeacherEmail() {
    return teacherEmail;
  }

  public void setTeacherEmail(String teacherEmail) {
    this.teacherEmail = teacherEmail;
  }

  public TaskTeacherItemDTO submission(SubmissionStatusItemDTO submission) {
    this.submission = submission;
    return this;
  }

  /**
   * Get submission
   * @return submission
  */
  @Valid 
  @Schema(name = "submission", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("submission")
  public SubmissionStatusItemDTO getSubmission() {
    return submission;
  }

  public void setSubmission(SubmissionStatusItemDTO submission) {
    this.submission = submission;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskTeacherItemDTO taskTeacherItem = (TaskTeacherItemDTO) o;
    return Objects.equals(this.teacherId, taskTeacherItem.teacherId) &&
        Objects.equals(this.teacherName, taskTeacherItem.teacherName) &&
        Objects.equals(this.teacherEmail, taskTeacherItem.teacherEmail) &&
        Objects.equals(this.submission, taskTeacherItem.submission);
  }

  @Override
  public int hashCode() {
    return Objects.hash(teacherId, teacherName, teacherEmail, submission);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskTeacherItemDTO {\n");
    sb.append("    teacherId: ").append(toIndentedString(teacherId)).append("\n");
    sb.append("    teacherName: ").append(toIndentedString(teacherName)).append("\n");
    sb.append("    teacherEmail: ").append(toIndentedString(teacherEmail)).append("\n");
    sb.append("    submission: ").append(toIndentedString(submission)).append("\n");
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

