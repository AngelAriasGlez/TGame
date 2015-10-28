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
        Player p = null;
        switch (cmd) {
            case "SST":
                mGame.showMessage("Game started");
            break;
            case "SNS":
                mGame.showPopMessage("Game not started");
            break;
            case "SJN":
                p = new Player(Integer.parseInt(data));
                mGame.join(p);
                mGame.showMessage("Player "+p.getId()+" joined");
            break;
            case "SLV":
                p = new Player(Integer.parseInt(data));
                mGame.showPopMessage("Player "+p.getId()+" leave");
            break;
            case "STR":
                mGame.showMessage("Turn player " + mGame.getPlayerById(Integer.parseInt(data)).getId());
            break;
            case "SNT":
                mGame.showPopMessage("Not your turn");
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
        mClientSocket.setListener(this);

        mClientSocket.connect(address);


    }
    public void move(int x, int y) throws IOException{
        mClientSocket.send("MOV", x + " " + y);
    }
    
}
