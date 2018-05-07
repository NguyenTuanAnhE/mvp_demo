package com.example.anhnt.fastnetwork.ui.main;

public interface IMainView<T> {
    void onRequestSuccess(T response);

    void onRequestError(String msg);
}
