/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

/**
 *
 * @author stefan
 */
public class ForwardWF extends JPanelEnableSave {

    private void initComboboxValues() {
        List<String> values = new ArrayList<String>();
        values.add(bundle.getString("Type.dayhour"));
        values.add(bundle.getString("Type.interval"));
        this.comboBoxes.put("Type", values);
        HashMap<String, String> localTranslation = new HashMap<String, String>();
        localTranslation.put(bundle.getString("Type.interval"), "1");
        localTranslation.put(bundle.getString("Type.dayhour"), "0");
        translations.put("Type", localTranslation);
    }

   public ForwardWF(Properties oldProperties, final String fileName) {

        super(oldProperties, fileName);

        Locale locale = Locale.getDefault();
                
        try {            
            bundle = ResourceBundle.getBundle("notify/de/di/notify/gui/resources/ForwardWF", locale);
        } catch (Exception ex) {
            bundle = ResourceBundle.getBundle("contractmanager/de/di/notify/gui/resources/ForwardWF", locale);
        }
        
        //bundle = ResourceBundle.getBundle("notify/de/di/notify/gui/resources/ForwardWF", locale);                       
        LinkedHashMap<String, GuiTypeC> mapa = new LinkedHashMap<String, GuiTypeC>();
        for (String key : oldProperties.stringPropertyNames()) {
            mapa.put(key, GuiTypeC.TEXT);
        }
                   
        mapa.put("SendImmediately", GuiTypeC.BOOLEAN);  
        mapa.put("ForwardWFsetUsers.Exactname", GuiTypeC.BOOLEAN);
        mapa.put("ForwardWFsetUsers.isSordSpecified", GuiTypeC.BOOLEAN);        
        mapa.put("SendHour", GuiTypeC.HOURS);
        mapa.put("SendDays", GuiTypeC.DAYS);
        mapa.put("ForwardWFsetUsers.docStateValue", GuiTypeC.CONTRACTSTATES);
        mapa.put("Action", GuiTypeC.TEXT_READONLY);
        mapa.put("ForwardWFsetUsers.docState", GuiTypeC.TEXT_READONLY);
        mapa.put(oldProperties.getProperty("Action") + ".template", GuiTypeC.TEMPLATE);
//        mapa.put(oldProperties.getProperty("Action") + ".Date", GuiTypeC.MASKLINE);
//        mapa.put(oldProperties.getProperty("Action") + ".Users", GuiTypeC.MULTIPLE_MASKLINE);
//        mapa.put(oldProperties.getProperty("Action") + ".StatusField", GuiTypeC.MASKLINE);
//        mapa.put(oldProperties.getProperty("Action") + ".Exactname", GuiTypeC.BOOLEAN);
        mapa.put(oldProperties.getProperty("Action") + ".Nodenames", GuiTypeC.MULTIPLE_NODENAME);
        
//        mapa.put("Interval", GuiTypeC.INTEGER);
//        mapa.put("Type", GuiTypeC.COMBOBOX);
        //initComboboxValues();
          HashMap<String, String> mappedLines = new HashMap<String, String>();
//        if (oldProperties.containsKey(oldProperties.getProperty("Action") + ".Users")) {
//            mappedLines.put(oldProperties.getProperty("Action") + ".Users", oldProperties.getProperty("Action") + ".maskName");
//        }
//        if (oldProperties.containsKey(oldProperties.getProperty("Action") + ".StatusField")) {
//            mappedLines.put(oldProperties.getProperty("Action") + ".StatusField", oldProperties.getProperty("Action") + ".maskName");
//        }
        if (oldProperties.containsKey(oldProperties.getProperty("Action") + ".Nodenames")) {
            mappedLines.put(oldProperties.getProperty("Action") + ".Nodenames", oldProperties.getProperty("Action") + ".template");
        }
//
        mappedLines.put(oldProperties.getProperty("Action") + ".Date", oldProperties.getProperty("Action") + ".maskName");
//
//        mapa.put(oldProperties.getProperty("Action") + ".maskName", GuiTypeC.MASK);
        mapa.put("ProfileName", GuiTypeC.TEXT_MANDATORY);
        setConstraints(FilesTab.profileNames);
        setIgnoredConstraint(FilesTab.currentProfile);
//        HashSet<String> blanks = new HashSet<String>();
//        blanks.add(oldProperties.getProperty("Action") + ".Date");
//        setAllowedBlanks(blanks);
        this.setSaveButton(ConfigView.saveButton);
//        HashMap<String, Integer> eloTypesLocal = new HashMap<String, Integer>();
//        eloTypesLocal.put(oldProperties.getProperty("Action") + ".Date", 3004);
//        setEloTypes(eloTypesLocal);
        setMaskLinesMapper(mappedLines);
        setTypes(mapa);

//        if (!this.oldProperties.containsKey("Interval") || !this.newProperties.containsKey("Interval")) {
////            System.out.println("Adding interval");
//            this.oldProperties.setProperty("Interval", "5");
//            this.newProperties.setProperty("Interval", "5");
//        }

        GuiGenerator.eloClient = ConfigApp.client;
        try {
            draw();
        } catch (Exception ex) {
//            System.out.println(ex);
//            ex.printStackTrace();
        }

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

}
