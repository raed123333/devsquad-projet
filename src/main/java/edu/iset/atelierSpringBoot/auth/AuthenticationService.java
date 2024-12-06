package edu.iset.atelierSpringBoot.auth;

import edu.iset.atelierSpringBoot.config.JWTService;
import edu.iset.atelierSpringBoot.mail.MailService;
import edu.iset.atelierSpringBoot.repositories.IEmployeeRepository;
import edu.iset.atelierSpringBoot.auth.AuthenticationResponse;
import edu.iset.atelierSpringBoot.auth.AuthenticationRequest;
import edu.iset.atelierSpringBoot.auth.RegisterRequest;
import edu.iset.atelierSpringBoot.entites.Employee;
import edu.iset.atelierSpringBoot.entites.Role;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IEmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;

    // User Registration
    public AuthenticationResponse register(RegisterRequest request) {
        var user = Employee.builder()
                .email(request.getEmail())
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .age(request.getAge())
                .cin(request.getCin())
                .numPhone(request.getNumPhone())
                .poste(request.getPoste())
                .genre(request.getGenre())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : Role.EMPLOYEE) // Default role to USER
                .build();

        employeeRepository.save(user);

        var jwtToken = jwtService.generateToken(Map.of(), user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    // User Authentication
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = employeeRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(Map.of(), user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    // Initiate Password Reset
    public void initiatePasswordReset(String email) {
        var user = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        // Generate Reset Token
        String resetToken = jwtService.generateToken(Map.of("email", email), user);

        // Build Reset Link
        String resetLink = " http://localhost:5173/reset-password?token=" + resetToken;

        // Send Email
        String emailBody = "<p>Hi " + user.getNom() + ",</p>"
                + "<p>Click the link below to reset your password:</p>"
                + "<a href=\"" + resetLink + "\">Reset Password</a>"
                + "<p>This link will expire in 15 minutes.</p>";
        mailService.sendEmail(email, "Password Reset Request", emailBody);
    }

    // Reset Password
    public void resetPassword(String token, String newPassword) {
        if (token == null || token.isBlank() || newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("Token or new password cannot be empty.");
        }

        try {
            // Extract email from token
            String email = jwtService.extractClaim(token, claims -> claims.get("email", String.class));

            // Find user by email
            var user = employeeRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found for provided token."));

            // Validate token against user details
            if (!jwtService.isTokenValid(token, user)) {
                throw new IllegalArgumentException("Invalid or expired token.");
            }

            // Update the user's password
            user.setPassword(passwordEncoder.encode(newPassword));
            employeeRepository.save(user);

            // Optional: Invalidate the token if stored in a database
            // tokenService.invalidateToken(token);

        } catch (ExpiredJwtException ex) {
            throw new IllegalArgumentException("Token has expired.");
        } catch (Exception ex) {
            throw new IllegalArgumentException("Failed to reset password. " + ex.getMessage());
        }
    }


}
