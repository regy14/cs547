package com.ozu.cs547;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.IOException;

@ShellComponent
public class MyServerCreator {

    @Autowired
    MyServer myserver;

    @ShellMethod("Creates server")
    public String createserver(@ShellOption String serverPort) throws IOException {
        myserver.createMyServer(Integer.parseInt(serverPort));
        return "Server created. Listening for connection on port " + serverPort + " ....";
    }

}
