
package matopeli;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/************OMENAOBJEKTIN LUOKKA**********************/
public class omena extends ObjektienHallinta{

    ObjektienKasittely kasittelija;
    boolean hups = false;               //omenan väärään paikkaan spawnaamista varten
    boolean jes = false;                //omenan syöntiä varten    
    int testHups = 0;                   //omenan väärään paikkaan spawnaamista varten
    Random rand = new Random();         //random koordinaatteja varten 
    
    
    public omena(int x, int y, ID id, ObjektienKasittely kasittelija) {
        super(x, y, id);
        this.kasittelija = kasittelija;
    }

    @Override
    public void tick() {
                       
        hitBoxJes();        //hitboxjes testaa osuuko madon ja omenan hitboxit
        
        //jos osuu niin annetaan omenalle uudet randomit koordinaatit
        if(jes){
            x = rand.nextInt(63)*scl;
            y = rand.nextInt(32)*scl;
            System.out.println("X: " + x);
            System.out.println("Y: " + y);
        }
        testHups = hitBoxHups();        //annetaan testhupsille arvo hitbox hups metodilta joka testaa
                                        //spawnaako omena seinän sisään
        //jos spawnaa niin annetaan taas uudet randomit koordinaatit
        if(testHups == 1){
            x = rand.nextInt(63)*scl;
            y = rand.nextInt(32)*scl;
            System.out.println("hups");
        }       
    }
    
    private int hitBoxHups(){
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
    private void hitBoxJes(){
        for(int i = 0; i < kasittelija.object.size(); i++){
            ObjektienHallinta tempObject1 = kasittelija.object.get(i);
            if(tempObject1.getID() == ID.Mato){
                if(hitbox().intersects(tempObject1.hitbox())){
                    jes = true;           
                }else jes = false;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, scl, scl);
    }

    @Override
    public Rectangle hitbox() {
        return new Rectangle(x, y, scl, scl);
    }
}
