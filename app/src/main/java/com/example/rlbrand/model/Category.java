package com.example.rlbrand.model;

import java.io.Serializable;

public class Category implements Serializable {
    private String category, subCategory, description, imageCategory, itemSize, itemPrice;
    private boolean isSelected = false;

    public Category() {
    }

    public Category(String category, String subCategory, String description, String imageCategory, String itemPrice, String itemSize) {
        this.category = category;
        this.subCategory = subCategory;
        this.description = description;
        this.imageCategory = imageCategory;
        this.itemPrice = itemPrice;
        this.itemSize = itemSize;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageCategory() {
        return imageCategory;
    }

    public void setImageCategory(String imageCategory) {
        this.imageCategory = imageCategory;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean isSelected() {
        return isSelected;
    }
}
