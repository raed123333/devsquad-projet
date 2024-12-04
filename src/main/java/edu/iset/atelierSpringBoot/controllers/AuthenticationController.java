package edu.iset.atelierSpringBoot.controllers;

import edu.iset.atelierSpringBoot.auth.AuthenticationResponse;
import edu.iset.atelierSpringBoot.auth.AuthenticationRequest;
import edu.iset.atelierSpringBoot.auth.AuthenticationService;
import edu.iset.atelierSpringBoot.auth.RegisterRequest;
import edu.iset.atelierSpringBoot.entites.Employee;
import edu.iset.atelierSpringBoot.entites.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
