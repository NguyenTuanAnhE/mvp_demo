package com.example.anhnt.fastnetwork.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhnt.fastnetwork.R;
import com.example.anhnt.fastnetwork.model.movie.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private Context context;
    private ArrayList<Movie> movies;
    private LayoutInflater inflater;
    private OnMovieSelectListener onMovieSelectListener;

    public VideoAdapter(Context context, OnMovieSelectListener onMovieSelectListener) {
        this.context = context;
        this.onMovieSelectListener = onMovieSelectListener;
        inflater = LayoutInflater.from(context);
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoHolder(inflater.inflate(R.layout.item_video, parent, false));
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        final Movie movie = movies.get(position);
        holder.setData(movie);

        holder.mImageViewPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMovieSelectListener.onMovieSelected(movie.getImdbID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_poster)
        ImageView mImageViewPoster;
        @BindView(R.id.text_movie_name)
        TextView mTextViewMovieTitle;
        @BindView(R.id.text_year)
        TextView mTextViewMovieYear;
        @BindView(R.id.text_id)
        TextView mTextViewMovieId;
        @BindView(R.id.text_type)
        TextView mTextViewMovieType;

        public VideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setData(Movie movie) {
            Picasso.with(context).load(movie.getPoster())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(mImageViewPoster);
            mTextViewMovieTitle.setText(movie.getTitle());
            mTextViewMovieYear.setText(movie.getYear());
            mTextViewMovieId.setText(movie.getImdbID());
            mTextViewMovieType.setText(movie.getType());
        }
    }

    interface OnMovieSelectListener {
        void onMovieSelected(String id);
    }

}
