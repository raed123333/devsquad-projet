package edu.iset.atelierSpringBoot.controllers;

import edu.iset.atelierSpringBoot.auth.*;
import edu.iset.atelierSpringBoot.entites.Employee;
import edu.iset.atelierSpringBoot.entites.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
    @PostMapping("/registerRH")
    public ResponseEntity<AuthenticationResponse> registerEmployeeAsRH(@RequestBody RegisterRequest registerRequest) {
        // Set the role to RH explicitly
        registerRequest.setRole(Role.RH);

        // Register using the AuthenticationService
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            authenticationService.initiatePasswordReset(email);
            return ResponseEntity.ok("Password reset email sent successfully.");
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        }
    }

    @PostMapping("/auth/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request) {
        try {
            // Extract token and new password from the request
            String token = request.getToken();
            String newPassword = request.getNewPassword();

            // Call the service to handle the password reset logic
            authenticationService.resetPassword(token, newPassword);

            return ResponseEntity.ok("Password reset successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to reset password: " + ex.getMessage());
        }
    }


}
