package com.tayyipgoren.yuru.yuru;

import java.io.Serializable;
import java.util.Objects;

public class Coupon implements Serializable
{
    private String name;
    private String code;
    private boolean is_used;
    private Campaign campaign;

    public Coupon(String name, String code, boolean is_used, Campaign campaign) {
        this.name = name;
        this.code = code;
        this.is_used = is_used;
        this.campaign = campaign;
    }

    public Coupon() {
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public boolean isIs_used() {
        return is_used;
    }

    public void setIs_used(boolean is_used) {
        this.is_used = is_used;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coupon)) return false;
        Coupon coupon = (Coupon) o;
        return isIs_used() == coupon.isIs_used() &&
                Objects.equals(getName(), coupon.getName()) &&
                Objects.equals(getCode(), coupon.getCode()) &&
                Objects.equals(getCampaign(), coupon.getCampaign());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getCode(), isIs_used(), getCampaign());
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", is_used=" + is_used +
                ", campaign=" + campaign +
                '}';
    }


}
