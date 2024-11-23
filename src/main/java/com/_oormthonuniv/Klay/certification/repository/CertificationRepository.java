package com._oormthonuniv.Klay.certification.repository;

import com._oormthonuniv.Klay.certification.domain.Certification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
    Page<Certification> findAll(Pageable pageable);

    Page<Certification> findByChallengeId(Pageable pageable, Long challengeId);

    Page<Certification> findByUserId(Pageable pageable, Long userId);

    Page<Certification> findByChallengeIdAndUserId(Pageable pageable, Long challengeId, Long userId);

    Optional<Certification> findByChallengeIdAndUserId(Long challengeId, Long userId);
}
