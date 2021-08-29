package de.di.notify.gui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jdesktop.application.Action;
import de.di.notify.gui.Config.Property;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.text.JTextComponent;

/**
 *
 * @author E. Rahman
 */
public class GeneralTab extends javax.swing.JPanel implements ConfigTab {

    public static Config config = null;
    private boolean setup = false;
    private String title = "General";
    private File lastDirectory = null;
    private static ResourceBundle bundle;
    private org.jdesktop.application.ResourceMap resourceMap;
    private static String basicNewInstall = "FALSE";
    private static String applicationPollTime = "";
    private static String agentWorkflowsPerRun = "";
    private static String directoriesLogging = "";
    private static String directoriesMailque = "";
    private static String indexServerPassword = "";
    private static String indexServerUser = "";
    private static String indexServer = "";
    private static String eloGroupName = "";
    private Properties oldProperties = new Properties();
    private Properties actualProperties = new Properties();

    /**
     * Creates new form GeneralTab
     */
    public GeneralTab() {        
        try {
            bundle = ResourceBundle.getBundle("notify/de/di/notify/gui/resources/GeneralTab");                        
        } catch (Exception ex) {
            bundle = ResourceBundle.getBundle("contractmanager/de/di/notify/gui/resources/GeneralTab");
        }
        try {            
            oldProperties.load(new FileInputStream("../conf/config.properties"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "config.properties can not be found");
        }
        resourceMap = org.jdesktop.application.Application.getInstance(de.di.notify.gui.ConfigApp.class).getContext().getResourceMap(GeneralTab.class);
        title = resourceMap.getString("tabTitle.text");
        initComponents();

        createLogLevelCombobox();
        initComponentsCustom();
    }
    //[790, 560]

    private void initPanelStatus() {
        jPanelStatus.setLayout(null);
        jPanelStatus.setBounds(10, 10, 770, 100);
        jPanelStatus.setBorder(BorderFactory.createTitledBorder(bundle.getString("jPanelStatus.text")));
        serviceLabel.setText(bundle.getString("serviceLabel.text"));
        serviceLabel.setBounds(20, 20, 150, 20);
        statusLabel.setText(bundle.getString("statusLabel.text"));
        statusLabel.setBounds(20, 50, 150, 20);
        jButtonStatus.setBounds(190, 50, 100, 20);
        jButtonStatus.setText(bundle.getString("jButtonStatus.text"));
        jButtonActivate.setBounds(190, 20, 100, 20);
        jButtonActivate.setText(bundle.getString("jButtonActivate.text"));

    }

    private void initPanelDirectories() {
        jPanelDirectories.setLayout(null);
        jPanelDirectories.setBounds(10, 120, 770, 100);
        jPanelDirectories.setBorder(BorderFactory.createTitledBorder(bundle.getString("jPanelDirectories.text")));
        jLabelLoggingDirectory.setText(bundle.getString("jLabelLoggingDirectory.text"));
        jLabelLoggingDirectory.setBounds(20, 20, 150, 20);
        jTextFieldLoggingDir.setBounds(190, 20, 300, 20);
        jButtonSelectLogDir.setText(bundle.getString("jButtonSelectLogDir.text"));
        jButtonSelectLogDir.setBounds(500, 20, 30, 20);
        jLabelLogLevel.setText(bundle.getString("jLabelLogLevel.text"));
        jLabelLogLevel.setBounds(540, 20, 80, 20);
        jComboBoxLogLevels.setBounds(630, 20, 80, 20);

        jLabelMailqueueDir.setText(bundle.getString("jLabelMailqueueDir.text"));
        jLabelMailqueueDir.setBounds(20, 50, 150, 20);
        jTextFieldMailqueDir.setBounds(190, 50, 300, 20);
        jButtonSelectMailqueDir.setText(bundle.getString("jButtonSelectMailqueDir.text"));
        jButtonSelectMailqueDir.setBounds(500, 50, 30, 20);
    }

    private void initPanelIx() {
        jPanelIx.setLayout(null);
        jPanelIx.setBorder(BorderFactory.createTitledBorder(bundle.getString("jPanelIx.text")));
        jPanelIx.setBounds(10, 230, 770, 130);
        jLabelIxUrl.setText(bundle.getString("jLabelIxUrl.text"));
        jLabelUserName.setText(bundle.getString("jLabelUserName.text"));
        jLabelPassword.setText(bundle.getString("jLabelPassword.text"));
        jLabelIxUrl.setBounds(20, 20, 150, 20);
        jLabelUserName.setBounds(20, 50, 150, 20);
        jLabelPassword.setBounds(20, 80, 150, 20);
        jTextFieldIxURL.setBounds(190, 20, 300, 20);
        jTextFieldUserName.setBounds(190, 50, 300, 20);
        jPasswordField.setBounds(190, 80, 300, 20);
    }

    private void initPanelWork() {
        jPanelWork.setLayout(null);
        jPanelWork.setBorder(BorderFactory.createTitledBorder(bundle.getString("jPanelWork.text")));
        jPanelWork.setBounds(10, 370, 770, 130);
        jLabelGroupName.setText(bundle.getString("jLabelGroupName.text"));
        jLabelUrlEloClient.setText(bundle.getString("jLabelUrlEloClient.text"));
        jLabelUrlWebClient.setText(bundle.getString("jLabelUrlWebClient.text"));
        jLabelGroupName.setBounds(20, 20, 150, 20);
        jLabelUrlEloClient.setBounds(20, 50, 150, 20);
        jLabelUrlWebClient.setBounds(20, 80, 150, 20);
        jTextFieldELOGroup.setBounds(190, 20, 300, 20);
        jCheckBoxInsertDocURL.setBounds(190, 50, 30, 20);
        jCheckBoxInsertWebclientURL.setBounds(190, 80, 30, 20);
        jTextFieldAppWEBURL.setBounds(230, 80, 350, 20);
    }

    private void initComponentsCustom() {
        this.setLayout(null);
        initPanelStatus();
        initPanelDirectories();
        initPanelIx();
        initPanelWork();
    }

    @SuppressWarnings("unchecked")
	//<editor-fold defaultstate = "collapsed" desc = "Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelStatus = new javax.swing.JPanel();
        serviceLabel = new javax.swing.JLabel();
        jButtonActivate = new javax.swing.JToggleButton();
        statusLabel = new javax.swing.JLabel();
        jButtonStatus = new javax.swing.JButton();
        jPanelDirectories = new javax.swing.JPanel();
        jLabelLogLevel = new javax.swing.JLabel();
        jLabelLoggingDirectory = new javax.swing.JLabel();
        jTextFieldLoggingDir = new javax.swing.JTextField();
        jButtonSelectLogDir = new javax.swing.JButton();
        jComboBoxLogLevels = new javax.swing.JComboBox();
        jLabelMailqueueDir = new javax.swing.JLabel();
        jButtonSelectMailqueDir = new javax.swing.JButton();
        jTextFieldMailqueDir = new javax.swing.JTextField();
        jPanelIx = new javax.swing.JPanel();
        jLabelIxUrl = new javax.swing.JLabel();
        jLabelUserName = new javax.swing.JLabel();
        jLabelPassword = new javax.swing.JLabel();
        jTextFieldIxURL = new javax.swing.JTextField();
        jTextFieldUserName = new javax.swing.JTextField();
        jPasswordField = new javax.swing.JPasswordField();
        jPanelWork = new javax.swing.JPanel();
        jLabelGroupName = new javax.swing.JLabel();
        jTextFieldELOGroup = new javax.swing.JTextField();
        jLabelUrlEloClient = new javax.swing.JLabel();
        jLabelUrlWebClient = new javax.swing.JLabel();
        jCheckBoxInsertDocURL = new javax.swing.JCheckBox();
        jCheckBoxInsertWebclientURL = new javax.swing.JCheckBox();
        jTextFieldAppWEBURL = new javax.swing.JTextField();

        setName("Form"); // NOI18N

        jPanelStatus.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanelStatus.setName("jPanelStatus"); // NOI18N
        jPanelStatus.setRequestFocusEnabled(false);
        jPanelStatus.setVerifyInputWhenFocusTarget(false);

        serviceLabel.setName("serviceLabel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(GeneralTab.class, this);
        jButtonActivate.setAction(actionMap.get("toggleService")); // NOI18N
        jButtonActivate.setName("jButtonActivate"); // NOI18N

        statusLabel.setName("statusLabel"); // NOI18N

        jButtonStatus.setAction(actionMap.get("showStatus")); // NOI18N
        jButtonStatus.setName("jButtonStatus"); // NOI18N

        javax.swing.GroupLayout jPanelStatusLayout = new javax.swing.GroupLayout(jPanelStatus);
        jPanelStatus.setLayout(jPanelStatusLayout);
        jPanelStatusLayout.setHorizontalGroup(
            jPanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(serviceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonActivate, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(jButtonStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanelStatusLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonActivate, jButtonStatus});

        jPanelStatusLayout.setVerticalGroup(
            jPanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serviceLabel)
                    .addComponent(jButtonActivate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusLabel)
                    .addComponent(jButtonStatus))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanelDirectories.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanelDirectories.setName("jPanelDirectories"); // NOI18N
        jPanelDirectories.setRequestFocusEnabled(false);
        jPanelDirectories.setVerifyInputWhenFocusTarget(false);

        jLabelLogLevel.setName("jLabelLogLevel"); // NOI18N

        jLabelLoggingDirectory.setName("jLabelLoggingDirectory"); // NOI18N

        jTextFieldLoggingDir.setText(oldProperties.getProperty("Directories.Logging"));
        jTextFieldLoggingDir.setName("jTextFieldLoggingDir"); // NOI18N
        jTextFieldLoggingDir.setVerifyInputWhenFocusTarget(false);
        jTextFieldLoggingDir.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldLoggingDirCaretUpdate(evt);
            }
        });

        jButtonSelectLogDir.setName("jButtonSelectLogDir"); // NOI18N
        jButtonSelectLogDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectLogDirActionPerformed(evt);
            }
        });

        jComboBoxLogLevels.setName("jComboBoxLogLevels"); // NOI18N
        jComboBoxLogLevels.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxLogLevelsItemStateChanged(evt);
            }
        });

        jLabelMailqueueDir.setName("jLabelMailqueueDir"); // NOI18N

        jButtonSelectMailqueDir.setName("jButtonSelectMailqueDir"); // NOI18N
        jButtonSelectMailqueDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectMailqueDirActionPerformed(evt);
            }
        });

        jTextFieldMailqueDir.setText(oldProperties.getProperty("Directories.MailQueue"));
        jTextFieldMailqueDir.setName("jTextFieldMailqueDir"); // NOI18N
        jTextFieldMailqueDir.setVerifyInputWhenFocusTarget(false);
        jTextFieldMailqueDir.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldMailqueDirCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanelDirectoriesLayout = new javax.swing.GroupLayout(jPanelDirectories);
        jPanelDirectories.setLayout(jPanelDirectoriesLayout);
        jPanelDirectoriesLayout.setHorizontalGroup(
            jPanelDirectoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDirectoriesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDirectoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLoggingDirectory, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addComponent(jLabelMailqueueDir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDirectoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldLoggingDir, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMailqueDir, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDirectoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDirectoriesLayout.createSequentialGroup()
                        .addComponent(jButtonSelectLogDir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelLogLevel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxLogLevels, 0, 101, Short.MAX_VALUE)
                        .addContainerGap(336, Short.MAX_VALUE))
                    .addGroup(jPanelDirectoriesLayout.createSequentialGroup()
                        .addComponent(jButtonSelectMailqueDir)
                        .addContainerGap())))
        );
        jPanelDirectoriesLayout.setVerticalGroup(
            jPanelDirectoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDirectoriesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDirectoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelDirectoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelLogLevel)
                        .addComponent(jComboBoxLogLevels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldLoggingDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonSelectLogDir))
                    .addComponent(jLabelLoggingDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDirectoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMailqueueDir, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMailqueDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSelectMailqueDir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelIx.setBorder(javax.swing.BorderFactory.createTitledBorder("Access data"));
        jPanelIx.setName("jPanelIx"); // NOI18N
        jPanelIx.setRequestFocusEnabled(false);
        jPanelIx.setVerifyInputWhenFocusTarget(false);

        jLabelIxUrl.setName("jLabelIxUrl"); // NOI18N

        jLabelUserName.setName("jLabelUserName"); // NOI18N

        jLabelPassword.setName("jLabelPassword"); // NOI18N

        jTextFieldIxURL.setText(oldProperties.getProperty("IndexServer.URL"));
        jTextFieldIxURL.setName("jTextFieldIxURL"); // NOI18N
        jTextFieldIxURL.setVerifyInputWhenFocusTarget(false);
        jTextFieldIxURL.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldIxURLCaretUpdate(evt);
            }
        });

        jTextFieldUserName.setText(oldProperties.getProperty("IndexServer.User"));
        jTextFieldUserName.setName("jTextFieldUserName"); // NOI18N
        jTextFieldUserName.setVerifyInputWhenFocusTarget(false);
        jTextFieldUserName.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldUserNameCaretUpdate(evt);
            }
        });

        jPasswordField.setName("jPasswordField"); // NOI18N
        jPasswordField.setVerifyInputWhenFocusTarget(false);
        jPasswordField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jPasswordFieldCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanelIxLayout = new javax.swing.GroupLayout(jPanelIx);
        jPanelIx.setLayout(jPanelIxLayout);
        jPanelIxLayout.setHorizontalGroup(
            jPanelIxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelIxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelIxUrl, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(jLabelPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                    .addComponent(jLabelUserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelIxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPasswordField)
                    .addComponent(jTextFieldUserName)
                    .addComponent(jTextFieldIxURL, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
                .addContainerGap(517, Short.MAX_VALUE))
        );
        jPanelIxLayout.setVerticalGroup(
            jPanelIxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelIxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelIxUrl)
                    .addComponent(jTextFieldIxURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelIxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUserName)
                    .addComponent(jTextFieldUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelIxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPassword)
                    .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelWork.setBorder(javax.swing.BorderFactory.createTitledBorder("Work"));
        jPanelWork.setName("jPanelWork"); // NOI18N
        jPanelWork.setRequestFocusEnabled(false);
        jPanelWork.setVerifyInputWhenFocusTarget(false);

        jLabelGroupName.setName("jLabelGroupName"); // NOI18N

        jTextFieldELOGroup.setText(oldProperties.getProperty("Application.Groupname",""));
        jTextFieldELOGroup.setName("jTextFieldELOGroup"); // NOI18N
        jTextFieldELOGroup.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldELOGroupCaretUpdate(evt);
            }
        });

        jLabelUrlEloClient.setName("jLabelUrlEloClient"); // NOI18N

        jLabelUrlWebClient.setName("jLabelUrlWebClient"); // NOI18N

        jCheckBoxInsertDocURL.setSelected(oldProperties.getProperty("Application.InsertJavaclientURL", "").equalsIgnoreCase("TRUE"));
        jCheckBoxInsertDocURL.setName("jCheckBoxInsertDocURL"); // NOI18N
        jCheckBoxInsertDocURL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxInsertDocURLActionPerformed(evt);
            }
        });

        jCheckBoxInsertWebclientURL.setSelected(oldProperties.getProperty("Application.InsertWebclientURL","").equalsIgnoreCase("TRUE"));
        jCheckBoxInsertWebclientURL.setName("jCheckBoxInsertWebclientURL"); // NOI18N
        jCheckBoxInsertWebclientURL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxInsertWebclientURLActionPerformed(evt);
            }
        });

        jTextFieldAppWEBURL.setText(oldProperties.getProperty("Application.WebclientURL",""));
        jTextFieldAppWEBURL.setName("jTextFieldAppWEBURL"); // NOI18N
        jTextFieldAppWEBURL.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldAppWEBURLCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanelWorkLayout = new javax.swing.GroupLayout(jPanelWork);
        jPanelWork.setLayout(jPanelWorkLayout);
        jPanelWorkLayout.setHorizontalGroup(
            jPanelWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWorkLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelUrlWebClient, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addComponent(jLabelUrlEloClient, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addComponent(jLabelGroupName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxInsertDocURL)
                    .addGroup(jPanelWorkLayout.createSequentialGroup()
                        .addComponent(jCheckBoxInsertWebclientURL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldAppWEBURL, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldELOGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(390, 390, 390))
        );
        jPanelWorkLayout.setVerticalGroup(
            jPanelWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWorkLayout.createSequentialGroup()
                .addGroup(jPanelWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelGroupName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldELOGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelWorkLayout.createSequentialGroup()
                        .addGroup(jPanelWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelUrlEloClient, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBoxInsertDocURL))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelUrlWebClient, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                            .addComponent(jCheckBoxInsertWebclientURL)))
                    .addComponent(jTextFieldAppWEBURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelWork, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanelIx, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanelDirectories, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelDirectories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelIx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelWork, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(276, 276, 276))
        );
    }//<editor-fold>//GEN-END:initComponents
	
    private void jButtonSelectLogDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectLogDirActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(jPanelIx);
        File f;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();
            directoriesLogging = f.getPath();
            jTextFieldLoggingDir.setText(f.getPath());
            jTextFieldLoggingDir.setName(f.getPath());
        }
    }//GEN-LAST:event_jButtonSelectLogDirActionPerformed

    private void jTextFieldIxURLCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldIxURLCaretUpdate
        if (setup) {
            return;
        }
        indexServer = jTextFieldIxURL.getText();
        config.setProperty(Config.Property.IndexServerURL, jTextFieldIxURL.getText());
    }//GEN-LAST:event_jTextFieldIxURLCaretUpdate

    private void jTextFieldLoggingDirCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldLoggingDirCaretUpdate
        if (setup) {
            return;
        }
        directoriesLogging = jTextFieldLoggingDir.getText();
        config.setProperty(Config.Property.DirectoriesLogging, jTextFieldLoggingDir.getText());
    }//GEN-LAST:event_jTextFieldLoggingDirCaretUpdate

    private void jTextFieldUserNameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldUserNameCaretUpdate
        if (setup) {
            return;
        }
        indexServerUser = jTextFieldUserName.getText();
        config.setProperty(Config.Property.IndexServerUser, jTextFieldUserName.getText());
    }//GEN-LAST:event_jTextFieldUserNameCaretUpdate

    private void jPasswordFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jPasswordFieldCaretUpdate
        if (setup) {
            return;
        }
        // Encode +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++   
//        try {
//            de.elo.utils.sec.DesEncryption des = new de.elo.utils.sec.DesEncryption();
//            String pwdEncrypted = des.encrypt(String.copyValueOf(jPasswordField.getPassword()));
//            config.setProperty(Config.Property.IndexServerPassword, pwdEncrypted);
//        } catch(Exception ex) {
//            
//        }
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++SL 17.03.2020
        
        indexServerPassword = String.copyValueOf(jPasswordField.getPassword());
        config.setProperty(Config.Property.IndexServerPassword, String.copyValueOf(jPasswordField.getPassword()));
    }//GEN-LAST:event_jPasswordFieldCaretUpdate

    private void jComboBoxLogLevelsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxLogLevelsItemStateChanged
        ConfigView.saveButton.setEnabled(true);
    }//GEN-LAST:event_jComboBoxLogLevelsItemStateChanged

private void jButtonSelectMailqueDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectMailqueDirActionPerformed
    JFileChooser fc = new JFileChooser();
    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int returnVal = fc.showOpenDialog(jPanelIx);
    File f;
    if (returnVal == JFileChooser.APPROVE_OPTION) {
        f = fc.getSelectedFile();
        directoriesMailque = f.getPath();
        jTextFieldMailqueDir.setText(f.getPath());
        jTextFieldMailqueDir.setName(f.getPath());
    }
}//GEN-LAST:event_jButtonSelectMailqueDirActionPerformed

private void jTextFieldMailqueDirCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldMailqueDirCaretUpdate
    if (setup) {
        return;
    }
    directoriesMailque = jTextFieldMailqueDir.getText();
    config.setProperty(Config.Property.DirectoriesMailQueue, jTextFieldLoggingDir.getText());
}//GEN-LAST:event_jTextFieldMailqueDirCaretUpdate

private void jTextFieldELOGroupCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldELOGroupCaretUpdate
    if (setup) {
        return;
    }
    eloGroupName = jTextFieldELOGroup.getText();
    config.setProperty(Config.Property.ApplicationGroupname, jTextFieldELOGroup.getText());
}//GEN-LAST:event_jTextFieldELOGroupCaretUpdate

private void jCheckBoxInsertDocURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxInsertDocURLActionPerformed
    if (setup) {
        return;
    }
    if (jCheckBoxInsertDocURL.isSelected()) {
        config.setProperty("Application.InsertDocURL", "TRUE");
    } else {
        config.setProperty("Application.InsertDocURL", "FALSE");
    }
}//GEN-LAST:event_jCheckBoxInsertDocURLActionPerformed

private void jCheckBoxInsertWebclientURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxInsertWebclientURLActionPerformed
    if (setup) {
        return;
    }
    if (jCheckBoxInsertWebclientURL.isSelected()) {
        config.setProperty("Application.InsertWebclientURL", "TRUE");
    } else {
        config.setProperty("Application.InsertWebclientURL", "FALSE");
    }
}//GEN-LAST:event_jCheckBoxInsertWebclientURLActionPerformed

private void jTextFieldAppWEBURLCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldAppWEBURLCaretUpdate
    if (setup) {
        return;
    }
    config.setProperty(Config.Property.ApplicationGroupname, jTextFieldAppWEBURL.getText());

}//GEN-LAST:event_jTextFieldAppWEBURLCaretUpdate
    //Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton jButtonActivate;
    private javax.swing.JButton jButtonSelectLogDir;
    private javax.swing.JButton jButtonSelectMailqueDir;
    private javax.swing.JButton jButtonStatus;
    private javax.swing.JCheckBox jCheckBoxInsertDocURL;
    private javax.swing.JCheckBox jCheckBoxInsertWebclientURL;
    private javax.swing.JComboBox jComboBoxLogLevels;
    private javax.swing.JLabel jLabelGroupName;
    private javax.swing.JLabel jLabelIxUrl;
    private javax.swing.JLabel jLabelLogLevel;
    private javax.swing.JLabel jLabelLoggingDirectory;
    private javax.swing.JLabel jLabelMailqueueDir;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelUrlEloClient;
    private javax.swing.JLabel jLabelUrlWebClient;
    private javax.swing.JLabel jLabelUserName;
    private javax.swing.JPanel jPanelDirectories;
    private javax.swing.JPanel jPanelIx;
    private javax.swing.JPanel jPanelStatus;
    private javax.swing.JPanel jPanelWork;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JTextField jTextFieldAppWEBURL;
    private javax.swing.JTextField jTextFieldELOGroup;
    private javax.swing.JTextField jTextFieldIxURL;
    private javax.swing.JTextField jTextFieldLoggingDir;
    private javax.swing.JTextField jTextFieldMailqueDir;
    private javax.swing.JTextField jTextFieldUserName;
    private javax.swing.JLabel serviceLabel;
    private javax.swing.JLabel statusLabel;
    //End of variables declaration//GEN-END:variables

    @Override
    public JPanel getJPanel() {
        return this;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setConfig(Config c) {
        config = c;
        setup = true;

        int queueSize = 0;
        try {
            queueSize = Integer.parseInt(config.getProperty(
                    Config.Property.BasicQueueSize, "30"));
        } catch (Exception e) {
        }
        boolean newInstall = Boolean.parseBoolean(
                config.getProperty(Config.Property.BasicNewInstall, "TRUE"));

        jButtonActivate.setSelected(!newInstall);
        if (newInstall) {
            jButtonActivate.setText(resourceMap.getString("toggleService.Action.text"));
        } else {
            jButtonActivate.setText(resourceMap.getString("toggleService.Action.Inactive.text"));
        }
        int i = 10485760;
        try {
            i = Integer.parseInt(config.getProperty(
                    Property.BasicMaxRecoveryLogSize));
        } catch (Exception e) {
        }
        i = i / (1024 * 1024);
        setup = false;
        jPasswordField.setText("**********");
    }

    private File getSelectedDirectory(String filename) {
        File f = null;

        if (filename != null) {
            f = new File(filename);
        }
        JFileChooser fileChooser = new JFileChooser();

        if (f != null && f.exists()) {
            fileChooser.setCurrentDirectory(f);
        } else if (lastDirectory != null) {
            fileChooser.setCurrentDirectory(lastDirectory);
        }
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    @Action
    public void toggleService() {
        boolean newInstall = !jButtonActivate.isSelected();
        if (newInstall) {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    resourceMap.getString("toggleService.Deactivate.Confirm.text"),
                    resourceMap.getString("toggleService.Deactivate.Confirm.title"),
                    JOptionPane.WARNING_MESSAGE);

            if (result != JOptionPane.YES_OPTION) {
                basicNewInstall = "FALSE";
                jButtonActivate.setSelected(true); //reset state
                return;
            }
            basicNewInstall = "TRUE";
            jButtonActivate.setText(resourceMap.getString("toggleService.Action.text"));
        } else {
            basicNewInstall = "FALSE";
            jButtonActivate.setText(resourceMap.getString("toggleService.Action.Inactive.text"));
        }
        ConfigView.saveButton.setEnabled(true);
    }

    @Action
    public void showStatus() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("../conf/config.properties"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "config.properties can not be found");
        }
        String uri = prop.getProperty("Basic.TomcatURL") + prop.getProperty("Basic.ServiceName") + "/";
        java.net.URI serviceURI;

        try {
            serviceURI = new java.net.URI(uri);
            java.awt.Desktop.getDesktop().browse(serviceURI);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    resourceMap.getString("showStatus.Error.text"),
                    resourceMap.getString("showStatus.Error.title"),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void writeConfigProperties() {
        actualProperties.setProperty("Basic.LogLevel", jComboBoxLogLevels.getSelectedItem().toString());
        actualProperties.setProperty("Basic.NewInstall", basicNewInstall);
        if (oldProperties.getProperty("Basic.MaxRecoveryLogSize") != null) {
            actualProperties.setProperty("Basic.MaxRecoveryLogSize", oldProperties.getProperty("Basic.MaxRecoveryLogSize"));
        } else {
            actualProperties.setProperty("Basic.MaxRecoveryLogSize", "10485760");
        }
        if (oldProperties.getProperty("Basic.LogPattern") != null) {
            actualProperties.setProperty("Basic.LogPattern", oldProperties.getProperty("Basic.LogPattern"));
        } else {
            actualProperties.setProperty("Basic.LogPattern", "%d{dd.MM.yyyy HH:mm:ss} %-5p [%t]: %m%n");
        }
        if (oldProperties.getProperty("Basic.ServiceName") != null) {
            actualProperties.setProperty("Basic.ServiceName", oldProperties.getProperty("Basic.ServiceName"));
        } else {
            actualProperties.setProperty("Basic.ServiceName", "NOTIFYagent");
        }
        if (oldProperties.getProperty("Basic.TomcatURL") != null) {
            actualProperties.setProperty("Basic.TomcatURL", oldProperties.getProperty("Basic.TomcatURL"));
        } else {
            actualProperties.setProperty("Basic.TomcatURL", "http://localhost:8080/");
        }
        if (directoriesLogging.isEmpty()) {
            actualProperties.setProperty("Directories.Logging", jTextFieldLoggingDir.getText());
        } else {
            actualProperties.setProperty("Directories.Logging", directoriesLogging);
        }
        if (directoriesMailque.isEmpty()) {
            actualProperties.setProperty("Directories.MailQueue", jTextFieldMailqueDir.getText());
        } else {
            actualProperties.setProperty("Directories.MailQueue", directoriesMailque);
        }
        if (indexServerPassword.equals("**********")) {
            if (oldProperties.getProperty("IndexServer.Password") == null) {
                actualProperties.setProperty("IndexServer.Password", "");
            } else {
                actualProperties.setProperty("IndexServer.Password", oldProperties.getProperty("IndexServer.Password"));
            }
        } else {
            actualProperties.setProperty("IndexServer.Password", indexServerPassword);
        }
        if (indexServerUser.isEmpty()) {
            actualProperties.setProperty("IndexServer.User", jTextFieldUserName.getText());
        } else {
            actualProperties.setProperty("IndexServer.User", indexServerUser);
        }
        if (indexServer.isEmpty()) {
            actualProperties.setProperty("IndexServer.URL", jTextFieldIxURL.getText());
        } else {
            actualProperties.setProperty("IndexServer.URL", indexServer);
        }
//        if (applicationPollTime.isEmpty()) {
//            actualProperties.setProperty("Application.PollTime", jTextField2.getText() + "000");
//        } else {
//            actualProperties.setProperty("Application.PollTime", applicationPollTime + "000");
//        }
//        if (agentWorkflowsPerRun.isEmpty()) {
//            actualProperties.setProperty("Agent.workflowsPerRun", jTextField3.getText());
//        } else {
//            actualProperties.setProperty("Agent.workflowsPerRun", agentWorkflowsPerRun);
//        }
        actualProperties.setProperty("Application.Groupname", jTextFieldELOGroup.getText());
        actualProperties.setProperty("Application.InsertJavaclientURL", jCheckBoxInsertDocURL.isSelected() ? "TRUE" : "FALSE");
        actualProperties.setProperty("Application.InsertWebclientURL", jCheckBoxInsertWebclientURL.isSelected() ? "TRUE" : "FALSE");
        actualProperties.setProperty("Application.WebclientURL", jTextFieldAppWEBURL.getText());
        actualProperties.setProperty("MailQueue.SignalFileExtension", oldProperties.getProperty("MailQueue.SignalFileExtension", ".sig"));
        try {
            actualProperties.store(new FileOutputStream("../conf/config.properties"), null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "can not write in config.properties" + ex.getMessage());
        }
    }

    public String getPollTime() {
        if (oldProperties.containsKey("Application.PollTime") && !oldProperties.getProperty("Application.PollTime").isEmpty()) {
            return oldProperties.getProperty("Application.PollTime").substring(0, oldProperties.getProperty("Application.PollTime").length() - 3);
        } else {
            return "";
        }
    }

    private void createLogLevelCombobox() {
        jComboBoxLogLevels.addItem((Object) "ALL");
//        jComboBox1.addItem((Object) "TRACE");
        jComboBoxLogLevels.addItem((Object) "DEBUG");
        jComboBoxLogLevels.addItem((Object) "INFO");
        jComboBoxLogLevels.addItem((Object) "WARN");
        jComboBoxLogLevels.addItem((Object) "ERROR");
//        jComboBox1.addItem((Object) "FATAL");
//        jComboBox1.addItem((Object) "OFF");
        if (oldProperties.getProperty("Basic.LogLevel") == null) {
            jComboBoxLogLevels.setSelectedItem((Object) "ALL");
        } else {
            jComboBoxLogLevels.setSelectedItem((Object) oldProperties.getProperty("Basic.LogLevel"));
        }
    }

    public class KeyAdapterNumbersOnly extends KeyAdapter {

        private String allowedRegex = "[^0-9]";

        public void keyReleased(KeyEvent e) {
            String curText = ((JTextComponent) e.getSource()).getText();
            curText = curText.replaceAll(allowedRegex, "");
            ((JTextComponent) e.getSource()).setText(curText);
        }
    }
}
