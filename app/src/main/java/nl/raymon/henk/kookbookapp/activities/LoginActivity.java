package nl.raymon.henk.kookbookapp.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.raymon.henk.kookbookapp.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient;
    private static final String TAG = "SignIn";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private View loginView;

    private ProgressBar loginLoadingBar;
    private EditText emailInput;
    private EditText passwordInput;
    private SignInButton googleSignInButton;
    private Button loginButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        findViewById(R.id.sign_in_google).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });

        findViewById(R.id.login_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.super.onBackPressed();
            }
        });

        findViewById(R.id.login_signin).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithEmail();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        emailInput = findViewById(R.id.login_email);
        passwordInput = findViewById(R.id.login_password);
        googleSignInButton = findViewById(R.id.sign_in_google);
        loginButton = findViewById(R.id.login_signin);
        backButton = findViewById(R.id.login_back);
        loginView = findViewById(R.id.LoginActivity);
        loginLoadingBar = loginView.findViewById(R.id.loginLoading);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            goToHome();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Geen netwerk gevonden", Toast.LENGTH_SHORT).show();
    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess() && result.getSignInAccount() != null) {
                firebaseAuthWithGoogle(result.getSignInAccount());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        hideLoginElements();

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToHome();
                            return;
                        }
                        showLoginElements();
                        showLoginFailureSnackbar();
                    }
                });
    }

    public void signInWithEmail() {
        Pattern p = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$");
        Matcher m = p.matcher(emailInput.getText());

        if (!m.matches()) {
            emailInput.setError("Ongeldig e-mailadres");
            return;
        }
        if (passwordInput.getText().toString().isEmpty()) {
            passwordInput.setError("Dit veld is vereist!");
            return;
        }
        hideLoginElements();


        mAuth.signInWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToHome();
                            return;
                        }
                        showLoginElements();
                        showLoginFailureSnackbar();
                    }
                });
    }

    private void hideLoginElements() {
        emailInput.setVisibility(View.INVISIBLE);
        passwordInput.setVisibility(View.INVISIBLE);
        googleSignInButton.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.INVISIBLE);
        backButton.setVisibility(View.INVISIBLE);
        loginLoadingBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(loginView.getContext(), R.color.colorAppRed), PorterDuff.Mode.MULTIPLY);
        loginLoadingBar.setVisibility(View.VISIBLE);
    }

    private void showLoginElements() {
        emailInput.setVisibility(View.VISIBLE);
        passwordInput.setVisibility(View.VISIBLE);
        googleSignInButton.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        loginLoadingBar.setVisibility(View.GONE);
    }

    private void showLoginFailureSnackbar() {
        final Snackbar snackbar = Snackbar.make(loginView, "Inloggen mislukt. \nControleer uw inloggegevens.", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Sluiten", new OnClickListener() {
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

