package de.di.dokinform.util;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author A. Sopicki
 */
public class BackupWriter {

  public static void makeBackup(File srcFile, File destFile, Logger logger) throws IOException {
    java.io.FileInputStream ins = null;
    java.io.FileOutputStream outs = null;

    java.nio.channels.FileChannel iChannel = null;
    java.nio.channels.FileChannel oChannel = null;

    logger.info("Making backup of file " + srcFile.toString());

    try {
      if (!destFile.exists()) {
        logger.info("Creating new file " + destFile.toString());
        destFile.createNewFile();
      } else {
        logger.info("Backupfile " + destFile.toString() + " already exists");
        throw new IOException("Existing file exception");
      }

      ins = new java.io.FileInputStream(srcFile);
      outs = new java.io.FileOutputStream(destFile);

      iChannel = ins.getChannel();
      oChannel = outs.getChannel();

      oChannel.transferFrom(iChannel, 0, iChannel.size());

      iChannel.close();
      oChannel.close();
    } catch (IOException ioex) {
      logger.log(Level.FATAL, "IOException occured!", ioex);
      if (iChannel != null) {
        iChannel.close();
      }
      if (oChannel != null) {
        oChannel.close();
      }

      throw ioex;
    }
  }
}
