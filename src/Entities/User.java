package Entities;


public class User {

    public int id;
    public String email;
    public String password;
    public String username;
    public String lastname;
    public int nbr_tel;

    public User() {
    }

    public User(int id, String email, String password, String username, String lastname, int nbr_tel) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.lastname = lastname;
        this.nbr_tel = nbr_tel;
    }

    public User(int id, String email, String password, String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }
    
//    public User(String email, String password, String firstname, String lastname, int nbr_tel) {
//        
//        this.email = email;
//        this.password = password;
//        this.firstname = firstname;
//        this.lastname = lastname;
//        this.nbr_tel = nbr_tel;
//    }

    public User(String text, String text0, String text1, String text2, int k) {
        this.email = text;
        this.password = text0;
        this.username = text1;
        this.lastname = text2;
        this.nbr_tel = k;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getNbr_tel() {
        return nbr_tel;
    }

    public void setNbr_tel(int nbr_tel) {
        this.nbr_tel = nbr_tel;
    }

    @Override
    public String toString() {
        return "User{" +
//                "id=" + id +
                " email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + username + '\'' +
                ", lastname='" + lastname + '\'' +
                ", nbr_tel=" + nbr_tel +
                '}';
    }
    public String NbrParse() {
        return "" + nbr_tel + "" ;
    }
    
    
}

