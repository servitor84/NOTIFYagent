package de.di.notify.gui;

import de.di.notify.gui.Config.Property;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The application's main frame.
 */
public class ConfigView extends FrameView implements ConfigChangeListener {

    private String[] tabs = null;
    private Config config = null;
    private boolean tabsActive = false;
    private java.util.ArrayList<ConfigTab> configTabs = null;
    private FilesTab filesTab;
    private GeneralTab generalTab;

    public ConfigView(SingleFrameApplication app) {
        super(app);
        config = ConfigApp.getApplication().getConfig();
        this.getFrame().setResizable(false);
        initComponents();
        String tabString = "de.di.notify.gui.GeneralTab,de.di.notify.gui.FilesTab,de.di.notify.gui.LicenseTab";
        if (tabString != null) {
            tabs = tabString.split(",");
        }
        addTabs();
        saveButton.setEnabled(false);
        config.addConfigChangeListener(this);
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = ConfigApp.getApplication().getMainFrame();
            aboutBox = new ConfigAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        ConfigApp.getApplication().show(aboutBox);
    }

    private void addTabs() {
        if (tabs == null) {
            return;
        }
        configTabs = new ArrayList<ConfigTab>(tabs.length);
        ImageIcon icon = this.getResourceMap().getImageIcon("tab.icon");

        for (String tabName : tabs) {
            try {
                ConfigTab tab = (ConfigTab) Class.forName(tabName).newInstance();
                if (tab instanceof de.di.notify.gui.FilesTab) {
//                    if (ConfigApp.client == null){
//                        continue;
//                    }
                    filesTab = (FilesTab) tab;
                }
                if (tab instanceof de.di.notify.gui.GeneralTab) {
                    generalTab = (GeneralTab) tab;
                }
                tab.setConfig(config);
                
                configTabs.add(tab);
                JPanel panel = tab.getJPanel();
                panel.setAutoscrolls(false);
                panel.setPreferredSize(jTabbedPane1.getPreferredSize());
                panel.setMinimumSize(jTabbedPane1.getPreferredSize());
                String title = "<html><font color=\"00307B\">" + tab.getTitle() + "</font></html>";
                this.jTabbedPane1.addTab(title, icon, panel);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        this.jTabbedPane1.setTabPlacement(JTabbedPane.TOP);
        tabsActive = true;
        jScrollPane1.getViewport().validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        saveButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        mainPanel.setAutoscrolls(true);
        mainPanel.setInheritsPopupMenu(true);
        mainPanel.setMaximumSize(new java.awt.Dimension(32757, 32757));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(795, 750));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(ConfigView.class);
        jLabel1.setIcon(resourceMap.getIcon("logo.icon")); // NOI18N
        jLabel1.setText(resourceMap.getString("logo.text")); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setName("jLabel1"); // NOI18N

        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setMinimumSize(null);
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(790, 560));
        jTabbedPane1.setName("jTabbedPane1"); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(790, 560));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });
        jTabbedPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabbedPane1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTabbedPane1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(ConfigView.class, this);
        saveButton.setAction(actionMap.get("saveAction")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        exitButton.setAction(actionMap.get("quit")); // NOI18N
        exitButton.setName("exitButton"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(saveButton)
                        .addGap(20, 20, 20)
                        .addComponent(exitButton)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(saveButton)
                    .addComponent(exitButton))
                .addGap(100, 100, 100))
        );

        setComponent(mainPanel);
    }// </editor-fold>//GEN-END:initComponents

private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
    if (!tabsActive) {
        return;
    }
    int i = jTabbedPane1.getSelectedIndex();
    if (i >= 0 && i < configTabs.size()) {
        configTabs.get(i).setConfig(config);
    }
}//GEN-LAST:event_jTabbedPane1StateChanged
// Rauskommentier, um ExpertTab zu entfernen
private void jTabbedPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabbedPane1KeyPressed
//    if (evt.isControlDown() && evt.isShiftDown() && evt.getKeyCode() == KeyEvent.VK_M) {
////        if (expertMode) {
//            removeExpertTab();
//            expertMode = false;
//        } else {
////            addExpertTab();
//            expertMode = true;
//        }
//    }
}//GEN-LAST:event_jTabbedPane1KeyPressed
// Rauskommentier, um ExpertTab zu entfernen
private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
//    if (evt.isControlDown() && evt.getButton() == MouseEvent.BUTTON1) {
//        if (expertMode) {
//            removeExpertTab();
//            expertMode = false;
//        } else {
////            addExpertTab();
//            expertMode = true;
//        }
//    }
}//GEN-LAST:event_jTabbedPane1MouseClicked

private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_saveButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel mainPanel;
    public static javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
    private JDialog aboutBox;

    @Override
    public void configChange(ConfigChangeEvent event) {
        saveButton.setEnabled(true);
    }

    @Action
    public void saveAction() {
        try {
            if (this.filesTab.save()) {
                this.generalTab.writeConfigProperties();
                if (this.filesTab.fieldSet) {
                    this.filesTab.showRestartMessage();
                }
                config.setProperty(Property.BasicNewInstall, "0");
                saveButton.setEnabled(false);
            }
        } catch (Exception e) {
            saveButton.setEnabled(false);
        }
    }
}
