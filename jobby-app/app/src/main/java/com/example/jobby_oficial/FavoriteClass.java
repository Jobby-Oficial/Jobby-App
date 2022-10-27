package com.example.jobby_oficial;

public class FavoriteClass {
    private int imageFavorite;
    private String nameFavorite, categoryFavorite;

    public FavoriteClass(int imageFavorite, String nameFavorite, String categoryFavorite) {
        this.imageFavorite = imageFavorite;
        this.nameFavorite = nameFavorite;
        this.categoryFavorite = categoryFavorite;
    }

    public int getImageFavorite() {
        return imageFavorite;
    }

    public void setImageFavorite(int imageFavorite) {
        this.imageFavorite = imageFavorite;
    }

    public String getNameFavorite() {
        return nameFavorite;
    }

    public void setNameFavorite(String nameFavorite) {
        this.nameFavorite = nameFavorite;
    }

    public String getCategoryFavorite() {
        return categoryFavorite;
    }

    public void setCategoryFavorite(String categoryFavorite) {
        this.categoryFavorite = categoryFavorite;
    }
}
