package com.example.listoffilmssequenia.data.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listoffilmssequenia.R;
import com.example.listoffilmssequenia.data.data.model.Genre;
import com.example.listoffilmssequenia.data.ui.films.OnClickGenreListener;

import java.util.ArrayList;
import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.Holder> {

    private OnClickGenreListener onClickGenreListener;

    private List<Genre> newGenres = new ArrayList<>();

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
        return newGenres.size();
    }

    public void setGenres(List<String> genres) {
        newGenres.clear();
        for (int i = 0; i < genres.size(); i++) {
            newGenres.add(new Genre(genres.get(i), false));
        }

        notifyDataSetChanged();
    }

    public void setSelectedGenre(int genrePositionSelected, List<String> genres){
        setGenres(genres);
        newGenres.get(genrePositionSelected).setGenreChecked(true);
        onClickGenreListener.onClickGenre(genrePositionSelected, true);
        notifyDataSetChanged();
    }


    class Holder extends RecyclerView.ViewHolder {

        TextView genre;

        Holder(@NonNull View itemView) {
            super(itemView);

            genre = itemView.findViewById(R.id.genre);
        }

        void bind(final int position) {
            genre.setText(newGenres.get(position).getGenre());

            if (newGenres.get(position).isGenreChecked()) {
                genre.setBackgroundResource(R.color.colorPrimary);
            } else {
                genre.setBackgroundResource(R.color.colorTransparent);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 0; i < newGenres.size(); i++) {
                        if (i != position) {
                            newGenres.get(i).setGenreChecked(false);
                        }
                    }
                    newGenres.get(position).setGenreChecked(!newGenres.get(position).isGenreChecked());
                    if (newGenres.get(position).isGenreChecked()) {
                        onClickGenreListener.onClickGenre(position, true);
                    }else{
                        onClickGenreListener.onClickGenre(position, false);
                    }
                    notifyDataSetChanged();
                }
            });

        }

    }
}
