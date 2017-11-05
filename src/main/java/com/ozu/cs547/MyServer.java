package com.ozu.cs547;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class MyServer {

    @Autowired
    RequestReader requestReader;

    @Async
    public void createMyServer(Integer portNumber) throws IOException {
        ServerSocket server = new ServerSocket(portNumber);
        while (true) {
            Socket clientSocket = server.accept();
            requestReader.readRequest(clientSocket);
        }
    }

}
