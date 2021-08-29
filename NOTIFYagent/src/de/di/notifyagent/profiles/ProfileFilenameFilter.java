package de.di.notifyagent.profiles;

import java.io.File;
import java.io.FilenameFilter;

public class ProfileFilenameFilter implements FilenameFilter {

    private final String extension = ".properties";

    @Override
    public boolean accept(File dir, String name) {
        if (name.endsWith(extension)) {
            return true;
        }
        return false;
    }
}
