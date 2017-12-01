package eduhollcs184assignments.ucsb.cs.httpwww.kongkong;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //updateUI(currentUser);
        if (currentUser != null && currentUser.isEmailVerified()){
            Intent myIntent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(myIntent);
            finish();
            Toast.makeText(this, "Already In", Toast.LENGTH_SHORT).show();
        }
        else{
            //Toast.makeText(this, "Register Page", Toast.LENGTH_SHORT).show();
            //Wait for 2 seconds and start Activity Main
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SplashScreen.this.startActivity(new Intent(SplashScreen.this,RegisterActivity.class));
                    SplashScreen.this.finish();
                }
            },2000); // 2000 = 2seconds

        }
    }
}

