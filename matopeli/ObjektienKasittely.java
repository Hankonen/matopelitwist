
package matopeli;

import java.awt.Graphics;
import java.util.LinkedList;


public class ObjektienKasittely {
    //luodaan linkedlistin avulla lista kaikista peliobjekteista
    LinkedList<ObjektienHallinta> object = new LinkedList<ObjektienHallinta>();
    
    //tick metodi päivittää/bufferoi kaikki pelin objektit
    public void tick(){
        //for looppi käy läpi kaikki objektit
        for(int i = 0; i < object.size(); i++){
            ObjektienHallinta tempObject = object.get(i); //alustetaan väliaikainen objekti ja annetaan sille
                                                   //arvo linkedlistissä olevan metodin get avulla. saa siis i paikalla
                                                   //olevan objektin idn
            tempObject.tick();
                                                  
        }
    }
    
    //render metodi piirtää objektit ruudulle
    public void render(Graphics g){
        //sama looppi kuin tick metodissa
        for(int i = 0; i < object.size(); i++){
            ObjektienHallinta tempObject = object.get(i);
            tempObject.render(g);
            
        }
        
    }
    
    //objektin lisäys metodi pääluokkaa varten
    public void addObject(ObjektienHallinta object){
        this.object.add(object);
    }
    //objektin poisto metodi EI KÄYTETTY
    public void removeObject(ObjektienHallinta object){
        this.object.remove(object);
    }
}
