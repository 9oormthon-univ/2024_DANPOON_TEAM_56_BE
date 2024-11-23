package com._oormthonuniv.Klay.login.controller;

import com._oormthonuniv.Klay.login.entity.User;
import com._oormthonuniv.Klay.login.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes("nickname")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/logout")
    public String logout(Model model, HttpServletResponse response) {

        // 로그아웃 쿠키 삭제 (음.. 작동을 할까요...?)
        model.addAttribute("nickname", null);
        Cookie codeCookie = new Cookie("code", null);
        codeCookie.setMaxAge(0);
        response.addCookie(codeCookie);

        Cookie idCookie = new Cookie("kakaoId", null);
        idCookie.setMaxAge(0);
        response.addCookie(idCookie);

        return "redirect:/";
    }

    @GetMapping("/login/after")
    public String kakaoRedirect(@RequestParam("code") String code, Model model, HttpServletResponse response) {
        // 카카오 로그인 부분
        String token = authService.kakaoGetAccessViaCode(code);
        String nickname = authService.kakaoGetUserInfoViaAccessToken(token);
        model.addAttribute("nickname", nickname);

        // 쿠키에 kakaoId 저장 부분
        String kakaoId = authService.getKakaoIdByAccessToken(token);
        setCookie(kakaoId, response);

        return "redirect:https://klay-ten.vercel.app/home";
    }

    // 쿠키 생성
    @PostMapping("/cookie")
    public ResponseEntity<String> setCookie(@RequestParam("kakaoId") String kakaoId, HttpServletResponse response) {
        Cookie cookie = new Cookie("kakaoId", kakaoId);
        cookie.setHttpOnly(true); // JavaScript에서 접근 불가능하도록 설정
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
        response.addCookie(cookie);
        return ResponseEntity.ok("Cookie set!");
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 쿠키 확인 페이지
    @GetMapping("/check-cookies")
    public String checkCookies(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("kakaoId".equals(cookie.getName())) {
                    String cookieValue = cookie.getValue();
                    model.addAttribute("cookieValue", cookieValue);
                    break;
                }
            }
        }
        return "cookie-check";
    }

    // 유저 정보 조회
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("kakaoId".equals(cookie.getName())) {
                    String kakaoId = cookie.getValue();
                    Optional<User> userOptional = authService.getUserByKakaoId(kakaoId);
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
