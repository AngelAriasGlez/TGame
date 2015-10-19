/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private final int mMaxPlayers;
    private final Game mGame;
    private final ServerProtocol mProtocol;
    public Server(int maxPlayers, Game game){
        mMaxPlayers = maxPlayers;
        mGame = game;
        mProtocol = new ServerProtocol(mGame);
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
                        ServerPlayerTask pt = new ServerPlayerTask(clientSocket, mProtocol);
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

    public class ServerPlayerTask implements Runnable {
        private final Socket mClientSocket;
        private final ServerProtocol mProtocol;
        
        Player mPlayer;
        
        BufferedReader mIn;
        OutputStream mOut;

        private ServerPlayerTask(Socket clientSocket, ServerProtocol protocol) {
            mClientSocket = clientSocket;
            mProtocol = protocol;
            
            mPlayer = new Player(mClientSocket.getInetAddress());
            mGame.join(mPlayer);
            
            try {
                mIn = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()), 2048);
                mOut = mClientSocket.getOutputStream();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void send(String out){
            try {
                mOut.write(out.getBytes());
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            System.out.println("Got a client !");

            
            if(mPlayer != null && mIn != null && mOut != null){
            
            
            try {
                while(!mClientSocket.isClosed()){
                   mProtocol.process(mIn.readLine(), mPlayer, this);
                }
            
            
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            }

            try {
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}