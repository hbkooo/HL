package love.lxy.hbk.hl.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import love.lxy.hbk.hl.Beans.LoveProcess;
import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Util.DataProcessing;
import love.lxy.hbk.hl.Util.ProcessDataHelper;
import love.lxy.hbk.hl.Util.Util;

public class AddLoveProcessActivity extends AppCompatActivity implements View.OnClickListener {

    public static int ADD_SUCCESS = 1;  // 添加成功标志
    public static int ADD_FAILE = 2;  // 添加失败标志

    public static final int CAMERA_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private String img_path1, img_path2;
    private File imageFile;

    private Typeface kai_ti_typeface;

    private TextView add_process_time_tv;
    private EditText title_et, content_et;
    private ImageView add_process_iv1, add_process_iv2, add_hint_iv1, add_hint_iv2;

    private int setCurrentIndex = -1;

    private SQLiteDatabase processDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_love_process);

        // 加载字体
        AssetManager manager = getAssets();
        kai_ti_typeface = Typeface.createFromAsset(manager, "fonts/kaiti.ttf");

        InitControl();

        // android 7.0以上系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void InitControl() {

        add_process_time_tv = findViewById(R.id.add_process_time_tv);
        title_et = findViewById(R.id.add_process_title_et);
        content_et = findViewById(R.id.add_process_content_et);
        add_process_iv1 = findViewById(R.id.add_process_activity_iv1);
        add_process_iv2 = findViewById(R.id.add_process_activity_iv2);
        add_hint_iv1 = findViewById(R.id.add_hint_iv1);
        add_hint_iv2 = findViewById(R.id.add_hint_iv2);

        add_process_time_tv.setOnClickListener(this);
        add_process_iv1.setOnClickListener(this);
        add_process_iv2.setOnClickListener(this);
        add_hint_iv1.setOnClickListener(this);
        add_hint_iv2.setOnClickListener(this);

        title_et.setTypeface(kai_ti_typeface);
        content_et.setTypeface(kai_ti_typeface);

        Button add_btn = findViewById(R.id.add_love_process_btn);
        add_btn.setOnClickListener(this);
        add_btn.setTypeface(kai_ti_typeface);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String m, d;

        if (month < 9)
            m = "0" + String.valueOf(month + 1);
        else
            m = String.valueOf(month + 1);

        if (day < 10)
            d = "0" + String.valueOf(day);
        else
            d = String.valueOf(day);
        String time = String.valueOf(year) + "年" +
                m + "月" + d + "日";
        add_process_time_tv.setText(time);

        content_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }

    //打开相册
    private void GetPhotos() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);//打开相册
    }

    //调用相机
    private void OpenCamera() {
        //创建File对象，用户存储拍照后的图片
        imageFile = new File(getExternalCacheDir(),
                java.util.UUID.randomUUID() + ".jpg");
        try {
            if (imageFile.exists()) {
                imageFile.delete();
            }
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri imageUri;
        imageUri = Uri.fromFile(imageFile);
//        if (Build.VERSION.SDK_INT >= 24) {
//            imageUri = FileProvider.getUriForFile(AddLoveProcessActivity.this,
//                    "love.lxy.hbk.hl.Activity.fileprovider", imageFile);
//        } else {
//            imageUri = Uri.fromFile(imageFile);
//        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case CAMERA_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (setCurrentIndex == 1) {
                        img_path1 = imageFile.getAbsolutePath();
                        if (setImage(add_process_iv1, img_path1))
                            add_hint_iv1.setVisibility(View.INVISIBLE);
                    } else {
                        img_path2 = imageFile.getAbsolutePath();
                        if (setImage(add_process_iv2, img_path2))
                            add_hint_iv2.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {

                    String img_path = handleImageKitKat(data);
                    if (setCurrentIndex == 1) {
                        img_path1 = img_path;
                        if (setImage(add_process_iv1, img_path1))
                            add_hint_iv1.setVisibility(View.INVISIBLE);
                    } else {
                        img_path2 = img_path;
                        if (setImage(add_process_iv2, img_path2))
                            add_hint_iv2.setVisibility(View.INVISIBLE);
                    }
                    Log.i("hbk --->", "onActivityResult: " + img_path);
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    // 根据图片路径设置图片
    private boolean setImage(ImageView imageView, String path) {
        boolean isSuccess = false;
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            imageView.setImageBitmap(bitmap);
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_process_time_tv:
                chooseTime();
                break;
            case R.id.add_hint_iv1:
                setCurrentIndex = 1;
                chooseImg(add_hint_iv1);
                break;
            case R.id.add_hint_iv2:
                setCurrentIndex = 2;
                chooseImg(add_hint_iv2);
                break;
            case R.id.add_process_activity_iv1:
                setCurrentIndex = 1;
                chooseImg(add_process_iv1);
                break;
            case R.id.add_process_activity_iv2:
                setCurrentIndex = 2;
                chooseImg(add_process_iv2);
                break;
            case R.id.add_love_process_btn:
                if ("".equals(title_et.getText().toString()) ||
                        "".equals(content_et.getText().toString())) {
                    Toast.makeText(AddLoveProcessActivity.this, "标题或者内容不能为空呦...",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddLoveProcessActivity.this, "添加历程...",
                            Toast.LENGTH_SHORT).show();
                    saveProcess();
                }
                break;
        }
    }

    // 给出上下文选择器选择图像
    private void chooseImg(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.add_img_way, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.camera:
                        if (ContextCompat.checkSelfPermission(AddLoveProcessActivity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(AddLoveProcessActivity.this, new
                                    String[]{Manifest.permission.CAMERA}, 0);
                        } else {
                            OpenCamera();
                        }
                        break;
                    case R.id.photo:
                        if (ContextCompat.checkSelfPermission(AddLoveProcessActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                                .PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(AddLoveProcessActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            GetPhotos();
                        }
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    // 保存历程
    private void saveProcess() {
        LoveProcess loveProcess = new LoveProcess();
        loveProcess.setBlank_tag(false);
        loveProcess.setDescription(title_et.getText().toString());
        loveProcess.setLong_description(content_et.getText().toString());
        loveProcess.setDescription_imgID(Util.descripImgID[(int) (Math.random() * 100 %
                Util.descripImgID.length)]);
        loveProcess.setTime(add_process_time_tv.getText().toString());
        loveProcess.setImg_path1(img_path1);
        loveProcess.setImg_path2(img_path2);
        if (processDatabase == null) {
            // 加载数据库
            processDatabase = new ProcessDataHelper(this, "process",
                    null, 1).getWritableDatabase();
        }
        if (DataProcessing.addProcess(processDatabase, loveProcess)) {
            Toast.makeText(this, "傻猪猪，共同的历程添加成功啦！", Toast.LENGTH_LONG)
                    .show();
            Intent intent = new Intent();
            intent.putExtra("isAdd", true);
            setResult(ADD_SUCCESS, intent);
            finish();
        } else {
            Toast.makeText(this, "不好，出错了，历程添加失败啦...", Toast.LENGTH_LONG)
                    .show();
        }
        Log.i("save process", "saveProcess: " + loveProcess.toString());
    }

    // 选择日期
    private void chooseTime() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String month, day;
                        if (i1 < 9) {
                            month = "0" + String.valueOf(i1 + 1);
                        } else {
                            month = String.valueOf(i1 + 1);
                        }
                        if (i2 < 10)
                            day = "0" + String.valueOf(i2);
                        else
                            day = String.valueOf(i2);
                        String time = String.valueOf(i) + "年" +
                                month + "月" + day + "日";
                        add_process_time_tv.setText(time);
                    }
                }, year, month, day);
        datePickerDialog.show();

    }

    //处理选择的图片
    @TargetApi(19)
    private String handleImageKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (Build.VERSION.SDK_INT < 19) {
            imagePath = getImagePath(uri, null);
            //displayImage(imagePath);
            return imagePath;
        }
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的uri，则通过document id处理
            String docID = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docID.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        selection);
            } else if ("com.android.providers.downloads.documents"
                    .equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads//public_downloads"), Long.valueOf(docID));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通处理类型方法
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        //displayImage(imagePath);
        return imagePath;
    }

    //通过Uri和selection来获取真实的图片路径
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore
                        .Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    OpenCamera();
                } else {
                    Toast.makeText(AddLoveProcessActivity.this, "只有同意了权限才能打开相机",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    GetPhotos();
                } else {
                    Toast.makeText(AddLoveProcessActivity.this, "只有同意了权限才能打开相册",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
