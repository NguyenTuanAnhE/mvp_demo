package com.example.anhnt.fastnetwork.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.anhnt.fastnetwork.R;
import com.example.anhnt.fastnetwork.base.BaseActivity;
import com.example.anhnt.fastnetwork.base.FastNetworkHelper;
import com.example.anhnt.fastnetwork.model.movie.Movie;
import com.example.anhnt.fastnetwork.model.movie.Search;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.registerable.connection.Connectable;
import com.novoda.merlin.registerable.disconnection.Disconnectable;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.example.anhnt.fastnetwork.utils.Contants.API.API_KEY;
import static com.example.anhnt.fastnetwork.utils.Contants.API.MOVIE_ID;
import static com.example.anhnt.fastnetwork.utils.Contants.API.SEARCH_ALL_TITLE;

public class MainActivity extends BaseActivity implements IMainView, BaseQuickAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.edit_text_search)
    EditText mEdtSearch;
    @BindView(R.id.button_get_data)
    Button mBtnGetData;
    @BindView(R.id.recycle_view_video)
    RecyclerView mRcvVideo;
    @BindView(R.id.text_previous_page)
    TextView mTextViewPreviousPage;
    @BindView(R.id.text_next_page)
    TextView mTextViewNextPage;
    @BindView(R.id.text_current_page)
    TextView mTextViewCurrentPage;
    @BindView(R.id.text_total_page)
    TextView mTextViewTotalPage;

    private MainPst mainPst;
    private VideoAdapter2 videoAdapter;
    private ArrayList<Movie> movies;

    private int currentPage = 1;
    private int totalPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setTitle(R.string.search_movie_activity);

        mainPst = new MainPst(this);
        mRcvVideo.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean showHomeEnable() {
        return true;
    }

    @OnClick(R.id.button_get_data)
    void onGetData() {
        Log.d(TAG, "onGetData: ");
        currentPage = 1;
        getDataMovies();
    }

    @OnClick(R.id.text_next_page)
    void goNextPage() {
        currentPage++;
        if (currentPage > totalPage) {
            return;
        }
        getDataMovies();
    }

    @OnClick(R.id.text_previous_page)
    void goPreviousPage() {
        currentPage--;
        if (currentPage < 1) {
            return;
        }
        getDataMovies();
    }

    @Override
    public void onRequestSuccess(Object response) {
        if (response != null && response instanceof Search) {
            Search result = (Search) response;
            ArrayList<Movie> movies = result.getSearch();
            if (movies == null) {
                Toast.makeText(this, ((Search) response).getError(), Toast.LENGTH_SHORT).show();
            } else {
                getResultPage(result, movies);
            }
        }
        hideProcessDialog();
    }

    @Override
    public void onRequestError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        hideProcessDialog();
    }

    public void getDataMovies() {
        mainPst.getDataMovie(MainActivity.this, API_KEY, SEARCH_ALL_TITLE,
                mEdtSearch.getText().toString().trim(), String.valueOf(currentPage));
        showProcessDialog();
    }

    public void getResultPage(Search search, ArrayList<Movie> movies) {
        totalPage = Integer.parseInt(search.getTotalResults()) / 10 + 1;
        mTextViewCurrentPage.setText(String.valueOf(currentPage));
        mTextViewTotalPage.setText(String.valueOf(totalPage));
        this.movies = movies;
        videoAdapter = new VideoAdapter2(this, R.layout.item_video, movies);
        videoAdapter.setOnItemClickListener(this);
        mRcvVideo.setAdapter(videoAdapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(MOVIE_ID, movies.get(position).getImdbID());
        startActivity(intent);

    }


}
