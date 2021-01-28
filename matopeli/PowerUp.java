
package matopeli;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class PowerUp extends ObjektienHallinta {
    
    
    
    ObjektienKasittely kasittelija;
    Random rand = new Random();         //random koordinaatteja varten
    boolean hups = false;               //powerUpin väärään paikkaan spawnaamista varten
    boolean jes = false;                //powerUpin syöntiä varten 
    boolean timerBoolean = false;       //timeria varten boolean
    int testHups = 0;                   //väärään paikkaan spawnaamista varten
    int timer = 0;                      //timer spawn aikoja varten
    
    public PowerUp(int x, int y, ID id, ObjektienKasittely kasittelija) {
        super(x, y, id);
        this.kasittelija = kasittelija;
    }

    
    public void tick() {
        
        hitBoxJes();
        
        /***************TIMER SPAWNAAMISELLE***********************/
        if(!jes && timer < 100 && !timerBoolean){
            timer++;
            //System.out.println(timer);
        }
        if(jes){
            timerBoolean = true;
        }
        if(timerBoolean == true){
            timer--;
            //System.out.println(timer);
        }
        if(timerBoolean && timer == 0)timerBoolean = false;
        /**********************************************************/   
        
        //Jos powerup syödään se saa uudet koordinaatit randin avulla randomiin paikkaan
        if(jes){
            x = rand.nextInt(63)*scl;
            y = rand.nextInt(32)*scl;           
        }
        
        testHups = hitBoxHups();        //hitboxhups metodi antaa arvon jolla päätellään spawnasiko powerup seinän sisään
        
        //jos powerup spawnaa seinän sisään niin annetaan sille uudet randomit koordinaatit
        if(testHups == 1){
            x = rand.nextInt(63)*scl;
            y = rand.nextInt(32)*scl;
        }        
    }
   
    public void render(Graphics g) {
        //piirretään powerup ruudulle vain kun timer on 100
        if(timer == 100){
            g.setColor(Color.yellow);
            g.fillRect(x, y, scl, scl);
        }
    }
   
    public Rectangle hitbox() {
        return new Rectangle(x, y, scl, scl);
    }
    
    //hitboxmetodi powerupin syömiselle
    //sama periaate kuin muissakin hitbox metodeissa
    private void hitBoxJes() {
        for(int i = 0; i < kasittelija.object.size(); i++){
            ObjektienHallinta tempObject1 = kasittelija.object.get(i);
            if(tempObject1.getID() == ID.Mato){
                if(hitbox().intersects(tempObject1.hitbox())){
                    jes = true;           
                }else jes = false;
            }
        }
    }
    
    //hitbox metodi sen varalle että powerup spawnaa jonkin blokin sisään
    //sama periaate kuin muissakin hitboxmetodeissa, ainut ero on että palauttaa returni muuttujan
    private int hitBoxHups() {
        int returni = 0;
        for(int i = 0; i < kasittelija.object.size(); i++){
            ObjektienHallinta tempObject1 = kasittelija.object.get(i);
            if(tempObject1.getID() == ID.BlokkiM || 
                    tempObject1.getID() == ID.BlokkiSV0 ||
                        tempObject1.getID() == ID.BlokkiSH0){
                if(hitbox().intersects(tempObject1.hitbox())){                    
                    System.out.println("hups");
                    returni = 1;
                    return returni;
                }               
            }else returni = 0;
        }
        return returni;
    }  
}
