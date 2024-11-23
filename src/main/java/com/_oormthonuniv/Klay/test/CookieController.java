package com._oormthonuniv.Klay.test;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CookieController {
    @PostMapping("/cookie")
    public ResponseEntity<String> setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("test", "Hello");
        cookie.setHttpOnly(true); // JavaScript에서 접근 불가능하도록 설정
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
        response.addCookie(cookie);
        return ResponseEntity.ok("Cookie set!");
    }
}
