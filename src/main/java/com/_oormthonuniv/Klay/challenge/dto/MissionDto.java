package com._oormthonuniv.Klay.challenge.dto;

import com._oormthonuniv.Klay.challenge.domain.Mission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MissionDto {

    private Long id;
    private String description;
    private int level;

    public MissionDto(Mission mission) {
        this.id = mission.getId();
        this.description = mission.getDescription();
        this.level = mission.getLevel();
    }
}