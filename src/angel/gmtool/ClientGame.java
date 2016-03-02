package angel.gmtool;

import angel.*;
import java.io.IOException;

import javax.swing.JOptionPane;



/**
 *
 * @author aariasgonzalez
 */
public class ClientGame extends Game implements IClientSocketListener{
    ClientProtocol mProtocol;
    Window mWindow;
    
    public ClientGame(Window window, ClientProtocol protocol){
        mWindow = window;
        mProtocol = protocol; 
    }

    
    public void showMessage(String msg){
        mWindow.showMessage(msg);
    }
    public void showPopMessage(String msg){
        JOptionPane.showMessageDialog(mWindow, msg);
    }

    
    public void win(Player p){
        JOptionPane.showMessageDialog(null, "Ganador Player " + p.getId());
        //reset();
    
    }
    
    private ClientSocket mClientSocket;
    
    public void connect(String address){
        mClientSocket = new ClientSocket();
        mClientSocket.setListener(this);

        mClientSocket.connect(address);


    }
    public void send(String cmd, String data) throws IOException{
        mClientSocket.send(cmd, data);

    }
    
    @Override
    public void onDisconnect() {
    }

    @Override
    public void onErrorToConnect() {
    }
    @Override
    public void onDataReceived(String in) {
        String cmd = in.substring(0, 3);
        String data = "";
        if(in.length() > 4) data = in.substring(4, in.length());
        mProtocol.process(cmd, data, this);
    }

    
    
 
    
    
    
    
}

