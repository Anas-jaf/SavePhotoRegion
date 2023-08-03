package xyz.net7.savephotoregion;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.net7.savephotoregion.databinding.FragmentImageBinding;


public class ImageFragment extends Fragment {

    private Bitmap bitmap;
    private FragmentImageBinding binding;

//    @BindView(R.id.res_photo)
//    ImageView resPhoto;
//
//    @BindView(R.id.res_photo_size)
//    TextView resPhotoSize;

    public void imageSetupFragment(Bitmap bitmap) {
        if (bitmap != null) {
            this.bitmap = bitmap;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.fragment_image, container, false);
//        ButterKnife.bind(this, view);
        binding = FragmentImageBinding.inflate(getLayoutInflater());
        //check if bitmap exist, set to ImageView
        if (bitmap != null) {
            binding.resPhoto.setImageBitmap(bitmap);
            String info = "image with:" + bitmap.getWidth() + "\n" + "image height:" + bitmap.getHeight();
            binding.resPhotoSize.setText(info);
        }
//        return view;
        return binding.getRoot() ;
    }

}
