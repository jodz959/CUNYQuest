package cunycodes.org.cunyquest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mMessage;
    private ListView list;
    private TextView textView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
   // private Button btnHelp, btnNoHelp;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_activvity);

        mAuth = FirebaseAuth.getInstance();
        mMessage = (EditText) findViewById(R.id.message);
        textView = (TextView) findViewById(R.id.users_name);
      //  btnHelp = (Button) findViewById(R.id.helpful);
      //  btnNoHelp = (Button) findViewById(R.id.not_helpful);
        submit = (Button) findViewById(R.id.submit_question);
        list = (ListView) findViewById(R.id.list_view);
        arrayList = new ArrayList<String>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        textView.setText(user.getDisplayName());

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.lisr_item, arrayList);
        list.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.add(mMessage.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });





    }
}
