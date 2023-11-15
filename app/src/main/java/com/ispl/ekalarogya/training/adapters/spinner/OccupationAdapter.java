package com.ispl.ekalarogya.training.adapters.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.models.OccupationModel;

import java.util.List;

public class OccupationAdapter  extends ArrayAdapter<OccupationModel> {

    public OccupationAdapter(Context context,
                            List<OccupationModel> occupationModels)
    {
        super(context, 0, occupationModels);
//        Collections.reverse(occupationModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.village_item_list, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.village_name);
        OccupationModel currentItem = getItem(position);

        if (currentItem != null) {
            textViewName.setText(currentItem.getOccupation());
        }
        return convertView;
    }



}

