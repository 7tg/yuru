package com.tayyipgoren.yuru.yuru;

import java.io.Serializable;
import java.util.Objects;

public class Campaign implements Serializable
{
    private Company company;
    private int capacity;
    private int capacity_taken;
    private String desc;
    private boolean is_closed;
    private String name;

    public Campaign(Company company, int capacity, int capacity_taken, String desc, boolean is_closed, String name) {
        this.company = company;
        this.capacity = capacity;
        this.capacity_taken = capacity_taken;
        this.desc = desc;
        this.is_closed = is_closed;
        this.name = name;
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


}
