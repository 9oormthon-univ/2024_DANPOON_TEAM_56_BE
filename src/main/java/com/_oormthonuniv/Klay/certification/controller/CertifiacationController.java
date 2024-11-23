package com._oormthonuniv.Klay.certification.controller;

import com._oormthonuniv.Klay.certification.dto.CertificationResponseDTO;
import com._oormthonuniv.Klay.certification.service.CertificationService;
import com._oormthonuniv.Klay.response.PageResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/certification")
@AllArgsConstructor
public class CertifiacationController {
    private final CertificationService certificationService;

    @PostMapping
    public ResponseEntity<CertificationResponseDTO> post(
            @RequestParam("img") MultipartFile img,
            @RequestParam("challenge_id") Long challengeId
    ) {
        Long userId = 1L; // todo: 로그인 로직 세션방식으로 변경
        certificationService.post(challengeId, userId, img);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{challenge-id}")
    public ResponseEntity<CertificationResponseDTO> get(
            @PathVariable("challenge-id") Long challengeId
    ) {
        Long userId = 1L; // todo: 로그인 로직 세션방식으로 변경
        return new ResponseEntity<>(certificationService.getByChallengeIdAndUserId(challengeId, userId), HttpStatus.OK);
    }

    @GetMapping
    public PageResponseDTO<CertificationResponseDTO> getPages(
            @PageableDefault(page = 0, size = 5) Pageable pageable,
            @RequestParam("challenge") Optional<Long> challengeId,
            @RequestParam("user") Optional<Long> userId
    ) {
        return certificationService.getPage(pageable, challengeId, userId);
    }
}
