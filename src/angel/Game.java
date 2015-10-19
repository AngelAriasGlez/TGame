/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package angel;

import java.awt.Point;


/**
 *
 * @author aariasgonzalez
 */
public class Game {
    public enum Player{
        EMPTY, 
        LOCAL, 
        REMOTE_0

    }
    public enum Status{
        READY, WAITING
    }
    private Player mPrevTurn = Player.REMOTE_0;
    
    public static int LINE_ELEMENTS = 20;
    private Box mBoxes[][] = new Box[LINE_ELEMENTS][LINE_ELEMENTS];
    
    Game(){
        for(int x = 0; x < mBoxes.length ; x++){
            for(int y = 0; y < mBoxes[x].length ; y++){
                mBoxes[x][y] = new Box(new Point(x, y));
            }
        }
    }
    public Box[][] getBoxes(){
        return mBoxes;
    }
    
    public boolean tryMove(int x, int y, Player player){
        if(getTurn() == player && mBoxes[x][y].getPlayer() == Player.EMPTY){
            mBoxes[x][y].setPlayer(player);
            mPrevTurn = player;
            return true;
        }
        return false;
    }
    public Player checkWin(){
        
        Player p = checkRow();
        if(p != null){
            return p;
        } 
        p = checkCol();
        if(p != null){
            return p;
        }
        p = checkDiag();
        if(p != null){
            return p;
        }        
        return null;
    }
    
    //Need revision
    private Player checkRow() {
        for(int y = 0; y < mBoxes.length; y++){
            for(int x = 0; x < mBoxes.length; x++){
                for(int i = 0; x+i < mBoxes.length-1; i++){
                    Player p0 = mBoxes[x+i][y].getPlayer();
                    Player p1 = mBoxes[x+i+1][y].getPlayer();
                    if(p0 == p1 && p0 != Player.EMPTY){
                        if(i >= 2){
                            return mBoxes[x+i][y].getPlayer();
                        }
                    }else{
                        break;
                    }
                }
            }
        }
        return null;
    } 
    private Player checkCol() {
        for(int x = 0; x < mBoxes.length; x++){
            for(int y = 0; y < mBoxes[x].length; y++){
                for(int i = 0; y+i < mBoxes[x].length-1; i++){
                    Player p0 = mBoxes[x][y+i].getPlayer();
                    Player p1 = mBoxes[x][y+i+1].getPlayer();
                    if(p0 == p1 && p0 != Player.EMPTY){
                        if(i >= 2){
                            return mBoxes[x][y+i].getPlayer();
                        }
                    }else{
                        break;
                    }
                }
            }
        }
        return null;
    }
    
    private Player checkDiag() {

        for(int z = 0; z <= mBoxes.length*2; z++){
            for(int c = 0; c < z; c++){
                Player p = null;
                for(int l = 0; l < mBoxes.length; l++){
                    int x = (c+l);
                    int y = mBoxes.length-(z-(c+l));
                    if(x < 0 || x > mBoxes.length-1 || y < 0 || y > mBoxes.length-1) continue;
                    if(p == null) p = mBoxes[x][y].getPlayer();
                    if(p == mBoxes[x][y].getPlayer() && p != Player.EMPTY){
                        p = mBoxes[x][y].getPlayer();
                        if(l >= 3){
                            return p;
                        }
                    }else{
                        break;
                    }
                 

                }
            }
        }
        for(int z = 0; z <= mBoxes.length*2; z++){
            for(int c = 0; c < z; c++){
                Player p = null;
                for(int l = 0; l < mBoxes.length; l++){
                    int x = (c+l);
                    int y = (z-(c+l));
                    if(x < 0 || x > mBoxes.length-1 || y < 0 || y > mBoxes.length-1) continue;
                    if(p == null) p = mBoxes[x][y].getPlayer();
                    if(p == mBoxes[x][y].getPlayer() && p != Player.EMPTY){
                        p = mBoxes[x][y].getPlayer();
                        if(l >= 3){
                            return p;
                        }
                    }else{
                        break;
                    }
                 

                }
            }
        }
        
        
        

        return null;
    } 
     
     
    public Player getTurn(){

        Player l[] = Player.values();
        int i;
        for(i=1;i<l.length;i++){
            if(l[i] == mPrevTurn){
                break;
            }
        }
        i++;
        if(i >= l.length) i = 1;
        
        return l[i];
    }
    
    
    
    
}
