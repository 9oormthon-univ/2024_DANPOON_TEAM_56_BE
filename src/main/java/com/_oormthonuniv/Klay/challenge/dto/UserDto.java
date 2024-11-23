package com._oormthonuniv.Klay.challenge.dto;

import com._oormthonuniv.Klay.login.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {

    private Long id;
    private String username;
    private int level;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getKakaoId();
//        this.level = user.getLevel();
        this.level = 1;
    }


}
