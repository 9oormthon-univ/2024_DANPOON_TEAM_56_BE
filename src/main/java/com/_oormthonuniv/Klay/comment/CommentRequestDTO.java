package com._oormthonuniv.Klay.comment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentRequestDTO {
    private Optional<Long> certificationId;
    private Optional<Long> userId;
    private String msg;

    public CommentRequestDTO() {}

    public CommentRequestDTO(Optional<Long> certificationId, Optional<Long> userId, String msg) {
        this.certificationId = certificationId;
        this.userId = userId;
        this.msg = msg;
    }

    public CommentRequestDTO(Optional<Long> certificationId, Optional<Long> userId) {
        this.certificationId = certificationId;
        this.userId = userId;
    }
}
