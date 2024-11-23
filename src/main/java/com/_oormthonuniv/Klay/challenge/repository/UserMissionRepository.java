package com._oormthonuniv.Klay.challenge.repository;

import com._oormthonuniv.Klay.challenge.domain.UserMission;
import com._oormthonuniv.Klay.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMissionRepository extends JpaRepository<UserMission, Long> {
    // 특정 유저가 받은 모든 미션을 조회
    List<UserMission> findByUser(User user);

    // 특정 유저가 완료한 모든 미션을 조회
    List<UserMission> findByUserAndCompletedTrue(User user);}