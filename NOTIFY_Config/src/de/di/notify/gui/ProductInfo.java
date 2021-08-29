package de.di.notify.gui;

import java.io.InputStream;
import java.util.Map;

/**
 *
 * @author A. Sopicki
 */
public class ProductInfo {
    public static void readProductInfo(InputStream in, Map<String, String> status) {
        java.util.Properties p = new java.util.Properties();
            try {
                p.load(in);
                String version = p.getProperty("app.version");
                String productName = p.getProperty("app.product_name");
                String product = p.getProperty("app.product");
                in.close();
                
                if ( version != null )
                    status.put("version", version);
                else
                    status.put("version", "");
                
                if ( productName != null )
                    status.put("product_name", productName);
                else
                    status.put("product_name", "DOKinform NOTIFYagent for ELO");
                
                if ( product != null ) 
                {
                    status.put("product", product);
                }
                else
                    status.put("product", "Illegal product value");
            } catch (Exception e) {
                //logger.log(Level.INFO, "Unable to load product info", e);
                status.put("product_name", "");
                status.put("version", "");
                status.put("product", "Illegal product value");
            }
    }
}
