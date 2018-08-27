package com.appodeal.support.test.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.appodeal.support.test.OnListViewButtonClickListener;
import com.appodeal.support.test.R;
import com.appodeal.support.test.constants.Constants;
import com.appodeal.support.test.items.ListViewItem;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter implements Constants {

    private ArrayList<ListViewItem> listViewItems = new ArrayList<>();
    private Context context;
    OnListViewButtonClickListener onListViewButtonClickListener;

    public ListViewAdapter(Context context, ArrayList<ListViewItem> listViewItems,
                           OnListViewButtonClickListener onListViewButtonClickListener) {
        if (listViewItems != null)
            this.listViewItems = listViewItems;
        this.context = context;
        this.onListViewButtonClickListener = onListViewButtonClickListener;
    }

    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        TextView title;
        TextView description;
        TextView sign;
        RatingBar ratingBar;
        Button buttonAction;
        ImageView adIcon;
        ImageView adImage;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_view_item, viewGroup, false);

            viewHolder = new ViewHolder();

            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.description = (TextView) view.findViewById(R.id.description);
            viewHolder.sign = (TextView) view.findViewById(R.id.ad_sign);
            viewHolder.ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
            viewHolder.buttonAction = (Button) view.findViewById(R.id.button_action);
            viewHolder.adIcon = (ImageView) view.findViewById(R.id.icon);
            viewHolder.adImage = (ImageView) view.findViewById(R.id.image);

            view.setTag(viewHolder);
        } else viewHolder = (ViewHolder) view.getTag();

        viewHolder.title.setText(listViewItems.get(i).getTitle());

        viewHolder.description.setText(listViewItems.get(i).getDescription());
        viewHolder.description.setMaxLines(3);
        viewHolder.description.setEllipsize(TextUtils.TruncateAt.END);

        viewHolder.sign.setText(listViewItems.get(i).getSign());

        if (listViewItems.get(i).getRating() == 0) {
            viewHolder.ratingBar.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.ratingBar.setVisibility(View.VISIBLE);
            viewHolder.ratingBar.setRating(listViewItems.get(i).getRating());
            viewHolder.ratingBar.setIsIndicator(true);
            viewHolder.ratingBar.setStepSize(0.1f);
        }

        viewHolder.buttonAction.setText(listViewItems.get(i).getButtonAction());
        viewHolder.buttonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onListViewButtonClickListener.onButtonClick(listViewItems.get(i));
            }
        });

        viewHolder.adIcon.setImageBitmap(listViewItems.get(i).getAdIcon());
        viewHolder.adImage.setImageBitmap(listViewItems.get(i).getAdImage());

        return view;
    }
}
