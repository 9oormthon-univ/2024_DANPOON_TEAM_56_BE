package com._oormthonuniv.Klay.challenge.controller;

import com._oormthonuniv.Klay.challenge.domain.Mission;
import com._oormthonuniv.Klay.challenge.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mission")
public class MissionController {

    @Autowired
    private MissionService missionService;

    // 유저가 받은 모든 미션 조회
    @GetMapping("/daily/{userId}")
    public ResponseEntity<?> assignMission(@PathVariable Long userId) {
        try {
            // 다음 미션 부여
            Mission mission = missionService.assignNextMissionToUser(userId);

            // 부여된 미션 반환
            return ResponseEntity.ok(mission);
        } catch (Exception e) {
            // 오류 발생 시 404 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}