package angel;

import angel.gmtool.ClientGame;
import angel.gmtool.ClientProtocol;
import angel.gmtool.Player;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



/**
 *
 * @author aariasgonzalez
 */
public class TttClientGame extends ClientGame{
    
    private Box boxes[][] = TttServerGame.generateBoxes();
    
    TttClientGame(Window window, ClientProtocol protocol){
        super(window, protocol);
    }

    
    
    public void tryMove(int x, int y){
        try {
            move(x, y);
        } catch (IOException ex) {
            Logger.getLogger(TttServerGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void move(int x, int y) throws IOException{
        send("MOV", x + " " + y);
    }
    
    
    
    
}

