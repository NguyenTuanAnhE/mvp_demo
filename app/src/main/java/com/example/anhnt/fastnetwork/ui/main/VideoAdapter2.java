package com.example.anhnt.fastnetwork.ui.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.androidnetworking.widget.ANImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.anhnt.fastnetwork.R;
import com.example.anhnt.fastnetwork.model.movie.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter2 extends BaseQuickAdapter<Movie, BaseViewHolder> {

    private Context mContext;

    public VideoAdapter2(Context context, int layoutResId, @Nullable List<Movie> data) {
        super(layoutResId, data);
        openLoadAnimation(SLIDEIN_LEFT);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie movie) {
        helper.setText(R.id.text_movie_name, movie.getTitle());
        helper.setText(R.id.text_year, movie.getYear());
        helper.setText(R.id.text_id, movie.getImdbID());
        helper.setText(R.id.text_type, movie.getType());

//        ANImageView mANImageViewPoster = helper.getView(R.id.image_poster);
//        mANImageViewPoster.setDefaultImageResId(R.drawable.ic_launcher_background);
//        mANImageViewPoster.setErrorImageResId(R.drawable.ic_launcher_foreground);
//        mANImageViewPoster.setImageUrl(movie.getPoster());

        Picasso.with(mContext).load(movie.getPoster())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into((ImageView) helper.getView(R.id.image_poster));
    }


}
