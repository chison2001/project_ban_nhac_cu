package com.example.projectbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.Cloudinary;
import com.example.projectbanhang.R;
import com.example.projectbanhang.activity.config.MyConfiguration;
import com.example.projectbanhang.database.ProductDbHelper;
import com.example.projectbanhang.model.Product;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST_CODE =  1433;
    private static final Pattern PRICE_PATTERN = Pattern.compile("[1-9]\\d{6,}");
    Cloudinary cloudinary = new Cloudinary(MyConfiguration.getMyConfig());
    Spinner spinner;
    Toolbar toolbar;
    ImageView imagepick, imgsanpham;
    private Bitmap bitmap;
    AppCompatButton btnadd;
    EditText etname, etprice, etdes;

    ProductDbHelper productDbHelper;
    String public_id ;

    Product productNew, product;
    String activity;
    String[] so = new String[]{"Guitar", "Okulele", "Violin", "Tỳ bà", "Đàn tranh", "Organ"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getView();
        initControl();
        actionToolBar();
    }
    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initControl() {
        activity = getIntent().getStringExtra("activity");
        if (activity.equals("update")){
            toolbar.setTitle("Cập nhật sản phẩm");
            btnadd.setText("Cập nhật");
            product = (Product) getIntent().getSerializableExtra("product");
            Glide.with(getApplicationContext()).load(product.getImage()).into(imgsanpham);
            etname.setText(product.getName());
            etprice.setText(String.valueOf(product.getPrice()));
            etdes.setText(product.getMota());
            for (int i = 0; i< so.length; i++){
                if (so[i].equals(product.getCategory())){
                    spinner.setSelection(i);
                }
            }
        }
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               public_id = etname.getText().toString().trim() + randomKey();
                String str_name = etname.getText().toString();
                String str_price = etprice.getText().toString();
                String str_des = etdes.getText().toString();
                String category = spinner.getSelectedItem().toString();
                Matcher matcher_price = PRICE_PATTERN.matcher(str_price);
                if (TextUtils.isEmpty(str_name)){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_price)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập giá sản phẩm", Toast.LENGTH_SHORT).show();
                }else if (!matcher_price.matches()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập giá sản phẩm hơn 1.000.000", Toast.LENGTH_SHORT).show();
                }else if (bitmap == null) {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn hình ảnh sản phẩm", Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            uploadImage(bitmap);
                        }
                    }).start();
                    String imageUrl = cloudinary.url().generate("projectbannhaccu/"+public_id);
                    if (activity.equals("add")){
                        productNew = new Product(str_name, imageUrl, Long.parseLong(str_price), str_des, category);
                        productDbHelper = new ProductDbHelper(getApplicationContext());
                        productDbHelper.insertProduct(productNew);
                        productDbHelper.close();
                        finish();
                    }else if(activity.equals("update")){
                        productNew = new Product(product.getId(), str_name, imageUrl, Long.parseLong(str_price), str_des, category);
                        productDbHelper = new ProductDbHelper(getApplicationContext());
                        productDbHelper.updateProduct(productNew);
                        productDbHelper.close();
                        finish();
                    }
                }


            }
        });
    }

    private String randomKey() {
        Random rand = new Random();

        // Generate a random integer between 0 and 99999999
        int randomNumber = rand.nextInt(100000000);

        // Format the number as a string with leading zeros if necessary
        String formattedNumber = String.format("%08d", randomNumber);
        return  formattedNumber;
    }

    private void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openGallery();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(AddProductActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE); // replace PICK_IMAGE_REQUEST_CODE with your own request code
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgsanpham.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void uploadImage(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArray = baos.toByteArray();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
            // Create a map to store the options for the upload:
            Map<String, Object> options = new HashMap<>();
            options.put("folder", "projectbannhaccu"); // replace "my_folder" with the name of the folder you want to upload the image to
            options.put("public_id",public_id ); // replace "my_image" with the name you want to give to the uploaded image
            // Upload the image to Cloudinary and get the URL of the uploaded image:
            Object response = cloudinary.uploader().upload(inputStream, options);
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private void getView() {
        spinner = findViewById(R.id.spinnercategory);
        toolbar = findViewById(R.id.toolbarsanpham);
        imagepick = findViewById(R.id.imgcamera);
        imgsanpham = findViewById(R.id.imgsanpham);
        btnadd = findViewById(R.id.btnAdd);
        etname = findViewById(R.id.etname);
        etprice = findViewById(R.id.etprice);
        etdes = findViewById(R.id.etdescript);
        ArrayAdapter<String> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(adapterspin);
        imagepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
    }

}