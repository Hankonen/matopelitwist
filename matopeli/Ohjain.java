
package matopeli;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/*************OHJAIMEN LUOKKA******************************/
public class Ohjain extends KeyAdapter {
    
   
    private ObjektienKasittely kasittelija;
    
    //constructori ohjaimelle
    public Ohjain(ObjektienKasittely kasittelija){     //saa parametreinä objektienkasittely constructorin arvoja
        this.kasittelija = kasittelija;     //syötetään ne tämän kasittelija instassin handleriin
    }

    //näppäimistön inputtien tunnistus
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();           //luodaan muuttuja key joka saa integer ascii arvoja 
                                            //riippuen mitä nappia painetaan
                                            
        //luodaan uusi objektienhallinnan instanssi tempObject
        ObjektienHallinta tempObject = kasittelija.object.get(0);           //saa linkedlistin 0n paikan objektin. mikä on mato
        int liikkeessaX = tempObject.getSpeedX();       //muuttujat joiden avulla seurataan mihin suuntaan
        int liikkeessaY = tempObject.getSpeedY();       //mato liikkuu
        
        //välilyönti asettaa lyhennykselle arvon 1
        if(key == KeyEvent.VK_SPACE){
            tempObject.setLyhennys(1);
        }
        
        //MADON SUUNNAN MUUTTAMISELLE IF LAUSEET
        if(key == KeyEvent.VK_D && liikkeessaX == 0){
            tempObject.setSpeedX(1);
            tempObject.setSpeedY(0);
        }
        if(key == KeyEvent.VK_A && liikkeessaX == 0){
            tempObject.setSpeedX(-1);
            tempObject.setSpeedY(0);
        }
        if(key == KeyEvent.VK_W && liikkeessaY == 0){
            tempObject.setSpeedY(-1);
            tempObject.setSpeedX(0);
        }
        if(key == KeyEvent.VK_S && liikkeessaY == 0){
            tempObject.setSpeedY(+1);
            tempObject.setSpeedX(0);
        }  
    }
    
    //NAPIN VAPAUTTAMISMETODI
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        ObjektienHallinta tempObject = kasittelija.object.get(0);
        
        //release spacebar. palauttaa lyhennys booleanin falseksi
        if(key == KeyEvent.VK_SPACE) tempObject.setLyhennys(0);       
    }
}
