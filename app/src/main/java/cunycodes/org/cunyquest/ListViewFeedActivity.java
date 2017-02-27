package cunycodes.org.cunyquest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ListViewFeedActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mMessage;
    private ListView list;
    private TextView textView;
    private Button submit;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_activvity);

        mAuth = FirebaseAuth.getInstance();
        mMessage = (EditText) findViewById(R.id.message);
        textView = (TextView) findViewById(R.id.users_name);
        submit = (Button) findViewById(R.id.submit_question);
        list = (ListView) findViewById(R.id.list_view);
        arrayList = new ArrayList<String>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        textView.setText(user.getDisplayName());

        arrayList.add(mMessage.getText().toString());
     //   list.setAdapter(new MyListAdapter(this, R.layout.lisr_item, data));

    }
}
