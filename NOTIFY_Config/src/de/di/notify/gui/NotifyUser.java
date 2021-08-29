/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.di.notify.gui;

import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import de.di.uigenerator.GuiGenerator;
import de.di.uigenerator.GuiTypeC;
import de.di.uigenerator.JPanelEnableSave;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

/**
 *
 * @author stefan
 */
public class NotifyUser extends JPanelEnableSave {

    /**
     * Urspruenglicher Code
     * @param oldProperties
     * @param fileName 
     */
    public NotifyUser(Properties oldProperties, final String fileName) {
        super(oldProperties, fileName);
        Locale locale = Locale.getDefault();
        
        File file = new File("notify/de/di/notify/gui/resources/NotifyUser");
        try {
            FileInputStream fis = new FileInputStream(file);
            bundle = ResourceBundle.getBundle("notify/de/di/notify/gui/resources/NotifyUser", locale);
        } catch (FileNotFoundException ex) {
            bundle = ResourceBundle.getBundle("contractmanager/de/di/notify/gui/resources/NotifyUser", locale);
        }
        
        //bundle = ResourceBundle.getBundle("notify/de/di/notify/gui/resources/NotifyUser", locale);
        LinkedHashMap<String, GuiTypeC> mapa = new LinkedHashMap<String, GuiTypeC>();
        for (String key : oldProperties.stringPropertyNames()) {
            mapa.put(key, GuiTypeC.TEXT);
        }
//        mapa.put("NotifyUser.maskName", GuiTypeC.MASK);
        mapa.put("Action", GuiTypeC.TEXT_READONLY);        
        mapa.put("NotifyUser.showSubstitute", GuiTypeC.BOOLEAN);
        mapa.put("SendImmediately", GuiTypeC.BOOLEAN);
//        mapa.put("NotifyUser.priorities", GuiTypeC.PRIORITIES);
        mapa.put("NotifyUser.maxWorkflows", GuiTypeC.INTEGER);
        mapa.put("SendHour", GuiTypeC.HOURS);        
        mapa.put("SendDays", GuiTypeC.DAYS);
        mapa.put("NotifyUser.messageTemplate", GuiTypeC.FILE);
        mapa.put("NotifyUser.template", GuiTypeC.TEMPLATE);
        mapa.put("NotifyUser.AfterHours", GuiTypeC.INTEGER);
        mapa.put("NotifyUser.Nodenames", GuiTypeC.MULTIPLE_NODENAME);  
        mapa.put("ProfileName", GuiTypeC.TEXT_MANDATORY);      

        //
        HashMap<String, String> mappedLines = new HashMap<String, String>();
        if (oldProperties.containsKey(oldProperties.getProperty("Action") + ".Nodenames")) {
            mappedLines.put(oldProperties.getProperty("Action") + ".Nodenames", oldProperties.getProperty("Action") + ".template");
        }       
        //
        
        this.setSaveButton(ConfigView.saveButton);
        setMaskLinesMapper(mappedLines);
        setTypes(mapa);
        setConstraints(FilesTab.profileNames);
        setIgnoredConstraint(FilesTab.currentProfile);
        GuiGenerator.eloClient = ConfigApp.client;
        try {
            draw();
        } catch (Exception ex) {
//            System.out.println(ex);
//            ex.printStackTrace();
        }
    }
    
//     private void initComboboxValues() {
//        List<String> values = new ArrayList<String>();
//        values.add(bundle.getString("Type.dayhour"));
//        values.add(bundle.getString("Type.interval"));
//        this.comboBoxes.put("Type", values);
//        HashMap<String, String> localTranslation = new HashMap<String, String>();
//        localTranslation.put(bundle.getString("Type.interval"), "1");
//        localTranslation.put(bundle.getString("Type.dayhour"), "0");
//        translations.put("Type", localTranslation);
//    }
//    
//    public NotifyUser(Properties oldProperties, final String fileName) {
//        super(oldProperties, fileName);
//
//        Locale locale = Locale.getDefault();
//        bundle = ResourceBundle.getBundle("de/arivato/notify/gui/resources/NotifyUser", locale);
//        HashMap<String, GuiTypeC> mapa = new HashMap<String, GuiTypeC>();
//        for (String key : oldProperties.stringPropertyNames()) {
//            mapa.put(key, GuiTypeC.TEXT);
//        }
//        this.newProperties.setProperty("Type", oldProperties.getProperty("Type", "0").equals("0") ? bundle.getString("Type.dayhour") : bundle.getString("Type.interval"));
//        this.oldProperties.setProperty("Type", oldProperties.getProperty("Type", "0").equals("0") ? bundle.getString("Type.dayhour") : bundle.getString("Type.interval"));
//        mapa.put(oldProperties.getProperty("Action") + ".maskName", GuiTypeC.MASK);
//        mapa.put(oldProperties.getProperty("Action") + ".showSubstitute", GuiTypeC.BOOLEAN);
//        mapa.put("SendImmediately", GuiTypeC.BOOLEAN);
//        mapa.put("SendHour", GuiTypeC.HOURS);
//        mapa.put("SendDays", GuiTypeC.DAYS);
//        mapa.put("Action", GuiTypeC.TEXT_READONLY);
//        mapa.put(oldProperties.getProperty("Action") + ".template", GuiTypeC.TEMPLATE);
//        mapa.put(oldProperties.getProperty("Action") + ".Date", GuiTypeC.MASKLINE);
//        mapa.put(oldProperties.getProperty("Action") + ".Users", GuiTypeC.MULTIPLE_MASKLINE);
//        mapa.put(oldProperties.getProperty("Action") + ".StatusField", GuiTypeC.MASKLINE);
//        mapa.put(oldProperties.getProperty("Action") + ".Exactname", GuiTypeC.BOOLEAN);
//        mapa.put(oldProperties.getProperty("Action") + ".Nodenames", GuiTypeC.MULTIPLE_NODENAME);
//        mapa.put("Interval", GuiTypeC.INTEGER);
//        mapa.put("Type", GuiTypeC.COMBOBOX);
//        initComboboxValues();
//        HashMap<String, String> mappedLines = new HashMap<String, String>();
//        if (oldProperties.containsKey(oldProperties.getProperty("Action") + ".Users")) {
//            mappedLines.put(oldProperties.getProperty("Action") + ".Users", oldProperties.getProperty("Action") + ".maskName");
//        }
//        if (oldProperties.containsKey(oldProperties.getProperty("Action") + ".StatusField")) {
//            mappedLines.put(oldProperties.getProperty("Action") + ".StatusField", oldProperties.getProperty("Action") + ".maskName");
//        }
//        if (oldProperties.containsKey(oldProperties.getProperty("Action") + ".Nodenames")) {
//            mappedLines.put(oldProperties.getProperty("Action") + ".Nodenames", oldProperties.getProperty("Action") + ".template");
//        }
//
//        mappedLines.put(oldProperties.getProperty("Action") + ".Date", oldProperties.getProperty("Action") + ".maskName");
//
//        mapa.put(oldProperties.getProperty("Action") + ".maskName", GuiTypeC.MASK);
//        mapa.put("ProfileName", GuiTypeC.TEXT_MANDATORY);
//        setConstraints(FilesTab.profileNames);
//        setIgnoredConstraint(FilesTab.currentProfile);
//        HashSet<String> blanks = new HashSet<String>();
//        blanks.add(oldProperties.getProperty("Action") + ".Date");
//        setAllowedBlanks(blanks);
//        this.setSaveButton(ConfigView.saveButton);
//        HashMap<String, Integer> eloTypesLocal = new HashMap<String, Integer>();
//        eloTypesLocal.put(oldProperties.getProperty("Action") + ".Date", 3004);
//        setEloTypes(eloTypesLocal);
//        setMaskLinesMapper(mappedLines);
//        setTypes(mapa);
//
//        if (!this.oldProperties.containsKey("Interval") || !this.newProperties.containsKey("Interval")) {
////            System.out.println("Adding interval");
//            this.oldProperties.setProperty("Interval", "5");
//            this.newProperties.setProperty("Interval", "5");
//        }
//
//        GuiGenerator.eloClient = ConfigApp.client;
//        try {
//            draw();
//        } catch (Exception ex) {
////            System.out.println(ex);
////            ex.printStackTrace();
//        }
//
//        if (translations.get("Type").get(oldProperties.getProperty("Type", "0")).equals("0")) {
//            spinners.get("Interval").setEnabled(false);
//            buttons.get("SendHour").setEnabled(true);
//            for (JCheckBox checkBox : days.get("SendDays")) {
//                checkBox.setEnabled(true);
//            }
//        } else {
//            spinners.get("Interval").setEnabled(true);
//            buttons.get("SendHour").setEnabled(false);
//            for (JCheckBox checkBox : days.get("SendDays")) {
//                checkBox.setEnabled(false);
//            }
//        }
//
//        final JComboBox typeCombobox = mainControls.get("Type");
//        typeCombobox.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                String value = (String) typeCombobox.getSelectedItem();
//                if (translations.get("Type").get(value).equals("0")) {
//                    spinners.get("Interval").setEnabled(false);
//                    buttons.get("SendHour").setEnabled(true);
//                    for (JCheckBox checkBox : days.get("SendDays")) {
//                        checkBox.setEnabled(true);
//                    }
//                } else {
//                    spinners.get("Interval").setEnabled(true);
//                    buttons.get("SendHour").setEnabled(false);
//                    for (JCheckBox checkBox : days.get("SendDays")) {
//                        checkBox.setEnabled(false);
//                    }
//                }
//            }
//        });
    }

