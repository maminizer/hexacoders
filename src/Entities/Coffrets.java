/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javafx.collections.ObservableList;

/**
 *
 * @author WIKI
 */
public class Coffrets {

   
            private int id;
    private String nom;
    private String description;
    private String image;
        private Double prix,offre;
        
           private Set services = new HashSet(0);
 

        
        
        
    public Coffrets(){
    }
    public Coffrets(int id, String nom, String description,String image,Double prix,Double offre) {
        this.id = id;
        this.nom = nom;
        this.description = description;
           this.prix=prix;
        this.offre=offre;
        this.image=image;
    }

    public Coffrets(int id, String nom, String description, String image, Double prix, Double offre, Set services) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.offre = offre;
        this.services = services;
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
    
    

    @Override
    public String toString() {
        return "Coffrets{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", image=" + image + ", prix=" + prix + ", offre=" + offre + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.nom);
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.image);
        hash = 97 * hash + Objects.hashCode(this.prix);
        hash = 97 * hash + Objects.hashCode(this.offre);
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
        final Coffrets other = (Coffrets) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.prix, other.prix)) {
            return false;
        }
        if (!Objects.equals(this.offre, other.offre)) {
            return false;
        }
        return true;
    }

    

    public Double getPrix() {
        return prix;
    }

    public Double getOffre() {
        return offre;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public void setOffre(Double offre) {
        this.offre = offre;
    }
    
    
   
    /*
    public Service getServiceById(int id) throws SQLException {
        Service ls = new Service();
        Statement st = cnx.createStatement();
        String req = "SELECT * FROM service where id="+id;
        ResultSet rs = st.executeQuery(req);
          if(rs.next()){
              ls = new Service(rs.getInt(1),rs.getString(2));
          }
        return ls;
   }
    public List<Service> getServicesByIdLab(int id) throws SQLException {
        List<Service> ls = new ArrayList<Service>();
        Statement st = cnx.createStatement();
        String req = "SELECT * FROM laboratoire_service where laboratoire_id="+id;
        ResultSet rs = st.executeQuery(req);
          while(rs.next()){
              ls.add(this.getServiceById(rs.getInt("service_id")));
          }
        return ls;
   }
    public List<Laboratoire> getLaboratoires() throws SQLException {
        List<Laboratoire> ls = new ArrayList<Laboratoire>();
        Statement st = cnx.createStatement();
        String req = "SELECT * FROM Laboratoire";
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            ServiceUser us = new ServiceUser();
            ServiceService ss= new ServiceService();
            User u = us.getUserById(rs.getInt(1));
            List<Service> s = ss.getServicesByIdLab(rs.getInt(1));
            ls.add( new Laboratoire(rs.getInt(1),u, rs.getInt(2), rs.getString(3), rs.getDate(4), rs.getDate(5),new HashSet<Service>(s)));
        }
        return ls;
    }
    private Set services = new HashSet(0);
    */
}
