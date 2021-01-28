
package matopeli;

import java.awt.Graphics;
import java.awt.Rectangle;

/*********************OBJEKTIEN HALLINTALUOKKA**************************************/
public abstract class ObjektienHallinta {
    //alustetaan "suojatut" muuttujat, niitä voi käyttää vain ne luokat jotka perii tämän luokan
    protected int x, y, speedX = 0, speedY, scl = 10;   //koordinaatit, nopeudet sekä skaalaus muuttujat
    protected boolean lyhennys = false;         //lyhennysnappia varten muuttuja
    protected boolean GameOver = false;     //Gameover tilaa varten muuttuja
    
    protected ID id;                //id muuttuja


    //constructor metodi 
    public ObjektienHallinta(int x, int y, ID id){     //saa parametreinä muuttujat perillis luokista (objekteilta)
        
        //syötetään parametreinä saadut arvot muuttujille
        this.x = x;     
        this.y = y;
        this.id = id;
        
    }
    //Abstractit metodeja on kaikkien perillisluokkien käytettävä
    public abstract void tick();
    
    public abstract void render(Graphics g);
    
    public abstract Rectangle hitbox();
    
    /*****************************************************************************/
    //set metodit joiden avulla muut luokat voi asettaa arvoja muuttujille
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }    
    public void setID(ID id){
        this.id = id;
    }
    public void setSpeedX(int speedX){
        this.speedX = speedX;
    }
    public void setSpeedY(int speedY){
        this.speedY = speedY;
    }
    public void setLyhennys(int totuus){
        int voisko = totuus;
        if(voisko == 1){
            this.lyhennys = true;                  
        }else this.lyhennys = false;            
    }
    public void setGameOver(int totta){
        int joopajoo = totta;
        
        if(joopajoo == 1){
            this.GameOver = true;
        }else this.GameOver = false;
    }
    /****************************************************************************/
    
    /****************************************************************************/
    //get metodit joiden avulla eri luokissa voidaan hakea arvoja
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public ID getID(){
        return id;
    }
    public int getSpeedX(){
        return speedX;
    }
    public int getSpeedY(){
        return speedY;
    }
    public boolean getGameOver(){
        return GameOver;
    }
    /****************************************************************************/
}   


 
