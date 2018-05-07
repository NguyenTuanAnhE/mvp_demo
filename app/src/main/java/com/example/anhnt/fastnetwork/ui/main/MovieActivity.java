package com.example.anhnt.fastnetwork.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhnt.fastnetwork.R;
import com.example.anhnt.fastnetwork.base.BaseActivity;
import com.example.anhnt.fastnetwork.model.movie.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

import static com.example.anhnt.fastnetwork.utils.Contants.API.API_KEY;
import static com.example.anhnt.fastnetwork.utils.Contants.API.MOVIE_ID;
import static com.example.anhnt.fastnetwork.utils.Contants.API.REQUEST_BY_ID;

public class MovieActivity extends BaseActivity implements IMainView {

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
    @BindView(R.id.text_description)
    TextView mTextViewMovieDescription;
    @BindView(R.id.text_actor)
    TextView mTextViewMovieActor;
    @BindView(R.id.text_director)
    TextView mTextViewMovieDirector;
    @BindView(R.id.text_genre)
    TextView mTextViewMovieGenre;
    @BindView(R.id.text_runtime)
    TextView mTextViewMovieRuntime;

    private MainPst mainPst;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.movie_activity);

        showProcessDialog();
        getDataMovie();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_movie;
    }

    @Override
    protected boolean showHomeEnable() {
        return true;
    }

    @Override
    public void onRequestSuccess(Object response) {
        if (response != null && response instanceof Movie) {
            Movie movie = (Movie) response;
            Picasso.with(this).load(movie.getPoster())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(mImageViewPoster);
            mTextViewMovieTitle.setText(movie.getTitle());
            mTextViewMovieYear.setText(movie.getYear());
            mTextViewMovieId.setText(movie.getImdbID());
            mTextViewMovieType.setText(movie.getType());
            mTextViewMovieDescription.setText(movie.getPlot());
            mTextViewMovieActor.setText(movie.getActors());
            mTextViewMovieDirector.setText(movie.getDirector());
            mTextViewMovieGenre.setText(movie.getGenre());
            mTextViewMovieRuntime.setText(movie.getRuntime());
        }
        hideProcessDialog();
    }

    @Override
    public void onRequestError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        hideProcessDialog();
    }

    public void getDataMovie() {
        mainPst = new MainPst(this);
        String searchKey = getIntent().getStringExtra(MOVIE_ID);
        mainPst.getDataMovie(this, API_KEY, REQUEST_BY_ID, searchKey, "1");
    }

}
