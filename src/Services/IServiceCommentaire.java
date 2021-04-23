/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import Entities.commentaire;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author yossr
 */
public interface IServiceCommentaire {
      public void AddCommentaire(commentaire c);
    public boolean DeleteCommentaire(int id);
     public boolean UpdateCommentaire(commentaire p,int id);
    public ObservableList<commentaire>  AfficherCommentaire() throws SQLException;
}
