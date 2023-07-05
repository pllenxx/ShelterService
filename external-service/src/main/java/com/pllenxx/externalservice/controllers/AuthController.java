package com.pllenxx.externalservice.controllers;

import lombok.RequiredArgsConstructor;
import com.pllenxx.externalservice.dto.UserDto;
import com.pllenxx.externalservice.security.jwt.JwtUtils;
import com.pllenxx.externalservice.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.pllenxx.externalservice.security.UserDetailsServiceImpl;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService service;
    @PostMapping("/registration")
    public ResponseEntity<?> registerNewUser(@RequestBody UserDto user) {
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        service.saveUser(user);
        return status(HttpStatus.OK).body("Registration was successful");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = jwtUtils.createToken(username, userDetailsService.loadUserByUsername(username).getAuthorities());
            return ok().header(HttpHeaders.AUTHORIZATION).body("token: " + token);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials", e);
        }
    }
}
