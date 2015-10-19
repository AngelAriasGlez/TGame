/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angel
 */
public class Client {
    private Game mGame;
    private ClientSocket mClientSocket;
    
    public Client(Game game){
        mGame = game;
    
    }
    public void connect(String address) throws IOException{
        mClientSocket = new ClientSocket();
        mClientSocket.connect(address);

    }
    public void move(int x, int y) throws IOException{
        mClientSocket.send("MOV", x + " " + y);
    }
    
}
