/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel;


import angel.gmtool.ServerGame;
import angel.gmtool.ServerProtocol;
import angel.gmtool.ServerTask;

/**
 *
 *  aariasgonzalez
 */
public class TttServerProtocol extends ServerProtocol{

       
        @Override
        public void process(String cmd, String data, ServerTask task, ServerGame game){
                        super.process(cmd, data, task, game);
                        TttServerGame tgame = (TttServerGame)game;
                        switch (cmd) {
                            case "MOV":
                                String b[] = data.split(" ");
                                if (b.length < 2) {
                                    return;
                                }

                                
                                int result = tgame.isMoveValid(Integer.parseInt(b[0]), Integer.parseInt(b[1]), task.getPlayer());
                                if (result == 1) {
                                    tgame.broadcast("SMV "+b[0]+" "+b[1]+" "+task.getPlayer().getId());
                                    tgame.broadcast("STR "+tgame.getCurrentTurn().getId());
                                    if (tgame.checkForWin() != null) {
                                        tgame.reset();
                                        tgame.broadcast("WIN "+task.getPlayer().getId());
                                        
                                    }
                                }else if(result == -1){
                                    task.send("SNS");
                                }else if(result == -2){
                                    task.send("SNT");
                                }else if(result == -3){
                                    task.send("SNV");
                                }
                                break;

                        }   
    
    
    }
        
 
}
