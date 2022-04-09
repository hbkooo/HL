package love.lxy.hbk.hl.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
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
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.gui.ViewPagerAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import love.lxy.hbk.hl.Beans.LoveProcess;
import love.lxy.hbk.hl.MyView.HeartClickLayout;
import love.lxy.hbk.hl.R;
import love.lxy.hbk.hl.Util.DataProcessing;
import love.lxy.hbk.hl.Util.ProcessDataHelper;
import love.lxy.hbk.hl.Util.ShowImageViewPagerAdapter;
import love.lxy.hbk.hl.Util.Util;

import static love.lxy.hbk.hl.Util.Util.setViewTypeface;

public class LoveProcessItemActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "LoveProcessItemActivity";

    public static int CHANGE = 3;
    private boolean isChanged = false;

    public static final int CAMERA_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private int setCurrentIndex = -1;

    private File imageFile;

    private Typeface kai_ti_typeface;

    private ImageView save_iv, delete_iv, change_iv;

    private EditText title_et, content_et;
    private TextView time_tv;
    private ImageView stage_iv1, stage_iv2;

    private String img_path1 = "", img_path2 = "";
    private LoveProcess loveProcess = null;

    private SQLiteDatabase processDatabase = null;

    private boolean isChanging = false;

    private ViewPager viewPager;
    private List<View> imageViewList = new ArrayList<>();
    private ShowImageViewPagerAdapter adapter;

    private HeartClickLayout layout = null;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_process_item);

        // 加载字体
        AssetManager manager = getAssets();
        kai_ti_typeface = Typeface.createFromAsset(manager, "fonts/kaiti.ttf");

        // android 7.0以上系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        InitControl();
        InitControlData();

        if (!loveProcess.isChangeable()) {
            ((LinearLayout) findViewById(R.id.love_process_item_toolbar_layout)).setVisibility(View.GONE);
        }
        Log.i("process", "onCreate: " + loveProcess.toString());

    }

    private void InitControl() {

        gestureDetector = new GestureDetector(this, new onGestureListener());
        layout = findViewById(R.id.love_process_item_heart_click_layout);

        title_et = findViewById(R.id.love_process_item_activity_title_et);
        content_et = findViewById(R.id.love_process_item_activity_content_et);
        time_tv = findViewById(R.id.love_process_item_activity_time_tv);

        title_et.setTypeface(kai_ti_typeface);
        content_et.setTypeface(kai_ti_typeface);
        time_tv.setTypeface(kai_ti_typeface);

        stage_iv1 = findViewById(R.id.love_process_item_activity_iv1);
        stage_iv2 = findViewById(R.id.love_process_item_activity_iv2);

        save_iv = findViewById(R.id.love_process_item_activity_save_iv);
        delete_iv = findViewById(R.id.love_process_item_activity_delete_iv);
        change_iv = findViewById(R.id.love_process_item_activity_change_iv);

        time_tv.setOnClickListener(this);
        save_iv.setOnClickListener(this);
        delete_iv.setOnClickListener(this);
        change_iv.setOnClickListener(this);
        stage_iv1.setOnClickListener(this);
        stage_iv2.setOnClickListener(this);


    }

    private void InitControlData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        loveProcess = (LoveProcess) bundle.getSerializable("process");
        if (loveProcess != null) {
            title_et.setText(loveProcess.getDescription());
            content_et.setText(loveProcess.getLong_description());
            time_tv.setText(loveProcess.getTime());
            img_path1 = loveProcess.getImg_path1();
            img_path2 = loveProcess.getImg_path2();
        } else {
            Toast.makeText(this, "消息为空！", Toast.LENGTH_SHORT).show();
        }


        if (!"".equals(img_path1) && img_path1 != null) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(img_path1);
                if (bitmap == null) {
                    Toast.makeText(this, "图片找不到了！傻猪猪是不是把手机上的图片删除了呐...",
                            Toast.LENGTH_LONG).show();
                }
                stage_iv1.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "图片找不到了！傻猪猪是不是把手机上的图片删除了呐...",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            stage_iv1.setImageResource(loveProcess.getStageImgID1());
        }
        if (!"".equals(img_path2) && img_path2 != null) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(img_path2);
                if (bitmap == null) {
                    Toast.makeText(this, "图片找不到了！傻猪猪是不是把手机上的图片删除了呐...",
                            Toast.LENGTH_LONG).show();
                }
                stage_iv2.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "图片找不到了！傻猪猪是不是把手机上的图片删除了呐...",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            stage_iv2.setImageResource(loveProcess.getStageImgID2());
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.love_process_item_activity_time_tv:
                if (isChanging)
                    chooseTime();
                break;
            case R.id.love_process_item_activity_delete_iv:
                deleteProcess();
                break;
            case R.id.love_process_item_activity_change_iv:
                Toast.makeText(LoveProcessItemActivity.this, "点击编辑", Toast.LENGTH_SHORT)
                        .show();
                change_iv.setImageResource(R.drawable.change);
                save_iv.setImageResource(R.drawable.save1);
                isChanging = true;
                title_et.setEnabled(true);
                content_et.setEnabled(true);
                break;
            case R.id.love_process_item_activity_save_iv:
                changeProcess();
                title_et.setEnabled(false);
                content_et.setEnabled(false);
                isChanging = false;
                change_iv.setImageResource(R.drawable.change1);
                save_iv.setImageResource(R.drawable.save);
                break;
            case R.id.love_process_item_activity_iv1:
                setCurrentIndex = 1;
                if (isChanging) {
                    chooseImg(stage_iv1);
                } else {
                    showImage(setCurrentIndex);
                }
                break;
            case R.id.love_process_item_activity_iv2:
                setCurrentIndex = 2;
                if (isChanging) {
                    chooseImg(stage_iv2);
                } else {
                    showImage(setCurrentIndex);
                }
                break;

        }
    }

    private void showImage(int currentIndex) {

        View view1 = getLayoutInflater().inflate(R.layout.image_one, null, false);
        ImageView imageView1 = view1.findViewById(R.id.show_image_one_iv);
        if (!"".equals(img_path1) && img_path1 != null) {
            Bitmap bitmap1 = BitmapFactory.decodeFile(img_path1);
            imageView1.setImageBitmap(bitmap1);
        } else {
            imageView1.setImageResource(loveProcess.getStageImgID1());
        }

        View view2 = getLayoutInflater().inflate(R.layout.image_one, null, false);
        ImageView imageView2 = view2.findViewById(R.id.show_image_one_iv);
        if (!"".equals(img_path2) && img_path2 != null) {
            Bitmap bitmap2 = BitmapFactory.decodeFile(img_path2);
            imageView2.setImageBitmap(bitmap2);
        } else {
            imageView2.setImageResource(loveProcess.getStageImgID2());
        }

        imageViewList.clear();
        imageViewList.add(view1);
        imageViewList.add(view2);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.show_images, null, false);

        viewPager = (ViewPager) view.findViewById(R.id.love_process_item_activity_view_pager);
        adapter = new ShowImageViewPagerAdapter(imageViewList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        builder.setView(view);
        builder.show();

        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(currentIndex - 1);

    }

    // 删除历程
    private void deleteProcess() {

        if (processDatabase == null) {
            processDatabase = new ProcessDataHelper(this, "process", null,
                    1).getWritableDatabase();
        }

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("来自二傻傻的提示：")
                .setMessage("        大傻傻，确定要删除这条记录吗？")

                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("删除",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                if (DataProcessing.deleteProcess(processDatabase, loveProcess.getId())) {
                                    Toast.makeText(LoveProcessItemActivity.this, "删除成功",
                                            Toast.LENGTH_LONG).show();
                                    isChanged = true;
                                    Intent intent = new Intent();
                                    intent.putExtra("ischange", isChanged);
                                    setResult(CHANGE, intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoveProcessItemActivity.this, "出错啦，删除失败！",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                .show();
        setViewTypeface(dialog.getWindow().getDecorView(), kai_ti_typeface);
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
                            day = String .valueOf(i2);
                        String time = String.valueOf(i) + "年" +
                                month + "月" + day + "日";
                        time_tv.setText(time);
                    }
                }, year, month, day);
        datePickerDialog.show();

    }

    // 修改历程
    private void changeProcess() {

        loveProcess.setTime(time_tv.getText().toString());
        loveProcess.setDescription(title_et.getText().toString());
        loveProcess.setLong_description(content_et.getText().toString());
        loveProcess.setImg_path1(img_path1);
        loveProcess.setImg_path2(img_path2);

        if (processDatabase == null) {
            processDatabase = new ProcessDataHelper(this, "process", null,
                    1).getWritableDatabase();
        }

        if (DataProcessing.changeProcess(processDatabase, loveProcess)) {
            Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
            isChanged = true;
        } else {
            Toast.makeText(this, "不好，出错啦，没有修改成功...", Toast.LENGTH_LONG).show();
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
                        if (ContextCompat.checkSelfPermission(LoveProcessItemActivity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(LoveProcessItemActivity.this, new
                                    String[]{Manifest.permission.CAMERA}, 0);
                        } else {
                            OpenCamera();
                        }
                        break;
                    case R.id.photo:
                        if (ContextCompat.checkSelfPermission(LoveProcessItemActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                                .PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(LoveProcessItemActivity.this,
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
                    Toast.makeText(LoveProcessItemActivity.this, "只有同意了权限才能打开相机",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    GetPhotos();
                } else {
                    Toast.makeText(LoveProcessItemActivity.this, "只有同意了权限才能打开相册",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case CAMERA_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (setCurrentIndex == 1) {
                        img_path1 = imageFile.getAbsolutePath();
                        setImage(stage_iv1, img_path1);
                    } else {
                        img_path2 = imageFile.getAbsolutePath();
                        setImage(stage_iv2, img_path2);
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {

                    String img_path = handleImageKitKat(data);
                    if (setCurrentIndex == 1) {
                        img_path1 = img_path;
                        setImage(stage_iv1, img_path1);
                    } else {
                        img_path2 = img_path;
                        setImage(stage_iv2, img_path2);
                    }
                    Log.i("hbk --->", "onActivityResult: " + img_path);
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("ischange", isChanged);
            setResult(CHANGE, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    class onGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (Util.background_click_heart) {
                Log.i(TAG, "onSingleTapUp: x : " + e.getRawX() + ", y : " + e.getRawY());
                layout.addLoveView(e.getRawX(), e.getRawY());
            }
            return super.onDoubleTap(e);
        }
    }


}
