package eduhollcs184assignments.ucsb.cs.httpwww.kongkong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class PostShowActivity extends AppCompatActivity {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = db.getReference();
    DatabaseReference postRef = rootRef.child("Posts");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView title;
    TextView author;
    TextView location;
    TextView content;
    ImageButton email;
    final String[] author_email = new String [1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_show);
        title = (TextView) findViewById(R.id.postview_title);
        author = (TextView) findViewById(R.id.postview_author);
        location = (TextView) findViewById(R.id.postview_location);
        content = (TextView) findViewById(R.id.postview_content);
        email = (ImageButton) findViewById(R.id.email_button);

        Intent intent = getIntent();
        String post_id = intent.getStringExtra("ID");
        postRef.orderByKey().equalTo(post_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> tmp = (HashMap<String,String>) ds.getValue();
                    title.setText(tmp.get("Title"));
                    author.setText(tmp.get("Email"));
                    author_email[0] = tmp.get("Email");
                    location.setText(tmp.get("Location"));
                    content.setText(tmp.get("Description"));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAdd = mAuth.getCurrentUser().getEmail();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                System.out.println(author_email[0]);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{author_email[0]});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Message From Kong");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "I am interested in your post ...");

                emailIntent.setType("message/rfc882");
                startActivity(Intent.createChooser(emailIntent, "Choose email client..."));
            }
        });
    }


}
