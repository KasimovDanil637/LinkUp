package web.controllers;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import web.dto.DtoService;
import web.dto.request.SignInRequest;
import web.dto.request.SignUpRequest;
import web.dto.response.TokenResponse;
import web.security.JWT.JwtCore;
import web.service.StatsService;
import web.service.UserService;

@RestController
@RequestMapping("auth")
@CrossOrigin("${cors.hosts}")
public class SecurityController {
    private final Logger log = LoggerFactory.getLogger(SecurityController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private StatsService statsService;
    @Autowired
    private JwtCore jwtCore;

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody @Valid SignUpRequest signUpRequest){
        if (signUpRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userService.existsUserByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DtoService.responseMessage("Имя пользователя занято"));
        }
        if (userService.existsUserByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DtoService.responseMessage("Почта занята"));
        }
        userService.registry(signUpRequest);
        statsService.save(signUpRequest.getUsername());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e){
            log.warn(String.valueOf(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DtoService.responseMessage("Ошибка аутентификации"));
        }
        String jwt = jwtCore.generateToken(authentication);
        TokenResponse tokenResponse = new TokenResponse("token", jwt, signUpRequest.getUsername());
        return ResponseEntity.ok(tokenResponse);

    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid SignInRequest signInRequest) {
        if (signInRequest == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!userService.existsUserByUsername(signInRequest.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DtoService.responseMessage("Неверное имя пользователя"));
        }
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        } catch (BadCredentialsException e) {
            log.warn(String.valueOf(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DtoService.responseMessage("Неверный пароль"));
        }
        statsService.save(signInRequest.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);
        TokenResponse tokenResponse = new TokenResponse("token", jwt, signInRequest.getUsername());
        return ResponseEntity.ok(tokenResponse);
    }
}
