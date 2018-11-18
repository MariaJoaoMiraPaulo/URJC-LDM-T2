package com.example.mariajoaomirapaulo.myshoppinglist;

/**
 * The product class.
 */
public class ProductItem {

    private String name;

    private String photo = "";

    /**
     * Product constructor.
     *
     * @param name the name of the product
     */
    public ProductItem(String name) {
        this.name = name;
    }

    /**
     * Gets the product name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the product photo.
     *
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Sets the product photo.
     *
     * @param photo the photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
