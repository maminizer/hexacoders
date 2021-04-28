/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author WIKI
 */
public class coffrets_product {
    private int coffrets_id;
    private int product_id;
    private Coffrets coffrets;
    private Product product;
    

    public coffrets_product() {
    }

    public coffrets_product(int coffrets_id, int product_id) {
        this.coffrets_id = coffrets_id;
        this.product_id = product_id;
    }

    public int getCoffrets_id() {
        return coffrets_id;
    }

    public void setCoffrets_id(int coffrets_id) {
        this.coffrets_id = coffrets_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "coffrets_product{" + "coffrets_id=" + coffrets_id + ", product_id=" + product_id + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.coffrets_id;
        hash = 83 * hash + this.product_id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final coffrets_product other = (coffrets_product) obj;
        if (this.coffrets_id != other.coffrets_id) {
            return false;
        }
        if (this.product_id != other.product_id) {
            return false;
        }
        return true;
    }
    
    
}
