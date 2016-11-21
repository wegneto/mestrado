/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.client.ui;

import java.util.ArrayList;
import java.util.List;
import mt.Order;
import mt.comm.ClientComm;

/**
 *
 * @author wegneto
 */
public class Session {
    
    public static String loggedUser = "";
    
    public static ClientComm clientComm = null;
    
    public static List<Order> orders = new ArrayList<>();
    
    public static List<Order> history = new ArrayList<>();
    
}
