package love.lxy.hbk.hl.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 19216 on 2019/8/11.
 * 保存历程信息
 */

public class ProcessDataHelper extends SQLiteOpenHelper {

    public static String TABLE_NAME = "love_process";

    private final String CREATE_LOVE_PROCESS = "Create table " + TABLE_NAME + "(" +
            "id integer  primary key autoincrement," +
            "des_img integer," +
            "title text NOT NULL," +
            "content text NOT NULL," +
            "date date NOT NULL," +
            "img_path1 text," +
            "img_path2 text);";

    public ProcessDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                             int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LOVE_PROCESS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(CREATE_LOVE_PROCESS);
        onCreate(sqLiteDatabase);
    }
}
