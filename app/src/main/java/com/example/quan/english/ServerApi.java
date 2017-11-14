package com.example.quan.english;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerApi {
    String link;

    public ServerApi(String link) {
        this.link = link;
    }

    public void getDataAsync(final OnDataLoadListener listener) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(link);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    String data = convertInputStreamToString(inputStream);
                    listener.onLoaded(data);
                    inputStream.close();
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onError();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private String convertInputStreamToString(InputStream inputStream) {
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface OnDataLoadListener {
        void onLoaded(String data);

        void onError();
    }
}