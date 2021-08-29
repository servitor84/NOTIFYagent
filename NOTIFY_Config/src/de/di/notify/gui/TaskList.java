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
public class TaskList extends JPanelEnableSave {
    
    public TaskList(Properties oldProperties, final String fileName) {
        super(oldProperties, fileName);
        Locale locale = Locale.getDefault();
                
        try {            
            bundle = ResourceBundle.getBundle("notify/de/di/notify/gui/resources/TaskList", locale);
        } catch (Exception ex) {
            bundle = ResourceBundle.getBundle("contractmanager/de/di/notify/gui/resources/TaskList", locale);
        }
        
        //bundle = ResourceBundle.getBundle("notify/de/di/notify/gui/resources/TaskList", locale);
        LinkedHashMap<String, GuiTypeC> mapa = new LinkedHashMap<String, GuiTypeC>();
        for (String key : oldProperties.stringPropertyNames()) {
            mapa.put(key, GuiTypeC.TEXT);
        }
        mapa.put("TaskList.maskName", GuiTypeC.MASK);
        mapa.put("TaskList.showSubstitute", GuiTypeC.BOOLEAN);
        mapa.put("SendImmediately", GuiTypeC.BOOLEAN);
        mapa.put("TaskList.priorities", GuiTypeC.PRIORITIES);
        mapa.put("TaskList.maxWorkflows", GuiTypeC.INTEGER);
        mapa.put("SendHour", GuiTypeC.HOURS);
        mapa.put("SendDays", GuiTypeC.DAYS);
        mapa.put("Action", GuiTypeC.TEXT_READONLY);
        mapa.put("ProfileName", GuiTypeC.TEXT_MANDATORY);
        mapa.put("TaskList.messageTemplate", GuiTypeC.FILE);
//        mapa.put("TaskList.messageTemplate", GuiTypeC.MULTIPLE_MASKLINE);
        this.setSaveButton(ConfigView.saveButton);
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
    
}
