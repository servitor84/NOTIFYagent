package de.di.notify.gui;

import de.elo.ix.client.DocMask;
import de.elo.ix.client.DocMaskLine;
import java.util.List;
import javax.swing.JComboBox;

public class SwingUtils {

    public static boolean setItems(JComboBox comboBox, List<DocMask> masks, String selectedValue) {
        if (comboBox == null || masks == null) {
            return false;
        }
        comboBox.removeAllItems();
        boolean found = false;
        for (DocMask docMask : masks) {
            comboBox.addItem(docMask.getName());
            if (selectedValue != null && docMask.getName().equals(selectedValue)) {
                comboBox.setSelectedItem(selectedValue);
                found = true;
            }
        }
        if (!found) {
            comboBox.addItem(selectedValue);
            comboBox.setSelectedItem(selectedValue);
            return false;
        }
        return true;
    }

    public static boolean setItems(JComboBox comboBox, DocMask mask, String selectedValue) {
        if (comboBox == null || mask == null) {
            return false;
        }
        boolean found = false;
        comboBox.removeAllItems();
        for (DocMaskLine docMaskLine : mask.getLines()) {
            comboBox.addItem(docMaskLine.getName());
            if (selectedValue != null && docMaskLine.getName().equals(selectedValue)) {
                comboBox.setSelectedItem(selectedValue);
                found = true;
            }
        }
        if (!found) {
            comboBox.addItem(selectedValue);
            comboBox.setSelectedItem(selectedValue);
            return false;
        }
        return true;
    }
}
