package isetb.tp5.checkinproapp.model;

import static android.os.Build.UNKNOWN;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private static final String PREF_NAME = "auth_preferences";
    private static final String KEY_TOKEN = "auth_token";
    private static final String KEY_USERNAME = "auth_username";
    private static final String KEY_ROLE = "auth_role"; // Added key for role
    private static final String KEY_REMEMBER_ME = "remember_me";

    private static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveLoginDetails(String token, String username, String role, boolean rememberMe) {
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_ROLE, role); // Save role
        editor.putBoolean(KEY_REMEMBER_ME, rememberMe);
        editor.apply();
    }

    public boolean isRememberMeEnabled() {
        return sharedPreferences.getBoolean(KEY_REMEMBER_ME, false);
    }

    public static String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getRole() {
        return sharedPreferences.getString(KEY_ROLE, UNKNOWN); // Retrieve role
    }

    public void clearLoginDetails() {
        editor.clear();
        editor.apply();
    }
}
