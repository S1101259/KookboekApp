package nl.raymon.henk.kookbookapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.raymon.henk.kookbookapp.R;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        findViewById(R.id.register_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

        findViewById(R.id.register_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            goToHome();
        }
    }

    public void onSubmit() {
        EditText email = findViewById(R.id.register_email);
        Pattern p = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$");
        Matcher m = p.matcher(email.getText().toString());

        if (!m.matches()) {
            email.setError("Ongeldig e-mailadres");
            return;
        }

        EditText password = findViewById(R.id.register_password);

        p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!?*<>€_~-])(?=\\S+$).{8,}$");
        m = p.matcher(password.getText().toString());
        if (!m.matches()) {
            password.setError("Uw wachtwoord dient minstens 8 karakters te bevatten, waarvan minstens 1 hoofdletter, 1 kleine letter, 1 cijfer, en 1 speciaal teken (@#$%^&+=!?*<>€_~-)");
            return;
        }

        EditText name = findViewById(R.id.register_displayname);
        if (name.getText().toString().isEmpty()) {
            name.setError("Dit veld is vereist");
            return;
        }

        createAccount(email.getText().toString(), password.getText().toString(), name.getText().toString());
    }

    private void createAccount(String email, String password, final String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    goToHome();
                                                }
                                            }
                                        });
                            }
                        } else {
                            View view = findViewById(R.id.registerLayout);
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                showRegistrationFailureSnackbar(view, "Dit emailadres is al in gebruik");
                            } else {
                                showRegistrationFailureSnackbar(view, "Account aanmaken mislukt");
                            }
                        }
                    }
                });
    }

    private void showRegistrationFailureSnackbar(View view, String s) {
        final Snackbar snackbar = Snackbar.make(view, s, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Sluiten", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    public void goToHome() {
        startActivity(new Intent(this, SideNavigationActivity.class));
    }
}
