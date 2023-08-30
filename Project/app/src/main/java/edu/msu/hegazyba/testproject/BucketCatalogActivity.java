package edu.msu.hegazyba.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class BucketCatalogActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private BucketItem[] items = {
            new BucketItem("Some item", R.drawable.msu_backdrop),
            new BucketItem("Some item", R.drawable.msu_backdrop),
            new BucketItem("Some item", R.drawable.msu_backdrop),
            new BucketItem("Some item", R.drawable.msu_backdrop),
            new BucketItem("Some item", R.drawable.msu_backdrop),
            new BucketItem("Some item", R.drawable.msu_backdrop),
            new BucketItem("Some item", R.drawable.msu_backdrop),
            new BucketItem("Some item", R.drawable.msu_backdrop),
            new BucketItem("Some item", R.drawable.msu_backdrop),
            new BucketItem("Some item", R.drawable.msu_backdrop)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_catalog);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser == null) {
            // Somehow there is no user, logout
            logout();
        }

        GridAdapter adapter = new GridAdapter(BucketCatalogActivity.this, items);
        GridView gv = findViewById(R.id.bucket_grid_view);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Grid item clicked", adapter.getItem(position).getName());
            }
        });
    }

    public void onLogout(View v) {
        if(currentUser != null) {
            // Logout user from firebase
            mAuth.signOut();
        }
        logout();
    }

    public void logout() {
        Intent i = new Intent(BucketCatalogActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}