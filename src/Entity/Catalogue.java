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
public class Catalogue {
    private int id;
    private String nom;
    private String description;
    private Categorie categories;

    public Catalogue() {
    }

    public Catalogue(int id, String nom, String description, Categorie categories) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.categories = categories;
    }

     public Catalogue( String nom, String description, Categorie categories) {
       
        this.nom = nom;
        this.description = description;
        this.categories = categories;
    }

    public Catalogue(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public Catalogue(int id) {
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

    public Categorie getCategories() {
        return categories;
    }

    public void setCategories(Categorie categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Catalogue{" + "id=" + id + '}';
    }


    
    
    
}
