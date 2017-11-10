package com.ozu.cs547;

import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class MyServerCreator {

  @Autowired
  MyServer myserver;

  @PostConstruct
  public void startLogger() {
    try {
      myserver.printLogs();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @ShellMethod("Creates server")
  public String createserver(@ShellOption String serverPort, @ShellOption String path)
      throws IOException {
    myserver.createMyServer(Integer.parseInt(serverPort), path);
    return "Server created. Listening for connection on port " + serverPort + " ....";
  }

}
