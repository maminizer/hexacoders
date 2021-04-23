/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author yossr
 */
public class commentaire {
    private int id;
    private int produit_id;

    public commentaire(String contenu) {
        this.contenu = contenu;
    }
    private String contenu;

    public commentaire() {
    }

    public commentaire(int id, int produit_id, String contenu) {
        this.id = id;
        this.produit_id = produit_id;
        this.contenu = contenu;
    }

    public commentaire(int produit_id, String contenu) {
        this.produit_id = produit_id;
        this.contenu = contenu;
    }

    public int getId() {
        return id;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    
    
}
