/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel.gmtool;


/**
 *
 * @author Angel
 */
public class ClientProtocol{

    public void process(String cmd, String data, ClientGame game){
        Player p = null;
        switch (cmd) {
            case "SST":
                game.showMessage("Game started");
            break;
            case "SNS":
                game.showPopMessage("Game not started");
            break;
            case "SJN":
                p = new Player(Integer.parseInt(data));
                game.join(p);
                game.showMessage("Player "+p.getId()+" joined");
            break;
            case "SLV":
                p = new Player(Integer.parseInt(data));
                game.showPopMessage("Player "+p.getId()+" leave");
            break;
            case "STR":
                game.showMessage("Turn player " + game.getPlayerById(Integer.parseInt(data)).getId());
            break;
            case "SNT":
                game.showPopMessage("Not your turn");
            break;
            case "WIN":
                game.win(game.getPlayerById(Integer.parseInt(data)));
            break;
        }
        
    }


    
}
