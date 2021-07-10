package ddwudom.mobile.finalreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class RestaurantDBManager {

    RestaurantDBHelper restaurantDBHelper = null;
    Cursor cursor;

    public RestaurantDBManager(Context context) { restaurantDBHelper = new RestaurantDBHelper(context);}

    public ArrayList<Restaurant> getAllRestaurant(){
        ArrayList restaurantList = new ArrayList();

        SQLiteDatabase db = restaurantDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ RestaurantDBHelper.TABLE_NAME, null);

        while(cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(RestaurantDBHelper.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_NAME));
            String region = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_REGION));
            String type = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_TYPE));
            int rating = cursor.getInt(cursor.getColumnIndex(RestaurantDBHelper.COL_RATING));
            String menu = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_MENU));
            String review = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_REVIEW));
            restaurantList.add(new Restaurant(id, name, region, type, rating, menu, review));
        }

        close();
        return restaurantList;
    }

    //    _id 를 기준으로 food 의 이름과 nation 변경
    public boolean modifyFood(Restaurant restaurant) {
        SQLiteDatabase sqLiteDatabase = restaurantDBHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(RestaurantDBHelper.COL_NAME, restaurant.getName());
        row.put(RestaurantDBHelper.COL_MENU, restaurant.getMenu());
        row.put(RestaurantDBHelper.COL_RATING, restaurant.getRating());
        row.put(RestaurantDBHelper.COL_TYPE, restaurant.getType());
        row.put(RestaurantDBHelper.COL_REGION, restaurant.getRegion());
        row.put(RestaurantDBHelper.COL_REVIEW, restaurant.getReview());

        String whereClause = RestaurantDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(restaurant.get_id()) };
        int result = sqLiteDatabase.update(RestaurantDBHelper.TABLE_NAME, row, whereClause, whereArgs);
        Log.d("DBM",whereClause);
        Log.d("DBM", restaurant.getMenu() + restaurant.getName());
        close();
        if (result > 0) return true;
        return false;
    }

    public boolean addRestaurant(Restaurant restaurant){
        SQLiteDatabase sqLiteDatabase = restaurantDBHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(RestaurantDBHelper.COL_NAME, restaurant.getName());
        value.put(RestaurantDBHelper.COL_MENU, restaurant.getMenu());
        value.put(RestaurantDBHelper.COL_RATING, restaurant.getRating());
        value.put(RestaurantDBHelper.COL_TYPE, restaurant.getType());
        value.put(RestaurantDBHelper.COL_REGION, restaurant.getRegion());
        value.put(RestaurantDBHelper.COL_REVIEW, restaurant.getReview());
        long result = (int)sqLiteDatabase.insert(RestaurantDBHelper.TABLE_NAME, null, value);

        close();
        if (result > 0) return true;
        return false;
    }

    public boolean removeRestaurant(long id){
        SQLiteDatabase sqLiteDatabase = restaurantDBHelper.getWritableDatabase();
        String whereClause = RestaurantDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        int result = sqLiteDatabase.delete(RestaurantDBHelper.TABLE_NAME, whereClause,whereArgs);
        close();
        if (result > 0) return true;
            return false;
    }

    public ArrayList<Restaurant> getRestaurantsByType (String keyType){
        SQLiteDatabase sqLiteDatabase = restaurantDBHelper.getWritableDatabase();
        String selection = RestaurantDBHelper.COL_TYPE + "=?";
        String[] selectArgs = new String[] { keyType };

        Cursor cursor = sqLiteDatabase.query(RestaurantDBHelper.TABLE_NAME, null, selection
                ,selectArgs, null, null, null, null);
        ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();

        while (cursor.moveToNext()){
            long id = cursor.getInt(cursor.getColumnIndex(RestaurantDBHelper.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_NAME));
            String region = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_REGION));
            String type = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_TYPE));
            int rating = cursor.getInt(cursor.getColumnIndex(RestaurantDBHelper.COL_RATING));
            String menu = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_MENU));
            String review = cursor.getString(cursor.getColumnIndex(RestaurantDBHelper.COL_REVIEW));
            restaurantList.add(new Restaurant(id, name, region, type, rating, menu, review));
        }
        close();
        return restaurantList;
    }

    public void close() {
        if (restaurantDBHelper != null) restaurantDBHelper.close();
        if (cursor != null) cursor.close();
    };
}
