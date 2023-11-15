package com.ispl.ekalarogya.training.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.models.ClickImageModel;

import java.util.List;

public class PosterImageClickAdapter extends RecyclerView.Adapter<PosterImageClickAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<ClickImageModel> posterDisplayModelList;
    private static final String TAG = "OwnerListAdapter";
    private PosterImageClickAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(ClickImageModel item);
    }

    public PosterImageClickAdapter(Context context, List<ClickImageModel> posterDisplayModelList) {
        this.mContaxt = context;
        this.posterDisplayModelList=posterDisplayModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PosterImageClickAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_add_image, null);

        return new PosterImageClickAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PosterImageClickAdapter.ViewHolder holder, int position) {
        final ClickImageModel baseResponse = posterDisplayModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());
//       holder.viewImage.setImageBitmap(baseResponse.getPhotos());
        holder.bind(posterDisplayModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+posterDisplayModelList.size());
        return posterDisplayModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView viewImage,delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewImage=itemView.findViewById(R.id.list_Image);
            delete=itemView.findViewById(R.id.list_Image_delete);

        }


        public void bind(ClickImageModel posterDisplayModelList, PosterImageClickAdapter.OnItemClickListener listener, int position) {


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+posterDisplayModelList.getPhotos()+"\n");

                }
            });

        }
    }



}
