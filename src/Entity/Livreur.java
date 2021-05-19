/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author PCS
 */
public class Livreur {
    
    private int id ;
    private String nom; 
    private String prenom; 
    private String email; 
    private boolean disponibilite; 
    private int dis ;
    private int cin;
    private int numtlf;
    
    public Livreur(){};
    
     public Livreur(int id , String nom,String prenom,boolean disponibilite,int cin,int numtlf,String email){
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.disponibilite=disponibilite;
        this.cin=cin;
        this.numtlf=numtlf;
        this.email=email;
    }
     
         public Livreur(String nom,String prenom,boolean disponibilite,int cin,int numtlf,String email){
        this.nom=nom;
        this.prenom=prenom;
        this.disponibilite=disponibilite;
         this.cin=cin;
         this.numtlf=numtlf;
        this.email=email;
    
    }
   public Livreur(String nom,String prenom,int dis,int cin,int numtlf,String email){
        this.nom=nom;
        this.prenom=prenom;
        this.dis=dis;
         this.cin=cin;
         this.numtlf=numtlf;
        this.email=email;
    
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public int getNumtlf() {
        return numtlf;
    }

    public void setNumtlf(int numtlf) {
        this.numtlf = numtlf;
    }

    public int getDis() {
        return dis;
    }

    public void setDis(int dis) {
        this.dis = dis;
    }
    

    @Override
    public String toString() {
        return "Livreur{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", disponibilite=" + disponibilite + ", cin=" + cin + ", numtlf=" + numtlf + '}';
    }


     
     
    
    
}
