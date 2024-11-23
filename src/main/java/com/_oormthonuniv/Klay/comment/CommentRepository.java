package com._oormthonuniv.Klay.comment;

import com._oormthonuniv.Klay.certification.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByCertificationIdAndUserId(Pageable pageable, Long certificationId, Long userId);
    Page<Comment> findByCertificationId(Pageable pageable, Long certificationId);
    Page<Comment> findByUserId(Pageable pageable, Long userId);
}
