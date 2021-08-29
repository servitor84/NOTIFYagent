//package elo.di.notifyagent.elo;
//
//import de.elo.ix.client.*;
//import de.elo.utils.net.RemoteException;
//import java.io.File;
//import java.io.PrintStream;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.*;
//import org.apache.log4j.Logger;
//
//public class ELOClientNG
//{
//
//    public IXClient indexClient;
//    public ClientInfo clientInfo;
//    private String connectionUrl;
//    private String userName;
//    private String password;
//    private String runAs;
//    protected IXServicePortC ixConstants;
//    protected LoginResult loginResult;
//    private boolean connected;
//    public static final String PATH_SEP = "\266";
//    private Logger logger;
//    public static final String basePath = "\266Administration\266DOKinform\266NOTIFYagent";
//    private static final String documentMask = "0";
//    private List masks;
//    private HashMap masksMap;
//
//    public ELOClientNG()
//    {
//        userName = "";
//        password = "";
//        runAs = null;
//        loginResult = null;
//        connected = false;
//        logger = Logger.getLogger(getClass());
//    }
//
//    public ELOClientNG(Logger logger)
//    {
//        userName = "";
//        password = "";
//        runAs = null;
//        loginResult = null;
//        connected = false;
//        this.logger = Logger.getLogger(getClass());
//        this.logger = logger;
//    }
//
//    public ELOClientNG(Properties settings)
//    {
//        userName = "";
//        password = "";
//        runAs = null;
//        loginResult = null;
//        connected = false;
//        logger = Logger.getLogger(getClass());
//        connectionUrl = settings.getProperty("IndexServer.URL");
//        userName = settings.getProperty("IndexServer.User");
//        password = settings.getProperty("IndexServer.Password");
//        System.out.println((new StringBuilder()).append("INDEXSERVER: ").append(connectionUrl).append(" ,userName: ").append(userName).append(" ,password: ").append(password).toString());
//        System.out.println("init ");
//        if(clientInfo == null)
//        {
//            clientInfo = new ClientInfo();
//        }
//        clientInfo.setTimeZone(getSystemTimeZone());
//        if(indexClient == null)
//        {
//            indexClient = new IXClient(getConnectionUrl());
//        }
//    }
//
//    public void reconect()
//    {
//        do
//        {
//            try
//            {
//                clientInfo = new ClientInfo();
//                clientInfo.setTimeZone(getSystemTimeZone());
//                indexClient = new IXClient(getConnectionUrl());
//                break;
//            }
//            catch(IllegalStateException stateex)
//            {
//                logger.error("Cannot connect to elo indexserver");
//                logger.error("Please secure the elo indexserver is runing");
//                logger.info("If the url, user or password is wrong, please correct it in config.properties fi" +
//"le and restart the service"
//);
//                logger.info("Try to reconnect in 10 seconds");
//                logger.error("An exception occured while connecting to the index server.");
//                try
//                {
//                    Thread.sleep(10000L);
//                }
//                catch(InterruptedException interruptedexception) { }
//            }
//            catch(Exception ex)
//            {
//                logger.error("Cannot connect to elo indexserver");
//                logger.error("Please secure the elo indexserver is runing");
//                logger.info("If the url, user or password is wrong, please correct it in config.properties fi" +
//"le and restart the service"
//);
//                logger.info("Try to reconnect in 10 seconds");
//                logger.error("An exception occured while connecting to the index server.");
//                try
//                {
//                    Thread.sleep(10000L);
//                }
//                catch(InterruptedException interruptedexception1) { }
//            }
//        } while(true);
//    }
//
//    public void init()
//    {
//        do
//        {
//            try
//            {
//                if(clientInfo == null)
//                {
//                    clientInfo = new ClientInfo();
//                }
//                clientInfo.setTimeZone(getSystemTimeZone());
//                if(indexClient == null)
//                {
//                    indexClient = new IXClient(getConnectionUrl());
//                }
//                break;
//            }
//            catch(IllegalStateException stateex)
//            {
//                logger.error("Cannot connect to elo indexserver");
//                logger.error("Please secure the elo indexserver is runing");
//                logger.info("If the url, user or password is wrong, please correct it in config.properties fi" +
//"le and restart the service"
//);
//                logger.info("Try to reconnect in 10 seconds");
//                try
//                {
//                    Thread.sleep(10000L);
//                }
//                catch(InterruptedException interruptedexception) { }
//            }
//            catch(Exception ex)
//            {
//                logger.error("Cannot connect to elo indexserver");
//                logger.error("Please secure the elo indexserver is runing");
//                logger.info("If the url, user or password is wrong, please correct it in config.properties fi" +
//"le and restart the service"
//);
//                logger.info("Try to reconnect in 10 seconds");
//                try
//                {
//                    Thread.sleep(10000L);
//                }
//                catch(InterruptedException interruptedexception1) { }
//            }
//        } while(true);
//    }
//
//    public ServerInfo getServerInfo()
//        throws RemoteException, IllegalStateException
//    {
//        if(indexClient == null)
//        {
//            init();
//        }
//        return indexClient.ix.getServerInfo(clientInfo);
//    }
//
//    public void close()
//    {
//        if(connected)
//        {
//            logoff();
//        }
//    }
//
//    public boolean isConnected()
//    {
//        return connected;
//    }
//
//    public IXServicePortC getServicePort()
//        throws RemoteException
//    {
//        if(!connected)
//        {
//            login();
//        }
//        return ixConstants;
//    }
//
//    private String getSystemTimeZone()
//    {
//        return TimeZone.getDefault().getID();
//    }
//
//    private String getLocalHost()
//        throws UnknownHostException
//    {
//        return InetAddress.getLocalHost().getHostName();
//    }
//
//    public void login()
//        throws RemoteException
//    {
//        if(indexClient == null)
//        {
//            init();
//        }
//        try
//        {
//            loginResult = indexClient.login(clientInfo, getUserName(), getPassword(), getLocalHost(), runAs);
//            ixConstants = indexClient.getCONST(clientInfo);
//            clientInfo = loginResult.getClientInfo();
//        }
//        catch(UnknownHostException uhex)
//        {
//            throw new RemoteException("Unable to login due to a network problem", uhex);
//        }
//        catch(Exception ex)
//        {
//            if(indexClient != null)
//            {
//                try
//                {
//                    indexClient.done();
//                    indexClient = null;
//                }
//                catch(Exception ex2)
//                {
//                    ex2.getStackTrace();
//                }
//            }
//            throw new RemoteException("Unable to login due to a network problem", ex);
//        }
//        connected = true;
//    }
//
//    public void logoff()
//    {
//label0:
//        {
//            try
//            {
//                if(indexClient != null)
//                {
//                    indexClient.logout(clientInfo);
//                    indexClient.done();
//                    indexClient = null;
//                }
//            }
//            catch(RemoteException remoteexception)
//            {
//                connected = false;
//                break label0;
//            }
//            finally
//            {
//                connected = false;
//                //throw exception;
//            }
//            connected = false;
//            break label0;
//        }
//    }
//
//    public void finalize()
//        throws Throwable
//    {
//        super.finalize();
//        if(connected)
//        {
//            try
//            {
//                indexClient.logout(clientInfo);
//            }
//            catch(Exception exception) { }
//        }
//        if(indexClient != null)
//        {
//            indexClient.done();
//            indexClient = null;
//        }
//    }
//
//    public int getTicketLifeTime()
//    {
//        if(!connected)
//        {
//            return 0;
//        } else
//        {
//            return loginResult.getTicketLifetime();
//        }
//    }
//
//    public void alive()
//        throws RemoteException
//    {
//        if(!connected)
//        {
//            login();
//            return;
//        }
//        try
//        {
//            if(calculateBestBefore() < System.nanoTime())
//            {
//                indexClient.ix.alive(clientInfo);
//            }
//        }
//        catch(RemoteException remoteexception) { }
//    }
//
//    public String getConnectionUrl()
//    {
//        return connectionUrl;
//    }
//
//    public void setConnectionUrl(String connectionUrl)
//    {
//        this.connectionUrl = connectionUrl;
//    }
//
//    public String getUserName()
//    {
//        return userName;
//    }
//
//    public void setUserName(String userName)
//    {
//        this.userName = userName;
//    }
//
//    public String getRunAs()
//    {
//        return runAs;
//    }
//
//    public void setRunAs(String userName)
//    {
//        runAs = userName;
//    }
//
//    public String getPassword()
//    {
//        return password;
//    }
//
//    public void setPassword(String password)
//    {
//        this.password = password;
//    }
//
//    public IXExceptionData parseException(String msg)
//        throws RemoteException
//    {
//        return indexClient.ix.parseException(clientInfo, msg);
//    }
//
//    private long calculateBestBefore()
//    {
//        long us = getTicketLifeTime() * 1000 * 1000;
//        long ns100 = us * 10L;
//        return (System.nanoTime() * (ns100 * 9L)) / 10L;
//    }
//
//    public Sord getSord(String path)
//        throws RemoteException
//    {
//        EditInfo info = null;
//        RemoteException rex;
//        info = indexClient.ix.checkoutSord(clientInfo, (new StringBuilder()).append("ARCPATH:").append(path).toString(), EditInfoC.mbSord, LockC.NO);
//        return info.getSord();
//        //RemoteException rex;
////        if(!rex.getMessage().contains("ELOIX:50"))
////        {
////            throw new RemoteException("Unable to retrieve sord from ELO!", rex);
////        } else
////        {
////            return null;
////        }
//    }
//
//    public EditInfo getDocument(int objId)
//        throws RemoteException
//    {
//        EditInfo info = null;
//        RemoteException rex;
//        info = indexClient.ix.checkoutDoc(clientInfo, Integer.toString(objId), null, EditInfoC.mbSordDoc, LockC.NO);
//        return info;
//        //RemoteException rex;
////        if(!rex.getMessage().contains("ELOIX:50"))
////        {
////            throw new RemoteException("Unable to retrieve sord from ELO!", rex);
////        } else
////        {
////            return null;
////        }
//    }
//
//    protected synchronized Sord createPath(String destination, String userName, String mask)
//        throws RemoteException
//    {
//        String pathComponents[] = destination.substring(1).split("\266");
//        String path = "";
//        EditInfo info = null;
//        Sord parent = null;
//        for(int i = 0; i < pathComponents.length; i++)
//        {
//            path = (new StringBuilder()).append(path).append("\266").append(pathComponents[i].trim()).toString();
//            info = null;
//            try
//            {
//                info = indexClient.ix.checkoutSord(clientInfo, (new StringBuilder()).append("ARCPATH:").append(path).toString(), EditInfoC.mbSord, LockC.NO);
//            }
//            catch(RemoteException rex)
//            {
//                if(!rex.getMessage().contains("ELOIX:50"))
//                {
//                    throw new RemoteException((new StringBuilder()).append("Unable to create structure path '").append(path).append("' for document!").toString());
//                }
//            }
//            if(info == null)
//            {
//                if(parent == null)
//                {
//                    if(mask == null)
//                    {
//                        info = indexClient.ix.createSord(clientInfo, "1", "", EditInfoC.mbSord);
//                    } else
//                    {
//                        info = indexClient.ix.createSord(clientInfo, "1", mask, EditInfoC.mbSord);
//                    }
//                } else
//                if(mask == null)
//                {
//                    info = indexClient.ix.createSord(clientInfo, parent.getGuid(), Integer.toString(parent.getMask()), EditInfoC.mbSord);
//                } else
//                {
//                    info = indexClient.ix.createSord(clientInfo, parent.getGuid(), mask, EditInfoC.mbSord);
//                }
//                parent = info.getSord();
//                parent.setName(pathComponents[i]);
//                AclItem aclItems[] = new AclItem[2];
//                aclItems[0] = new AclItem();
//                aclItems[0].setType(100);
//                aclItems[1] = new AclItem();
//                aclItems[1].setName(userName);
//                aclItems[1].setAccess(getServicePort().getACCESS().getLUR_ALL());
//                parent.setAclItems(aclItems);
//                indexClient.ix.checkinSord(clientInfo, parent, SordC.mbAll, LockC.NO);
//            } else
//            {
//                parent = info.getSord();
//            }
//        }
//
//        if(info == null)
//        {
//            throw new RemoteException("Unable to create structure path for document!");
//        } else
//        {
//            parent = info.getSord();
//            return parent;
//        }
//    }
//
//    public void saveSubscription(String user, String objId, String name, File propertiesFile)
//        throws RemoteException
//    {
//        String path = (new StringBuilder()).append("\266Administration\266DOKinform\266NOTIFYagent\266").append(user).toString();
//        Sord userDir = createPath(path, user, null);
//        Sord subFolder = getSord((new StringBuilder()).append(path).append("\266").append(name).toString());
//        EditInfo info = null;
//        Sord subDoc = null;
//        if(subFolder == null)
//        {
//            info = indexClient.ix.createSord(clientInfo, userDir.getGuid(), "0", EditInfoC.mbSord);
//            subFolder = info.getSord();
//            subFolder.setName(name);
//        } else
//        {
//            subDoc = getSord((new StringBuilder()).append(path).append("\266").append(name).append("\266").append(name).toString());
//            if(subDoc == null)
//            {
//                throw new RemoteException("Profile not found");
//            }
//            info = getDocument(subDoc.getId());
//            subDoc = info.getSord();
//        }
//        indexClient.ix.checkinSord(clientInfo, subFolder, SordC.mbAll, LockC.NO);
//        if(subDoc == null)
//        {
//            info = indexClient.ix.createDoc(clientInfo, subFolder.getGuid(), "0", null, EditInfoC.mbSordDocAtt);
//            subDoc = info.getSord();
//        }
//        DocVersion versions[] = new DocVersion[1];
//        versions[0] = new DocVersion();
//        versions[0].setExt("txt");
//        versions[0].setPathId(subDoc.getPath());
//        versions[0].setEncryptionSet(subDoc.getDetails().getEncryptionSet());
//        subDoc.getDetails().setArchivingMode(2001);
//        subDoc.setName(name);
//        info.getDocument().setDocs(versions);
//        Document checkinResult = indexClient.ix.checkinDocBegin(clientInfo, info.getDocument());
//        info.setDocument(checkinResult);
//        versions = checkinResult.getDocs();
//        versions[0].setUploadResult(indexClient.upload(versions[0].getUrl(), propertiesFile));
//        info.setDocument(indexClient.ix.checkinDocEnd(clientInfo, subDoc, SordC.mbAll, info.getDocument(), LockC.NO));
//        indexClient.ix.checkinSord(clientInfo, userDir, SordC.mbLean, LockC.NO);
//    }
//
//    public void saveStatus(String user, String subscriptionName, String name, File propertiesFile)
//        throws RemoteException
//    {
//        String path = (new StringBuilder()).append("\266Administration\266DOKinform\266NOTIFYagent\266").append(user).append("\266").append(subscriptionName).toString();
//        Sord parent = getSord(path);
//        EditInfo info = null;
//        path = (new StringBuilder()).append("\266Administration\266DOKinform\266NOTIFYagent\266").append(user).append("\266").append(parent.getName()).append("\266").append(name).toString();
//        Sord statusSord = getSord(path);
//        if(statusSord == null)
//        {
//            info = indexClient.ix.createDoc(clientInfo, parent.getGuid(), "0", null, EditInfoC.mbSordDocAtt);
//            statusSord = info.getSord();
//            statusSord.setName(name);
//        } else
//        {
//            info = getDocument(statusSord.getId());
//            statusSord = info.getSord();
//        }
//        DocVersion versions[] = new DocVersion[1];
//        versions[0] = new DocVersion();
//        versions[0].setExt("txt");
//        versions[0].setPathId(statusSord.getPath());
//        versions[0].setEncryptionSet(statusSord.getDetails().getEncryptionSet());
//        statusSord.getDetails().setArchivingMode(2000);
//        info.getDocument().setDocs(versions);
//        Document checkinResult = indexClient.ix.checkinDocBegin(clientInfo, info.getDocument());
//        info.setDocument(checkinResult);
//        versions = checkinResult.getDocs();
//        versions[0].setUploadResult(indexClient.upload(versions[0].getUrl(), propertiesFile));
//        info.setDocument(indexClient.ix.checkinDocEnd(clientInfo, statusSord, SordC.mbAll, info.getDocument(), LockC.NO));
//        indexClient.ix.checkinSord(clientInfo, parent, SordC.mbLean, LockC.NO);
//    }
//
//    public List getMasks()
//    {
//        if(masks == null)
//        {
//            try
//            {
//                if(!connected)
//                {
//                    login();
//                }
//                masks = new ArrayList();
//                EditInfoZ editZ = new EditInfoZ(1L, SordC.mbMin);
//                EditInfo ed = indexClient.ix.createSord(clientInfo, "1", "1", editZ);
//                MaskName amaskname[] = ed.getMaskNames();
//                int i = amaskname.length;
//                for(int j = 0; j < i; j++)
//                {
//                    MaskName dm = amaskname[j];
//                    masks.add(indexClient.ix.checkoutDocMask(clientInfo, dm.getName(), DocMaskC.mbAll, LockC.NO));
//                }
//
//            }
//            catch(RemoteException ex)
//            {
//                System.out.println(ex);
//            }
//        }
//        return masks;
//    }
//
//    public HashMap getMasksAsMap()
//    {
//        if(masksMap == null)
//        {
//            masksMap = new HashMap();
//            DocMask docMask;
//            for(Iterator iterator = getMasks().iterator(); iterator.hasNext(); masksMap.put(docMask.getName(), docMask))
//            {
//                docMask = (DocMask)iterator.next();
//            }
//
//        }
//        return masksMap;
//    }
//}
