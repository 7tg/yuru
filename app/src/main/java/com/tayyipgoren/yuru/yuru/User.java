package com.tayyipgoren.yuru.yuru;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable
{
    private Date birth_date;
    private String first_name;
    private String last_name;
    private String mail;
    private String passwd;
    private String sex;
    private long steps;
    private long points;

    private ArrayList<Coupon> coupons;
    private ArrayList<Achivement> achivements;


    private static User user;
    public User()
    {
    }

    public User(Date birth_date, String first_name, String last_name, String mail, String passwd, String sex, long steps, long points)
    {
        this.birth_date = birth_date;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mail = mail;
        this.passwd = passwd;
        this.sex = sex;
        this.steps = steps;
        this.points = points;
        this.coupons = new ArrayList<Coupon>();
        this.achivements = new ArrayList<Achivement>();
    }

    @Override
    public String toString() {
        return "User{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }


    public long getSteps() {
        return steps;
    }

    public void setSteps(long steps) {
        this.steps = steps;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getMail() {
        return mail;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getSex() {
        return sex;
    }

    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }

    public void addCoupon(Coupon coupon) {
        this.coupons.add(coupon);
    }

    public ArrayList<Achivement> getAchivements() {
        return achivements;
    }

    public void addAchivement(Achivement achivement) {
        this.achivements.add(achivement);
    }

    public long getPoints()
    {
        return this.points;
    }

    public boolean saveUser()
    {
        // TODO : Create saveUser function

        return true;
    }

    public boolean joinCampaign(Campaign campaign)
    {
        if (this.points < campaign.getPoint()) { return false; }

        this.points -= campaign.getPoint();
        this.coupons.add(new Coupon(campaign.getName(), "XXXXXXXXXXXXXX", false, campaign));

        return true;
    }


    public static User getFakeUser()
    {
        if (user == null)
        {
            user = new User(new Date(Date.UTC(1999,1,13,0,0,0)), "Tayyip", "Gören", "tayyipgoren@gmail.com",
                    "tayyi13", "M", 1000, 10000);
            return user;
        }
        else return user;
    }
}
