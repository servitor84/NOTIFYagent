package de.di.elo.client;

import de.elo.ix.client.ClientInfo;
import de.elo.ix.client.DocMask;
import de.elo.ix.client.DocMaskC;
import de.elo.ix.client.Document;
import de.elo.ix.client.EditInfo;
import de.elo.ix.client.EditInfoZ;
import de.elo.ix.client.FindByIndex;
import de.elo.ix.client.FindByVersion;
import de.elo.ix.client.FindInfo;
import de.elo.ix.client.FindResult;
import de.elo.ix.client.IXClient;
import de.elo.ix.client.IXServicePortC;
import de.elo.ix.client.IXServicePortIF;
import de.elo.ix.client.LockC;
import de.elo.ix.client.LockZ;
import de.elo.ix.client.LoginResult;
import de.elo.ix.client.MaskName;
import de.elo.ix.client.ServerInfo;
import de.elo.ix.client.Sord;
import de.elo.ix.client.SordC;
import de.elo.ix.client.SordZ;
import de.elo.ix.client.WFTypeZ;
import de.elo.utils.net.RemoteException;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

public class ELOClient
{
  private IXClient indexClient;
  private ClientInfo clientInfo;
  private String connectionUrl;
  private String userName;
  private String password;
  private IXServicePortC ixConstants;
  private LoginResult loginResult = null;
  private List<DocMask> masks;
  private HashMap<String, DocMask> masksMap;
  private boolean connected = false;
  private Properties settings;
  
  public ELOClient()
  {
    this(null);
  }
  
  public ELOClient(Properties settings)
  {
    this.settings = settings;
    init(settings);
  }
  
  private void init(Properties settings)
  {
    this.connectionUrl = settings.getProperty("IndexServer.URL");
    this.userName = settings.getProperty("IndexServer.User");
    this.password = settings.getProperty("IndexServer.Password");
    // PWD decription ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        String pattern = "^((\\d){1,})([-]{1}(\\d){1,}){1,}";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = p.matcher(password);
        if(matcher.matches()) {
//            try {
//                de.elo.utils.sec.DesEncryption des = new de.elo.utils.sec.DesEncryption();
//                password = des.decrypt(password);
//            } catch (Exception ex) {
//                
//            }            
        }
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++SL 17.03.2020
    if (this.clientInfo == null) {
      this.clientInfo = new ClientInfo();
    }
    this.clientInfo.setTimeZone(getSystemTimeZone());
    if (this.indexClient == null) {
      this.indexClient = new IXClient(getConnectionUrl());
    }
  }
  
  public void alive()
    throws RemoteException
  {
    if (!this.connected)
    {
      login();
      
      return;
    }
    this.indexClient.ix.alive(this.clientInfo);
  }
  
  public ServerInfo getServerInfo()
    throws RemoteException
  {
    return this.indexClient.ix.getServerInfo(this.clientInfo);
  }
  
  public Document checkinDocBegin(Document doc)
    throws RemoteException
  {
    if (!this.connected) {
      login();
    }
    return this.indexClient.ix.checkinDocBegin(this.clientInfo, doc);
  }
  
  public Document checkinDocEnd(Sord docSord, SordZ sordInfo, Document doc, LockZ lockInfo)
    throws RemoteException
  {
    if (!this.connected) {
      login();
    }
    return this.indexClient.ix.checkinDocEnd(this.clientInfo, docSord, sordInfo, doc, lockInfo);
  }
  
  public int checkinSord(Sord parent, SordZ sordInfo, LockZ lockInfo)
    throws RemoteException
  {
    if (!this.connected) {
      login();
    }
    return this.indexClient.ix.checkinSord(this.clientInfo, parent, sordInfo, lockInfo);
  }
  
  public EditInfo checkoutSord(String path, EditInfoZ editInfo, LockZ lockInfo)
    throws RemoteException
  {
    if (!this.connected) {
      login();
    }
    return this.indexClient.ix.checkoutSord(this.clientInfo, path, editInfo, lockInfo);
  }
  
  public EditInfo createDoc(String parent, String mask, String template, EditInfoZ editInfo)
    throws RemoteException
  {
    if (!this.connected) {
      login();
    }
    return this.indexClient.ix.createSord(this.clientInfo, parent, mask, editInfo);
  }
  
  public EditInfo createSord(String parent, String mask, EditInfoZ editInfo)
    throws RemoteException
  {
    if (!this.connected) {
      login();
    }
    return this.indexClient.ix.createSord(this.clientInfo, parent, mask, editInfo);
  }
  
  public Sord findDoc(String md5, String name)
    throws RemoteException
  {
    FindInfo info = new FindInfo();
    FindByVersion versionInfo = new FindByVersion();
    FindByIndex indexInfo = new FindByIndex();
    versionInfo.setVersionMD5(md5);
    indexInfo.setName(name);
    info.setFindByVersion(versionInfo);
    info.setFindByIndex(indexInfo);
    
    FindResult result = this.indexClient.ix.findFirstSords(this.clientInfo, info, 1, SordC.mbMinDocVersion);
    Sord[] sords = result.getSords();
    if ((sords != null) && (sords.length > 0)) {
      return sords[0];
    }
    return null;
  }
  
  public void close()
  {
    if (this.connected) {
      logoff();
    }
  }
  
  public boolean isConnected()
  {
    return this.connected;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public IXServicePortC getServicePort()
  {
    return this.ixConstants;
  }
  
  public int getTicketLifeTime()
  {
    if (!this.connected) {
      return 0;
    }
    return this.loginResult.getTicketLifetime();
  }
  
  public void refSord(String oldParentId, String newParentId, String objId, int manSortIdx)
    throws RemoteException
  {
    this.indexClient.ix.refSord(this.clientInfo, oldParentId, newParentId, objId, manSortIdx);
  }
  
  public String upload(String url, File file)
    throws RemoteException
  {
    return this.indexClient.upload(url, file);
  }
  
  public int startWorkFlow(String template, String workflow, String objId)
    throws RemoteException
  {
    return this.indexClient.ix.startWorkFlow(this.clientInfo, template, workflow, objId);
  }
  
  public void deleteWorkFlow(Integer workflowId, WFTypeZ type, LockZ lock)
    throws RemoteException
  {
    this.indexClient.ix.deleteWorkFlow(this.clientInfo, workflowId.toString(), type, lock);
  }
  
  private String getConnectionUrl()
  {
    return this.connectionUrl;
  }
  
  private String getSystemTimeZone()
  {
    return TimeZone.getDefault().getID();
  }
  
  private void login()
    throws RemoteException
  {
    if (this.indexClient == null) {
      init(this.settings);
    }
    try
    {
      this.loginResult = this.indexClient.login(this.clientInfo, getUserName(), this.password, InetAddress.getLocalHost().getHostName(), "");
      this.ixConstants = this.indexClient.getCONST(this.clientInfo);
      this.clientInfo = this.loginResult.getClientInfo();
    }
    catch (UnknownHostException uhex)
    {
      throw new RemoteException("Unable to login due to a network problem", uhex);
    }
    catch (Exception ex)
    {
      if (this.indexClient != null) {
        try
        {
          this.indexClient.done();
          this.indexClient = null;
        }
        catch (Exception ex2)
        {
          ex2.getStackTrace();
        }
      }
      throw new RemoteException("Unable to login due to a network problem", ex);
    }
    this.connected = true;
  }
  
  public List<DocMask> getMasks()
  {
    if (this.masks == null) {
      try
      {
        if (!this.connected) {
          login();
        }
        this.masks = new ArrayList();
        EditInfoZ editZ = new EditInfoZ(1L, SordC.mbMin);
        EditInfo ed = this.indexClient.ix.createSord(this.clientInfo, null, null, editZ);
        for (MaskName dm : ed.getMaskNames()) {
          this.masks.add(this.indexClient.ix.checkoutDocMask(this.clientInfo, dm.getName(), DocMaskC.mbAll, LockC.NO));
        }
      }
      catch (RemoteException localRemoteException) {}
    }
    return this.masks;
  }
  
  public HashMap<String, DocMask> getMasksAsMap()
  {
    if (this.masksMap == null)
    {
      this.masksMap = new HashMap();
      for (DocMask docMask : getMasks()) {
        this.masksMap.put(docMask.getName(), docMask);
      }
    }
    return this.masksMap;
  }
  
  /* Error */
  private void logoff()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 19	de/arivato/elo/client/ELOClient:indexClient	Lde/elo/ix/client/IXClient;
    //   4: aload_0
    //   5: getfield 14	de/arivato/elo/client/ELOClient:clientInfo	Lde/elo/ix/client/ClientInfo;
    //   8: invokevirtual 93	de/elo/ix/client/IXClient:logout	(Lde/elo/ix/client/ClientInfo;)V
    //   11: aload_0
    //   12: iconst_0
    //   13: putfield 4	de/arivato/elo/client/ELOClient:connected	Z
    //   16: goto +20 -> 36
    //   19: astore_1
    //   20: aload_0
    //   21: iconst_0
    //   22: putfield 4	de/arivato/elo/client/ELOClient:connected	Z
    //   25: goto +11 -> 36
    //   28: astore_2
    //   29: aload_0
    //   30: iconst_0
    //   31: putfield 4	de/arivato/elo/client/ELOClient:connected	Z
    //   34: aload_2
    //   35: athrow
    //   36: return
    // Line number table:
    //   Java source line #271	-> byte code offset #0
    //   Java source line #274	-> byte code offset #11
    //   Java source line #275	-> byte code offset #16
    //   Java source line #272	-> byte code offset #19
    //   Java source line #274	-> byte code offset #20
    //   Java source line #275	-> byte code offset #25
    //   Java source line #274	-> byte code offset #28
    //   Java source line #276	-> byte code offset #36
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	37	0	this	ELOClient
    //   19	1	1	localRemoteException	RemoteException
    //   28	7	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	11	19	de/elo/utils/net/RemoteException
    //   0	11	28	finally
  }
  
  public void finalize()
  {
    if (this.connected) {
      try
      {
        this.indexClient.logout(this.clientInfo);
      }
      catch (Exception localException) {}
    }
  }
  
  public static void main(String[] args)
  {
    Properties properties = new Properties();
    properties.setProperty("IndexServer.URL", "http://localhost:9090/ix-elo/ix");
    properties.setProperty("IndexServer.User", "Administrator");
    properties.setProperty("IndexServer.Password", "elo");
    
    ELOClient eLOClient = new ELOClient(properties);
    List<DocMask> masks = eLOClient.getMasks();
  }
}
