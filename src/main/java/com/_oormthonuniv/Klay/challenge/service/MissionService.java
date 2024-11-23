package com._oormthonuniv.Klay.challenge.service;

import com._oormthonuniv.Klay.challenge.domain.Mission;
import com._oormthonuniv.Klay.challenge.domain.UserMission;
import com._oormthonuniv.Klay.challenge.repository.MissionRepository;
import com._oormthonuniv.Klay.challenge.repository.UserMissionRepository;
import com._oormthonuniv.Klay.login.entity.User;
import com._oormthonuniv.Klay.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserRepository userRepository;
    private final UserMissionRepository userMissionRepository;

    @Autowired
    public MissionService(MissionRepository missionRepository, UserRepository userRepository, UserMissionRepository userMissionRepository) {
        this.missionRepository = missionRepository;
        this.userRepository = userRepository;
        this.userMissionRepository = userMissionRepository;
    }

    // 유저에게 순차적으로 미션을 부여하는 메서드
    public Mission assignNextMissionToUser(Long userId) {
        // 유저 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 유저가 받은 모든 미션 조회 (완료 여부와 관계없이)
        List<UserMission> userMissions = userMissionRepository.findByUser(user);

        // 레벨에 맞는 미션 목록 조회
//        List<Mission> missionsForLevel = missionRepository.findByLevel(user.getLevel());
        List<Mission> missionsForLevel = missionRepository.findByLevel(1);

        // 유저에게 아직 부여되지 않은 미션 찾기
        Mission nextMission = null;
        for (Mission mission : missionsForLevel) {
            // 이미 부여된 미션은 제외하고, 아직 부여되지 않은 미션을 찾기
            boolean isMissionAssigned = userMissions.stream().anyMatch(um -> um.getMission().equals(mission));
            if (!isMissionAssigned) {
                nextMission = mission;
                break; // 다음 미션을 찾으면 바로 리턴
            }
        }

        if (nextMission == null) {
            throw new RuntimeException("All missions for this user are completed.");
        }

        // 미션 부여
        UserMission userMission = new UserMission();
        userMission.setUser(user);
        userMission.setMission(nextMission);
        userMission.setAssignedDate(LocalDate.now());
        userMission.setAssignedTime(LocalDateTime.now()); // 미션 부여 시간을 현재 시간으로 설정
        userMission.setCompleted(false);  // 처음에는 미완료 상태
        userMissionRepository.save(userMission);

        return nextMission;
    }
}