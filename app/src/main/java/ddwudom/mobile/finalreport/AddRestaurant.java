package ddwudom.mobile.finalreport;

import android.content.Intent;
import android.os.Bundle;
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

import static android.app.Activity.RESULT_CANCELED;

public class AddRestaurant extends AppCompatActivity {
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);
        spType = findViewById(R.id.spType);
        restaurantDBManager = new RestaurantDBManager(this);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.typeList,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spType.setAdapter(adapter);

        intent = getIntent();

        tvEditTitle = findViewById(R.id.tvEditTitle);
        btnAdd = findViewById(R.id.btn_add);
        etName = findViewById(R.id.etName);
        etMenu = findViewById(R.id.etMenu);
        etRegion = findViewById(R.id.etRegion);
        etReview = findViewById(R.id.etReview);
        rating = findViewById(R.id.rgRating);

    }


    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_add:
                RadioButton btn = findViewById(rating.getCheckedRadioButtonId());
                restaurant = new Restaurant(etName.getText().toString(), etRegion.getText().toString(),
                    spType.getSelectedItem().toString(), Integer.parseInt(btn.getText().toString()),
                    etMenu.getText().toString(), etReview.getText().toString());

                if (checkBlank(restaurant)){
                    if (restaurantDBManager.addRestaurant(restaurant)) {
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

}
