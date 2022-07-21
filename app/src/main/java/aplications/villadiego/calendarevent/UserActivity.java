package aplications.villadiego.calendarevent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button logOut;
    private Button inCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mAuth = FirebaseAuth.getInstance();
        logOut = findViewById(R.id.logout);
        inCreate = findViewById(R.id.create);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        // updateUI(currentUser);
    }

    public void LogOut(View view) {
            mAuth.signOut();
            Toast.makeText(getApplicationContext(), "Logout Successful!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), MainActivitySolid.class);
            startActivity(i);
        }

    public void InCreate(View view) {
        Toast.makeText(getApplicationContext(), "Let's Create a Calendar", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}