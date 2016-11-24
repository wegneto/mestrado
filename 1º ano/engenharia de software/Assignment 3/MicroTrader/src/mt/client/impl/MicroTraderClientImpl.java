package mt.client.impl;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import mt.client.MicroTraderClient;
import mt.client.Session;
import mt.client.ui.MicroTraderClientUI;
import mt.comm.ClientComm;
import mt.comm.impl.ClientCommImpl;
import mt.dummy.ClientCommDummy;

/**
 *
 * @author wegneto
 */
public class MicroTraderClientImpl implements MicroTraderClient {

    @Override
    public void start(ClientComm clientComm) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MicroTraderClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MicroTraderClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MicroTraderClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MicroTraderClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        MicroTraderClientUI ui = new MicroTraderClientUI();
        Session.clientComm = clientComm;
        ui.setVisible(true);
        
        try {
            this.waitObject(ui);
        } catch (InterruptedException ex) {
            Logger.getLogger(MicroTraderClientImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Logger.getLogger(MicroTraderClientImpl.class.getName()).log(Level.INFO, "That's all folks...");
    }
    
    public void waitObject(MicroTraderClientUI object) throws InterruptedException {
        synchronized(object) {
            object.wait();
        }
    }

    public static void main(String args[]) {
        //ClientComm clientComm = new ClientCommImpl();
        ClientComm clientComm = new ClientCommDummy();
        MicroTraderClient client = new MicroTraderClientImpl();
        client.start(clientComm);
        Logger.getLogger(MicroTraderClientImpl.class.getName()).log(Level.INFO, "This is the end...");
    }

}
