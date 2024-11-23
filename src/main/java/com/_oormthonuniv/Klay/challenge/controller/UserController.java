package com._oormthonuniv.Klay.challenge.controller;

import com._oormthonuniv.Klay.login.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/missionlevel")

public class UserController {

    // 특정 레벨의 페이지에 접근하려는 경우, 레벨 확인 API
    @GetMapping("/{userId}/{requiredLevel}")
    public ResponseEntity<String> checkLevel(@PathVariable Long userId, @PathVariable int requiredLevel) {
        if (1 < requiredLevel) {
//        if (user.getLevel() < requiredLevel) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Comming soon !");
        }
        return ResponseEntity.ok("pass");
    }
}