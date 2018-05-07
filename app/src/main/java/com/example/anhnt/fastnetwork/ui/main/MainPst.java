package com.example.anhnt.fastnetwork.ui.main;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.error.ANError;
import com.example.anhnt.fastnetwork.base.FastNetworkHelper;
import com.example.anhnt.fastnetwork.model.movie.Movie;
import com.example.anhnt.fastnetwork.model.movie.Search;
import com.example.anhnt.fastnetwork.utils.VolleyUtils;

import java.util.HashMap;

import static com.example.anhnt.fastnetwork.utils.Contants.API.API_KEY;
import static com.example.anhnt.fastnetwork.utils.Contants.API.HOST;
import static com.example.anhnt.fastnetwork.utils.Contants.API.SEARCH_ALL_TITLE;
import static com.example.anhnt.fastnetwork.utils.Contants.API.SEARCH_PAGE;

public class MainPst implements IMainPst {

    private static final String TAG = MainPst.class.getSimpleName();
    IMainView iMainView;

    public MainPst(IMainView listener) {
        this.iMainView = listener;
    }

    @Override
    public void getDataMovie(Context context, String apiKey, String param, String title, String page) {
        HashMap<String, String> params = new HashMap<>();
        params.put("apikey", API_KEY);
        params.put(param, title);
        params.put(SEARCH_PAGE, page);

        String url = VolleyUtils.makeUrl(HOST, params);
        Log.d(TAG, "getDataMovie: " + url);
        if (param.equals(SEARCH_ALL_TITLE)) {
            new FastNetworkHelper<>(Search.class).get(url, new FastNetworkHelper.INetworkResponse<Search>() {
                @Override
                public void onSuccess(Search response) {
                    iMainView.onRequestSuccess(response);
                }

                @Override
                public void onError(ANError error) {
                    String msg = "Request Error";
                    Log.d(TAG, "onError: " + error.toString());
                    iMainView.onRequestError(msg);
                }
            });
        } else {
            new FastNetworkHelper<>(Movie.class).get(url, new FastNetworkHelper.INetworkResponse<Movie>() {
                @Override
                public void onSuccess(Movie response) {
                    Log.d(TAG, "onSuccess: ");
                    iMainView.onRequestSuccess(response);
                }

                @Override
                public void onError(ANError error) {
                    String msg = "Request Error";
                    iMainView.onRequestError(msg);
                }
            });

        }
    }
}
