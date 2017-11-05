package com.ozu.cs547;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Date;

@Component
public class RequestReader{

    @Async
    public void readRequest(Socket clientSocket) {
        try {
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }
            Date today = new Date();
            String htmlcontent = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "\n" +
                    "   <head>\n" +
                    "      <title>HTML Layout using Tables</title>\n" +
                    "   </head>\n" +
                    "\n" +
                    "   <body>\n" +
                    "      <table width = \"100%\" border = \"0\">\n" +
                    "         \n" +
                    "         <tr>\n" +
                    "            <td colspan = \"2\" bgcolor = \"#b5dcb3\">\n" +
                    "               <h1>This is Web Page Main title</h1>\n" +
                    "            </td>\n" +
                    "         </tr>\n" +
                    "         <tr valign = \"top\">\n" +
                    "            <td bgcolor = \"#aaa\" width = \"50\">\n" +
                    "               <b>Main Menu</b><br />\n" +
                    "               HTML<br />\n" +
                    "               PHP<br />\n" +
                    "               PERL...\n" +
                    "            </td>\n" +
                    "            \n" +
                    "            <td bgcolor = \"#eee\" width = \"100\" height = \"200\">\n" +
                    "               Technical and Managerial Tutorials\n" +
                    "            </td>\n" +
                    "         </tr>\n" +
                    "         <tr>\n" +
                    "            <td colspan = \"2\" bgcolor = \"#b5dcb3\">\n" +
                    "               <center>\n" +
                    "                  Copyright © 2007 Tutorialspoint.com\n" +
                    "               </center>\n" +
                    "            </td>\n" +
                    "         </tr>\n" +
                    "         \n" +
                    "      </table>\n" +
                    "   </body>\n" +
                    "\n" +
                    "</html>";
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + htmlcontent;
            clientSocket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
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
