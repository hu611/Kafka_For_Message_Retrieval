package com.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SqlUserDetailsService implements UserDetailsService {

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return User.withUsername(userId).password("123").roles("user").build();
    }


}



