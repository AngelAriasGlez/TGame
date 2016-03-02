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
public class Player {

    private static int mIdC = 0;
    private int mId;

    public Player(int id) {
        mId = id;
    }

    public Player() {
        mId = mIdC;
        mIdC++;


    }

    public int getId() {
        return mId;
    }
    public void setId(int id){
        mId = id;
    }




}
