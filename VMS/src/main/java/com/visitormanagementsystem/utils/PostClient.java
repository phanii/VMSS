package com.visitormanagementsystem.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.MediaType;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Phani.Gullapalli on 25/11/2015.
 */
public class PostClient {
    public static final String BASE_URL = "http://172.16.100.244:9090/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)

            .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
    public MyApiEndpointInterface myApiEndpointInterface = retrofit.create(MyApiEndpointInterface.class);

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    /*OkHttpClient client = new OkHttpClient();

    Call post(String url, String json, Callback callback) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }*/
    private static final String[] DATE_FORMATS = new String[]{
            "yyyy-MM-dd'T'HH:mm:ss'Z'"
    };
    Gson gson = new GsonBuilder().setDateFormat("E MMM dd HH:mm:ss Z yyyy").create();

    public static class DateDeserializer implements JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonElement jsonElement, Type typeOF,
                                JsonDeserializationContext context) throws JsonParseException {
            for (String format : DATE_FORMATS) {
                try {
                    return new SimpleDateFormat(format, Locale.US).parse(jsonElement.getAsString());
                } catch (ParseException e) {
                }
            }
            throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
                    + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
        }
    }




}
