package com._oormthonuniv.Klay.certification.dto;

import com._oormthonuniv.Klay.certification.domain.Certification;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CertificationResponseDTO {
    private Long certification_id;
    private Long user_id;
    private Long challenge_id;
    private String img;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;

    public CertificationResponseDTO(Certification certification) {
        this.certification_id = certification.getId();
        this.user_id = certification.getUserId();
        this.challenge_id = certification.getChallengeId();
        this.img = certification.getImg();
        this.created_date = certification.getCreatedDate();
        this.updated_date = certification.getCreatedDate();
    }
}
