package com.appodeal.support.test.activities;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.NativeAd;
import com.appodeal.support.test.OnListViewButtonClickListener;
import com.appodeal.support.test.R;
import com.appodeal.support.test.adapters.ListViewAdapter;
import com.appodeal.support.test.items.ListViewItem;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements OnListViewButtonClickListener {

    ListView listView;
    ArrayList<ListViewItem> listViewItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getAdContext();
    }

    public void showNativeAd() {
        NativeAd nativeAd = Appodeal.getNativeAds(1).get(0);

        for (int i = 0; i < 10; i++) {
            if (i == 4) {
                listViewItems.add(new ListViewItem(nativeAd.getTitle(), nativeAd.getDescription(),
                        getResources().getString(R.string.advertising_marker),
                        nativeAd.getCallToAction(), nativeAd.getRating(), nativeAd.getIcon(),
                        nativeAd.getImage()));
            } else {
                listViewItems.add(new ListViewItem(getResources().getString(R.string.title) + i,
                        getResources().getString(R.string.description) + i,
                        getResources().getString(R.string.empty_field),
                        getResources().getString(R.string.button_text) + i, 0,
                        BitmapFactory.decodeResource(getResources(), R.drawable.photo),
                        BitmapFactory.decodeResource(getResources(), R.drawable.pictures)));
            }
        }

        listView = (ListView) this.findViewById(R.id.list_view);
        listView.setAdapter(new ListViewAdapter(this, listViewItems, SecondActivity.this));
    }

    private void getAdContext() {
        Appodeal.setAutoCacheNativeIcons(true);
        Appodeal.setAutoCacheNativeMedia(false);
        showNativeAd();
    }

    @Override
    public void onButtonClick(ListViewItem listViewItem) {
        if (listViewItem.getSign().equals(getResources().getString(R.string.advertising_marker)))
            Appodeal.show(this, Appodeal.INTERSTITIAL);
    }
}
