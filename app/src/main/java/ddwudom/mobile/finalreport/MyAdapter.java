package ddwudom.mobile.finalreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private Context context; //inflater 객체 생성 시 필요
    private int layout; //adapterView의 layout
    private ArrayList<Restaurant> restaurantList;
    private LayoutInflater layoutInflater; //inflater
    int count;

    public MyAdapter(Context context, int layout, ArrayList<Restaurant> restaurantList) {
        this.context = context;
        this.layout = layout;
        this.restaurantList = restaurantList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        count = 0;
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return restaurantList.get(position).get_id();
    }

    //원본 데이터 개수만큼 반복 호출된다. 리스트 뷰 순서대로 원본 데이터를 이용해 뷰를 만들어 반환한다.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        ViewHolder viewHolder;

        if (convertView == null){
            convertView = layoutInflater.inflate(layout, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.tvName = convertView.findViewById(R.id.tvName);
            viewHolder.tvRegion = convertView.findViewById(R.id.tvRegion);
            viewHolder.ratingBar = convertView.findViewById(R.id.ratingBar);
            viewHolder.imgType = convertView.findViewById(R.id.imgType);
            viewHolder.tvType = convertView.findViewById(R.id.tvType);
            //최초에 convertView가 만들어질 때만 호출한다.

            convertView.setTag(viewHolder);//view의 정보들을 보관.
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.tvName.setText(restaurantList.get(pos).getName());
        viewHolder.tvRegion.setText(restaurantList.get(pos).getRegion());
        viewHolder.ratingBar.setRating(restaurantList.get(pos).getRating());
        viewHolder.tvType.setText(restaurantList.get(pos).getType());
        String type = restaurantList.get(pos).getType();
        switch(type){
            case "한식":
                viewHolder.imgType.setImageResource(R.mipmap.food_korean);
                break;
            case "중식":
                viewHolder.imgType.setImageResource(R.mipmap.food_chinese);
                break;
            case "일식":
                viewHolder.imgType.setImageResource(R.mipmap.food_japanese);
                break;
            case "양식":
                viewHolder.imgType.setImageResource(R.mipmap.food_western);
                break;
            case "카페":
                viewHolder.imgType.setImageResource(R.mipmap.food_cafe);
                break;
            default:
                viewHolder.imgType.setImageResource(R.mipmap.ic_yummy);
                break;
        }

        return convertView;
    }

    static class ViewHolder{
        TextView tvName;
        TextView tvRegion;
        RatingBar ratingBar;
        ImageView imgType;
        TextView tvType;
    }
}
