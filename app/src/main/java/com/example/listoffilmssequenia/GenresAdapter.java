package com.example.listoffilmssequenia;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.Holder> {

    List<String> genres = new ArrayList<>();
    OnClickGenreListener onClickGenreListener;

    public GenresAdapter(OnClickGenreListener onClickGenreListener) {
        this.onClickGenreListener = onClickGenreListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public void setGenres(List<String> genres) {
        this.genres.addAll(genres);
        Log.d("ListFragment", "" + this.genres.size());
        notifyDataSetChanged();
    }


    public class Holder extends RecyclerView.ViewHolder{

        TextView genre;

        public Holder(@NonNull View itemView) {
            super(itemView);

            genre = itemView.findViewById(R.id.genre);
        }

        public void bind(final int position) {
            genre.setText(genres.get(position));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickGenreListener.onClickGenre(position);
                }
            });
        }
    }
}
