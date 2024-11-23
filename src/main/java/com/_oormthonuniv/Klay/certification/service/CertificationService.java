package com._oormthonuniv.Klay.certification.service;

import com._oormthonuniv.Klay.certification.domain.Certification;
import com._oormthonuniv.Klay.certification.dto.CertificationResponseDTO;
import com._oormthonuniv.Klay.certification.repository.CertificationRepository;
import com._oormthonuniv.Klay.response.PageResponseDTO;
import com._oormthonuniv.Klay.s3.S3UploadService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public void post(Long challengeId, Long userId, MultipartFile img) {
        Certification certification;
        String imgUrl = s3UploadService.uploadFile(img);
        Optional<Certification> optionalCertification = certificationRepository.findByChallengeIdAndUserId(challengeId, userId);

        if (optionalCertification.isPresent()) {
            certification = optionalCertification.get();
            certification.setImg(imgUrl);
            certification.setUpdateDate(LocalDateTime.now());
        } else {
            certification = optionalCertification.orElse(Certification.builder()
                    .challengeId(challengeId)
                    .userId(userId)
                    .img(imgUrl)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build());
        }
        certificationRepository.save(certification);
    }

    public CertificationResponseDTO getByChallengeIdAndUserId(Long challengeId, Long userId) {
        Certification certification = certificationRepository.findByChallengeIdAndUserId(challengeId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Certification not found"));
        return new CertificationResponseDTO(certification);
    }

    public PageResponseDTO<CertificationResponseDTO> getPage(Pageable pageable, Optional<Long> challengeId, Optional<Long> userId) {
        Page<Certification> data;
        if (userId.isPresent() && challengeId.isPresent()) {
            data = certificationRepository.findByChallengeIdAndUserId(pageable, userId.get(), challengeId.get());
        } else if (userId.isPresent()) {
            data = certificationRepository.findByUserId(pageable, userId.get());
        } else if (challengeId.isPresent()) {
            data = certificationRepository.findByChallengeId(pageable, challengeId.get());
        } else {
            data = certificationRepository.findAll(pageable);
        }
        return new PageResponseDTO<>(data.map(CertificationResponseDTO::new));
    }

}