/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel;

import java.net.InetAddress;

/**
 *
 * @author Angel
 */
public class Player {
    private static int mIdC = 0;
    private int mId;
    InetAddress mAddress;
    public Player(){
        mId = 0;
    
    }
    public Player(InetAddress addr){
        mId = mIdC;
        mIdC++;
        mAddress = addr;
    
    }
    public String getAddr(){
        return mAddress.getHostAddress();
    }
    public int getId(){
        return mId;
    }
}
