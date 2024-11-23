package com._oormthonuniv.Klay.comment;

import com._oormthonuniv.Klay.certification.domain.Certification;
import com._oormthonuniv.Klay.certification.domain.Comment;
import com._oormthonuniv.Klay.certification.dto.CertificationResponseDTO;
import com._oormthonuniv.Klay.response.PageResponseDTO;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;

    public CommentDTO createComment(Long userId, Long certificationId, String msg) {
        Comment comment = Comment.builder()
                .certificationId(certificationId)
                .userId(userId)
                .msg(msg)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now()).build();
        commentRepository.save(comment);

        return new CommentDTO(comment);
    }

    public PageResponseDTO<CommentDTO> getCommentPages(Pageable pageable, Optional<Long> userId,
                                                       Optional<Long> certificationId) {
        Page<Comment> data;
        if (userId.isPresent() && certificationId.isPresent()) {
            data = commentRepository.findByCertificationIdAndUserId(pageable, userId.get(), certificationId.get());
        } else if (userId.isPresent()) {
            data = commentRepository.findByUserId(pageable, userId.get());
            System.out.println("test");
        } else if (certificationId.isPresent()) {
            data = commentRepository.findByCertificationId(pageable, certificationId.get());
        } else {
            data = commentRepository.findAll(pageable);
        }
        return new PageResponseDTO<>(data.map(CommentDTO::new));
    }
}
