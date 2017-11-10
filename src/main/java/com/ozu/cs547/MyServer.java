package com.ozu.cs547;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MyServer {

  @Autowired
  RequestReader requestReader;

  private static Queue<Log> logQueue = new LinkedBlockingQueue<Log>();

  @Async
  public void createMyServer(Integer portNumber, String path) throws IOException {
    ServerSocket server = new ServerSocket(portNumber);
    while (true) {
      Socket clientSocket = server.accept();
      requestReader.readRequest(clientSocket, path);
    }
  }

  @Async
  public void printLogs() throws InterruptedException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
    String logContent;
    Log log;
    while (true) {
      synchronized (logQueue) {
        while (!logQueue.isEmpty()) {
          log = logQueue.poll();
          logContent = sdf.format(log.getDate().getTime());
          logContent += " " + log.getRequest() + " " + log.getDestination();
          System.out.println(logContent);
        }
      }
      Thread.sleep(5000);
    }
  }

  public static void putLogQueue(Log log) {
    synchronized (logQueue) {
      logQueue.add(log);
    }
  }

}
