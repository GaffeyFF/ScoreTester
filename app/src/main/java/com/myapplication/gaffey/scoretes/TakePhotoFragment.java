package com.myapplication.gaffey.scoretes;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 72356 on 2017/11/9.
 */

public class TakePhotoFragment extends Fragment {

    private String TAG = "TakePhotoFragment";
    public static final int TAKE_PHOTO = 1;
    private Button takePhoto;
    private Uri imageUri;

    private ImageView picture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//                    return super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.take_photo_layout, container, false);
        takePhoto = view.findViewById(R.id.take_photo);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "ButtonOnClick ");
                File outputImage = new File(getActivity().getExternalCacheDir(), "out_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();

                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(getActivity(), "com.myapplication.gaffey.scoretes.fileprovider", outputImage);

                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
        picture = view.findViewById(R.id.photo_image);

        picture.setImageBitmap(PaperActivity.bitmap);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        PaperActivity.bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(PaperActivity.bitmap);
                        initMyAnswer();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;

        }
    }

    private void initMyAnswer() {
        for (int i = 0; i < PaperActivity.list.size(); i++) {
            Answer answer = PaperActivity.list.get(i);
            answer.setMyAnswer(RandomAnswer());
            PaperActivity.list.remove(i);
            PaperActivity.list.add(i,answer);
        }

    }

    private String RandomAnswer() {
        Random random = new Random();
        int selected = random.nextInt(4);
        switch (selected) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            default:
                return null;
        }
    }
}