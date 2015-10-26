/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel;

import java.io.IOException;

/**
 *
 * @author Angel
 */
public class Client implements IClientSocketListener{

    @Override
    public void onDataReceived(String in) {
        String cmd = in.substring(0, 3);
        String data = in.substring(4, in.length());
        switch (cmd) {
            case "SJN":
                mGame.join(new Player(Integer.parseInt(data)));
            break;
            case "SOV":
                String b[] = data.split(" ");
                if (b.length < 2) {
                    return;
                }
                Box box[][] = mGame.getBoxes();
                box[Integer.parseInt(b[0])][Integer.parseInt(b[1])].setPlayer(mGame.getPlayerById(Integer.parseInt(b[2])));
            break;
            case "WIN":
                mGame.win(mGame.getPlayerById(Integer.parseInt(data)));
            break;
        }
        
    }
    private ClientGame mGame;
    private ClientSocket mClientSocket;
    
    public Client(ClientGame game){
        mGame = game;
    
    }
    public void connect(String address){
        mClientSocket = new ClientSocket();
        mClientSocket.setAddress(address);
        mClientSocket.setListener(this);
        mClientSocket.start();


    }
    public void move(int x, int y) throws IOException{
        mClientSocket.send("MOV", x + " " + y);
    }
    
}
