package de.di.license.check;

import de.di.elo.client.ELOClient;
//import de.di.license.check.BasicLicenseChecker;
//import de.di.license.check.BasicLicenseChecker;
//import de.di.license.check.LicenseAttribute;
//import de.di.license.check.LicenseAttribute;
//import de.di.license.check.LicenseException;
//import de.di.license.check.LicenseException;
//import de.di.license.check.LicenseExpiredException;
//import de.di.license.check.LicenseExpiredException;
//import de.di.license.check.LicenseKey;
//import de.di.license.check.LicenseKey;
import de.di.notify.gui.ConfigApp;
import de.elo.ix.client.License;
import de.elo.ix.client.ServerInfo;
import org.apache.log4j.Logger;
import java.util.Properties;

/**
 *
 * @author A. Sopicki
 */
public class ELOLicenseChecker extends BasicLicenseChecker {

    private Logger logger = null;
    private Properties settings = null;
    private String product = null;

    public ELOLicenseChecker(String product) throws java.security.NoSuchAlgorithmException {
        super();

        this.product = product;
        logger = Logger.getRootLogger();
        
    }

    @Override
    public void check(LicenseKey key, int rounds) throws LicenseException {
        ELOClient eloClient = new ELOClient(settings);
        LicenseAttribute client = key.getAttribute("client");
        LicenseAttribute eloVersion = key.getAttribute("ELO-version");
        if (client == null) {
            throw new LicenseException("client attribute missing");
        }

        if (eloVersion == null) {
            throw new LicenseException("ELO-version attribute missing");
        }

        if (key.getAttribute("ERP-system") == null) {
            throw new LicenseException("ERP-system attribute missing");
        }

        if (key.getAttribute("license-type") == null) {
            throw new LicenseException("license-type attribute missing");
        }


        if (!eloVersion.getValue().equals("Office")) {
            if (!eloVersion.getValue().equals("Professional")
                    && !eloVersion.getValue().equals("Enterprise")) {
                throw new LicenseException("Illegal ELO-version");
            }

            License license1 = null;
            ServerInfo serverInfo = null;
            int count = 0;
            try {
                while (serverInfo == null && count < 20) {
                    try {
                        serverInfo = eloClient.getServerInfo();
                        license1 = serverInfo.getLicense();
                    } catch (Exception ex) {
                        logger.trace("An exception occured while contacting ELO", ex);
                        if (count == 20) {
                            throw ex;
                        } else {
                            count++;
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException iex) {
                            }
                        }
                    } finally {
                        if (eloClient != null) {
                            eloClient.close();
                        }
                        eloClient = null;
                    }
                }
            } catch (Exception ex) {
                logger.debug("An exception occured while retrieving license info from ELO", ex);
                throw new ELOException("ELO not available");
            }

            String serno = license1.getSerno();

            if (serno.indexOf("\\n") != -1) {
                serno = license1.getSerno().substring(0, license1.getSerno().indexOf("\\n"));
            }

            serno = serno.trim();

            if (!serno.equals(client.getValue())) {
                throw new LicenseException("client attribute missmatch (got '"
                        + serno + "' from ELO instead of '" + client.getValue() + "')");
            }
            super.check(key, rounds);

            String version = serverInfo.getVersion();
            if (version != null) {
                eloVersion.setValue("ELO " + eloVersion.getValue() + " " + version);
            }
            try {
                
                if (key.getAttribute("ELO-version").getValue().toLowerCase().contains("office")) {
                    logger.fatal("ELO Office not supported!");
                    throw new LicenseException(("ELO Office not supported!"));
                }
//                System.out.println("Product: " + product);
                ConfigApp.productName = product;
                if (!key.getAttribute("product").getValue().equals(product)) {
                    logger.fatal("License violation detected! Product mismatch");
                    throw new LicenseException("Product not supported");
                }
            } catch (NullPointerException ex) {
                logger.fatal("Missing license attribute. License not valid!");
                throw new LicenseException("Missing license attribute. License not valid!");
            }


            serverInfo = null;
            license1 = null;
        } else {

            eloVersion.setValue("ELO " + eloVersion.getValue());

            logger.fatal("ELO Office not supported!");
            eloClient.close();
            eloClient = null;
            throw new LicenseException(("ELO Office not supported!"));
        }

        if (key.getExpirationDate() == null) {
            throw new LicenseException("Illegal license expiration date!");
        }

        if (key.getExpirationDate().compareTo(new java.util.Date()) < 0) {
            throw new LicenseExpiredException("License expired on: "
                    + String.format(java.util.Locale.getDefault(), "%1$tc", key.getExpirationDate()));
        }
    }

    public Properties getSettings() {
        return settings;
    }

    public void setSettings(Properties settings) {
        this.settings = settings;
    }
}
