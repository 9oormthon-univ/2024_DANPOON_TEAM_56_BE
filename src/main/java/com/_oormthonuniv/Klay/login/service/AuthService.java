package com._oormthonuniv.Klay.login.service;

import com._oormthonuniv.Klay.login.entity.User;
import com._oormthonuniv.Klay.login.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    @Value("d67abb59691d4f30320c647af9334ce9")
    private String restApiKey;

    @Value("https://klay-ten.vercel.app/api/login/after")
    private String redirectUri;

    @Autowired
    private UserRepository userRepository;

    RestTemplate restTemplate = new RestTemplate();

    public String kakaoGetAccessViaCode(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", restApiKey);
        body.add("redirect_uri", redirectUri);
        body.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<JsonNode> responseNode = restTemplate.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST, entity, JsonNode.class);
            log.info(responseNode.getBody().toPrettyString());

            return responseNode.getBody().get("access_token").asText();
        } catch (HttpClientErrorException e) {
            log.error("Error during getting access token: {}", e.getResponseBodyAsString());
            throw e;
        }
    }

    public String kakaoGetUserInfoViaAccessToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me", HttpMethod.GET, entity, JsonNode.class);
        log.info(responseNode.getBody().toPrettyString());

        JsonNode userInfo = responseNode.getBody();
        String kakaoId = userInfo.get("id").asText();
        String nickname = userInfo.get("properties").get("nickname").asText();
        String profileImageUrl = userInfo.get("properties").get("profile_image").asText();

        Optional<User> existingUser = userRepository.findByKakaoId(kakaoId);
        if (existingUser.isEmpty()) {
            User user = new User();
            user.setKakaoId(kakaoId);
            user.setNickname(nickname);
            user.setProfileImageUrl(profileImageUrl);
            userRepository.save(user);
        } else {
            log.info("kakaoId {}는 이미 가입된 사용자입니다!", kakaoId);
        }

        return nickname;
    }

    public String getKakaoIdByAccessToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me", HttpMethod.GET, entity, JsonNode.class);
        log.info(responseNode.getBody().toPrettyString());

        JsonNode userInfo = responseNode.getBody();
        return userInfo.get("id").asText();
    }

    public Optional<User> getUserByKakaoId(String kakaoId) {
        return userRepository.findByKakaoId(kakaoId);
    }
}