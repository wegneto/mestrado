/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.client.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import mt.client.ui.Session;
import mt.comm.ClientComm;

/**
 *
 * @author wegneto
 */
public class Controller {
    
    private ClientComm clientComm;
    
    public Controller (ClientComm clientComm) {
        this.clientComm = clientComm;
    }
    
    public void connect(String host, String nickname) throws UnknownHostException, IOException{
        try {
            clientComm.connect(host, nickname);
            Session.loggedUser = nickname;
        } catch (UnknownHostException uhe) {
            throw new IOException(String.format("Host '%s' not found", host));
        } catch (IOException ex) {
            throw new IOException(String.format("Could not connect to the host %s: %s", host, ex.getMessage()));
        }
    }
    
}
