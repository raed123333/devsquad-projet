package edu.iset.atelierSpringBoot;

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
 private String numPhone;
 private String image;
 private String poste;

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

 public String getImage() {
     return image;
 }

 public void setImage(String image) {
     this.image = image;
 }

 public String getPoste() {
     return poste;
 }

 public void setPoste(String poste) {
     this.poste = poste;
 }

 @Override
 public String toString() {
     return "Employee{" +
             "id=" + id +
             ", nom='" + nom + '\'' +
             ", prenom='" + prenom + '\'' +
             ", email='" + email + '\'' +
             ", cin='" + cin + '\'' +
             ", age=" + age +
             ", numPhone='" + numPhone + '\'' +
             ", image='" + image + '\'' +
             ", poste='" + poste + '\'' +
             '}';
 }
}

