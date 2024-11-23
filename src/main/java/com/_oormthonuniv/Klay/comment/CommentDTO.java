package com._oormthonuniv.Klay.comment;

import com._oormthonuniv.Klay.certification.domain.Comment;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentDTO {
    private Long id;
    private Long userId;
    private Long certificationId;
    private String msg;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUserId();
        this.certificationId = comment.getCertificationId();
        this.msg = comment.getMsg();
        this.createdDate = comment.getCreatedDate();
        this.updatedDate = LocalDateTime.now();
    }
}
