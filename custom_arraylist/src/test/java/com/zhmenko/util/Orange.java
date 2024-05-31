package com.zhmenko.util;

/**
 * POJO for Comparator sorter version
 */
class Orange {
    private int id;

    public Orange(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Orange{" +
                "id=" + id +
                '}';
    }
}
