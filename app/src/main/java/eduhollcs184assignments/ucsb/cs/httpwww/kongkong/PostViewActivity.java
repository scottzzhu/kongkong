package eduhollcs184assignments.ucsb.cs.httpwww.kongkong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by scottzhu on 2017/12/3.
 * Activity for all Posts.
 */

public class PostViewActivity extends AppCompatActivity {


    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = db.getReference();
    DatabaseReference postRef = rootRef.child("Posts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        final List<PostViewAdapter.Post> posts;
        posts = new ArrayList<>();

        final RecyclerView rv = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        PostViewAdapter adapter = new PostViewAdapter(posts);
        rv.setAdapter(adapter);

        postRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                HashMap<String, String> tmp = (HashMap<String, String>) dataSnapshot.getValue();
                PostViewAdapter.Post newPost =
                        new PostViewAdapter.Post(tmp.get("Email"),
                                tmp.get("Location"),
                                tmp.get("Title"),
                                tmp.get("Description"));
                Log.d("db","Email: "+tmp.get("Email"));
                posts.add(newPost);
                PostViewAdapter adapter = new PostViewAdapter(posts);
                rv.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
