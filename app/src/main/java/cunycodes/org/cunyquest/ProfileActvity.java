package cunycodes.org.cunyquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileActvity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final String TAG = "ProfileActivity";

    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth.AuthStateListener mAuthListerner;
    private Button btnSetProfile;
    private EditText mUsername;
    private Spinner sMajors, sSchools;
    private RadioButton rCurStudent, rPotStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_actvity);

        btnSetProfile = (Button) findViewById(R.id.set_profile);
        mUsername = (EditText) findViewById(R.id.user_name);
        sSchools = (Spinner) findViewById(R.id.school_spinner);
        sMajors = (Spinner) findViewById(R.id.majors);
        rCurStudent = (RadioButton) findViewById(R.id.curStudent);
        rPotStudent = (RadioButton) findViewById(R.id.pot_student);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cuny_schools, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSchools.setAdapter(adapter);
        sSchools.setOnItemSelectedListener(this);

      /*  ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.majors, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMajors.setAdapter(adapter); */

        mAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuthListerner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
            }
        };

        btnSetProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileChang = new UserProfileChangeRequest.Builder()
                        .setDisplayName(mUsername.getText().toString())
                        .build();

                user.updateProfile(profileChang).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "User Profile Updated");
                        }
                    }
                });

                Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListerner);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.addAuthStateListener(mAuthListerner);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            adapterView.getItemAtPosition(i);
            mFirebaseAnalytics.setUserProperty("school_name",adapterView.getItemAtPosition(i).toString() );
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(ProfileActvity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.curStudent:
                if (checked)
                    mFirebaseAnalytics.setUserProperty("type_of_student", "Current Student");// Pirates are the best
                    break;
            case R.id.pot_student:
                if (checked)
                    mFirebaseAnalytics.setUserProperty("type_of_student", "Potential Student");// Ninjas rule
                    break;
        }
    }
}
