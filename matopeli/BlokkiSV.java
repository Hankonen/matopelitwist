
package matopeli;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/************Vertikaalisten blokkien luokka***********/
public class BlokkiSV extends ObjektienHallinta {

    public BlokkiSV(int x, int y, ID id) {
        super(x, y, id);
    }

    //ei tarvi päivittää
    public void tick() {
        
    }

    //renderöidään
    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(x, y, 10, 190);
    }
    
    //hitboxi
    public Rectangle hitbox() {
        return new Rectangle(x, y, 10, 190);
    }   
}
