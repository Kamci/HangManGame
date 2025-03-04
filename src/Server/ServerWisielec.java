
package Server;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class ServerWisielec extends javax.swing.JFrame {

    static final int PORT = 12345;
   
    
    public ServerWisielec() {
        
        super("Serwer Wisielca");
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        logArea = new javax.swing.JTextArea();
        dolnyPanel = new javax.swing.JPanel();
        noweSlowoField = new javax.swing.JTextField();
        dodajButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server Wisielec");
        setLocation(new java.awt.Point(0, 0));
        setName("WiselecServerFrame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1000, 700));
        setSize(new java.awt.Dimension(1000, 700));

        logArea.setEditable(false);
        logArea.setBackground(new java.awt.Color(204, 255, 255));
        logArea.setColumns(20);
        logArea.setLineWrap(true);
        logArea.setRows(5);
        logArea.setWrapStyleWord(true);
        logArea.setMinimumSize(new java.awt.Dimension(500, 300));
        scrollPane.setViewportView(logArea);

        getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);

        noweSlowoField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        noweSlowoField.setAutoscrolls(false);
        noweSlowoField.setBorder(javax.swing.BorderFactory.createTitledBorder("Dodaj słowo:"));
        noweSlowoField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        noweSlowoField.setMinimumSize(new java.awt.Dimension(200, 40));
        noweSlowoField.setPreferredSize(new java.awt.Dimension(200, 40));
        noweSlowoField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noweSlowoFieldActionPerformed(evt);
            }
        });
        dolnyPanel.add(noweSlowoField);

        dodajButton.setBackground(new java.awt.Color(204, 255, 204));
        dodajButton.setText("Dodaj");
        dodajButton.setAlignmentX(0.5F);
        dodajButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        dodajButton.setPreferredSize(new java.awt.Dimension(42, 30));
        dodajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodajButtonActionPerformed(evt);
            }
        });
        dolnyPanel.add(dodajButton);

        getContentPane().add(dolnyPanel, java.awt.BorderLayout.PAGE_END);

        getAccessibleContext().setAccessibleName("ServerWisielec");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void noweSlowoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noweSlowoFieldActionPerformed
      String noweSlowo = noweSlowoField.getText().trim();
        if (!noweSlowo.isEmpty()) {
            ZarzadzanieSlowami.dodajSlowo(noweSlowo.toLowerCase());
            log("Dodano nowe słowo: " + noweSlowo);
            noweSlowoField.setText("");
        } else {
            log("Pole słowa nie może być puste");
        }
    
    }//GEN-LAST:event_noweSlowoFieldActionPerformed

    private void dodajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodajButtonActionPerformed
       noweSlowoFieldActionPerformed(evt);
        // TODO add your handling code here:
    }//GEN-LAST:event_dodajButtonActionPerformed

      public void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
 
    public static void main(String args[]) throws IOException {
    ServerWisielec server = new ServerWisielec();
    ServerSocket serverSocket = new ServerSocket(PORT);
    server.log("Serwer wystartował");
     try {
            while (true) {
                Socket socket = serverSocket.accept();
                server.log("Zgłosił się nowy klient");
                try {
                    new ObslugaKlienta(socket, server).start();
                } catch (IOException e) {
                    server.log("Błąd przy obsłudze klienta: " + e.getMessage());
                    socket.close();
                }
            }
        } finally {
            serverSocket.close();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dodajButton;
    private javax.swing.JPanel dolnyPanel;
    private javax.swing.JTextArea logArea;
    private javax.swing.JTextField noweSlowoField;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
