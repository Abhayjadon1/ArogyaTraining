package com.inventics.ekalarogya.training.adapters.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.models.PurposeModel;

import java.util.List;

public class  PurposeAdapter extends ArrayAdapter<PurposeModel> {

        public PurposeAdapter(Context context, List<PurposeModel> villageListModel) {
                super(context, 0, villageListModel);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                return initView(position, convertView, parent);
        }

        private View initView(int position, View convertView, ViewGroup parent) {
                // It is used to set our custom view.
                if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.village_item_list, parent, false);
                }

                TextView textViewName = convertView.findViewById(R.id.village_name);
                PurposeModel currentItem = getItem(position);

                if (currentItem != null) {
                        textViewName.setText(currentItem.getTitle());
                }
                return convertView;
        }



}

