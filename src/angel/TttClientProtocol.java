/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel;

import angel.gmtool.ClientGame;
import angel.gmtool.ClientProtocol;

/**
 *
 * @author aariasgonzalez
 */
public class TttClientProtocol extends ClientProtocol{
     public void process(String cmd, String data, ClientGame game){
         super.process(cmd, data, game);
        switch (cmd) {
            case "SMV":
                String b[] = data.split(" ");
                if (b.length < 2) {
                    return;
                }
                /*Box box[][] = game.getBoxes();
                box[Integer.parseInt(b[0])][Integer.parseInt(b[1])].setPlayer(game.getPlayerById(Integer.parseInt(b[2])));*/
            break;
        }
        
    }   
}
