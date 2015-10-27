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
        String data = "";
        if(in.length() > 4) data = in.substring(4, in.length());
        switch (cmd) {
            case "SST":
                mGame.showMessage("Game started");
            break;
            case "SNS":
                mGame.showMessage("Game not started");
            break;
            case "SJN":
                Player p = new Player(Integer.parseInt(data));
                mGame.join(p);
                mGame.showMessage("Player "+p.getId()+" joined");
            break;
            case "STR":
                mGame.showMessage("Turn player " + mGame.getPlayerById(Integer.parseInt(data)).getId());
            break;
            case "SNT":
                mGame.showMessage("Not your turn");
            break;
            case "SMV":
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
