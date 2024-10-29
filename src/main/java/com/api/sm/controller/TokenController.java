package com.api.sm.controller;

import com.api.sm.dto.LoginRequest;
import com.api.sm.dto.LoginResponse;
import com.api.sm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class TokenController {


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtEncoder jwtEncoder;

    private final UserRepository userRepository;

    public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public ResponseEntity<LoginResponse>login(@RequestBody LoginRequest loginRequest){

       var user =  userRepository.findByUsername(loginRequest.username());
       if(user.isEmpty() || !user.get().isLoginCorrect(loginRequest,bCryptPasswordEncoder)){

           throw new BadCredentialsException("user or password is invalid !");

       }
       var now = Instant.now();
       var expiresIn = 300L;
       var claims = JwtClaimsSet.builder()
               .issuer("socialmedia")
               .subject(user.get().getUserId().toString())
               .issuedAt(now)
               .expiresAt(now.plusSeconds(expiresIn))
               .build();
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return ResponseEntity.ok(new LoginResponse(jwtValue,expiresIn));


    }
}
