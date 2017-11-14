package com.example.quan.english;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class ShareFB {
    private String uri;
    private String uriImage;
    private String content;
    private Context context;

    public ShareFB(String uri, String uriImage, String content, Context context) {
        this.uri = uri;
        this.uriImage = uriImage;
        this.content = content;
        this.context = context;
    }

    public void share() {
        final ShareDialog shareDialog = new ShareDialog((Activity) context);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(uri))
                .setContentTitle("English We Can")
                .setImageUrl(Uri.parse(uriImage))
                .setContentDescription(this.content)
                .build();
        shareDialog.show(content);
    }
}
