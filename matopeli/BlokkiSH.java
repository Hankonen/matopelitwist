
package matopeli;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/****************Horisontaalisten blokkien luokka**********************/
public class BlokkiSH extends ObjektienHallinta {

    public BlokkiSH(int x, int y, ID id) {
        super(x, y, id);
    }

    //tätä ei tarvitse päivittää tick metodilla koska se pysyy aina paikoillaan
    public void tick() {
        
    }

    //renderöidään
    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(x, y, 300, 10);        
    }
    
    //hitboximetodi 
    public Rectangle hitbox() {
        return new Rectangle(x, y, 300, 10);
    }  
}
