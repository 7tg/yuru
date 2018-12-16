package com.tayyipgoren.yuru.yuru;

import java.io.Serializable;
import java.util.Objects;

public class Company implements Serializable
{
    private String desc;
    private String name;

    public Company(String desc, String name) {
        this.desc = desc;
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return Objects.equals(getDesc(), company.getDesc()) &&
                Objects.equals(getName(), company.getName());
    }

    @Override
    public String toString() {
        return "Company{" +
                "desc='" + desc + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(getDesc(), getName());
    }


}
