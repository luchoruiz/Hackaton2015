package com.android.desafioaudionews.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.desafioaudionews.R;
import com.android.desafioaudionews.models.Note;
import com.j256.ormlite.stmt.query.Not;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lucho on 25/09/2015.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    Context context;
    List<Note> mDataset;
    Picasso picasso;


    public NoteAdapter(Context context, List<Note> mData) {
        this.context = context;
        mDataset = mData;
        picasso = Picasso.with(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
   /* @Override public void onClick(final View v) {
        // Give some time to the ripple to finish the effect
        if (onItemClickListener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override public void run() {
                    onItemClickListener.onItemClick(v, (ViewModel) v.getTag());
                }
            }, 200);
        }
    }*/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Note currentItem= mDataset.get(position);
        String imageUrl=null;
        if (imageUrl!=null){
            loadPhoto(imageUrl, holder.noteImage);
        } else {
           // holder.noteImage.setImageResource(R.drawable.default_image);
        }
        holder.txtNoteBajada.setText(currentItem.titulo);
        holder.txtNoteBajada.setText(currentItem.bajada);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtNoteBajada;
        ImageView noteImage;

        public ViewHolder(View view) {
            super(view);
            txtTitle  = (TextView) view.findViewById(R.id.txtTitle);
            txtNoteBajada =(TextView) view.findViewById(R.id.txtBajada);
            noteImage = (ImageView) view.findViewById(R.id.imgNote);
        }
    }

    private void loadPhoto(String url, ImageView imageView) {

        picasso.load(url)
                .noFade()
                //.error(R.drawableplace_holder_read)
                .into(imageView);
    }
}
