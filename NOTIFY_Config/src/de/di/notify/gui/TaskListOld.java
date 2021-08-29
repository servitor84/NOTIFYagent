package de.di.notify.gui;

//import de.arivato.notify.gui.SwingUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * This screen is created for the TaskList profile.
 * @author stefan
 */
public class TaskListOld extends javax.swing.JPanel {

    Properties properties = new Properties();
    Properties newProperties = new Properties();
    private ResourceBundle bundle;

    public TaskListOld(Properties properties) {
        this.properties = properties;
        this.newProperties = properties;
        loadResources();
        initComponents();
        setProfileProperties(properties);
      
        
    }

    public final void enableSave() {
        for (String key : properties.stringPropertyNames()) {
            if (!properties.get(key).equals(newProperties.get(key))) {
                ConfigView.saveButton.setEnabled(true);
                return;
            }
        }
        ConfigView.saveButton.setEnabled(false);

    }

    public final void loadResources() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
        SwingUtilities.updateComponentTreeUI(this);
        Locale locale = Locale.getDefault();
                
        try {           
            bundle = ResourceBundle.getBundle("notify/de/di/notify/gui/resources/TaskList", locale);
        } catch (Exception ex) {
            bundle = ResourceBundle.getBundle("contractmanager/de/di/notify/gui/resources/TaskList", locale);
        }
        
        //bundle = ResourceBundle.getBundle("notify/de/di/notify/gui/resources/TaskList", locale);
    }

    public void save(Properties prop, String fileName) {
        try {
            newProperties.store(new FileOutputStream(fileName), null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "can not write in folder profiles" + ex.getMessage());
        }
    }

    public boolean validationOk() {
        return true;
    }

    private void setProfileProperties(Properties prop) {
        if (prop.containsKey("ProfileName")) {
            jTextFieldProfileName.setText(prop.getProperty("ProfileName"));
        }
        if (prop.containsKey("Action")) {
            jTextFieldProfileAction.setText(prop.getProperty("Action"));
        }
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
        DefaultListModel listModel = new DefaultListModel();
        String[] dayNames = symbols.getWeekdays();
        String[] dayIdsSaved = new String[]{};
        if (prop.containsKey("SendDays")) {
            dayIdsSaved = prop.getProperty("SendDays").split(";");
        }
        for (int i = 0; i < dayNames.length; i++) {
            listModel.add(i, dayNames[i]);
        }
        jListSendDays.setModel(listModel);
        int[] selectedIndices = new int[dayIdsSaved.length];
        for (int i = 0; i < dayIdsSaved.length; i++) {
            selectedIndices[i] = Integer.parseInt(dayIdsSaved[i]);
        }
        jListSendDays.setSelectedIndices(selectedIndices);
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        for (int i = 0; i < 24; i++) {
            comboBoxModel.addElement(String.valueOf(i));
        }
        jComboBoxSendHours.setModel(comboBoxModel);
        if (prop.containsKey("SendHour")) {
            jComboBoxSendHours.setSelectedIndex(Integer.parseInt(prop.getProperty("SendHour")));
        }
        if (prop.containsKey("SendImmediately") && prop.getProperty("SendImmediately").equalsIgnoreCase("TRUE")) {
            jCheckBoxSendImmediately.setSelected(true);
        }
        String priorityes = prop.getProperty("TaskList.priorities", "");
        if (priorityes.contains("A")) {
            jCheckBoxPriorityA.setSelected(true);
        }
        if (priorityes.contains("B")) {
            jCheckBoxPriorityB.setSelected(true);
        }
        if (priorityes.contains("C")) {
            jCheckBoxPriorityC.setSelected(true);
        }
        //TODO: add warning when it does not contain
        boolean contains = SwingUtils.setItems(jComboBoxMaskName, ConfigApp.client.getMasks(), prop.getProperty("TaskList.maskName", ""));
        jTextFieldTemplateLocation.setText(properties.getProperty("TaskList.messageTemplate"));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelProfileDesc = new javax.swing.JPanel();
        jLabelProfileName = new javax.swing.JLabel();
        jTextFieldProfileName = new javax.swing.JTextField();
        jLabelProfileAction = new javax.swing.JLabel();
        jTextFieldProfileAction = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabelSendDays = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListSendDays = new javax.swing.JList();
        jLabelSendHours = new javax.swing.JLabel();
        jComboBoxSendHours = new javax.swing.JComboBox();
        jLabelSendImmediately = new javax.swing.JLabel();
        jCheckBoxSendImmediately = new javax.swing.JCheckBox();
        jLabelTaskPriorityes = new javax.swing.JLabel();
        jCheckBoxPriorityA = new javax.swing.JCheckBox();
        jCheckBoxPriorityB = new javax.swing.JCheckBox();
        jCheckBoxPriorityC = new javax.swing.JCheckBox();
        jLabelMaskName = new javax.swing.JLabel();
        jComboBoxMaskName = new javax.swing.JComboBox();
        jLabelMaxWorkflows = new javax.swing.JLabel();
        jSpinnerMaxWorkflows = new javax.swing.JSpinner();
        jLabelTaskListFrom = new javax.swing.JLabel();
        jTextFieldEmailFrom = new javax.swing.JTextField();
        jLabelSubject = new javax.swing.JLabel();
        jTextFieldSubject = new javax.swing.JTextField();
        jLabelTemplate = new javax.swing.JLabel();
        jButtonSelectTemplate = new javax.swing.JButton();
        jTextFieldTemplateLocation = new javax.swing.JTextField();

        setToolTipText("");
        setMaximumSize(new java.awt.Dimension(630, 550));
        setMinimumSize(new java.awt.Dimension(630, 550));
        setPreferredSize(new java.awt.Dimension(630, 550));

        jPanelProfileDesc.setBorder(javax.swing.BorderFactory.createTitledBorder("Profile"));
        jPanelProfileDesc.setDoubleBuffered(false);
        jPanelProfileDesc.setEnabled(false);
        jPanelProfileDesc.setFocusable(false);
        jPanelProfileDesc.setName("jPanelProfileDesc"); // NOI18N

        jLabelProfileName.setText("Name");
        jLabelProfileName.setName("jLabelProfileName"); // NOI18N

        jTextFieldProfileName.setEditable(false);
        jTextFieldProfileName.setName("jTextFieldProfileName"); // NOI18N
        jTextFieldProfileName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldProfileNameFocusGained(evt);
            }
        });

        jLabelProfileAction.setText("Action");
        jLabelProfileAction.setName("jLabelProfileAction"); // NOI18N

        jTextFieldProfileAction.setEditable(false);
        jTextFieldProfileAction.setName("jTextFieldProfileAction"); // NOI18N

        javax.swing.GroupLayout jPanelProfileDescLayout = new javax.swing.GroupLayout(jPanelProfileDesc);
        jPanelProfileDesc.setLayout(jPanelProfileDescLayout);
        jPanelProfileDescLayout.setHorizontalGroup(
            jPanelProfileDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProfileDescLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelProfileDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelProfileDescLayout.createSequentialGroup()
                        .addComponent(jLabelProfileName)
                        .addGap(21, 21, 21)
                        .addComponent(jTextFieldProfileName, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelProfileDescLayout.createSequentialGroup()
                        .addComponent(jLabelProfileAction)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldProfileAction)))
                .addGap(65, 65, 65))
        );
        jPanelProfileDescLayout.setVerticalGroup(
            jPanelProfileDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProfileDescLayout.createSequentialGroup()
                .addGroup(jPanelProfileDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabelProfileName)
                    .addComponent(jTextFieldProfileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelProfileDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabelProfileAction)
                    .addComponent(jTextFieldProfileAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(132, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Profile properties"));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabelSendDays.setText(bundle.getString("TaskList.SendDays"));
        jLabelSendDays.setName("jLabelSendDays"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jListSendDays.setMaximumSize(new java.awt.Dimension(100, 200));
        jListSendDays.setMinimumSize(new java.awt.Dimension(100, 200));
        jListSendDays.setName("jListSendDays"); // NOI18N
        jListSendDays.setPreferredSize(new java.awt.Dimension(100, 200));
        jListSendDays.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListSendDaysValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListSendDays);

        jLabelSendHours.setText(bundle.getString("TaskList.SendHours"));
        jLabelSendHours.setName("jLabelSendHours"); // NOI18N

        jComboBoxSendHours.setName("jComboBoxSendHours"); // NOI18N
        jComboBoxSendHours.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSendHoursActionPerformed(evt);
            }
        });

        jLabelSendImmediately.setText(bundle.getString("TaskList.SendImmediately"));
        jLabelSendImmediately.setName("jLabelSendImmediately"); // NOI18N

        jCheckBoxSendImmediately.setName("jCheckBoxSendImmediately"); // NOI18N
        jCheckBoxSendImmediately.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxSendImmediatelyActionPerformed(evt);
            }
        });

        jLabelTaskPriorityes.setText(bundle.getString("TaskList.priorities"));
        jLabelTaskPriorityes.setName("jLabelTaskPriorityes"); // NOI18N

        jCheckBoxPriorityA.setText("A");
        jCheckBoxPriorityA.setName("jCheckBoxPriorityA"); // NOI18N
        jCheckBoxPriorityA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPriorityAActionPerformed(evt);
            }
        });

        jCheckBoxPriorityB.setText("B");
        jCheckBoxPriorityB.setName("jCheckBoxPriorityB"); // NOI18N
        jCheckBoxPriorityB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPriorityBActionPerformed(evt);
            }
        });

        jCheckBoxPriorityC.setText("C");
        jCheckBoxPriorityC.setName("jCheckBoxPriorityC"); // NOI18N
        jCheckBoxPriorityC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPriorityCActionPerformed(evt);
            }
        });

        jLabelMaskName.setText(bundle.getString("TaskList.maskName"));
        jLabelMaskName.setName("jLabelMaskName"); // NOI18N

        jComboBoxMaskName.setMaximumSize(new java.awt.Dimension(150, 20));
        jComboBoxMaskName.setMinimumSize(new java.awt.Dimension(150, 20));
        jComboBoxMaskName.setName("jComboBoxMaskName"); // NOI18N
        jComboBoxMaskName.setPreferredSize(new java.awt.Dimension(150, 20));
        jComboBoxMaskName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMaskNameActionPerformed(evt);
            }
        });

        jLabelMaxWorkflows.setText(bundle.getString("TaskList.maxWorkflows"));
        jLabelMaxWorkflows.setName("jLabelMaxWorkflows"); // NOI18N

        jSpinnerMaxWorkflows.setMaximumSize(new java.awt.Dimension(150, 20));
        jSpinnerMaxWorkflows.setMinimumSize(new java.awt.Dimension(150, 20));
        jSpinnerMaxWorkflows.setName("jSpinnerMaxWorkflows"); // NOI18N
        jSpinnerMaxWorkflows.setPreferredSize(new java.awt.Dimension(150, 20));
        jSpinnerMaxWorkflows.setValue(Integer.parseInt(properties.getProperty("TaskList.maxWorkflows")));
        jSpinnerMaxWorkflows.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerMaxWorkflowsStateChanged(evt);
            }
        });

        jLabelTaskListFrom.setText(bundle.getString("TaskList.from"));
        jLabelTaskListFrom.setName("jLabelTaskListFrom"); // NOI18N

        jTextFieldEmailFrom.setText(properties.getProperty("TaskList.from", ""));
        jTextFieldEmailFrom.setMaximumSize(new java.awt.Dimension(150, 20));
        jTextFieldEmailFrom.setMinimumSize(new java.awt.Dimension(150, 20));
        jTextFieldEmailFrom.setName("jTextFieldEmailFrom"); // NOI18N
        jTextFieldEmailFrom.setPreferredSize(new java.awt.Dimension(150, 20));

        jLabelSubject.setText(bundle.getString("TaskList.subject"));
        jLabelSubject.setName("jLabelSubject"); // NOI18N

        jTextFieldSubject.setText(properties.getProperty("TaskList.subject", ""));
        jTextFieldSubject.setMaximumSize(new java.awt.Dimension(150, 20));
        jTextFieldSubject.setMinimumSize(new java.awt.Dimension(150, 20));
        jTextFieldSubject.setName("jTextFieldSubject"); // NOI18N
        jTextFieldSubject.setPreferredSize(new java.awt.Dimension(150, 20));

        jLabelTemplate.setText(bundle.getString("TaskList.subject"));
        jLabelTemplate.setName("jLabelTemplate"); // NOI18N

        jButtonSelectTemplate.setText("Select");
        jButtonSelectTemplate.setName("jButtonSelectTemplate"); // NOI18N
        jButtonSelectTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectTemplateActionPerformed(evt);
            }
        });

        jTextFieldTemplateLocation.setName("jTextFieldTemplateLocation"); // NOI18N
        jTextFieldTemplateLocation.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldTemplateLocationFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelSubject, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTaskListFrom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabelMaxWorkflows, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelMaskName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelTaskPriorityes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelSendImmediately, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelSendDays, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabelTemplate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxSendImmediately)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBoxMaskName, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabelSendHours))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jComboBoxSendHours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextFieldSubject, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jCheckBoxPriorityA)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jCheckBoxPriorityB)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jCheckBoxPriorityC))
                        .addComponent(jTextFieldEmailFrom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSpinnerMaxWorkflows, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextFieldTemplateLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSelectTemplate)))
                .addGap(234, 234, 234))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSendDays, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabelSendHours, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxSendHours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelSendImmediately, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTaskPriorityes, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBoxPriorityA)
                            .addComponent(jCheckBoxPriorityB)
                            .addComponent(jCheckBoxPriorityC)))
                    .addComponent(jCheckBoxSendImmediately))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMaskName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxMaskName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMaxWorkflows, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerMaxWorkflows, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTaskListFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldEmailFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTemplateLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSelectTemplate))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelProfileDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanelProfileDesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(333, 333, 333))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldProfileNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldProfileNameFocusGained
    }//GEN-LAST:event_jTextFieldProfileNameFocusGained

private void jButtonSelectTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectTemplateActionPerformed
    GeneralTab.config.setProperty("", "");
    JFileChooser fc = new JFileChooser();
    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    int returnVal = fc.showOpenDialog(jPanel1);
    File f;
    if (returnVal == JFileChooser.APPROVE_OPTION) {
        f = fc.getSelectedFile();
        jTextFieldTemplateLocation.setText(f.getPath());
    }
}//GEN-LAST:event_jButtonSelectTemplateActionPerformed

private void jTextFieldTemplateLocationFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldTemplateLocationFocusGained
    GeneralTab.config.setProperty("", "");
}//GEN-LAST:event_jTextFieldTemplateLocationFocusGained

private void jListSendDaysValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListSendDaysValueChanged
    GeneralTab.config.setProperty("", "");
    int[] newValues = jListSendDays.getSelectedIndices();
    StringBuilder stringBuilder = new StringBuilder();
    for (int value : newValues) {
        stringBuilder.append(String.valueOf(value));
        stringBuilder.append(";");
    }
    newProperties.setProperty("SendDays", stringBuilder.substring(0, stringBuilder.length() - 1));
    enableSave();
}//GEN-LAST:event_jListSendDaysValueChanged

private void jComboBoxSendHoursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSendHoursActionPerformed
    GeneralTab.config.setProperty("", "");
    newProperties.setProperty("SendHours", (String) jComboBoxSendHours.getSelectedItem());
    enableSave();
}//GEN-LAST:event_jComboBoxSendHoursActionPerformed

private void jCheckBoxSendImmediatelyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxSendImmediatelyActionPerformed
    GeneralTab.config.setProperty("", "");
    newProperties.setProperty("SendHour", jCheckBoxSendImmediately.isSelected() ? "TRUE" : "FALSE");
    enableSave();
}//GEN-LAST:event_jCheckBoxSendImmediatelyActionPerformed

private void jCheckBoxPriorityAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPriorityAActionPerformed
    GeneralTab.config.setProperty("", "");
    if (!jCheckBoxPriorityA.isSelected()) {
        newProperties.setProperty("TaskList.priorities", newProperties.getProperty("TaskList.priorities").replace("A", ""));
    } else {
        newProperties.setProperty("TaskList.priorities", "A" + newProperties.getProperty("TaskList.priorities"));
    }
    enableSave();
}//GEN-LAST:event_jCheckBoxPriorityAActionPerformed

private void jCheckBoxPriorityBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPriorityBActionPerformed
    GeneralTab.config.setProperty("", "");
    if (!jCheckBoxPriorityA.isSelected()) {
        newProperties.setProperty("TaskList.priorities", newProperties.getProperty("TaskList.priorities").replace("B", ""));
    } else {
        String result = "B" + newProperties.getProperty("TaskList.priorities");
        char[] chars = result.toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);
        newProperties.setProperty("TaskList.priorities", sorted);
    }
    enableSave();
}//GEN-LAST:event_jCheckBoxPriorityBActionPerformed

private void jCheckBoxPriorityCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPriorityCActionPerformed
    GeneralTab.config.setProperty("", "");
    if (!jCheckBoxPriorityC.isSelected()) {
        newProperties.setProperty("TaskList.priorities", newProperties.getProperty("TaskList.priorities").replace("C", ""));
    } else {
        newProperties.setProperty("TaskList.priorities", newProperties.getProperty("TaskList.priorities") + "C");
    }
    enableSave();

}//GEN-LAST:event_jCheckBoxPriorityCActionPerformed

private void jComboBoxMaskNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMaskNameActionPerformed
    GeneralTab.config.setProperty("", "");
    newProperties.setProperty("TaskList.maskName", (String) jComboBoxMaskName.getSelectedItem());
    enableSave();
}//GEN-LAST:event_jComboBoxMaskNameActionPerformed

private void jSpinnerMaxWorkflowsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerMaxWorkflowsStateChanged
    GeneralTab.config.setProperty("", "");
    newProperties.setProperty("TaskList.maxWorkflows", (String) jSpinnerMaxWorkflows.getValue());
    enableSave();
}//GEN-LAST:event_jSpinnerMaxWorkflowsStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSelectTemplate;
    private javax.swing.JCheckBox jCheckBoxPriorityA;
    private javax.swing.JCheckBox jCheckBoxPriorityB;
    private javax.swing.JCheckBox jCheckBoxPriorityC;
    private javax.swing.JCheckBox jCheckBoxSendImmediately;
    private javax.swing.JComboBox jComboBoxMaskName;
    private javax.swing.JComboBox jComboBoxSendHours;
    private javax.swing.JLabel jLabelMaskName;
    private javax.swing.JLabel jLabelMaxWorkflows;
    private javax.swing.JLabel jLabelProfileAction;
    private javax.swing.JLabel jLabelProfileName;
    private javax.swing.JLabel jLabelSendDays;
    private javax.swing.JLabel jLabelSendHours;
    private javax.swing.JLabel jLabelSendImmediately;
    private javax.swing.JLabel jLabelSubject;
    private javax.swing.JLabel jLabelTaskListFrom;
    private javax.swing.JLabel jLabelTaskPriorityes;
    private javax.swing.JLabel jLabelTemplate;
    private javax.swing.JList jListSendDays;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelProfileDesc;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerMaxWorkflows;
    private javax.swing.JTextField jTextFieldEmailFrom;
    private javax.swing.JTextField jTextFieldProfileAction;
    private javax.swing.JTextField jTextFieldProfileName;
    private javax.swing.JTextField jTextFieldSubject;
    private javax.swing.JTextField jTextFieldTemplateLocation;
    // End of variables declaration//GEN-END:variables
}
