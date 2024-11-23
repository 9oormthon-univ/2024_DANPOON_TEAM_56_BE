package com._oormthonuniv.Klay.challenge.repository;

import com._oormthonuniv.Klay.challenge.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface MissionRepository extends JpaRepository<Mission, Long> {

    Optional<Mission> findByLevelAndId(int level, Long id);

    // 레벨에 맞는 모든 미션을 찾기
    List<Mission> findByLevel(int level);
}