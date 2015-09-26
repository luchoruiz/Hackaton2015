package com.android.desafioaudionews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.desafioaudionews.R;
import com.android.desafioaudionews.api.UrlConstants;
import com.android.desafioaudionews.interfaces.OnRecyclerItemClick;
import com.android.desafioaudionews.models.Note;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lucho on 25/09/2015.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    Context context;
    List<Note> mDataset;
    Picasso picasso;
    private OnRecyclerItemClick mlistener;

    public NoteAdapter(Context context, List<Note> mData, OnRecyclerItemClick listener) {
        this.context = context;
        mDataset = mData;
        picasso = Picasso.with(context);
        this.mlistener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_note_cardview, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Note currentItem= mDataset.get(position);

        onViewHolderClick listener = new onViewHolderClick() {
            @Override
            public void onclick() {
                mlistener.onListItemClick(mDataset.get(position));
            }
        };
        holder.setClickListener(listener);

        String imageUrl=currentItem.imageSrc;
        if (imageUrl!=null){
            loadPhoto(UrlConstants.BASE_IMAGE_URL+imageUrl, holder.noteImage,currentItem.imageWidth,currentItem.imageHeight);
        } else {
           holder.noteImage.setImageResource(R.drawable.placeholder);
        }
        holder.txtTitle.setText(currentItem.titulo);
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
        private onViewHolderClick clickListener;

        public ViewHolder(View view) {
            super(view);
            txtTitle  = (TextView) view.findViewById(R.id.txtTitle);
            txtNoteBajada =(TextView) view.findViewById(R.id.txtBajada);
            noteImage = (ImageView) view.findViewById(R.id.imgNote);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onclick();
                }
            });
        }

        public void setClickListener(onViewHolderClick listener) {
            clickListener = listener;
        }
    }

    private void loadPhoto(String url, ImageView imageView, int imageHeight, int imageWidth) {

        picasso.load(url)
                .noFade()
                .resize(imageHeight/2, imageWidth/2)
                .error(R.drawable.placeholder)
                .into(imageView);
    }

    public interface onViewHolderClick {
        void onclick();
    }
}
