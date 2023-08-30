package edu.msu.hegazyba.testproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private final String EMAIL_COMPLETION = "@bullshit-emails.com";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private EditText userText, passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            goToMainScreen();
        }

        userText = (EditText) findViewById(R.id.username_et);
        passText = (EditText) findViewById(R.id.password_et);
    }

    public void onLogin(View v) {
        String user, pass;
        user = userText.getText().toString();
        pass = passText.getText().toString();

        if(validateData(user, pass)) {
            mAuth.signInWithEmailAndPassword(user + EMAIL_COMPLETION, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success
                        goToMainScreen();
                    } else {
                        // Sign in fail
                        Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean validateData(String user, String password) {
        if(!(user.length() > 0)) {
            Toast.makeText(MainActivity.this, "Username is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!(password.length() > 0)) {
            Toast.makeText(MainActivity.this, "Password is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void onSignup(View v) {
        Intent int1 = new Intent(this, SignUpActivity.class);
        startActivity(int1);
    }


    public void goToMainScreen() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(MainActivity.this, "Could not login account", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), BucketCatalogActivity.class);
            startActivity(intent);
            finish();
        }

    }
}