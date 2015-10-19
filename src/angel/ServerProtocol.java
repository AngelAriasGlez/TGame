/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel;

/**
 *
 * @author aariasgonzalez
 */
public class ServerProtocol {

    
    private final String CMD_MOVE = "MOV";
    
    private final Game mGame;
    
    public ServerProtocol(Game game){
        mGame = game;
    }
    
    
    
    public void process(String in, Player player, Server.ServerPlayerTask task){
        String cmd = in.substring(0, 3);
        String data = in.substring(4, in.length());
        switch(cmd){
            case CMD_MOVE:
                String b[] = data.split(" ");
                if(b.length < 2) return;
                if(mGame.isMoveValid(Integer.parseInt(b[0]), Integer.parseInt(b[1]), player)){
                    
                }
            break;

        
        }
    }

    
}
