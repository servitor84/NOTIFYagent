package de.di.notify.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class FilesTab_Ext extends javax.swing.JFrame implements java.awt.event.ActionListener {

    public FilesTab_Ext() {
        initComponents();
    }
    /*
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jCheckBox11 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jCheckBox13 = new javax.swing.JCheckBox();
        jCheckBox14 = new javax.swing.JCheckBox();
        jCheckBox15 = new javax.swing.JCheckBox();
        jCheckBox16 = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jCheckBox17 = new javax.swing.JCheckBox();
        jCheckBox18 = new javax.swing.JCheckBox();
        jCheckBox19 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jCheckBox20 = new javax.swing.JCheckBox();
        jCheckBox21 = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jCheckBox22 = new javax.swing.JCheckBox();
        jCheckBox23 = new javax.swing.JCheckBox();
        jCheckBox24 = new javax.swing.JCheckBox();
        jCheckBox25 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setFocusCycleRoot(false);
        setFocusable(false);
        setFocusableWindowState(false);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("2 aus 5 Familie"));
        jPanel1.setName("jPanel1"); // NOI18N

        jCheckBox1.setText("jCheckBox1");
        jCheckBox1.setName("jCheckBox1"); // NOI18N

        jCheckBox2.setText("jCheckBox2");
        jCheckBox2.setName("jCheckBox2"); // NOI18N

        jCheckBox3.setText("jCheckBox3");
        jCheckBox3.setName("jCheckBox3"); // NOI18N

        jCheckBox4.setText("jCheckBox4");
        jCheckBox4.setName("jCheckBox4"); // NOI18N

        jCheckBox5.setText("jCheckBox5");
        jCheckBox5.setName("jCheckBox5"); // NOI18N

        jCheckBox6.setText("jCheckBox6");
        jCheckBox6.setName("jCheckBox6"); // NOI18N

        jCheckBox7.setText("jCheckBox7");
        jCheckBox7.setName("jCheckBox7"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox4)
                    .addComponent(jCheckBox5)
                    .addComponent(jCheckBox6)
                    .addComponent(jCheckBox7))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox7)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Code 39 Famile"));
        jPanel2.setName("jPanel2"); // NOI18N

        jCheckBox8.setText("jCheckBox8");
        jCheckBox8.setName("jCheckBox8"); // NOI18N

        jCheckBox9.setText("jCheckBox9");
        jCheckBox9.setName("jCheckBox9"); // NOI18N

        jCheckBox10.setText("jCheckBox10");
        jCheckBox10.setName("jCheckBox10"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox8)
                    .addComponent(jCheckBox9)
                    .addComponent(jCheckBox10))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Andere "));
        jPanel3.setName("jPanel3"); // NOI18N

        jCheckBox11.setText("jCheckBox11");
        jCheckBox11.setName("jCheckBox11"); // NOI18N

        jCheckBox12.setText("jCheckBox12");
        jCheckBox12.setName("jCheckBox12"); // NOI18N

        jCheckBox13.setText("jCheckBox13");
        jCheckBox13.setName("jCheckBox13"); // NOI18N

        jCheckBox14.setText("jCheckBox14");
        jCheckBox14.setName("jCheckBox14"); // NOI18N

        jCheckBox15.setText("jCheckBox15");
        jCheckBox15.setName("jCheckBox15"); // NOI18N

        jCheckBox16.setText("jCheckBox16");
        jCheckBox16.setName("jCheckBox16"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox12)
                    .addComponent(jCheckBox13)
                    .addComponent(jCheckBox14)
                    .addComponent(jCheckBox16)
                    .addComponent(jCheckBox11)
                    .addComponent(jCheckBox15))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox15)
                .addGap(3, 3, 3)
                .addComponent(jCheckBox16)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("2-D Barcodes"));
        jPanel4.setName("jPanel4"); // NOI18N

        jCheckBox17.setText("jCheckBox17");
        jCheckBox17.setEnabled(false);
        jCheckBox17.setName("jCheckBox17"); // NOI18N

        jCheckBox18.setText("jCheckBox18");
        jCheckBox18.setEnabled(false);
        jCheckBox18.setName("jCheckBox18"); // NOI18N

        jCheckBox19.setText("jCheckBox19");
        jCheckBox19.setEnabled(false);
        jCheckBox19.setName("jCheckBox19"); // NOI18N

        jLabel1.setText("jLabel1");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("jLabel2");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("jLabel3");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText("jLabel4");
        jLabel4.setName("jLabel4"); // NOI18N

        jCheckBox20.setText("jCheckBox20");
        jCheckBox20.setEnabled(false);
        jCheckBox20.setName("jCheckBox20"); // NOI18N

        jCheckBox21.setText("jCheckBox21");
        jCheckBox21.setEnabled(false);
        jCheckBox21.setName("jCheckBox21"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(85, 85, 85))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox17)
                            .addComponent(jCheckBox18)
                            .addComponent(jCheckBox19)
                            .addComponent(jCheckBox20)
                            .addComponent(jCheckBox21)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addContainerGap(32, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(7, 7, 7)
                .addComponent(jCheckBox20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Handlesstrichcodes"));
        jPanel5.setName("jPanel5"); // NOI18N

        jCheckBox22.setText("jCheckBox22");
        jCheckBox22.setName("jCheckBox22"); // NOI18N

        jCheckBox23.setText("jCheckBox23");
        jCheckBox23.setName("jCheckBox23"); // NOI18N

        jCheckBox24.setText("jCheckBox24");
        jCheckBox24.setName("jCheckBox24"); // NOI18N

        jCheckBox25.setText("jCheckBox25");
        jCheckBox25.setName("jCheckBox25"); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox22)
                    .addComponent(jCheckBox23)
                    .addComponent(jCheckBox25)
                    .addComponent(jCheckBox24))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox24)
                .addGap(3, 3, 3)
                .addComponent(jCheckBox25)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jButton1.setText("OK");
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setText("Default");
        jButton2.setName("jButton2"); // NOI18N

        jButton3.setText("Alle anwählen");
        jButton3.setName("jButton3"); // NOI18N

        jButton4.setText("Alle abwählen");
        jButton4.setBorderPainted(false);
        jButton4.setDefaultCapable(false);
        jButton4.setFocusPainted(false);
        jButton4.setFocusable(false);
        jButton4.setName("jButton4"); // NOI18N

        jButton5.setText("Abbrechen");
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    
    }//GEN-LAST:event_jButton5ActionPerformed
     */
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox15;
    private javax.swing.JCheckBox jCheckBox16;
    private javax.swing.JCheckBox jCheckBox17;
    private javax.swing.JCheckBox jCheckBox18;
    private javax.swing.JCheckBox jCheckBox19;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox20;
    private javax.swing.JCheckBox jCheckBox21;
    private javax.swing.JCheckBox jCheckBox22;
    private javax.swing.JCheckBox jCheckBox23;
    private javax.swing.JCheckBox jCheckBox24;
    private javax.swing.JCheckBox jCheckBox25;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
    public static java.util.Map<String, String> map = new java.util.HashMap<String, String>();
    private Properties prop = new Properties();

    private void initComponents() {
        try {
            if (System.getProperty("user.language").equalsIgnoreCase("de")) {
                prop.load(getClass().getResourceAsStream("resources/FilesTab_Ext_de.properties"));
            } else {
                prop.load(getClass().getResourceAsStream("resources/FilesTab_Ext.properties"));
            }
        } catch (Exception ex) {
        }
        jPanel1 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jCheckBox11 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jCheckBox13 = new javax.swing.JCheckBox();
        jCheckBox14 = new javax.swing.JCheckBox();
        jCheckBox15 = new javax.swing.JCheckBox();
        jCheckBox16 = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jCheckBox17 = new javax.swing.JCheckBox();
        jCheckBox18 = new javax.swing.JCheckBox();
        jCheckBox19 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jCheckBox20 = new javax.swing.JCheckBox();
        jCheckBox21 = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jCheckBox22 = new javax.swing.JCheckBox();
        jCheckBox23 = new javax.swing.JCheckBox();
        jCheckBox24 = new javax.swing.JCheckBox();
        jCheckBox25 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

//        setAutoRequestFocus(false);
        setFocusCycleRoot(false);
        setFocusable(false);
        setFocusableWindowState(false);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(prop.getProperty("jPanel1")));
        jPanel1.setName(
                "jPanel1"); // NOI18N

        jCheckBox1.setText(
                prop.getProperty("jCheckBox1"));
        jCheckBox1.setName(
                "jCheckBox1"); // NOI18N

        jCheckBox2.setText(
                prop.getProperty("jCheckBox2"));
        jCheckBox2.setName(
                "jCheckBox2"); // NOI18N

        jCheckBox3.setText(
                prop.getProperty("jCheckBox3"));
        jCheckBox3.setName(
                "jCheckBox3"); // NOI18N

        jCheckBox4.setText(
                prop.getProperty("jCheckBox4"));
        jCheckBox4.setName(
                "jCheckBox4"); // NOI18N

        jCheckBox5.setText(
                prop.getProperty("jCheckBox5"));
        jCheckBox5.setName(
                "jCheckBox5"); // NOI18N

        jCheckBox6.setText(
                prop.getProperty("jCheckBox6"));
        jCheckBox6.setName(
                "jCheckBox6"); // NOI18N

        jCheckBox7.setText(
                prop.getProperty("jCheckBox7"));
        jCheckBox7.setName(
                "jCheckBox7"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);

        jPanel1.setLayout(jPanel1Layout);

        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jCheckBox1).addComponent(jCheckBox2).addComponent(jCheckBox3).addComponent(jCheckBox4).addComponent(jCheckBox5).addComponent(jCheckBox6).addComponent(jCheckBox7)).addContainerGap(23, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jCheckBox1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox4).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox5).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox6).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox7).addContainerGap(52, Short.MAX_VALUE)));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(prop.getProperty("jPanel2")));
        jPanel2.setName(
                "jPanel2"); // NOI18N

        jCheckBox8.setText(
                prop.getProperty("jCheckBox8"));
        jCheckBox8.setName(
                "jCheckBox8"); // NOI18N

        jCheckBox9.setText(
                prop.getProperty("jCheckBox9"));
        jCheckBox9.setName(
                "jCheckBox9"); // NOI18N

        jCheckBox10.setText(
                prop.getProperty("jCheckBox10"));
        jCheckBox10.setName(
                "jCheckBox10"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);

        jPanel2.setLayout(jPanel2Layout);

        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jCheckBox8).addComponent(jCheckBox9).addComponent(jCheckBox10)).addContainerGap(21, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(jCheckBox8).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox9).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox10).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(prop.getProperty("jPanel3")));
        jPanel3.setName(
                "jPanel3"); // NOI18N

        jCheckBox11.setText(
                prop.getProperty("jCheckBox11"));
        jCheckBox11.setName(
                "jCheckBox11"); // NOI18N

        jCheckBox12.setText(
                prop.getProperty("jCheckBox12"));
        jCheckBox12.setName(
                "jCheckBox12"); // NOI18N

        jCheckBox13.setText(
                prop.getProperty("jCheckBox13"));
        jCheckBox13.setName(
                "jCheckBox13"); // NOI18N

        jCheckBox14.setText(
                prop.getProperty("jCheckBox14"));
        jCheckBox14.setName(
                "jCheckBox14"); // NOI18N

        jCheckBox15.setText(
                prop.getProperty("jCheckBox15"));
        jCheckBox15.setName(
                "jCheckBox15"); // NOI18N

        jCheckBox16.setText(
                prop.getProperty("jCheckBox16"));
        jCheckBox16.setName(
                "jCheckBox16"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);

        jPanel3.setLayout(jPanel3Layout);

        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jCheckBox12).addComponent(jCheckBox13).addComponent(jCheckBox14).addComponent(jCheckBox16).addComponent(jCheckBox11).addComponent(jCheckBox15)).addContainerGap(18, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(jCheckBox11).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox12).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox13).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox14).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox15).addGap(3, 3, 3).addComponent(jCheckBox16).addContainerGap(78, Short.MAX_VALUE)));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(prop.getProperty("jPanel4")));
        jPanel4.setName(
                "jPanel4"); // NOI18N

        jCheckBox17.setText(
                prop.getProperty("jCheckBox17"));
        jCheckBox17.setEnabled(
                false);
        jCheckBox17.setName(
                "jCheckBox17"); // NOI18N

        jCheckBox18.setText(
                prop.getProperty("jCheckBox18"));
        jCheckBox18.setEnabled(
                false);
        jCheckBox18.setName(
                "jCheckBox18"); // NOI18N

        jCheckBox19.setText(
                prop.getProperty("jCheckBox19"));
        jCheckBox19.setEnabled(
                false);
        jCheckBox19.setName(
                "jCheckBox19"); // NOI18N

        jLabel1.setText(
                prop.getProperty("jLabel1"));
        jLabel1.setName(
                "jLabel1"); // NOI18N

        jLabel2.setText(
                prop.getProperty("jLabel2"));
        jLabel2.setName(
                "jLabel2"); // NOI18N

        jLabel3.setText(
                prop.getProperty("jLabel3"));
        jLabel3.setName(
                "jLabel3"); // NOI18N

        jLabel4.setText(
                prop.getProperty("jLabel4"));
        jLabel4.setName(
                "jLabel4"); // NOI18N

        jCheckBox20.setText(
                prop.getProperty("jCheckBox20"));
        jCheckBox20.setEnabled(
                false);
        jCheckBox20.setName(
                "jCheckBox20"); // NOI18N

        jCheckBox21.setText(
                prop.getProperty("jCheckBox21"));
        jCheckBox21.setEnabled(
                false);
        jCheckBox21.setName(
                "jCheckBox21"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);

        jPanel4.setLayout(jPanel4Layout);

        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jCheckBox17).addComponent(jCheckBox18).addComponent(jCheckBox19).addGroup(jPanel4Layout.createSequentialGroup().addGap(21, 21, 21).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel2).addComponent(jLabel1).addComponent(jLabel3).addComponent(jLabel4))).addComponent(jCheckBox20).addComponent(jCheckBox21)).addContainerGap(32, Short.MAX_VALUE)));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(jCheckBox17).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox18).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jCheckBox19).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel4).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox20).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox21).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(prop.getProperty("jPanel5")));
        jPanel5.setName(
                "jPanel5"); // NOI18N

        jCheckBox22.setText(
                prop.getProperty("jCheckBox22"));
        jCheckBox22.setName(
                "jCheckBox22"); // NOI18N

        jCheckBox23.setText(
                prop.getProperty("jCheckBox23"));
        jCheckBox23.setName(
                "jCheckBox23"); // NOI18N

        jCheckBox24.setText(
                prop.getProperty("jCheckBox24"));
        jCheckBox24.setName(
                "jCheckBox24"); // NOI18N

        jCheckBox25.setText(
                prop.getProperty("jCheckBox25"));
        jCheckBox25.setName(
                "jCheckBox25"); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);

        jPanel5.setLayout(jPanel5Layout);

        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jCheckBox22).addComponent(jCheckBox23).addComponent(jCheckBox25).addComponent(jCheckBox24)).addContainerGap(21, Short.MAX_VALUE)));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addComponent(jCheckBox22).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox23).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jCheckBox24).addGap(3, 3, 3).addComponent(jCheckBox25).addContainerGap(12, Short.MAX_VALUE)));

        jButton1.setText(
                "OK");
        jButton1.setName(
                "jButton1"); // NOI18N

        jButton2.setText(
                "Default");
        jButton2.setName(
                "jButton2"); // NOI18N

        jButton3.setText(
                "Alle anwählen");
        jButton3.setName(
                "jButton3"); // NOI18N

        jButton4.setText(
                "Alle abwählen");
        jButton4.setBorderPainted(
                false);
        jButton4.setDefaultCapable(
                false);
        jButton4.setFocusPainted(
                false);
        jButton4.setFocusable(
                false);
        jButton4.setName(
                "jButton4"); // NOI18N
        jButton4.setRequestFocusEnabled(
                false);
        jButton4.setRolloverEnabled(
                false);
        jButton4.setVerifyInputWhenFocusTarget(
                false);

        jButton5.setText(
                "Abbrechen");
        jButton5.setName(
                "jButton5"); // NOI18N
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(18, 18, 18).addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(layout.createSequentialGroup().addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap(20, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButton5)).addContainerGap(23, Short.MAX_VALUE)));
        jButton1.addActionListener(this);
        jButton2.addActionListener(this);
        jButton3.addActionListener(this);
        jButton4.addActionListener(this);
        jButton5.addActionListener(this);
        map.put("BC_INTERLEAVED25", "0");
        map.put("BC_INDUSTRIE25", "0");
        map.put("BC_25_IATA", "0");
        map.put("BC_25_3MATRIX", "0");
        map.put("BC_25_3DATALOGIC", "0");
        map.put("BC_25_BCDMATRIX", "0");
        map.put("BC_25_INVERTIERT", "0");
        map.put("BC_CODE39", "0");
        map.put("BC_CODE39EXT", "0");
        map.put("BC_CODE32", "0");
        map.put("BC_CODE93", "0");
        map.put("BC_CODE93EXT", "0");
        map.put("BC_CODABAR", "0");
        map.put("BC_CODE128", "0");
        map.put("BC_EAN128", "0");
        map.put("BC_CODE11", "0");
        map.put("BC_EAN13", "0");
        map.put("BC_EAN8", "0");
        map.put("BC_UPC_A", "0");
        map.put("BC_UPC_E", "0");
        BufferedImage image;
        ImageIcon icon;
        try {
            image = ImageIO.read(getClass().getResource("resources/favicon.png"));
            icon = new ImageIcon(image);
            this.setIconImage(icon.getImage());
        } catch (IOException ex) {
        }       
        this.setTitle("Typen");
        this.setLocation(200, 200);

        pack();
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == jButton1) {
//            _FilesTab.filesTabExtIsSet = true;
            if (jCheckBox1.isSelected()) {
                map.put("BC_INTERLEAVED25", "1");
            }else{
                map.put("BC_INTERLEAVED25", "0");
            }
            if (jCheckBox2.isSelected()) {
                map.put("BC_INDUSTRIE25", "1");
            }else{
                map.put("BC_INDUSTRIE25", "0");
            }
            if (jCheckBox3.isSelected()) {
                map.put("BC_25_IATA", "1");
            }else{
                map.put("BC_25_IATA", "0");
            }
            if (jCheckBox4.isSelected()) {
                map.put("BC_25_3MATRIX", "1");
            }else{
                map.put("BC_25_3MATRIX", "0");
            }
            if (jCheckBox5.isSelected()) {
                map.put("BC_25_3DATALOGIC", "1");
            }else{
                map.put("BC_25_3DATALOGIC", "0");
            }
            if (jCheckBox6.isSelected()) {
                map.put("BC_25_BCDMATRIX", "1");
            }else{
                map.put("BC_25_BCDMATRIX", "0");
            }
            if (jCheckBox7.isSelected()) {
                map.put("BC_25_INVERTIERT", "1");
            }else{
                map.put("BC_25_INVERTIERT", "0");
            }
            if (jCheckBox8.isSelected()) {
                map.put("BC_CODE39", "1");
            }else{
                map.put("BC_CODE39", "0");
            }
            if (jCheckBox9.isSelected()) {
                map.put("BC_CODE39EXT", "1");
            }else{
                 map.put("BC_CODE39EXT", "0");
            }
            if (jCheckBox10.isSelected()) {
                map.put("BC_CODE32", "1");
            }else{
                map.put("BC_CODE32", "0");
            }
            if (jCheckBox11.isSelected()) {
                map.put("BC_CODE93", "1");
            }else{
                map.put("BC_CODE93", "0");
            }
            if (jCheckBox12.isSelected()) {
                map.put("BC_CODE93EXT", "1");
            }else{
                map.put("BC_CODE93EXT", "0");
            }
            if (jCheckBox13.isSelected()) {
                map.put("BC_CODABAR", "1");
            }else{
                map.put("BC_CODABAR", "0");
            }
            if (jCheckBox14.isSelected()) {
                map.put("BC_CODE128", "1");
            }else{
                map.put("BC_CODE128", "0");
            }
            if (jCheckBox15.isSelected()) {
                map.put("BC_EAN128", "1");
            }else{
                map.put("BC_EAN128", "0");
            }
            if (jCheckBox16.isSelected()) {
                map.put("BC_CODE11", "1");
            }else{
                map.put("BC_CODE11", "0");
            }
            if (jCheckBox22.isSelected()) {
                map.put("BC_EAN13", "1");
            }else{
                map.put("BC_EAN13", "0");
            }
            if (jCheckBox23.isSelected()) {
                map.put("BC_EAN8", "1");
            }else{
                map.put("BC_EAN8", "0");
            }
            if (jCheckBox24.isSelected()) {
                map.put("BC_UPC_A", "1");
            }else{
                map.put("BC_UPC_A", "0");
            }
            if (jCheckBox25.isSelected()) {
                map.put("BC_UPC_E", "1");
            }else{
                map.put("BC_UPC_E", "0");
            }
            GeneralTab.config.setProperty("", "");
            this.setVisible(false);
        } else if (evt.getSource() == jButton2) {
            jCheckBox1.setSelected(true);
            jCheckBox2.setSelected(true);
            jCheckBox3.setSelected(false);
            jCheckBox4.setSelected(false);
            jCheckBox5.setSelected(false);
            jCheckBox6.setSelected(false);
            jCheckBox7.setSelected(false);
            jCheckBox8.setSelected(true);
            jCheckBox9.setSelected(false);
            jCheckBox10.setSelected(false);
            jCheckBox11.setSelected(true);
            jCheckBox12.setSelected(true);
            jCheckBox13.setSelected(true);
            jCheckBox14.setSelected(true);
            jCheckBox15.setSelected(true);
            jCheckBox16.setSelected(false);
//            jCheckBox17.setSelected(false);
//            jCheckBox18.setSelected(false);
//            jCheckBox19.setSelected(false);
//            jCheckBox20.setSelected(false);
//            jCheckBox21.setSelected(false);
            jCheckBox22.setSelected(true);
            jCheckBox23.setSelected(true);
            jCheckBox24.setSelected(true);
            jCheckBox25.setSelected(true);

        } else if (evt.getSource() == jButton3) {
            jCheckBox1.setSelected(true);
            jCheckBox2.setSelected(true);
            jCheckBox3.setSelected(true);
            jCheckBox4.setSelected(true);
            jCheckBox5.setSelected(true);
            jCheckBox6.setSelected(true);
            jCheckBox7.setSelected(true);
            jCheckBox8.setSelected(true);
            jCheckBox9.setSelected(true);
            jCheckBox10.setSelected(true);
            jCheckBox11.setSelected(true);
            jCheckBox12.setSelected(true);
            jCheckBox13.setSelected(true);
            jCheckBox14.setSelected(true);
            jCheckBox15.setSelected(true);
            jCheckBox16.setSelected(true);
//            jCheckBox17.setSelected(true);
//            jCheckBox18.setSelected(true);
//            jCheckBox19.setSelected(true);
//            jCheckBox20.setSelected(true);
//            jCheckBox21.setSelected(true);
            jCheckBox22.setSelected(true);
            jCheckBox23.setSelected(true);
            jCheckBox24.setSelected(true);
            jCheckBox25.setSelected(true);

        } else if (evt.getSource() == jButton4) {
            jCheckBox1.setSelected(false);
            jCheckBox2.setSelected(false);
            jCheckBox3.setSelected(false);
            jCheckBox4.setSelected(false);
            jCheckBox5.setSelected(false);
            jCheckBox6.setSelected(false);
            jCheckBox7.setSelected(false);
            jCheckBox8.setSelected(false);
            jCheckBox9.setSelected(false);
            jCheckBox10.setSelected(false);
            jCheckBox11.setSelected(false);
            jCheckBox12.setSelected(false);
            jCheckBox13.setSelected(false);
            jCheckBox14.setSelected(false);
            jCheckBox15.setSelected(false);
            jCheckBox16.setSelected(false);
//            jCheckBox17.setSelected(false);
//            jCheckBox18.setSelected(false);
//            jCheckBox19.setSelected(false);
//            jCheckBox20.setSelected(false);
//            jCheckBox21.setSelected(false);
            jCheckBox22.setSelected(false);
            jCheckBox23.setSelected(false);
            jCheckBox24.setSelected(false);
            jCheckBox25.setSelected(false);

        } else if (evt.getSource() == jButton5) {
            this.dispose();
        }
    }

    public void setProfileProperties(Properties prop) {        
        if (prop.containsKey("BC_INTERLEAVED25") && prop.getProperty("BC_INTERLEAVED25").equals("1")) {
            jCheckBox1.setSelected(true);
            map.put("BC_INTERLEAVED25", "1");
        }else{
            jCheckBox1.setSelected(false);
            map.put("BC_INTERLEAVED25", "0");
        }
        if (prop.containsKey("BC_INDUSTRIE25") && prop.getProperty("BC_INDUSTRIE25").equals("1")) {
            jCheckBox2.setSelected(true);
            map.put("BC_INDUSTRIE25", "1");
        }else{
            jCheckBox2.setSelected(false);
            map.put("BC_INDUSTRIE25", "0");
        }
        if (prop.containsKey("BC_25_IATA") && prop.getProperty("BC_25_IATA").equals("1")) {
            jCheckBox3.setSelected(true);
            map.put("BC_25_IATA", "1");
        }else{
            jCheckBox3.setSelected(false);
            map.put("BC_25_IATA", "0");
        }
        if (prop.containsKey("BC_25_3MATRIX") && prop.getProperty("BC_25_3MATRIX").equals("1")) {
            jCheckBox4.setSelected(true);
            map.put("BC_25_3MATRIX", "1");
        }else{
            jCheckBox4.setSelected(false);
            map.put("BC_25_3MATRIX", "0");
        }
        if (prop.containsKey("BC_25_3DATALOGIC") && prop.getProperty("BC_25_3DATALOGIC").equals("1")) {
            jCheckBox5.setSelected(true);
            map.put("BC_25_3DATALOGIC", "1");
        }else{
            jCheckBox5.setSelected(false);
            map.put("BC_25_3DATALOGIC", "0");
        }
        if (prop.containsKey("BC_25_BCDMATRIX") && prop.getProperty("BC_25_BCDMATRIX").equals("1")) {
            jCheckBox6.setSelected(true);
            map.put("BC_25_BCDMATRIX", "1");
        }else{
            jCheckBox6.setSelected(false);
            map.put("BC_25_BCDMATRIX", "0");
        }
        if (prop.containsKey("BC_25_INVERTIERT") && prop.getProperty("BC_25_INVERTIERT").equals("1")) {
            jCheckBox7.setSelected(true);
            map.put("BC_25_INVERTIERT", "1");
        }else{
            jCheckBox7.setSelected(false);
            map.put("BC_25_INVERTIERT", "0");
        }
        if (prop.containsKey("BC_CODE39") && prop.getProperty("BC_CODE39").equals("1")) {
            jCheckBox8.setSelected(true);
            map.put("BC_CODE39", "1");
        }else{
            jCheckBox8.setSelected(false);
            map.put("BC_CODE39", "0");
        }
        if (prop.containsKey("BC_CODE39EXT") && prop.getProperty("BC_CODE39EXT").equals("1")) {
            jCheckBox9.setSelected(true);
            map.put("BC_CODE39EXT", "1");
        }else{
            jCheckBox9.setSelected(false);
            map.put("BC_CODE39EXT", "0");
        }
        if (prop.containsKey("BC_CODE32") && prop.getProperty("BC_CODE32").equals("1")) {
            jCheckBox10.setSelected(true);
            map.put("BC_CODE32", "1");
        }else{
            jCheckBox10.setSelected(false);
            map.put("BC_CODE32", "0");
        }
        if (prop.containsKey("BC_CODE93") && prop.getProperty("BC_CODE93").equals("1")) {
            jCheckBox11.setSelected(true);
            map.put("BC_CODE93", "1");
        }else{
            jCheckBox11.setSelected(false);
            map.put("BC_CODE93", "0");
        }
        if (prop.containsKey("BC_CODE93EXT") && prop.getProperty("BC_CODE93EXT").equals("1")) {
            jCheckBox12.setSelected(true);
            map.put("BC_CODE93EXT", "1");
        }else{
            jCheckBox12.setSelected(false);
            map.put("BC_CODE93EXT", "0");
        }
        if (prop.containsKey("BC_CODABAR") && prop.getProperty("BC_CODABAR").equals("1")) {
            jCheckBox13.setSelected(true);
            map.put("BC_CODABAR", "1");
        }else{
            jCheckBox13.setSelected(false);
            map.put("BC_CODABAR", "0");
        }
        if (prop.containsKey("BC_CODE128") && prop.getProperty("BC_CODE128").equals("1")) {
            jCheckBox14.setSelected(true);
            map.put("BC_CODE128", "1");
        }else{
            jCheckBox14.setSelected(false);
            map.put("BC_CODE128", "0");
        }
        if (prop.containsKey("BC_EAN128") && prop.getProperty("BC_EAN128").equals("1")) {
            jCheckBox15.setSelected(true);
            map.put("BC_EAN128", "1");
        }else{
            jCheckBox15.setSelected(false);
            map.put("BC_EAN128", "0");
        }
        if (prop.containsKey("BC_CODE11") && prop.getProperty("BC_CODE11").equals("1")) {
            jCheckBox16.setSelected(true);
            map.put("BC_CODE11", "1");
        }else{
            jCheckBox16.setSelected(false);
            map.put("BC_CODE11", "0");
        }
        if (prop.containsKey("BC_EAN13") && prop.getProperty("BC_EAN13").equals("1")) {
            jCheckBox22.setSelected(true);
            map.put("BC_EAN13", "1");
        }else{
            jCheckBox22.setSelected(false);
            map.put("BC_EAN13", "0");
        }
        if (prop.containsKey("BC_EAN8") && prop.getProperty("BC_EAN8").equals("1")) {
            jCheckBox23.setSelected(true);
            map.put("BC_EAN8", "1");
        }else{
            jCheckBox23.setSelected(false);
            map.put("BC_EAN8", "0");
        }
        if (prop.containsKey("BC_UPC_A") && prop.getProperty("BC_UPC_A").equals("1")) {
            jCheckBox24.setSelected(true);
            map.put("BC_UPC_A", "1");
        }else{
            jCheckBox24.setSelected(false);
            map.put("BC_UPC_A", "0");
        }
        if (prop.containsKey("BC_UPC_E") && prop.getProperty("BC_UPC_E").equals("1")) {
            jCheckBox25.setSelected(true);
            map.put("BC_UPC_E", "1");
        }else{
            jCheckBox25.setSelected(false);
            map.put("BC_UPC_E", "0");
        }
    }
}
