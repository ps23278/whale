package com.tbland.whale.controller.rest;

import com.tbland.whale.dto.record.LoginRequest;
import com.tbland.whale.entity.Account;
import com.tbland.whale.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public AuthController(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }
    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse){
//        System.out.println(">>> LOGIN EMAIL: " + request.email());
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );
        System.out.println("AUTHENTICATED: "
                + authentication.isAuthenticated());

        System.out.println("AUTHORITIES: "
                + authentication.getAuthorities());
        SecurityContext context =
                SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(
                context,
                httpRequest,
                httpResponse
        );
//        System.out.println(">>> LOGIN SUCCESS");
        return "Login successful";
    }
    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Account account = userDetails.getAccount();

        return Map.of(
                "id", account.getId(),
                "email", account.getEmail(),
                "role", authentication.getAuthorities()
                        .stream()
                        .findFirst()
                        .map(GrantedAuthority::getAuthority)
                        .orElse("")
        );
    }
    @PostMapping("/logout")
    public String logout(
            HttpServletRequest request,
            HttpServletResponse response) {

        SecurityContextHolder.clearContext();

        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }

        return "Logout successful";
    }


}
