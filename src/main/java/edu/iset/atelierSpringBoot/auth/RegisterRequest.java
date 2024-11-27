package edu.iset.atelierSpringBoot.auth;


import edu.iset.atelierSpringBoot.entites.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String cin;
    private int age;
    private String genre;
    private String numPhone;
    private String poste;
    private String password;
    private Role role=Role.RH;


}
