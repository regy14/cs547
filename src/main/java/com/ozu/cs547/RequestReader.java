package com.ozu.cs547;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RequestReader {

  @Async
  public void readRequest(Socket clientSocket, String path) {
    try {
      Log log = new Log();
      log.setDate(Calendar.getInstance());
      InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
      BufferedReader reader = new BufferedReader(isr);
      String line = reader.readLine();
      String requestedPath = null, httpHeader = "HTTP/1.1 200 OK\r\n\r\n";
      byte[] responseContent = null;
      while (line != null && !line.isEmpty()) {
        if (line != null && line.startsWith("GET")) {
          String[] splittedRequest = line.split(" ");
          log.setRequest(splittedRequest[0] + " " + splittedRequest[1]);
          requestedPath = splittedRequest[1];
        }
        line = reader.readLine();
      }
      if (clientSocket.getRemoteSocketAddress().toString().contains("/")) {
        log.setDestination(clientSocket.getRemoteSocketAddress().toString().substring(1));
      } else {
        log.setDestination(clientSocket.getRemoteSocketAddress().toString());
      }

      MyServer.putLogQueue(log);
      if (requestedPath != null && !requestedPath.isEmpty()) {
        File requestedFile = new File(path + requestedPath);

        if (requestedFile.exists()) {
          if (requestedFile.isDirectory()) {
            Path filePath = Paths.get(path + requestedPath + "\\index.html");
            responseContent = Files.readAllBytes(filePath);
          } else {
            Path filePath = Paths.get(path + requestedPath);
            responseContent = Files.readAllBytes(filePath);
            if (!filePath.endsWith("html")) {
              httpHeader += "Content-Disposition: attachment\r\n\r\n";
            }
          }
        }
      }
      if (responseContent != null) {
        clientSocket.getOutputStream()
            .write(httpHeader.getBytes());
        clientSocket.getOutputStream().write(responseContent);

      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (!clientSocket.isClosed()) {
          clientSocket.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
