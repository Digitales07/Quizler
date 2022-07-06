package com.assorttech.myquizler.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.assorttech.myquizler.R;

public class ChapterCustomAdapter extends ArrayAdapter<String> {
    Context mContext;
    String subName[];

    public ChapterCustomAdapter(Context c, String subjects[]) {
        super(c, R.layout.chapter_list_items, subjects);
        mContext = c;
        subName = subjects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.chapter_list_items, parent, false);

        TextView textView = row.findViewById(R.id.chapter);

        textView.setText(subName[position]);

        return row;
    }
}

