package angel;

import java.awt.Point;
import java.io.IOException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author aariasgonzalez
 */
public class ClientGame extends Game{
    Client mClient;
    
    
    ClientGame(){

        mClient = new Client(this);
        mClient.connect("localhost");
    }

    
    
    
    public void tryMove(int x, int y){
        try {
            mClient.move(x, y);
        } catch (IOException ex) {
            Logger.getLogger(ServerGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void win(Player p){
    
    
    }
    
    
 
    
    
    
    
}

