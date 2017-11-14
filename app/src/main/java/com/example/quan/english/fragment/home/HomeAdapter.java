package com.example.quan.english.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quan.english.Key;
import com.example.quan.english.R;
import com.example.quan.english.ServerApi;
import com.example.quan.english.fragment.audio.AudioPlayActivity;
import com.example.quan.english.fragment.menu.ClickListener;
import com.example.quan.english.fragment.menu.shop.Book;
import com.example.quan.english.fragment.menu.shop.BookActivity;
import com.example.quan.english.fragment.video.Video;
import com.example.quan.english.fragment.video.VideoPlay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    private Activity activity;
    private ClickListener clickListener;

    public HomeAdapter(Context context, Activity activity) {
        this.activity = activity;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_home_imv, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_home_video, parent, false);
                break;
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_home_audio, parent, false);
                break;
            case 4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_home_book, parent, false);
                break;

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position == 1) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.recyclerView.setLayoutManager(layoutManager);
            final ArrayList<Video> videos = new ArrayList<>();
            ServerApi api = new ServerApi("http://it2k.comli.com/video/data.json");
            api.getDataAsync(new ServerApi.OnDataLoadListener() {
                @Override
                public void onLoaded(String data) {
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        JSONArray jsonArray = jsonObject.getJSONArray("types");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObjectNameVideo = jsonArray.getJSONObject(i);
                            String nameType = jsonObjectNameVideo.getString("name");
                            JSONArray jsonArrayVideo = jsonObject.getJSONArray(jsonObjectNameVideo.getString("name"));
                            for (int j = 0; j < jsonArrayVideo.length(); j++) {
                                JSONObject jsonObjectVideo = jsonArrayVideo.getJSONObject(j);
                                String name = jsonObjectVideo.getString("name");
                                String url = jsonObjectVideo.getString("url");
                                videos.add(new Video(name, url, nameType));
                            }
                        }
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Collections.shuffle(videos);
                                VideoHomeAdapter videoHomeAdapter = new VideoHomeAdapter(videos, context);
                                videoHomeAdapter.setClickListener(new ClickListener() {
                                    @Override
                                    public void itemClicked(View view, int position) {
                                        Intent intent = new Intent(context, VideoPlay.class);
                                        intent.putExtra(Key.NAME_TYPE_VIDEO, videos.get(position).getNameType());
                                        intent.putExtra(Key.NAME_TYPE_AUDIO, videos.get(position).getName());
                                        intent.putExtra(Key.URL_YOUTUBE, videos.get(position).getPath());
                                        context.startActivity(intent);
                                    }
                                });
                                holder.recyclerView.setAdapter(videoHomeAdapter);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError() {
                }
            });

        }
        if (position == 0) {
            ArrayList<String> strings = new ArrayList<>();
            strings.add("http://bangdaihoc500k.com/wp-content/uploads/2016/08/psp-videos/139-youtube_EbO5q3aAS_c.jpg");
            strings.add("https://3.bp.blogspot.com/-Xkf7IeWrFUY/WKR9N-VspkI/AAAAAAAAApo/fhwYSIyuiEQyQO1oTaj9TYGiHuLmT0pvACLcB/s1600/60-cau-tieng-anh-hoi-ve-nghe-nghiep.png");
            strings.add("http://www.tkbooks.vn/wp-content/uploads/2017/01/phuong-phap-hoc-tieng-anh-hieu-qua-nhat1.jpg");
            holder.viewPager.setAdapter(new ImageAdapter(strings));
        }
        if (position == 2) {
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<String> strings = null;
                    try {
                        Document document = Jsoup.connect("http://www.listenaminute.com/").get();
                        Elements elements = document.select("tbody tr td ul li font a");
                        strings = new ArrayList<>();
                        for (int j = 0; j < elements.size(); j++) {
                            Element element = elements.get(j);
                            String link = element.attr("href");
                            link = link.substring(link.lastIndexOf("/") + 1);
                            link = link.replaceAll(".html", "");
                            link = link.replaceAll("_", " ");
                            strings.add(link);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final ArrayList<String> finalStrings = strings;
                    Collections.shuffle(finalStrings);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AudioHomeAdapter audioAdapter = new AudioHomeAdapter(context,finalStrings);
                            audioAdapter.setClickListener(new ClickListener() {
                                @Override
                                public void itemClicked(View view, int position) {
                                    Intent intent = new Intent(context, AudioPlayActivity.class);
                                    intent.putExtra(Key.LINK_AUDIO, finalStrings.get(position));
                                    context.startActivity(intent);
                                }
                            });
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                            holder.rcvAudio.setLayoutManager(layoutManager);
                            holder.rcvAudio.setAdapter(audioAdapter);
                        }
                    });
                }
            });
            thread.start();
        }
        if (position == 3) {

            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    final ArrayList<Book> books;
                    books = new ArrayList<>();
                    try {
                        Document document;
                        document = Jsoup.connect("https://tiki.vn/sach-hoc-tieng-anh/c1856?src=tree&page=2").get();
                        Elements elements = document.select("div.product-item a");
                        for (int i = 0; i < elements.size(); i++) {
                            String link = elements.get(i).attr("href");
                            Elements elements3 = elements.get(i).select("p.price-sale");
                            String price = elements3.text();
                            Elements elements2 = elements.get(i).select("span.title");
                            String title = elements2.text();
                            Elements elements1 = elements.get(i).select("span img");
                            String linkImage;
                            if (elements1.size() == 1) {
                                linkImage = elements1.attr("src");
                            } else {
                                linkImage = elements1.get(1).attr("src");
                            }
                            books.add(new Book(link, title, price, linkImage));
                        }
                        Collections.shuffle(books);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final BookHomeAdapter bookAdapter = new BookHomeAdapter(context, books);
                            bookAdapter.setClickListener(new ClickListener() {
                                @Override
                                public void itemClicked(View view, int position) {
                                    Intent intent = new Intent(context, BookActivity.class);
                                    intent.putExtra(Key.LINK_BOOK, books.get(position).getLink());
                                    intent.putExtra(Key.NAME_BOOK, books.get(position).getName());
                                    context.startActivity(intent);
                                }
                            });
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                            holder.rcvBook.setLayoutManager(layoutManager);
                            holder.rcvBook.setAdapter(bookAdapter);
                        }
                    });
                }
            });
            thread.start();
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerView recyclerView, rcvAudio, rcvBook;
        private ViewPager viewPager;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rcv_home_video);
            viewPager = (ViewPager) itemView.findViewById(R.id.vpg_home_imv);
            rcvAudio = (RecyclerView) itemView.findViewById(R.id.rcv_home_audio);
            rcvBook = (RecyclerView) itemView.findViewById(R.id.rcv_home_book);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getAdapterPosition());
            }
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
        }
        return 1;
    }
}