package eduhollcs184assignments.ucsb.cs.httpwww.kongkong;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
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
    ImageView picshow;
    Bitmap pic;
    String p;
    Button delete;
    final String[] author_email = new String [1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_post_show);
        title = (TextView) findViewById(R.id.postview_title);
        author = (TextView) findViewById(R.id.postview_author);
        location = (TextView) findViewById(R.id.postview_location);
        content = (TextView) findViewById(R.id.postview_content);
        email = (ImageButton) findViewById(R.id.email_button);
        picshow =(ImageView) findViewById(R.id.picshow);
        delete = (Button) findViewById(R.id.delete_button);
        final String userEmail = mAuth.getCurrentUser().getEmail();
        Intent intent = getIntent();
        final String post_id = intent.getStringExtra("ID");
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
                    p = tmp.get("PictureUri");
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference mStorage = storage.getReference();
                    StorageReference islandRef = mStorage.child("images/" + p);
                    /*
                    try {
                        final File localFile = File.createTempFile("Images", "bmp");
                        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // Local temp file has been created
                                pic = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                picshow.setImageBitmap(pic);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    */

                        final long ONE_MEGABYTE = 2048 * 2048;
                        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                // Data for "images/island.jpg" is returns, use this as needed
                                pic = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                picshow.setImageBitmap(pic);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    if (userEmail.equals(author_email[0]) == false){
                        delete.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                System.out.println(author_email[0]);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{author_email[0]});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Message From Kong");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "I am interested in your post ...");
                emailIntent.setType("message/rfc882");
                startActivity(Intent.createChooser(emailIntent, "Choose email client..."));
            }
        });
        final Context context = getApplicationContext();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = (String) delete.getText();
                System.out.println(text);

                if(text.equals("Delete")){
                    delete.setText("Confirm");
                }
                else if(text.equals("Confirm")){
                DatabaseReference delete_node = postRef.child(post_id);
                delete_node.removeValue();
                Toast.makeText(context, "Post deleted", Toast.LENGTH_SHORT).show();
                Intent backIntent =new Intent(context, MainActivity.class);
                context.startActivity(backIntent);
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), PostViewActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }




}
