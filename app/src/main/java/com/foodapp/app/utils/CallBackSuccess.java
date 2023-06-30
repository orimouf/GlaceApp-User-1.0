package com.foodapp.app.utils;

import com.airbnb.lottie.parser.moshi.JsonReader;

public interface CallBackSuccess {
    void onstart();
    void success(JsonReader.Token token);
    void failer(Exception error);
}
