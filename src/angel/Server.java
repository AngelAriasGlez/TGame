/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import static java.lang.System.in;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private final int mMaxPlayers;
    private final ServerGame mGame;
    
    private ArrayList<ServerTask> mServerTasks = new ArrayList();


    public Server(int maxPlayers, ServerGame game) {
        mMaxPlayers = maxPlayers;
        mGame = game;
    }

    public void start() {

        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(mMaxPlayers);

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(8000);
                    System.out.println("Waiting for clients to connect...");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        ServerTask pt = new ServerTask(Server.this, mGame, clientSocket);
                        addTask(pt);
                        clientProcessingPool.submit(pt);
                    }
                } catch (IOException e) {
                    System.err.println("Unable to process client request");
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }
    
    public synchronized void addTask(ServerTask task){
        mServerTasks.add(task);
    }
    public synchronized void broadToTasks(String msg){
        for(ServerTask t :mServerTasks){
            t.send(msg);
        };
    }
    public synchronized void broadToTasksE(String msg, Player p){
        for(ServerTask t :mServerTasks){
            t.send(msg);
        };
    }
    

    public class ServerTask implements Runnable {
        Socket mSocket;
        
        BufferedReader mInBr;
        InputStream mIn;
        OutputStream mOut;
        ServerGame mGame;

        Player mPlayer;
        
        BlockingQueue<String> mToSend;

        Server mServer;
        public ServerTask(Server server, ServerGame game, Socket clientSocket) {
            mServer = server;
            mToSend = new ArrayBlockingQueue(1024);
            
            mSocket = clientSocket;
            mGame = game;

            mPlayer = new Player();
            mGame.join(mPlayer);
            

            try {
                mIn = mSocket.getInputStream();
                mInBr = new BufferedReader(new InputStreamReader(mIn), 2048);
                mOut = mSocket.getOutputStream();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        public Player getPlayer(){
            return mPlayer;
        }

        public void send(String out) {
            out += System.lineSeparator();
            try {
                mOut.write(out.getBytes());
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {

            mServer.broadToTasks("SJN " + mPlayer.getId());
            
            for(Player p: mGame.getPlayers()){
                if(mPlayer.getId() == p.getId()) continue;
                send("SJN " + p.getId());
            }
            
            mServer.broadToTasks("SST");
            mGame.start();

            
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
                        String data = in.substring(4, in.length());
                        switch (cmd) {
                            case "MOV":
                                String b[] = data.split(" ");
                                if (b.length < 2) {
                                    return;
                                }
                                int result = mGame.isMoveValid(Integer.parseInt(b[0]), Integer.parseInt(b[1]), mPlayer);
                                if (result == 1) {
                                    mServer.broadToTasks("SMV "+b[0]+" "+b[1]+" "+mPlayer.getId());
                                    mServer.broadToTasks("STR "+mPlayer.getId());
                                    if (mGame.checkForWin() != null) {
                                        mGame.reset();
                                        mServer.broadToTasks("WIN "+mPlayer.getId());
                                        
                                    }
                                }else if(result == -1){
                                    send("SNS");
                                }else if(result == -2){
                                    send("SNT");
                                }else if(result == -3){
                                    send("SNV");
                                }
                                break;

                        }
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            mGame.mPlayers.remove(mPlayer);

            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
