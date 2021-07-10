package ddwudom.mobile.finalreport;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class UpdateRestaurant extends AppCompatActivity {
    Intent intent;
    RestaurantDBManager restaurantDBManager;
    Restaurant restaurant;

    TextView tvEditTitle;
    Button btnAdd;
    EditText etName;
    EditText etMenu;
    EditText etRegion;
    EditText etReview;
    RadioGroup rating;
    Spinner spType;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);
        spType = findViewById(R.id.spType);
        restaurantDBManager = new RestaurantDBManager(this);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.typeList,
        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spType.setAdapter(adapter);

        intent = getIntent();
        restaurant = (Restaurant)intent.getSerializableExtra("restaurant");

        tvEditTitle = findViewById(R.id.tvEditTitle);
        btnAdd = findViewById(R.id.btn_add);
        etName = findViewById(R.id.etName);
        etMenu = findViewById(R.id.etMenu);
        etRegion = findViewById(R.id.etRegion);
        etReview = findViewById(R.id.etReview);
        rating = findViewById(R.id.rgRating);


        tvEditTitle.setText("맛집 정보 수정");
        btnAdd.setText("수정");

        etName.setText(restaurant.getName());
        etMenu.setText(restaurant.getMenu());
        etRegion.setText(restaurant.getRegion());
        etReview.setText(restaurant.getReview());
        spType.setSelection(getTypePosition(spType, restaurant.getType()));

        int starId;
        switch(restaurant.getRating()){
            case 1:
                starId = R.id.rbStar1;
                break;
            case 2:
                starId = R.id.rbStar2;
                break;
            case 3:
                starId = R.id.rbStar3;
                break;
            case 4:
                starId = R.id.rbStar4;
                break;
            default:
                starId = R.id.rbStar5;
                break;
        }
        rating.check(starId);
    }


    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_add:
                restaurant.setMenu(etMenu.getText().toString());
                restaurant.setName(etName.getText().toString());
                RadioButton btn = findViewById(rating.getCheckedRadioButtonId());
                restaurant.setRating(Integer.parseInt(btn.getText().toString()));
                restaurant.setRegion(etRegion.getText().toString());
                restaurant.setReview(etReview.getText().toString());
                restaurant.setType(spType.getSelectedItem().toString());

                if (checkBlank(restaurant)){
                    if (restaurantDBManager.modifyFood(restaurant)) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("restaurant", restaurant);
                        setResult(RESULT_OK, resultIntent);
                    } else {
                        setResult(RESULT_CANCELED);
                    }
                    finish();
                }
                else{
                    Toast.makeText(this, "필수 항목을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }

    private boolean checkBlank(Restaurant restaurant){
        if (restaurant.getName().equals("")){
            return false;
        }
        if (restaurant.getMenu().equals("")){
            return false;
        }
        if (restaurant.getRegion().equals("")){
            return false;
        }
        if (restaurant.getReview().equals("")){
            return false;
        }
        return true;
    }

    private int getTypePosition(Spinner spinner, String item){
        for(int i = 0; i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).equals(item))
                return i;
        }
        return 0;
    }

}
