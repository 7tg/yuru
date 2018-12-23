package com.tayyipgoren.yuru.yuru;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import com.tayyipgoren.yuru.R;

public class Campaign implements Serializable
{
    private Company company;
    private int capacity;
    private int capacity_taken;
    private String desc;
    private boolean is_closed;
    private String name;
    private int point;
    private Drawable image;



    public Campaign(Company company, int capacity, int capacity_taken, String desc, int point , boolean is_closed, String name) {
        this.company = company;
        this.capacity = capacity;
        this.capacity_taken = capacity_taken;
        this.point = point;
        this.desc = desc;
        this.is_closed = is_closed;
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public Drawable getImage() {
        return image;
    }

    public Company getCompany() {
        return company;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCapacity_taken() {
        return capacity_taken;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isIs_closed() {
        return is_closed;
    }

    public String getName() {
        return name;
    }

    public void setCapacity_taken(int capacity_taken) {
        this.capacity_taken = capacity_taken;
    }

    public void setIs_closed(boolean is_closed) {
        this.is_closed = is_closed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Campaign)) return false;
        Campaign campaign = (Campaign) o;
        return getCapacity() == campaign.getCapacity() &&
                getCapacity_taken() == campaign.getCapacity_taken() &&
                isIs_closed() == campaign.isIs_closed() &&
                Objects.equals(getCompany(), campaign.getCompany()) &&
                Objects.equals(getDesc(), campaign.getDesc()) &&
                Objects.equals(getName(), campaign.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCompany(), getCapacity(), getCapacity_taken(), getDesc(), isIs_closed(), getName());
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "company=" + company +
                ", capacity=" + capacity +
                ", capacity_taken=" + capacity_taken +
                ", desc='" + desc + '\'' +
                ", is_closed=" + is_closed +
                ", name='" + name + '\'' +
                '}';
    }

    public static ArrayList<Campaign> getFakeCampaigns()
    {
        Random rn = new Random();

        Company[] companies = {
            new Company("Addax","Addax", R.drawable.marka_addax),
            new Company("Alixpress","Alixpress", R.drawable.marka_aliexpress),
            new Company("Boyner","Boyner", R.drawable.marka_boyner),
            new Company("D&R","D&R", R.drawable.marka_dand),
            new Company("DeFacto","DeFacto", R.drawable.marka_defacto),
            new Company("Deichman","Deichman", R.drawable.marka_deichmann),
            new Company("Forever","Forever", R.drawable.marka_forever),
            new Company("GittiGidiyor.com","GittiGidiyor.com", R.drawable.marka_gittigidiyor),
            new Company("Kaspersky","Kaspersky", R.drawable.marka_kaspersky),
            new Company("markapark","markapark", R.drawable.marka_markapark),
            new Company("Mavi","Mavi", R.drawable.marka_mavi),
            new Company("n11","n11", R.drawable.marka_n11),
            new Company("Penti","Penti", R.drawable.marka_penti),
            new Company("ToyzShop","ToyzShop", R.drawable.marka_toyzz_shop),
            new Company("Udemy","Udemy", R.drawable.marka_udemy),
        };

        Integer[] discounts = {10,20,30,40,50,60,70,80,90};

        ArrayList<Campaign> campaigns = new ArrayList<Campaign>();
        for (Company company:
             companies)
        {
            campaigns.add(new Campaign(
                    company,
                    2000 + rn.nextInt(1000),
                    rn.nextInt(2000),
                    company.getName() + " Kampanyası",
                    1000 + rn.nextInt(500),
                    false,
                    company.getName() + " " + discounts[rn.nextInt(9)].toString() + "% İndirim Kampanyası"
            ));
        }

        return campaigns;
    }


}
