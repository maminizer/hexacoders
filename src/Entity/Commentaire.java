/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author yossr
 */
public class Commentaire {
  int id, produitId , userId;
    String contenu;

    public Commentaire() {
    }

    public Commentaire(int produitId, String contenu) {
        this.produitId = produitId;
        this.contenu = contenu;
    }

    public Commentaire(int id, int produitId, String contenu) {
        this.id = id;
        this.produitId = produitId;
        this.contenu = contenu;
    }

    public Commentaire(int id, int produitId,int userId , String contenu) {
        this.id = id;
        this.userId = userId;
        this.produitId = produitId;
        this.contenu = contenu;
    }
    
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProduitId() {
        return produitId;
    }

    public String getContenu() {
        return contenu;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    
}
