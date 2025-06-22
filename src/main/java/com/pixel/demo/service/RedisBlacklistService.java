package com.pixel.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisBlacklistService {
    private static final String BLACKLIST_KEY_PREFIX = "jwt_blacklist:";

    private final StringRedisTemplate redisTemplate;

    public void addToBlacklist(String token, long expirationTime) {
        String key = BLACKLIST_KEY_PREFIX + token;
        redisTemplate.opsForValue().set(key, "invalid", expirationTime, TimeUnit.SECONDS);
    }

    public boolean isBlacklisted(String token) {
        String key = BLACKLIST_KEY_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}