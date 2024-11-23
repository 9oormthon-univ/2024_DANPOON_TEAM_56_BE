package com._oormthonuniv.Klay.comment;

import com._oormthonuniv.Klay.certification.dto.CertificationResponseDTO;
import com._oormthonuniv.Klay.response.PageResponseDTO;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {
    CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> post(
            @RequestBody CommentRequestDTO commentRequestDTO
    ) {
        Long userId = 1L; // todo : 세션방식
        commentService.createComment(userId,
                commentRequestDTO.getCertificationId().get(), commentRequestDTO.getMsg());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public PageResponseDTO<CommentDTO> getPages(
            @PageableDefault(page = 0, size = 5) Pageable pageable,
            @RequestParam("certification") Optional<Long> certificationId,
            @RequestParam("user") Optional<Long> userId
    ) {
        return commentService.getCommentPages(pageable, certificationId, userId);
    }
}
