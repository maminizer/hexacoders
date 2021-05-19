/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author maminizer
 */
public class User {
    
    public int id;
    public String email;
    public String password;
    public String firstname;
    public String lastname;
    public int nbrTel;

    public User(int id, String email, String password, String firstname, String lastname, int nbrTel) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.nbrTel = nbrTel;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getNbrTel() {
        return nbrTel;
    }

    public void setNbrTel(int nbrTel) {
        this.nbrTel = nbrTel;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", password=" + password + ", firstname=" + firstname + ", lastname=" + lastname + ", nbrTel=" + nbrTel + '}';
    }

    public User(String email, String password, String firstname, String lastname, int nbrTel) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.nbrTel = nbrTel;
    }
    
    
    
}
