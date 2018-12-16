package com.tayyipgoren.yuru.yuru;

import java.io.Serializable;
import java.util.Objects;

public class Achivement implements Serializable
{
    String name;

    public Achivement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Achivement)) return false;
        Achivement that = (Achivement) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Achivement{" +
                "name='" + name + '\'' +
                '}';
    }


}
