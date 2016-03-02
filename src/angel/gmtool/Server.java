/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angel.gmtool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
                        clientSocket.setKeepAlive(true);
                        ServerTask pt = new ServerTask(mGame, clientSocket);
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
    

    
}
