package edu.iset.atelierSpringBoot.auth;

import edu.iset.atelierSpringBoot.config.JWTService;
import edu.iset.atelierSpringBoot.entites.Employee;
import edu.iset.atelierSpringBoot.repositories.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IEmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        // Set 'etat' to false by default and set 'icon' based on 'genre'
        String icon = "default_icon.png"; // Default icon
        if ("male".equalsIgnoreCase(registerRequest.getGenre())) {
            icon = "male_icon.png"; // Male icon
        } else if ("female".equalsIgnoreCase(registerRequest.getGenre())) {
            icon = "female_icon.png"; // Female icon
        }

        var user = Employee.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .nom(registerRequest.getNom())
                .prenom(registerRequest.getPrenom())
                .cin(registerRequest.getCin())
                .age(registerRequest.getAge())
                .genre(registerRequest.getGenre())
                .numPhone(registerRequest.getNumPhone())
                .poste(registerRequest.getPoste())
                .role(registerRequest.getRole())
                .etat(false) // Set 'etat' to false by default
                .icon(icon)  // Set the icon based on genre
                .build();

        employeeRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        var user = employeeRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
