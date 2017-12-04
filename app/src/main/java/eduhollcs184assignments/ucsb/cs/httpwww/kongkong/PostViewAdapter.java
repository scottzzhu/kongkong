package eduhollcs184assignments.ucsb.cs.httpwww.kongkong;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by scottzhu on 2017/12/3.
 * Adapter for RecyclerView.
 */

public class PostViewAdapter extends RecyclerView.Adapter<PostViewAdapter.PostViewHolder> {

    static class Post {
        String email;
        String location;
        String title;
        String desc;

        public Post(String email, String location, String title, String desc) {
            this.email = email;
            this.location = location;
            this.title = title;
            this.desc = desc;
        }
    }

    List<Post> postList;

    PostViewAdapter(List<Post> list){
        postList = list;
        Log.d("pva", "Created Adapter.");
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_viewcard, parent, false);
        PostViewHolder holder = new PostViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.postEmail.setText(postList.get(position).email);
        holder.postLocation.setText(postList.get(position).location);
        holder.postTitle.setText(postList.get(position).title);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView postEmail;
        TextView postLocation;
        TextView postTitle;

        public PostViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            postEmail = itemView.findViewById(R.id.post_email);
            postLocation = itemView.findViewById(R.id.post_location);
            postTitle = itemView.findViewById(R.id.post_title);
        }
    }
}
