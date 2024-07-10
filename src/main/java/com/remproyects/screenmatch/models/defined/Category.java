package com.remproyects.screenmatch.models.defined;

public enum Category {
    ACTION("Action"),
    ROMANCE("Romance"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime"),
    ANIMATION("Animation");

    private String categoryOMBD;

    Category (String categoryOMBD) {
        this.categoryOMBD = categoryOMBD;
    }

    public static Category fromString(String s) {
        for(Category category : Category.values()) {
            if(category.categoryOMBD.equalsIgnoreCase(s)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria fue encontrada: " + s);
    }
}
