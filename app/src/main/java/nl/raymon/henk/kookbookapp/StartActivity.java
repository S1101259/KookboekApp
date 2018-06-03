package nl.raymon.henk.kookbookapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    private boolean backPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, SideNavigationActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            finishAffinity();
        } else {
            backPressedOnce = true;
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressedOnce = false;
                }
            }, 2000);
        }
    }

    public void goLogin(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void goRegister(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
