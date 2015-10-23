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

    public Server(int maxPlayers, Game game) {
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
                        ServerTask pt = new ServerTask(mGame, clientSocket);
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

    public class ServerTask implements Runnable {
        Socket mSocket;
        
        BufferedReader mIn;
        OutputStream mOut;
        Game mGame;

        Player mPlayer;

        public ServerTask(Game game, Socket clientSocket) {
            mSocket = clientSocket;
            mGame = game;

            mPlayer = new Player();
            mGame.join(mPlayer);

            try {
                mIn = new BufferedReader(new InputStreamReader(mSocket.getInputStream()), 2048);
                mOut = mSocket.getOutputStream();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        public void send(String out) {
            try {
                mOut.write(out.getBytes());
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            System.out.println("Got a client !");

            try {
                while (!mSocket.isClosed() && mSocket.isConnected()) {
                    String in = mIn.readLine();
                    String cmd = in.substring(0, 3);
                    String data = in.substring(4, in.length());
                    switch (cmd) {
                        case "MOV":
                            String b[] = data.split(" ");
                            if (b.length < 2) {
                                return;
                            }
                            if (mGame.isMoveValid(Integer.parseInt(b[0]), Integer.parseInt(b[1]), mPlayer)) {
                                if (mGame.checkForWin() != null) {

                                }
                            }
                            break;

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
