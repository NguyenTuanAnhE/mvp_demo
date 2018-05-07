package com.example.anhnt.fastnetwork.base;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

public class FastNetworkHelper<T> {

    private final Class<T> clazz;

    public FastNetworkHelper(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void get(String url, final INetworkResponse<T> responseListener) {

        AndroidNetworking.get(url)
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(clazz, new ParsedRequestListener<T>() {
                    @Override
                    public void onResponse(T response) {
                        responseListener.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        responseListener.onError(anError);
                    }
                });
    }

    public interface INetworkResponse<T> {
        void onSuccess(T response);

        void onError(ANError error);
    }
}
