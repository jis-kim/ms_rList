package ddwudom.mobile.finalreport;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Restaurant> restaurantList;
    MyAdapter adapter;
    RestaurantDBManager restaurantDBManager;
    final static int UPDATE_CODE = 100;
    final static int ADD_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        restaurantList = new ArrayList();
        adapter = new MyAdapter(this, R.layout.restaurant_layout, restaurantList);
        listView.setAdapter(adapter);
        restaurantDBManager = new RestaurantDBManager(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant restaurant = restaurantList.get(position);

                Intent intent = new Intent(MainActivity.this, UpdateRestaurant.class);
                intent.putExtra("restaurant", restaurant);
                startActivityForResult(intent, UPDATE_CODE);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.dialog_title)
                        .setMessage(restaurantList.get(pos).getName()+"을(를) 삭제하시겠습니까?")
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (restaurantDBManager.removeRestaurant(restaurantList.get(pos).get_id())) {
                                    Toast.makeText(MainActivity.this, "삭제 완료", Toast.LENGTH_SHORT).show();
                                    restaurantList.clear();
                                    restaurantList.addAll(restaurantDBManager.getAllRestaurant());
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(MainActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, null)
                        .setCancelable(false)
                        .show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        restaurantList.clear();
        restaurantList.addAll(restaurantDBManager.getAllRestaurant());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CODE) {  // AddActivity 호출 후 결과 확인
            switch (resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "추가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "추가 취소되었습니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (requestCode == UPDATE_CODE) {    // UpdateActivity 호출 후 결과 확인
            switch (resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "수정 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "수정 취소되었습니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        Intent intent;
        AlertDialog.Builder builder;
        switch(item.getItemId()){
            case R.id.menuAdd:
                intent = new Intent(MainActivity.this, AddRestaurant.class);
                startActivityForResult(intent, ADD_CODE);
                break;
            case R.id.menuDev:
                intent = new Intent(MainActivity.this, developerInfo.class);
                startActivity(intent);
                break;
            case R.id.menuExit:
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("앱 종료")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
                break;
            case R.id.menuTypeSearch:
                builder = new AlertDialog.Builder(MainActivity.this);
                View view = getLayoutInflater().inflate(R.layout.search_spinner, null);
                final Spinner sp = (Spinner)view.findViewById(R.id.spSearch);

                ArrayAdapter adp = ArrayAdapter.createFromResource(this, R.array.typeList,
                        android.R.layout.simple_spinner_item);
                sp.setAdapter(adp);
                builder.setTitle("종류별로 검색하기")
                        .setView(view)
                        .setPositiveButton("검색", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String keyType = sp.getSelectedItem().toString();
                                restaurantList.clear();
                                restaurantList.addAll(restaurantDBManager.getRestaurantsByType(keyType));
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("검색 취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                restaurantList.clear();
                                restaurantList.addAll(restaurantDBManager.getAllRestaurant());
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .show();
        }
        return true;
    }
/*
AlertDialog.Builder aBuilder = new AlertDialog.Builder(MainActivity.this);
View mView = getLayoutInflater().inflate(R.layout.report_dialog,null);

aBuilder.setTitle("커스텀 다이얼로그");       // 제목 설정
aBuilder.setMessage("커스텀 다이얼로그 연습.");   // 내용 설정

// 스피너 설정
final Spinner sp = (Spinner)mView.findViewById(R.id.spinner);

// 스피너 어댑터 설정
ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(this, R.array.location, android.R.layout.simple_spinner_item);
sp.setAdapter(yearAdapter);

// editText 설정
final EditText et = (EditText)findViewById(R.id.editT);

 */
}