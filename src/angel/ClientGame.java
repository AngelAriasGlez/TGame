package angel;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



/**
 *
 * @author aariasgonzalez
 */
public class ClientGame extends Game{
    Client mClient;
    Window mWindow;
    
    ClientGame(Window window){
        mWindow = window;
        mClient = new Client(this);
    }
    public void showMessage(String msg){
        mWindow.showMessage(msg);
    }
    public void connect(String address){
        mClient.connect(address);
    }
    
    
    public void tryMove(int x, int y){
        try {
            mClient.move(x, y);
        } catch (IOException ex) {
            Logger.getLogger(ServerGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void win(Player p){
        JOptionPane.showMessageDialog(null, "Ganador Player " + p.getId());
        reset();
    
    }
    
    
 
    
    
    
    
}

