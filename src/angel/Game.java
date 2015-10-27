/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package angel;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author aariasgonzalez
 */
public class Game {
    
    public static int LINE_ELEMENTS = 12;
    protected Box mBoxes[][] = new Box[LINE_ELEMENTS][LINE_ELEMENTS];
    
    protected ArrayList<Player> mPlayers = new ArrayList();
    private boolean mStarted = false;
    
    
    public ArrayList<Player> getPlayers(){
        return mPlayers;
    }
    
    public void start(){
        mStarted = true;
    }
    public boolean isStarted(){
        return mStarted;
    }
    
    public boolean join(Player player){
        if(!mPlayers.contains(player)){
            mPlayers.add(player);
            return true;
        }
        return false;
    }
    public Player getPlayerById(int id){
        for(Player p : mPlayers){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }
    
    
    public Game(){
        for(int x = 0; x < mBoxes.length ; x++){
            for(int y = 0; y < mBoxes[x].length ; y++){
                mBoxes[x][y] = new Box(new Point(x, y));
            }
        }   
    }
    void reset(){
         for(int x = 0; x < mBoxes.length ; x++){
            for(int y = 0; y < mBoxes[x].length ; y++){
                mBoxes[x][y].setPlayer(null);
            }
        }   
    }
    public Box[][] getBoxes(){
        return mBoxes;
    }
}
