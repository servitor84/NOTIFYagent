/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.di.notify.gui;

/**
 *
 * @author Rahman
 */
public class LogTest {
    private static GUILogger logger = GUILogger.getInstance();
    public static void test(){
        logger.log("Hier funktioniert");
    }
}
