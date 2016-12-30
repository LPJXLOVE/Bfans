package com.technology.lpjxlove.bfans.Bean;

/**
 * Created by LPJXLOVE on 2016/9/7.
 */
public class BattleEntity extends Entity {
    private String BattleType;
    private String BattleObject;
    private String State;
    private String StartDate;
    private String BattleLocation;
    private double latitude,longitude;
    private String city;
    private User Author;


    public User getAuthor() {
        return Author;
    }


    public void setAuthor(User author) {
        Author = author;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLantitude() {
        return latitude;
    }

    public void setLatitude(double lantitude) {
        this.latitude = lantitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getBattleType() {
        return BattleType;
    }

    public void setBattleType(String battleType) {
        BattleType = battleType;
    }

    public String getBattleObject() {
        return BattleObject;
    }

    public void setBattleObject(String battleObject) {
        BattleObject = battleObject;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getBattleLocation() {
        return BattleLocation;
    }

    public void setBattleLocation(String battleLocation) {
        BattleLocation = battleLocation;
    }

}
