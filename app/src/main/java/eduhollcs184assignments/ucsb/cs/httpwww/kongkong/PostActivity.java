package eduhollcs184assignments.ucsb.cs.httpwww.kongkong;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.HashMap;

public class PostActivity extends AppCompatActivity {


    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = db.getReference();
    DatabaseReference postRef = rootRef.child("Posts");
    FirebaseAuth myAuth = FirebaseAuth.getInstance();
    String email = myAuth.getCurrentUser().getEmail();

    EditText Title, Location, Email, Description;
    ImageView button;
    Button post;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference mStorage = storage.getReference();
    private static final int GALLERY = 4;

    //spinner for different topics
    //Spinner spinner;
    //ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        if (ContextCompat.checkSelfPermission(PostActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PostActivity.this, new String[] { android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.choices, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Title = (EditText) findViewById(R.id.titleeditText);
        Location = (EditText) findViewById(R.id.locationeditText3);
        //Email = (EditText) findViewById(R.id.emaileditText4);
        Description = (EditText) findViewById(R.id.deseditText5);
        button = (ImageView) findViewById(R.id.postbutton);
        post = (Button) findViewById(R.id.button3);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY);
            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Animation myAnim = AnimationUtils.loadAnimation(PostActivity.this, R.anim.bounce);

                // Use bounce interpolator with amplitude 0.2 and frequency 20
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);
                button.startAnimation(myAnim);
                String t = Title.getText().toString();
                String l = Location.getText().toString();
                //String e = Email.getText().toString();
                String d = Description.getText().toString();
                String s = spinner.getSelectedItem().toString();


                HashMap<String,String> posts_map = new HashMap<>();
                posts_map.put("Title", t);
                posts_map.put("Location", l);
                posts_map.put("Email", email);
                posts_map.put("Description", d);
                posts_map.put("Topic", s);

                postRef.push().setValue(posts_map);


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY){
            Uri uri = data.getData();
            //Log.i("uriiiiii",String.valueOf(uri.getLastPathSegment()));
            button.setImageURI(uri);

            //StorageReference fileName = mStorage.child("Photos/" + uri.getLastPathSegment() + ".png");
            StorageReference fileName = mStorage.child("images/"+uri.getLastPathSegment() + ".jpg");

            UploadTask up = fileName.putFile(uri);
            up.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                }
            });
        }

    }
}
