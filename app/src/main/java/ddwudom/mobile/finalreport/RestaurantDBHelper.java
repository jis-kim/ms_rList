package ddwudom.mobile.finalreport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class RestaurantDBHelper extends SQLiteOpenHelper {
    final static String TAG = "RestaurantDBHelper";

    final static String DB_NAME = "restaurants.db";
    public final static String TABLE_NAME = "restaurants_table";

    public final static String COL_ID = "_id";
    public final static String COL_NAME = "name";
    public final static String COL_REGION = "region";
    public final static String COL_TYPE = "type";
    public final static String COL_RATING = "rating";
    public final static String COL_MENU = "menu";
    public final static String COL_REVIEW = "review";

    public RestaurantDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
                COL_NAME + " TEXT, " + COL_REGION + " TEXT, " + COL_TYPE + " TEXT, " + COL_RATING +
                " integer, " + COL_MENU + " TEXT, " + COL_REVIEW + " TEXT)";
        Log.d(TAG, sql);
        db.execSQL(sql);

        db.execSQL("insert into " + TABLE_NAME + " values (null, '연스시', '강남구', '일식', 4, '회덮밥', '회가 가득해요');");
        db.execSQL("insert into "+ TABLE_NAME + " values (null, '청어람', '마포구', '한식', 4, '곱창전골', '곱창이 부드러워요');");
        db.execSQL("insert into "+ TABLE_NAME + " values (null, '매운향솥', '광진구', '중식', 5, '마라샹궈', '이게 진짜 마라다...');");
        db.execSQL("insert into "+ TABLE_NAME + " values (null, '마놀린', '성북구', '카페', 5, '아이리쉬라떼', '사장님이 커피 달인');");
        db.execSQL("insert into "+ TABLE_NAME + " values (null, '보니스피자펍', '용산구', '양식', 5, '페퍼로니피자', '안가봤지만 유명해서 넣었어요');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
