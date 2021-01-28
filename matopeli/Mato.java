
package matopeli;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

/************Madon luokka*******************/
public class Mato extends ObjektienHallinta {
    
    ObjektienKasittely kasittelija;     //instanssi handlerille
    
    int[] xLast = new int[160];         //taulukot x ja y koordinaateille
    int[] yLast = new int[160];         //madon häntää varten.
    int syodytOmenat = 0;           
    int highScore = 0;
    boolean omenaSyoty = false;     //tarkistus muuttujia 
    boolean powerUpSyoty = false;
    boolean powerUpTimer = false;
    int timer = 100;                  //timer muuttuja powerupiia varten
    
    //luodaan oma väri powerup timerille ja alpha arvoa muuttamalla säädetään sen läpikuultavuutta
    int alpha = 127; // 50% transparent
    Color myColour = new Color(255, 255, 0, alpha);
    
    public Mato(int x, int y, ID id, ObjektienKasittely kasittelija) {
        super(x, y, id);
        this.kasittelija = kasittelija;
    }

    //ticki päivittää objektin arvot
    public void tick() {
        
        //POWERUPIN TIMERI
        if(powerUpSyoty && timer > 0){                  //tarkistaa onko powerup syöty ja timer ei ole nollassa
            timer--;                            //timer laskee kunnes saavuttaa nollan  
            System.out.println(timer + " laskuri");
        }else if(timer == 0){                           //tarkistaa onko timer nollassa
            powerUpSyoty = false;               //pävitetään booleanit takaisin falseksi
            powerUpTimer = false;
            timer = 100;                        //asetetaan timer takaisin 100 
            System.out.println("NOLLAUS" + timer);
        }
                
        //If lauseelle tarkistetaan että ellei powerupia ole syöty JA nappia painettu..
        if((lyhennys && !powerUpSyoty) || (!lyhennys && powerUpSyoty) || (!lyhennys && !powerUpSyoty)){
            xLast[syodytOmenat] = x;    //..niin saa taulukon "viimeinen" paikka madon pään koordinaatit
            yLast[syodytOmenat] = y;
        }   //tärkeää että tämä on ennen x ja y koordinaattien uudelleen päivitystä!
        
        if(GameOver){
            speedX = 0;     //jos GameOver tilanne niin mato pysähtyy
            speedY = 0;     //asettamalla speedX ja Y nollaksi
            if(syodytOmenat > highScore)highScore = syodytOmenat;     //päivitetään myös Highscore        
        }
        //PELIN UUDELLEEN ALOITUS
        if(GameOver && lyhennys){       //Gameover tilanteessa lyhennysnappi painettuna
            GameOver = false;       //päivtetään GameOver takaisin falseksi
            syodytOmenat = 0;       //nollataan syödyt omenat
            x = 20;             //asetetaan madon päälle alun koordinaatit
            y = 200;
        }
        x += speedX*scl;    //päivitetään uudet arvot x ja y koordinaateille.
        y += speedY*scl;    //speedX ja Y  skaalataan scl muuttujalla, tällä simuloidaan 
                            //"ruudukkoa" jossa mato liikkuu
                            
        //System.out.println("X: " + x);        //debuggausta varten
        //System.out.println("Y: " + y);
        //if(lyhennys)System.out.println("NAPPITOIMII");   
        
        
        //looppaa kaikki madon blokit läpi, tarkistaa ensin ettei lyhennysnappi ole painettuna JA powerup syotyna
        if((lyhennys && !powerUpSyoty) || (!lyhennys && powerUpSyoty) || (!lyhennys && !powerUpSyoty)){
            for(int i = 0; i < syodytOmenat; i++){
                xLast[i] = xLast[i+1];              //asettaa taulukkoon aina edelliselle paikalle seuraavan arvon
                yLast[i] = yLast[i+1];
            }
        }
        
        //LYHENNYSNAPPIHÄSSÄKKÄ
        //nappi painettu ja powerupsyoty niin tallentaa kaikkiin xlast ja ylast taulukon paikkoihin x:n ja y:n arvon
        if(lyhennys && powerUpSyoty){
            for(int i = 0; i < syodytOmenat; i++){
                yLast[i] = y;
                xLast[i] = x;
            }
        }
        
        y = Matopeli.rajatMato(y, 0, 320);          //tarkistaa meneekö mato rajan yli
        x = Matopeli.rajatMato(x, 0, 640);          //ja jos menee niin palauttaa madon koordinaatit vastaiselle seinälle
        hitBoxiBlokki();                //muurien hitboxit
        hitBoxiOmena();             //omenan hitboxit
        osuukoHanta();          //hännän "hitboxit"
        hitBoxiPowerUp();   //powerupin hitboxi
        
        
    }
    
    //tässä tarkistetaan osuuko madon pää tai liikkuva este madon häntään, tässä ei käytetä rectanglen kirjastoa apuna
    //minkä vuoksi tässä ei varsinaisesti käytetä hitboxeja
    private void osuukoHanta(){ 
        for(int i = 0; i < kasittelija.object.size(); i++){     //käy kaikki pelin objektit läpi.
            ObjektienHallinta tempObject1 = kasittelija.object.get(i); //antaa tempObjectille loopin sen hetkisen objektin arvot.
            if(tempObject1.getID() == ID.BlokkiM){          //tarkistaa onko kyseisen objektin id liikkuvan esteen id.
                for(int j = 0; j < syodytOmenat-1; j++){    //toinen looppi käy läpi kaikki madon blokit poislukien pää.
                    if(!lyhennys && (tempObject1.getX() == xLast[j] && tempObject1.getY() == yLast[j] //tarkistaa ettei lyhennysnappia ole painettu
                            || xLast[j] == x && yLast[j] == y)){    //ja osuuko hännän koordinaatit yhteen esteen tai madon pään kanssa
                                System.out.println("AHA");
                                GameOver = true;
                    }
                }
            }
        }
    }
    //metodi joka tarkistaa intersectaako madon pää minkään blokki objektin kanssa
    private void hitBoxiBlokki(){       
        for(int i = 0; i < kasittelija.object.size(); i++){             //looppaa kaikki objektit läpi
            ObjektienHallinta tempObject1 = kasittelija.object.get(i);
            if(tempObject1.getID() == ID.BlokkiM || 
                    tempObject1.getID() == ID.BlokkiSV0 ||
                        tempObject1.getID() == ID.BlokkiSH0){
                if(hitbox().intersects(tempObject1.hitbox())){      //tarkistaa meneekö hitboxit päällekkäin
                    //System.out.println("Oho");        //Debuggausta varten
                    GameOver = true;                               
                }
            }
        }
    }
    
    private void hitBoxiOmena(){
        //samat loopit kuin blokkien kanssa
        for(int i = 0; i < kasittelija.object.size(); i++){
            ObjektienHallinta tempObject1 = kasittelija.object.get(i);
            if(tempObject1.getID() == ID.Omena){
                if(hitbox().intersects(tempObject1.hitbox())){                   
                    omenaSyoty = true;
                    syodytOmenat++;             //kasvattaa syötyjen omenien määrää
                    //System.out.println(syodytOmenat);     //debuggausta
                    //System.out.println("Omena syoty");
                }
            }
        }
    }
    private void hitBoxiPowerUp(){   
        for(int i = 0; i < kasittelija.object.size(); i++){
            ObjektienHallinta tempObject1 = kasittelija.object.get(i);
            if(tempObject1.getID() == ID.PowerUp){
                if(hitbox().intersects(tempObject1.hitbox())){
                    //System.out.println("POWERUPSYOTY");   //debuggausta
                    powerUpSyoty = true;
                    powerUpTimer = true; 
                }
            }
        }
    }
    
    //renderi renderöi objektin ruudulle
    public void render(Graphics g) {
        
        g.setColor(Color.white);
        g.fillRect(x, y, scl, scl);
        
        //powerup käytössä renderöi vain madon pään
        if(lyhennys && powerUpSyoty){       
                //System.out.println("APUAPUA");        //debuggausta               
                g.setColor(Color.white);           
                g.fillRect(x, y, 10, 10);  
                
        }   else{                                           //muulloin piirtää koko madon
                for(int i = 0; i < syodytOmenat; i++){  //looppi käy läpi kaikki x ja ylast taulukon paikat poislukien pää
                    g.setColor(Color.white);           
                    g.fillRect(xLast[i], yLast[i], 10, 10); //piirtää kaikki xlast ja ylast taulukon neliöt              
                } 
        }
        
        //POWERUPTIMERIN PIIRTO
        if(powerUpTimer){
            for(int i = 0; i < timer; i++){         //looppaa timerin loppuun asti
                g.setColor(myColour);               //käyttää alussa luotua omaa väriä
                g.fillRect(10 + i*2, 15, 2, 15);    //piirtää suorakulmiot aina i*2 pikseliä oikealle edellisestä
            }
        }
        
        //PISTEMÄÄRÄN PIIRTO RUUDULLE
        g.setColor(Color.red);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        g.drawString("Pisteet: " + syodytOmenat, 10, 10);
        
        //GAMEOVER tekstin piirto ruudulle
        if(GameOver){
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.drawString("HIGHSCORE: "+highScore, 300, 300);
        }
    }

    //Madon pään hitbox
    public Rectangle hitbox() {   
        return new Rectangle(x, y, scl, scl);       
    }   
}
