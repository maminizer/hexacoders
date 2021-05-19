/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author WIKI
 */
public class Categorie {
    private int id;
    private Catalogue catalogue;
    
    private String nom;
    private String description;
    private String image;

    public Categorie() {
    }

    public Categorie(int id, Catalogue catalogue, String nom, String description, String image) {
        this.id = id;
        this.catalogue = catalogue;
        this.nom = nom;
        this.description = description;
        this.image = image;
    }

    public Categorie(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public Categorie(String nom, String description, String image) {
        this.nom = nom;
        this.description = description;
        this.image = image;
    }

    public Categorie(Catalogue catalogue, String nom, String description, String image) {
        this.catalogue = catalogue;
        this.nom = nom;
        this.description = description;
        this.image = image;
    }
    

    public Categorie(int id) {
        this.id = id;
    }

    public Categorie(String nom) {
        this.nom = nom;
    }







    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
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

    @Override
    public String toString() {
        return "Categorie{" + "id=" + id + '}';
    }

  
    
    
}
