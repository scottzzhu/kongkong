package eduhollcs184assignments.ucsb.cs.httpwww.kongkong;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static eduhollcs184assignments.ucsb.cs.httpwww.kongkong.MainActivity.Category.ALL;

/**
 * Created by scottzhu on 2017/12/3.
 * Activity for all Posts.
 */

public class PostViewActivity extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = db.getReference();
    DatabaseReference postRef = rootRef.child("Posts");
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final List<PostViewAdapter.Post> posts;
        posts = new ArrayList<>();

        final RecyclerView rv = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        rv.setLayoutManager(llm);
        PostViewAdapter adapter = new PostViewAdapter(posts);
        rv.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fab2);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user != null){
                    Intent myIntent = new Intent(PostViewActivity.this, PostActivity.class);
                    startActivity(myIntent);
                }
                else{
                    Toast.makeText(PostViewActivity.this, "Please sign in to unlock more features...",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        postRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                HashMap<String, String> tmp = (HashMap<String, String>) dataSnapshot.getValue();
                PostViewAdapter.Post newPost =
                        new PostViewAdapter.Post(tmp.get("Email"),
                                tmp.get("Location"),
                                tmp.get("Title"),
                                tmp.get("Description"),
                                MainActivity.Category.toCategory(tmp.get("Topic")),
                                dataSnapshot.getKey());
                Log.d("db","Email: "+tmp.get("Email"));
                if(newPost.category == MainActivity.category)posts.add(newPost);
                else if(MainActivity.category == ALL)posts.add(newPost);
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
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private Menu menu2;
    private MenuItem loginMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu2 = menu;

        MenuItem publicMenu = menu2.findItem(R.id.action_public);
        publicMenu.setVisible(false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user == null){
            loginMenu = menu2.findItem(R.id.action_logout);
            MenuItem profileMenu = menu2.findItem(R.id.action_profile);
            profileMenu.setEnabled(false);
            loginMenu.setTitle("Login");
        }
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }


}
