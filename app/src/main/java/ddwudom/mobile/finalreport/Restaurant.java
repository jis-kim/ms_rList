package ddwudom.mobile.finalreport;

import java.io.Serializable;

//Data class
public class Restaurant implements Serializable {
    private long _id;
    private String name;
    private String region;
    private String type; //한식, 양식, 중식...
    private int rating; //0 ~ 5
    private String menu; //대표메뉴
    private String review;

    public Restaurant(String name, String region, String type, int rating, String menu, String review) {
        this.name = name;
        this.region = region;
        this.type = type;
        this.rating = rating;
        this.menu = menu;
        this.review = review;
    }

    public Restaurant(long _id, String name, String region, String type, int rating, String menu, String review) {
        this._id = _id;
        this.name = name;
        this.region = region;
        this.type = type;
        this.rating = rating;
        this.menu = menu;
        this.review = review;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
