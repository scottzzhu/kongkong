package eduhollcs184assignments.ucsb.cs.httpwww.kongkong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

        List<PostViewAdapter.Post> posts;
        posts = new ArrayList<>();
        posts.add(new PostViewAdapter.Post("hello@hello.com","pos","title","desc"));
        posts.add(new PostViewAdapter.Post("hello@hello.com","pos","title","desc"));
        posts.add(new PostViewAdapter.Post("hello@hello.com","pos","title","desc"));
        posts.add(new PostViewAdapter.Post("hello@hello.com","pos","title","desc"));



        RecyclerView rv = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        PostViewAdapter adapter = new PostViewAdapter(posts);
        rv.setAdapter(adapter);
    }

}
