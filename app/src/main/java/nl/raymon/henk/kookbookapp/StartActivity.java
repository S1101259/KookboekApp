package nl.raymon.henk.kookbookapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, SideNavigationActivity.class));
        }
    }

    public void goLogin(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void goRegister(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
