package com.assorttech.myquizler.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.assorttech.myquizler.R;

    public class MyCustomAdapter extends ArrayAdapter<String> {
        Context mContext;
        String subName[];

        public MyCustomAdapter(Context c, String subjects[]) {
            super(c, R.layout.list_item, subjects);
            mContext = c;
            subName = subjects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.list_item, parent, false);

            TextView textView = row.findViewById(R.id.text);

            textView.setText(subName[position]);

            return row;
        }
    }
