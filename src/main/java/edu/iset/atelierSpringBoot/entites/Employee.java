package edu.iset.atelierSpringBoot.entites;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee implements Serializable {

 private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 private String nom;
 private String prenom;
 private String email;
 private String cin;
 private int age;
 private String genre;
 private String numPhone;
 private String poste;
 private boolean etat = false; 
 private String password;
 
 
 public String getPassword() {
	return password;
}


public void setPassword(String password) {
	this.password = password;
}

private String icon; 


public void setIcon(String genre) {
    if ("Homme".equalsIgnoreCase(genre)) {
        this.icon = "icon-homme";
    } else if ("Femme".equalsIgnoreCase(genre)) {
        this.icon = "icon-femme";
    } else {
        this.icon = "icon-default";
    }
}
public String getIcon() {
    return this.icon; // Returns the automatically set icon
}

 
 public boolean isEtat() {
     return etat;
 }

 public void setEtat(boolean etat) {
     this.etat = etat;
 }

 // Getters and Setters

 public Long getId() {
     return id;
 }

 public void setId(Long id) {
     this.id = id;
 }

 public String getNom() {
     return nom;
 }

 public void setNom(String nom) {
     this.nom = nom;
 }

 public String getPrenom() {
     return prenom;
 }

 public void setPrenom(String prenom) {
     this.prenom = prenom;
 }

 public String getEmail() {
     return email;
 }

 public void setEmail(String email) {
     this.email = email;
 }

 public String getCin() {
     return cin;
 }

 public void setCin(String cin) {
     this.cin = cin;
 }

 public int getAge() {
     return age;
 }

 public void setAge(int age) {
     this.age = age;
 }

 public String getNumPhone() {
     return numPhone;
 }

 public void setNumPhone(String numPhone) {
     this.numPhone = numPhone;
 }
 public String getPoste() {
     return poste;
 }

 public void setPoste(String poste) {
     this.poste = poste;
 }



@Override
public String toString() {
	return "Employee [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", cin=" + cin + ", age="
			+ age + ", genre=" + genre + ", numPhone=" + numPhone + ", poste=" + poste + ", etat=" + etat
			+ ", password=" + password + ", icon=" + icon + "]";
}

public String getGenre() {
	return genre;
}

public void setGenre(String genre) {
	this.genre = genre;
}
}

