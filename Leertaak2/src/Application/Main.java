package Application;

import Logics.MongoDB;
import Logics.Settings;
import java.awt.BorderLayout;
import java.sql.Connection;

/**
 * The main form for this application it loads the other forms if requested
 *
 * @author Groep 12
 */
public class Main extends javax.swing.JFrame
{

    private final DataProcessing pDataProcessing;
    private final DataDisplay pDataDisplay;
    private static Connection connection;
    private final java.awt.Cursor cursorWait;
    private final java.awt.Cursor cursorDefault;

    /**
     * Creates new form Main
     */
    public Main()
    {
        initComponents();
        this.toBack();
        pDataProcessing = new DataProcessing();
        pDataDisplay = new DataDisplay();
        BorderLayout bl = new BorderLayout();
        this.setLayout(bl);
        cursorWait = java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR);
        cursorDefault = java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR);
        this.toFront();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        pMain = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lbError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BSS Leertaak 1 - Groep 12");
        setResizable(false);

        pMain.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                pMainFocusLost(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jButton1.setText("Data Processing");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jButton2.setText("Data Display");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });

        lbError.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbError.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout pMainLayout = new javax.swing.GroupLayout(pMain);
        pMain.setLayout(pMainLayout);
        pMainLayout.setHorizontalGroup(
            pMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pMainLayout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(pMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                    .addComponent(lbError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(60, 60, 60))
        );
        pMainLayout.setVerticalGroup(
            pMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbError, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addGap(22, 22, 22)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        if(MongoDB.tryConnect()) 
        {
            Main.addLine("# Connected to database "
                    + Settings.DB_NAME + " #");
            this.add(pDataProcessing, BorderLayout.CENTER);
            pMain.setVisible(false);
            pDataProcessing.setVisible(true);
        }
        else 
        {
           lbError.setText("# Failed to connect to MongoDB!");
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        this.setCursor(cursorWait);
        this.add(pDataDisplay, BorderLayout.CENTER);
        pDataDisplay.updateDisplay();
        pMain.setVisible(false);
        pDataDisplay.setVisible(true);
        if (pDataDisplay.isVisible()) this.setCursor(cursorDefault);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void pMainFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_pMainFocusLost
    {//GEN-HEADEREND:event_pMainFocusLost
        
    }//GEN-LAST:event_pMainFocusLost

    /**
     * Starts the Main form. 
     * Currently it does not do anything with arguments
     * 
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Main().setVisible(true);
            }
        });
    }

    /**
     * Sets the database connection
     * 
     * @param connection
     */
    public static void setConnection(Connection connection)
    {
        Main.connection = connection;
    }

    /**
     * Gets the database connection
     * 
     * @return
     */
    public static Connection getConnection()
    {
        return connection;
    }

    /**
     * Sets the visibility of the main Panel
     * 
     * @param visible
     */
    public static void setMainPanelVisible(boolean visible)
    {
        pMain.setVisible(visible);
    }

    /**
     * Sets the thread count in the data processing form
     * 
     * @param count
     */
    public static void setThreadCount(int count)
    {
        DataProcessing.lbThreadCount.setText(count + "");
    }

    /**
     * Writes a Exception to the screen
     * 
     * @param line
     */
    public static void addLine(Exception line)
    {
        addLine(line.toString());
    }

    /**
     * Writes a String to the screen
     * 
     * @param line
     */
    public static void addLine(String line)
    {
        DataProcessing.taMessages.append(line + System.getProperty("line.separator"));
        int p = DataProcessing.taMessages.getText().length();
        try
        {
            DataProcessing.taMessages.setCaretPosition(p);
        }
        catch (IllegalArgumentException ex)
        {
            //do nothing because not important for the correct working of the application.
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel lbError;
    private static javax.swing.JPanel pMain;
    // End of variables declaration//GEN-END:variables

}
