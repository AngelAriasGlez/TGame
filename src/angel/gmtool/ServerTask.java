/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel.gmtool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author aariasgonzalez
 */
public class ServerTask implements Runnable {
        
        Socket mSocket;
        
        BufferedReader mInBr;
        InputStream mIn;
        OutputStream mOut;

        Player mPlayer;
        
        BlockingQueue<String> mToSend;
        
        ServerGame mGame;

        public ServerTask(ServerGame game, Socket clientSocket) {
            
            try {
                mPlayer = new Player();

                
                
                mGame = game;
                mToSend = new ArrayBlockingQueue(1024);
                mSocket = clientSocket;
                mGame.join(mPlayer);

                mIn = mSocket.getInputStream();
                mInBr = new BufferedReader(new InputStreamReader(mIn), 2048);
                mOut = mSocket.getOutputStream();


            } catch (SecurityException ex) {

            }catch (IOException ex) {

            } catch (IllegalArgumentException ex) {


            }

        }
        public Player getPlayer(){
            return mPlayer;
        }

        public void send(String out) {
            out += System.lineSeparator();
            mToSend.add(out);
        }

        @Override
        public void run() {

            mGame.broadcast("SJN " + mPlayer.getId());
            
            for(Player p: mGame.getPlayers()){
                if(mPlayer.getId() == p.getId()) continue;
                send("SJN " + p.getId());
            }
            
            if(!mGame.isStarted() && mGame.getPlayers().size() >= mGame.getMinPlayers()){
                mGame.broadcast("SST");
                mGame.start();
            }

            Timer timer = new Timer (1000, new ActionListener ()
            {
                public void actionPerformed(ActionEvent e)
                {
                    send("SPN");
                 }
            });
            timer.start();

            

            
            try {
                while (mSocket.isConnected()) {
                    if(mToSend.size() > 0){
                        String out = null;
                        try {
                            out = mToSend.take();
                        } catch (InterruptedException ex) {
                            
                        }
                        if(out != null){
                            mOut.write(out.getBytes());
                        }
                    }
                    if(mIn.available() > 0){
                        String in = mInBr.readLine();
                        String cmd = in.substring(0, 3);
                        String data = "";
                        if(in.length() > 4) data = in.substring(4, in.length());
                        mGame.getProtocol().process(cmd, data, this, mGame);
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            mGame.removePlayer(mPlayer);
            mGame.broadcast("SLV "+mPlayer.getId());
            //mGame.broadcast("STR "+mGame.getCurrentTurn().getId());
            

            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }