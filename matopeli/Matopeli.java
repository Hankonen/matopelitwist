/*
*Author: Hankonen
*I got a lot of help from this tutorial series on youtube.com by RealTutsGML:
*https://youtu.be/1gir2R7G9ws
*
*/
package matopeli;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

//main class. extends canvas mahdollistaa renderöinnin ja implements runnable
//mahdollistaa tekemään main classista threadin
public class Matopeli extends Canvas implements Runnable{
    
    //muuttujat jotka viedään ikkuna luokkaan
    public static final String title = "Matopeli";
    public static final int WIDTH = 655;
    public static final int HEIGHT = 362;
    
    
    private Thread thread;
    
    //alustetaan kasittelija instanssi
    private ObjektienKasittely kasittelija;
    //alustetaan paalla muuttuja jota vertaillaan kun tutkitaan onko threadi käynnissä
    private boolean paalla = false;
    private boolean peliAlkaa = false;
    private boolean GameOver1 = false;
    
    //matopeli constructori
    public Matopeli(){
        
        kasittelija = new ObjektienKasittely();
        
        //kuuntelee napinpainalluksia parametreinään ohjain luokan constructorin kasittelija
        this.addKeyListener(new Ohjain(kasittelija));
        
        //uusi ikkuna luokan instanssi jolle annetaan parametrit
        new Ikkuna(WIDTH, HEIGHT, title, this);
        
        
        //random arvoja omenan ja powerupin koordinaateille
        Random rand = new Random();
        int xRO = rand.nextInt(64)*10;
        int yRO = rand.nextInt(31)*10;
        int xRP = rand.nextInt(64)*10;
        int yRP = rand.nextInt(31)*10;
        
        //lisätään kaikki objektit jotta niitä voidaan pääluokassa käyttää
        kasittelija.addObject(new Mato(20, 200, ID.Mato, kasittelija));
        kasittelija.addObject(new BlokkiSV(50, 70, ID.BlokkiSV0));
        kasittelija.addObject(new BlokkiSH(170, 50, ID.BlokkiSH0));
        kasittelija.addObject(new BlokkiSV(580, 70, ID.BlokkiSV0));
        kasittelija.addObject(new BlokkiSH(170, 270, ID.BlokkiSH0));
        kasittelija.addObject(new BlokkiM0(230, 220, ID.BlokkiM));
        kasittelija.addObject(new omena(xRO, yRO, ID.Omena, kasittelija));
        kasittelija.addObject(new PowerUp(xRP, yRP, ID.PowerUp, kasittelija));
        System.out.printf("x: %d\ny:%d\n",xRO, yRO);
    }
    
    
    /**********************************************************************/
    //
    public void run() {
        this.requestFocus();                            //ei tarvi klikata ikkunaa että se toimii
        
        double targetTPS = 10.0;
        double nsPerTick = 1000000000.0 / targetTPS;
        long lastTime = System.nanoTime();              //alustaa long muuttujan ja antaa sille tämän hetkisen system timerin arvon nanosekuntteina
        long timer = System.currentTimeMillis();        //alustaa long muuttujan ja antaa sille tämän hetkisen system timerin arvon millisekuntteina
        double unprocessed = 0.0;
        int fps = 0;
        int tps = 0;
        boolean canRender = false;
        
        while (paalla){
            long now = System.nanoTime();                   //alustaa long muuttujan ja antaa sille tämän hetkisen system timerin arvon nanosekuntteina
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;
            
            if(unprocessed >= 1.0){
                tick();
                //System.out.println(unprocessed);
                unprocessed--;
                tps++;
                canRender = true;
            }else// canRender = false;
                       
            try{
                Thread.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            
            if (canRender){
                render();
                fps++;
            }
            
            if(System.currentTimeMillis() - 1000 > timer){
                timer += 1000;
                //System.out.printf("FPS: %d | TPS: %d\n", fps, tps);
                
                tps = 0;
                fps = 0;
            }
        }
    }
    /******************************************************************************************************/
    //käynnistää threadin
    public synchronized void start() {
        thread = new Thread(this);          //this viittaa nykyiseen objektiin
        thread.start();                     //käynnistää run metodin
        paalla = true;
    }
    
    //sammuttaa threadin
    public synchronized void stop() {
        
        //testaa errorien varalle ja printtaa errormessagen jos error ilmenee
        try{
            thread.join();                  //blokkaa kutsuvan threadin kunnes kyseinen thread on lopetettu
            paalla = false;
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        //luodaan uusi matopeli luokan instanssi
        new Matopeli();
    }

    private void tick() {
        //käsittelijä käy läpi pelin objektit ja päivittää ne
        kasittelija.tick();
    }
    
    //renderöidään grafiikkaa ruudulle   
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();   //alustetaan bufferointi
        if(bs == null){                     //getbufferstrategy saa aina ensin arvon null
            this.createBufferStrategy(3);   //triple buffering
            return;
        }
        
        Graphics g = bs.getDrawGraphics();  
        /********************************************************/
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        //IF STARTUP NIIN TÄMÄ VALIKKO
        
        /**********************************/
        startUp();
        /****************************************/
        if(!peliAlkaa){
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
            g.drawString("Matopeli", 75, 100);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 12)); 
            g.drawString("Syö omenoita kasvaaksesi.", 75, 120);
            g.drawString("Syötyäsi banaanin voit lyhentää itseäsi", 75, 133);
            g.drawString("painamalla ohjaimen painiketta.", 75, 146);
            g.drawString("Valitse suunta aloittaaksesi peli!", 75, 159);
        }
        GameOver();
        
        if(GameOver1){
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
            g.drawString("GAME OVER", 75, 100);
            g.drawString("Paina ohjaimen painiketta aloittaaksesi uusi peli", 75, 120);
        }

        //käsittelijä käy läpi pelin objektit ja renderöi ne
        kasittelija.render(g);
        
        /********************************************************/
        g.dispose();
        bs.show();
    }
    public static int rajatMato(int arvo, int min, int max){
        if(arvo > max){
            arvo = min;
            return arvo;
        }
        else if(arvo < min){
            arvo = max;
            return arvo;
        }
        else return arvo;
    }
    private void startUp(){ 
        for(int i = 0; i < kasittelija.object.size(); i++){     //käy kaikki pelin objektit läpi.
            ObjektienHallinta tempObject1 = kasittelija.object.get(i); //antaa tempObjectille loopin sen hetkisen objektin arvot.
            if(tempObject1.getID() == ID.Mato){          //tarkistaa onko kyseisen objektin id liikkuvan esteen id.
                int liikkuuX = tempObject1.getSpeedX();
                int liikkuuY = tempObject1.getSpeedY();
                if(liikkuuX != 0 || liikkuuY != 0){
                    peliAlkaa = true;
                }else if(!GameOver1 && liikkuuX == 0 && liikkuuY == 0)peliAlkaa = false;
            }
        }
    }
    
    private void GameOver(){ 
        for(int i = 0; i < kasittelija.object.size(); i++){     //käy kaikki pelin objektit läpi.
            ObjektienHallinta tempObject1 = kasittelija.object.get(i); //antaa tempObjectille loopin sen hetkisen objektin arvot.
            if(tempObject1.getID() == ID.Mato){          //tarkistaa onko kyseisen objektin id liikkuvan esteen id.
                GameOver1 = tempObject1.getGameOver();               
            }
        }
    }
    
}

 
