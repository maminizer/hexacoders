/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Entity.Product;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author WIKI
 */
public class Coffret {
    
    private int id;
    private String nom;
    private String description;
    private String image;
    private Double prix, offre;
    private Set services = new HashSet(0);
    private Product productCoffrets;

    public Coffret() {
    }

    public Coffret(int id, String nom, String description, String image, Double prix, Double offre) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.offre = offre;
        this.image = image;
    }

    public Coffret(int id, String nom, String description, String image, Double prix, Double offre, Set services) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.offre = offre;
        this.services = services;
    }

    public Coffret(String nom, String description, String image, Double prix, Double offre) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.offre = offre;
    }

    public Coffret(int id, String nom, String description, String image, Double prix, Double offre, Product productCoffrets) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.offre = offre;
        this.productCoffrets = productCoffrets;
    }

    public Coffret(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set getServices() {
        return services;
    }

    public void setServices(Set services) {
        this.services = services;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Double getOffre() {
        return offre;
    }

    public void setOffre(Double offre) {
        this.offre = offre;
    }

    public Product getProductCoffrets() {
        return productCoffrets;
    }

    public void setProductCoffrets(Product productCoffrets) {
        this.productCoffrets = productCoffrets;
    }

}
