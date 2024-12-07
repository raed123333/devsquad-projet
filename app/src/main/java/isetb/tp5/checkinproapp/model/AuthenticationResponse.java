package isetb.tp5.checkinproapp.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
    @SerializedName("token")
    private String token;

    @SerializedName("role")
    private String role;

    @SerializedName("username")
    private String username;

    // Getters and setters
    public String getToken() {
        return token != null ? token : "";
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role != null ? role.toUpperCase() : "UNKNOWN";
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username != null ? username : "Guest";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "token='" + token + '\'' +
                ", role='" + role + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public boolean isValid() {
        return token != null && !token.isEmpty() &&
                role != null && !role.isEmpty() &&
                username != null && !username.isEmpty();
    }
}
