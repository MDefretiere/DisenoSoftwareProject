/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.disenosoftwareproject.interfaz.vista;

import com.mycompany.disenosoftwareproject.interfaz.pares_vista_control.CtrlVistaIdentificarse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author el_pouleto
 */
public class VistaOperador extends javax.swing.JFrame {
    private CtrlVistaIdentificarse controlador = CtrlVistaIdentificarse.getInstance();
    /**
     * Creates new form VistaOperador
     */
    public VistaOperador() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        consultarDatos = new javax.swing.JButton();
        consultarVacaciones = new javax.swing.JButton();
        attenderLlamada = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Operador");

        consultarDatos.setText("Consultar mis datos en la empresa");

        consultarVacaciones.setText("Consultar vacaciones");
        consultarVacaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarVacacionesActionPerformed(evt);
            }
        });

        attenderLlamada.setText("Attender llamada");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(consultarDatos)
                            .addComponent(consultarVacaciones)
                            .addComponent(attenderLlamada))))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(consultarDatos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(consultarVacaciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(attenderLlamada)
                .addContainerGap(114, Short.MAX_VALUE))
        );

        attenderLlamada.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    controlador.attenderLlamada();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    System.out.println(ex.getCause());
                }
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void consultarVacacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultarVacacionesActionPerformed
        // Ne fait rien
    }//GEN-LAST:event_consultarVacacionesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(VistaOperador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaOperador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaOperador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaOperador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaOperador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton attenderLlamada;
    private javax.swing.JButton consultarDatos;
    private javax.swing.JButton consultarVacaciones;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
