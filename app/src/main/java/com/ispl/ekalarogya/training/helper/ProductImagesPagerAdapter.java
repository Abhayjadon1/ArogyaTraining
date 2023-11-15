package com.ispl.ekalarogya.training.helper;

//public class ProductImagesPagerAdapter {
//}

import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;


public class ProductImagesPagerAdapter extends PagerAdapter {

    private List<ProductImageModel> productImagesActualPath = new ArrayList<>(0);
    private LayoutInflater inflater;
    private Context context;
    private DeleteImage deleteImage;

    public interface DeleteImage{
        void deleteListner(String id);
    }

    public ProductImagesPagerAdapter(Context context,DeleteImage deleteImage) {
        this.context = context;
        this.deleteImage=deleteImage;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.5f;
    }

    @Override
    public int getCount() {
        return productImagesActualPath.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        View rootLayout;
        rootLayout = inflater.inflate(R.layout.product_image_pager_item, view, false);
        ImageView imageView = rootLayout.findViewById(R.id.product_image_pager_image_view);
        ImageView deleteButton = rootLayout.findViewById(R.id.product_image_pager_delete_button);

        if (productImagesActualPath.get(position).getImageType() ==ProductImageModel.LOCAL_IMAGE_TYPE) {
            ImageUtils.loadImageURI(context, imageView, productImagesActualPath.get(position).getImageURI());
        } else {
            ImageUtils.loadImageUrl(context, imageView, R.drawable.no_image_icon,  productImagesActualPath.get(position).getImagePath());
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //productImagesActualPath.get(position).
                productImagesActualPath.remove(position);
                notifyDataSetChanged();
                if(deleteImage!=null){

                    if(productImagesActualPath.size()>0)
                    {
                        Log.d("TAG", "onClick: "+productImagesActualPath.get(position).getId());
                        deleteImage.deleteListner(String.valueOf(productImagesActualPath.get(position).getId()));
                    }
                }
            }
        });
        view.addView(rootLayout, 0);
        return rootLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    // This is called when notifyDataSetChanged() is called
    @Override
    public int getItemPosition(@NonNull Object object) {
        // refresh all fragments when data set changed
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public List<ProductImageModel> getProductImages() {
        return productImagesActualPath;
    }

    public void addImage(Uri productImage, String imagePath) {
        if (productImage != null) {
            ProductImageModel imageModel = new ProductImageModel();
            imageModel.setImagePath(imagePath);
            imageModel.setImageURI(productImage);
            imageModel.setImageType(ProductImageModel.LOCAL_IMAGE_TYPE);
            productImagesActualPath.add(imageModel);
            notifyDataSetChanged();
        }
    }

    public void clearImage() {
        productImagesActualPath.clear();
    }

    public int getImagesSize() {
        return productImagesActualPath.size();
    }

    public void addImage(List<String> productImageList,List<String> idList) {
        if (productImageList != null && productImageList.size() > 0) {
            for (int i=0;i<productImageList.size();i++) {
                ProductImageModel imageModel = new ProductImageModel();
                imageModel.setImagePath(productImageList.get(i));
                imageModel.setId(idList.get(i));
                imageModel.setImageType(ProductImageModel.REMOTE_IMAGE_TYPE);
                productImagesActualPath.add(imageModel);
            }
            notifyDataSetChanged();
        }
    }
    public void addImage(List<String> productImageList) {
        if (productImageList != null && productImageList.size() > 0) {
            for (int i=0;i<productImageList.size();i++) {
                ProductImageModel imageModel = new ProductImageModel();
                imageModel.setImagePath(productImageList.get(i));
                imageModel.setImageType(ProductImageModel.REMOTE_IMAGE_TYPE);
                productImagesActualPath.add(imageModel);
            }
            notifyDataSetChanged();
        }
    }


}
