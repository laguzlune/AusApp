package com.example.maist.ausapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by MaIst on 25/7/2559.
 */
public class AddItemDialog extends DialogFragment{

    private final String TAG = getClass().getSimpleName();

    private ImageView mImageBox;
    private Button mAddImgBtn;
    private EditText mPicName;
    private Spinner mSelectCategory;
    private String[] mCategories;
    private String selectedItem;
    private Uri imgUri;
    private AddDialogListener mListener;
    public static final int REQUEST_GALLERY = 1;

    private DBHelper mHelper;

    public AddItemDialog() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        String title = getArguments().getString("title");
        AlertDialog.Builder addItemDialog = new AlertDialog.Builder(getActivity());
        addItemDialog.setTitle(title);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_comm, null);
        addItemDialog.setView(view);

        mCategories = getResources().getStringArray(R.array.categories);
        mImageBox = (ImageView) view.findViewById(R.id.image_box);
        mAddImgBtn = (Button)view.findViewById(R.id.add_img_btn);
        mPicName = (EditText) view.findViewById(R.id.pic_name);
        mSelectCategory = (Spinner) view.findViewById(R.id.select_category);
        mHelper = new DBHelper(getActivity());

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, mCategories);
        mSelectCategory.setAdapter(categoryAdapter);
        mSelectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = mSelectCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mAddImgBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "เลือกรูปภาพ"), REQUEST_GALLERY);
            }
        });

        addItemDialog.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CommuItemList commuItemList = new CommuItemList();

                commuItemList.setName(mPicName.getText().toString());
                commuItemList.setCategory(selectedItem);
                commuItemList.setEditable(1);
                commuItemList.setUri(imgUri.toString());

                mHelper.addItem(commuItemList);

                if (mListener != null) {
                    mListener.onUpdateData();
                }
                Log.d("add","Data");
            }
        });
        addItemDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                Log.d("Dialog","Dismiss");
            }
        });

        return addItemDialog.create();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            imgUri = data.getData();
            try {
                if (isStoragePermissionGranted()) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri);
                    mImageBox.setImageBitmap(bitmap);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    public void setAddDialogListener(AddDialogListener listener) {
        this.mListener = listener;
    }

    public interface AddDialogListener {
        void onUpdateData();
    }
}
