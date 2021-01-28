
package matopeli;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/*****************Liikkuvan Blokin luokka*********************************/
public class BlokkiM0 extends ObjektienHallinta {
    
        public BlokkiM0(int x, int y, ID id) {
        super(x, y, id);
        speedX = 0;
        speedY = 0;
    }
    //muuttujat hitboxin "leventämiselle" ja "pidentämiselle"
    int hitBoxinKasvattajaX = 10;
    int hitBoxinKasvattajaY = 10;
    
    public void tick() {   
        //if lauseet tarkistaa blokin koordinaatit ja kun blokki saavuttaa
        //rajan niin if lauseet muuttavat sen suuntaa
        if(x == 230 && y == 220){
           speedX = 0;
           speedY = -1;
           hitBoxinKasvattajaY = 1;
           hitBoxinKasvattajaX = 0;
        }
        if(x == 230 && y == 100){
           speedY = 0;
           speedX = 1;
           hitBoxinKasvattajaX = 1;
           hitBoxinKasvattajaY = 0;
        }
        if(x == 390 && y == 100){
           speedX = 0;
           speedY = 1;
           hitBoxinKasvattajaY = 1;
           hitBoxinKasvattajaX = 0;
        }
        if(x == 390 && y == 220){
           speedY = 0;
           speedX = -1;
           hitBoxinKasvattajaX = 1;
           hitBoxinKasvattajaY = 0;
        }
        //päivitetään uudet x ja y koordinaatit ja skaalataan ne scl muuttujalla
        x += speedX*scl;
        y += speedY*scl;
    }
    
    //renderöidään blokki
    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(x, y, scl, scl);       
    }

    //hitboxin palautus metodi palauttaa hitboxin laatikon 
    public Rectangle hitbox() {
        //Rectangle saa arvot koordinaateiksi x ja y jolloin se pysyy blokin "päällä"
        return new Rectangle(x + hitBoxinKasvattajaX, y + hitBoxinKasvattajaY, scl, scl);
    }    
}
