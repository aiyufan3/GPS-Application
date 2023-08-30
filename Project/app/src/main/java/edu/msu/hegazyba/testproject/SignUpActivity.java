package edu.msu.hegazyba.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private final String EMAIL_COMPLETION = "@bullshit-emails.com";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private EditText userText, passText, passConfText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            goToMainScreen();
        }

        userText = (EditText) findViewById(R.id.username_et);
        passText = (EditText) findViewById(R.id.password_et);
        passConfText = (EditText) findViewById(R.id.password_confirm_et);
    }

    public void onSignup(View v) {
        String user,pass,passConf;

        user = userText.getText().toString();
        pass = passText.getText().toString();
        passConf = passConfText.getText().toString();

        if(validateData(user, pass, passConf)) {
            mAuth.createUserWithEmailAndPassword(user + EMAIL_COMPLETION, pass).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        // Account has been created
                        if (mAuth.getCurrentUser() == null) {
                            Toast.makeText(SignUpActivity.this, "Could not create new account", Toast.LENGTH_SHORT).show();
                        } else {
                            goToMainScreen();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private boolean validateData(String user, String password, String confirmPassword) {
        if(!(user.length() > 0)) {
            Toast.makeText(SignUpActivity.this, "Username is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.length() < 6 || !password.matches("^[a-zA-Z0-9]*$")) {
            Toast.makeText(SignUpActivity.this, "Password is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!confirmPassword.equals(password)) {
            Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void goToMainScreen() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(SignUpActivity.this, "Could not login account", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), BucketCatalogActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
