package love.lxy.hbk.hl.Util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import love.lxy.hbk.hl.Beans.LoveProcess;

/**
 * Created by 19216 on 2019/8/11.
 * 数据库操作类
 */

public class DataProcessing {

    /**
     * 获取数据库中的全部历程
     *
     * @param database 数据库读操作对象
     * @return 数据库中的信息
     */
    public static List<LoveProcess> LoadAllProcess(SQLiteDatabase database) {
        List<LoveProcess> informationList = new ArrayList<>();
        LoveProcess loveProcess;

        Cursor cursor = database.query(ProcessDataHelper.TABLE_NAME, null, null,
                null, null, null, "date ASC");
        if (cursor.moveToFirst()) {
            do {
                loveProcess = new LoveProcess();
                loveProcess.setId(cursor.getInt(cursor.getColumnIndex("id")));
                loveProcess.setDescription_imgID(cursor.getInt(cursor.getColumnIndex("des_img")));
                loveProcess.setDescription(cursor.getString(cursor.getColumnIndex("title")));
                loveProcess.setLong_description(cursor.getString(cursor.getColumnIndex("content")));
                loveProcess.setTime(cursor.getString(cursor.getColumnIndex("date")));
                loveProcess.setImg_path1(cursor.getString(cursor.getColumnIndex("img_path1")));
                loveProcess.setImg_path2(cursor.getString(cursor.getColumnIndex("img_path2")));
                informationList.add(loveProcess);
            } while (cursor.moveToNext());
        }

        cursor.close();

        Log.i("hbklxy", "LoadAllProcess: " + informationList.size());

        return informationList;
    }

    /**
     * 新增新的历程
     *
     * @param database    数据库读操作对象
     * @param loveProcess 历程信息
     * @return 插入是否成功
     */
    public static boolean addProcess(SQLiteDatabase database, LoveProcess loveProcess) {
        boolean isSuccess = false;
        if (database == null) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("des_img", loveProcess.getDescription_imgID());
        values.put("title", loveProcess.getDescription());
        values.put("content", loveProcess.getLong_description());
        values.put("date", loveProcess.getTime());
        values.put("img_path1", loveProcess.getImg_path1());
        values.put("img_path2", loveProcess.getImg_path2());
        long tag = database.insert(ProcessDataHelper.TABLE_NAME, null, values);
        isSuccess = tag != -1;
        return isSuccess;
    }

    /**
     * 修改某一条历程信息
     *
     * @param database    数据库读操作对象
     * @param loveProcess 历程信息
     * @return 修改是否成功
     */
    public static boolean changeProcess(SQLiteDatabase database, LoveProcess loveProcess) {
        boolean isSuccess;

        if (database == null) {
            return false;
        }

        ContentValues values = new ContentValues();
        values.put("des_img", loveProcess.getDescription_imgID());
        values.put("title", loveProcess.getDescription());
        values.put("content", loveProcess.getLong_description());
        values.put("date", loveProcess.getTime());
        values.put("img_path1", loveProcess.getImg_path1());
        values.put("img_path2", loveProcess.getImg_path2());

        int result = database.update(ProcessDataHelper.TABLE_NAME, values, "id = ?",
                new String[]{String.valueOf(loveProcess.getId())});
        isSuccess = result != 0;
        return isSuccess;
    }

    /**
     * 删除某条历程
     *
     * @param database 数据库读操作对象
     * @param id       待删除的历程的id
     * @return 删除是否成功
     */
    public static boolean deleteProcess(SQLiteDatabase database, int id) {
        boolean isSuccess;
        if (database == null) {
            return false;
        }

        Log.i("delete", "deleteProcess: " + id);

        int result = database.delete(ProcessDataHelper.TABLE_NAME, "id = ?",
                new String[]{String.valueOf(id)});

        isSuccess = result != 0;

        return isSuccess;
    }

}
