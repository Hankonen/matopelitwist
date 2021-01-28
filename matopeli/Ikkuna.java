
package matopeli;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

//ikkuna luokka. extends canvas mahdollistaa ikkunan renderöinnin
public class Ikkuna extends Canvas {
    
    public Ikkuna(int width, int height, String title, Matopeli game){
        
        //luodaan kehys niminen JFrame objekti
        JFrame kehys = new JFrame(title);   //ikkunan kehys joka saa titlen parametrina
        
        //määritellään kehyksen ulottuvuudet
        //Dimension saa parametreiksi int muuttujia width ja height
        kehys.setPreferredSize(new Dimension(width, height));
        kehys.setMaximumSize(new Dimension(width, height));
        kehys.setMinimumSize(new Dimension(width, height));
        
        kehys.setResizable(false); //ikkunan kokoa ei voi muuttaa
        kehys.setFocusable(true);  //mahdollistaa hiiren ja näppäimistön käytön
        kehys.setLocationRelativeTo(null);  //ikkuna aukeaa keskelle näyttöä
        kehys.setVisible(true);  //tekee ikkunasta näkyvän
         
        kehys.add(game);    //lisää game objektin jonka ikkuna luokka saa parametrina
        kehys.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //sammuttaa sovelluksen ikkunan sulkeuduttua
        game.start(); //main classissa oleva funktio
        
    } 
}
