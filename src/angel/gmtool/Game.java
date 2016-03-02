/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package angel.gmtool;

import angel.Box;
import angel.gmtool.Player;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author aariasgonzalez
 */
public class Game {
    
    protected ArrayList<Player> mPlayers = new ArrayList();
    private boolean mStarted = false;
    
    private int nMinPlayers = 1;
    private int nMaxPlayers = 4;
    
    public ArrayList<Player> getPlayers(){
        return mPlayers;
    }
    public void removePlayer(Player p){
        mPlayers.remove(p);
    }
    
    public void start(){
        mStarted = true;
    }
    public boolean isStarted(){
        return mStarted;
    }
    public void setMaxPlayers(int num){
        nMaxPlayers = num;
    }
    public void setMinPlayers(int num){
        nMinPlayers = num;
    }
    public int getMaxPlayers(){
        return nMaxPlayers;
    }
    public int getMinPlayers(){
        return nMinPlayers;
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
    

}
