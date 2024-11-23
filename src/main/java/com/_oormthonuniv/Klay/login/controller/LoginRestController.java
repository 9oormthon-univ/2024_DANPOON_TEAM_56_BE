package com._oormthonuniv.Klay.login.controller;

import com._oormthonuniv.Klay.login.entity.User;
import com._oormthonuniv.Klay.login.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginRestController {

    @Autowired
    private AuthService authService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("code") String code, HttpServletResponse response) {
        String token = authService.kakaoGetAccessViaCode(code);
        String nickname = authService.kakaoGetUserInfoViaAccessToken(token);
        String kakaoId = authService.getKakaoIdByAccessToken(token);
        setCookie(kakaoId, response);
        return ResponseEntity.ok(nickname);
    }

    // 쿠키 생성
    @PostMapping("/cookie")
    public ResponseEntity<String> setCookie(@RequestParam("kakaoId") String kakaoId, HttpServletResponse response) {
        Cookie cookie = new Cookie("kakaoId", kakaoId);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
        return ResponseEntity.ok("쿠키 저장 성공!");
    }

    // 쿠키 삭제 및 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie codeCookie = new Cookie("code", null);
        codeCookie.setMaxAge(0);
        response.addCookie(codeCookie);

        Cookie idCookie = new Cookie("kakaoId", null);
        idCookie.setMaxAge(0);
        response.addCookie(idCookie);

        return ResponseEntity.ok("로그아웃 성공!");
    }

    // 유저 정보 조회
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("kakaoId".equals(cookie.getName())) {
                    String kakaoId = cookie.getValue();
                    Optional<com._oormthonuniv.Klay.login.entity.User> userOptional = authService.getUserByKakaoId(kakaoId);
                    if (userOptional.isPresent()) {
                        User user = userOptional.get();
                        Map<String, String> userInfo = new HashMap<>();
                        userInfo.put("id", user.getId().toString());
                        userInfo.put("nickname", user.getNickname());
                        userInfo.put("profileImageUrl", user.getProfileImageUrl());
                        return ResponseEntity.ok(userInfo);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 조회 실패!");
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("쿠키가 존재하지 않습니다!");
    }
}