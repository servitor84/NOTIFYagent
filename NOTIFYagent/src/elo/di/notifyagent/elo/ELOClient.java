package elo.di.notifyagent.elo;

import de.di.dokinform.notifyabo.DocReader;
import de.di.dokinform.notifyabo.StatusDoc;
import de.elo.ix.client.CheckoutUsersC;
import de.elo.ix.client.DocMask;
import de.elo.ix.client.DocMaskC;
import de.elo.ix.client.EditInfo;
import de.elo.ix.client.EditInfoC;
import de.elo.ix.client.EditInfoZ;
import de.elo.ix.client.FindByIndex;
import de.elo.ix.client.FindChildren;
import de.elo.ix.client.FindInfo;
import de.elo.ix.client.FindOptions;
import de.elo.ix.client.FindResult;
import de.elo.ix.client.FindTasksInfo;
import de.elo.ix.client.FindWorkflowInfo;
import de.elo.ix.client.LockC;
import de.elo.ix.client.ObjKey;
import de.elo.ix.client.SearchModeC;
import de.elo.ix.client.Sord;
import de.elo.ix.client.SordC;
import de.elo.ix.client.SordZ;
import de.elo.ix.client.UserInfo;
import de.elo.ix.client.UserInfoC;
import de.elo.ix.client.UserTask;
import de.elo.ix.client.UserTaskPriorityC;
import de.elo.ix.client.UserTaskSortOrderC;
import de.elo.ix.client.WFCollectNode;
import de.elo.ix.client.WFDiagram;
import de.elo.ix.client.WFDiagramC;
import de.elo.ix.client.WFDiagramZ;
import de.elo.ix.client.WFEditNode;
import de.elo.ix.client.WFNode;
import de.elo.ix.client.WFNodeAssoc;
import de.elo.ix.client.WFNodeC;
import de.elo.ix.client.WFTypeC;
import java.io.FileNotFoundException;
import java.io.IOException;
import de.elo.utils.net.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

/**
 * A client for ELO providing methods to access the data in the ELO archive.
 *
 * @author A. Sopicki
 */
public class ELOClient extends de.di.dokinform.elo.ELOClientNG {

    private List<WFDiagram> templates;
    
    public ELOClient() {
        super();
    }
    
     public ELOClient(Logger logger) {
        super(logger);
    }
         
//    depricated
//    public WFCollectNode[] getActiveNodes(String workflow, String objId, String user) throws RemoteException {
//        String[] users = {user};
//
//        WFCollectNode[] nodes = indexClient.ix.collectWorkFlowNodes(
//                clientInfo,
//                workflow,
//                WFTypeC.ACTIVE,
//                null,
//                WFNodeC.TYPE_NOTHING,
//                objId,
//                "",
//                "",
//                users,
//                true,
//                false);                    
//        
//        return nodes;
//    }
    // ++++++++++++++++++++
    public java.util.List<UserTask> getActiveNodes(
            String user,
            //String nodeName,
            int minPriority,
            int maxPriority,
            int max,
            String endDate) throws RemoteException {
        String[] users = {user};

        FindTasksInfo fti = new FindTasksInfo();
        
        //fti.setTaskName(nodeName);
        fti.setUserIds(users);                             
        fti.setInclWorkflows(true);
        fti.setInclReminders(false);
        fti.setInclGroup(false);
        fti.setInclDeputy(false);
        fti.setInclActivities(false);
        fti.setHighestPriority(maxPriority);
        fti.setLowestPriority(minPriority);
        fti.setSortOrder(UserTaskSortOrderC.PRIORITY_DATE_NAME);
     
        //FindResult result = indexClient.ix.findFirstTasks(clientInfo, fti, max);
        FindResult result = _connection.ix().findFirstTasks(fti, max);
        java.util.List<UserTask> activeNodes = java.util.Arrays.asList(result.getTasks());

        return activeNodes;
    }
    
    // ++++ Filter by node name
    public java.util.List<UserTask> getActiveNodes(
            String user,
            String nodeName,
            int minPriority,
            int maxPriority,
            int max,
            String endDate) throws RemoteException {
        String[] users = {user};

        FindTasksInfo fti = new FindTasksInfo();
        
        fti.setTaskName(nodeName);
        fti.setUserIds(users);                             
        fti.setInclWorkflows(true);
        fti.setInclReminders(false);
        fti.setInclGroup(false);
        fti.setInclDeputy(false);
        fti.setInclActivities(false);
        fti.setHighestPriority(maxPriority);
        fti.setLowestPriority(minPriority);
        fti.setSortOrder(UserTaskSortOrderC.PRIORITY_DATE_NAME);
     
        //FindResult result = indexClient.ix.findFirstTasks(clientInfo, fti, max);
        FindResult result = _connection.ix().findFirstTasks(fti, max);
        java.util.List<UserTask> activeNodes = java.util.Arrays.asList(result.getTasks());

        return activeNodes;
    }
    
    public java.util.List<WFDiagram> getAllActiveWorkflows(String user, int max) throws RemoteException {
        String[] users = {user};

        FindWorkflowInfo fwi = new FindWorkflowInfo();
                
        fwi.setUserIds(users);
        fwi.setType(WFTypeC.ACTIVE);
        fwi.setInclDeleted(false);

        //FindResult result = indexClient.ix.findFirstWorkflows(clientInfo, fwi, max, WFDiagramC.mbLean);
        FindResult result = _connection.ix().findFirstWorkflows(fwi, max, WFDiagramC.mbLean);
     
        java.util.List<WFDiagram> workflows = java.util.Arrays.asList(result.getWorkflows());

        //indexClient.ix.findClose(clientInfo, result.getSearchId());
        _connection.ix().findClose(result.getSearchId());

        return workflows;
    }
    // ++++++

    public boolean isWorkflowTemplate(String template) throws RemoteException {

        //WFDiagram workflow = indexClient.ix.checkoutWorkFlow(clientInfo, template, WFTypeC.TEMPLATE, WFDiagramC.mbLean, LockC.NO);
        WFDiagram workflow = _connection.ix().checkoutWorkFlow(template, WFTypeC.TEMPLATE, WFDiagramC.mbLean, LockC.NO);

        if (workflow != null) {
            return true;
        }

        return false;
    }

    public java.util.List<WFDiagram> getWorkflows(
            String template,
            String user,
            int max) throws RemoteException {
        String[] users = {user};

        FindWorkflowInfo fwi = new FindWorkflowInfo();
        fwi.setTemplateId(template);
        fwi.setUserIds(users);
        fwi.setType(WFTypeC.ACTIVE);
        fwi.setInclDeleted(false);

        //FindResult result = indexClient.ix.findFirstWorkflows(clientInfo, fwi, max, WFDiagramC.mbLean);
        FindResult result = _connection.ix().findFirstWorkflows(fwi, max, WFDiagramC.mbLean);
     
        java.util.List<WFDiagram> workflows = java.util.Arrays.asList(result.getWorkflows());

        //indexClient.ix.findClose(clientInfo, result.getSearchId());
        _connection.ix().findClose(result.getSearchId());

        return workflows;
    }

    /**
     * 
     * @param user not used
     * @param minPriority
     * @param maxPriority
     * @param max
     * @param endDate
     * @return
     * @throws RemoteException 
     */
    public java.util.List<UserTask> getTaskList(
            String user,
            int minPriority,
            int maxPriority,
            int max,
            String endDate) throws RemoteException {
        String[] users = {user};

        FindTasksInfo fti = new FindTasksInfo();

        fti.setInclWorkflows(false);
        fti.setInclReminders(true);
        fti.setInclGroup(false);
        fti.setInclDeputy(false);
        fti.setInclActivities(false);
        fti.setHighestPriority(maxPriority);
        fti.setLowestPriority(minPriority);
        fti.setSortOrder(UserTaskSortOrderC.PRIORITY_DATE_NAME);
        if (endDate != null && endDate.isEmpty()) {
            fti.setEndDateIso(endDate);
        }

        //FindResult result = indexClient.ix.findFirstTasks(clientInfo, fti, max);
        FindResult result = _connection.ix().findFirstTasks(fti, max);

        java.util.List<UserTask> tasks = java.util.Arrays.asList(result.getTasks());

        return tasks;
    }       

    public String getShortDesc(String objId) throws RemoteException {
        //EditInfo info = indexClient.ix.checkoutSord(clientInfo, objId, EditInfoC.mbSord, LockC.NO);
        EditInfo info = _connection.ix().checkoutSord(objId, EditInfoC.mbSord, LockC.NO);

        return info.getSord().getName();
    }

    public String getIndexValue(String objId, String index) throws RemoteException {
        //EditInfo info = indexClient.ix.checkoutSord(clientInfo, objId, EditInfoC.mbSord, LockC.NO);
        EditInfo info = _connection.ix().checkoutSord(objId, EditInfoC.mbSord, LockC.NO);

        for (ObjKey key : info.getSord().getObjKeys()) {
            if (key.getName().equals(index)) {
                String[] data = key.getData();

                if (data != null && data.length > 0) {
                    return data[0];
                }

                return null;
            }
        }

        return null;
    }

//  public void setIndexValue(String objId, String index, String value) throws RemoteException {
//    EditInfo info = indexClient.ix.checkoutSord(clientInfo, objId, EditInfoC.mbSord, LockC.NO);
//
//    for (ObjKey key : info.getSord().getObjKeys()) {
//      if (key.getName().equals(index)) {
//        String[] data = new String[1];
//        data[0] = value;
//        key.setData(data);
//        break;
//      }
//    }
//
//    indexClient.ix.checkinSord(clientInfo, info.getSord(), SordC.mbLean, LockC.NO);
//  }
    public int getUserId(String user) throws RemoteException {
        String[] users = {user};

        //UserInfo[] info = indexClient.ix.checkoutUsers(clientInfo, users, CheckoutUsersC.BY_IDS, LockC.NO);
        UserInfo[] info = _connection.ix().checkoutUsers(users, CheckoutUsersC.BY_IDS, LockC.NO);

        if (info != null && info.length > 0) {
            return info[0].getId();
        }

        throw new RemoteException("User '" + user + "' not found");
    }

    public UserInfo[] getGroups() throws RemoteException {
        //return indexClient.ix.checkoutUsers(clientInfo, null, CheckoutUsersC.ALL_GROUPS, LockC.NO);
        return _connection.ix().checkoutUsers(null, CheckoutUsersC.ALL_GROUPS, LockC.NO);
    }

    public UserInfo[] getUsers() throws RemoteException {
        //return indexClient.ix.checkoutUsers(clientInfo, null, CheckoutUsersC.ALL_USERS, LockC.NO);
        return _connection.ix().checkoutUsers(null, CheckoutUsersC.ALL_USERS, LockC.NO);
    }

    public UserInfo[] getGroupMembers(String[] groupName) throws RemoteException, IOException, FileNotFoundException {
        UserInfo[] members;
        try {
            //members = indexClient.ix.checkoutUsers(clientInfo, groupName, CheckoutUsersC.MEMBERS_OF_GROUP, LockC.NO);
            members = _connection.ix().checkoutUsers(groupName, CheckoutUsersC.MEMBERS_OF_GROUP, LockC.NO);
        } catch (RemoteException ex) {
            try {
                //members = indexClient.ix.checkoutUsers(clientInfo, groupName, CheckoutUsersC.MEMBERS_OF_GROUP, LockC.NO);
                members = _connection.ix().checkoutUsers(groupName, CheckoutUsersC.MEMBERS_OF_GROUP, LockC.NO);
            } catch (RemoteException rex) {
                throw new RemoteException("Group not found : " + ex.getMessage());
            }
        }
        return members;
    }

    public String getKey(Sord sord, String key) {
        if (sord == null || key == null || key.isEmpty()) {
            return null;
        }
        for (ObjKey objKey : sord.getObjKeys()) {
            if (objKey.getName().equals(key)) {
                if (objKey.getData().length != 0 && !objKey.getData()[0].isEmpty()) {
                    return objKey.getData()[0];
                } else {
                    return "";
                }
            }
        }
        return null;
    }

    public void setKey(Sord sord, String key, String value) {
        if (sord == null || key == null || value == null) {
            return;
        }
        for (ObjKey objKey : sord.getObjKeys()) {
            if (objKey.getName().equals(key)) {
                objKey.setData(new String[]{value});
            }
        }
    }

    public List<WFDiagram> getWorkflows(String templateName, String userName) throws RemoteException {
        List<WFDiagram> ret = new ArrayList<WFDiagram>();

        FindWorkflowInfo findWorkflowInfo = new FindWorkflowInfo();
        findWorkflowInfo.setActiveUserIds(new String[]{userName});
        findWorkflowInfo.setType(WFTypeC.ACTIVE);
        findWorkflowInfo.setTemplateId(templateName);

        //FindResult findResult = indexClient.ix.findFirstWorkflows(clientInfo, findWorkflowInfo, 100, WFDiagramC.mbAll);
        FindResult findResult = _connection.ix().findFirstWorkflows(findWorkflowInfo, 100, WFDiagramC.mbAll);
        ret.addAll(Arrays.asList(findResult.getWorkflows()));
        while (findResult.isMoreResults()) {
            //findResult = indexClient.ix.findNextWorkflows(clientInfo, findResult.getSearchId(), ret.size(), 100);
            findResult = _connection.ix().findNextWorkflows(findResult.getSearchId(), ret.size(), 100);
            ret.addAll(Arrays.asList(findResult.getWorkflows()));
        }

        return ret;
    }

    public WFNode getNode(WFDiagram wFDiagram, String nodeName, boolean contains) {
        if (wFDiagram == null || nodeName == null || nodeName.isEmpty()) {
            return null;
        }
        for (WFNode wFNode : wFDiagram.getNodes()) {
            if (wFNode.getName().equals(nodeName) || (contains == true && wFNode.getName().contains(nodeName))) {
                return wFNode;
            }
        }
        return null;
    }

    public Sord getSord(String sordId, EditInfoZ editInfoZ) throws RemoteException {
        if (editInfoZ == null || sordId == null || sordId.isEmpty()) {
            return null;
        }
        //return indexClient.ix.checkoutSord(clientInfo, sordId, editInfoZ, LockC.NO).getSord();
        return _connection.ix().checkoutSord(sordId, editInfoZ, LockC.NO).getSord();
    }

    public WFNode getNode(WFDiagram wFDiagram, int nodeId) {
        WFNode ret = null;
        for (WFNode wFNode : wFDiagram.getNodes()) {
            if (wFNode.getId() == nodeId) {
                ret = wFNode;
            }
        }
        return ret;
    }

    public List<WFNode> getSuccNodes(WFDiagram wFDiagram, int nodeId) {
        List<WFNode> nodes = new ArrayList<WFNode>();
        for (WFNodeAssoc wFNodeAssoc : wFDiagram.getMatrix().getAssocs()) {
            if (wFNodeAssoc.getNodeFrom() == nodeId) {
                nodes.add(getNode(wFDiagram, wFNodeAssoc.getNodeTo()));
            }
        }
        return nodes;
    }

    public int checkinWorkflow(WFDiagram wFDiagram, WFDiagramZ wFDiagramZ) throws RemoteException {
        //return indexClient.ix.checkinWorkFlow(clientInfo, wFDiagram, wFDiagramZ, LockC.NO);
        return _connection.ix().checkinWorkFlow(wFDiagram, wFDiagramZ, LockC.NO);
    }

    public WFEditNode beginEditWorkflowNode(int flowId, int nodeId) throws RemoteException {
        //return indexClient.ix.beginEditWorkFlowNode(clientInfo, flowId, nodeId, LockC.NO);
        return _connection.ix().beginEditWorkFlowNode(flowId, nodeId, LockC.NO);
    }

    public void endEditWorkflowNode(WFEditNode wFEditNode, int[] nextNodeIds) throws RemoteException {
        //indexClient.ix.endEditWorkFlowNode(clientInfo, wFEditNode.getFlowId(), wFEditNode.getNodeId(), false, false, wFEditNode.getNode().getName(), wFEditNode.getNode().getComment(), nextNodeIds);
        _connection.ix().endEditWorkFlowNode(wFEditNode.getFlowId(), wFEditNode.getNodeId(), false, false, wFEditNode.getNode().getName(), wFEditNode.getNode().getComment(), nextNodeIds);
    }

    public int checkinSord(Sord sord, SordZ sordZ) throws RemoteException {
        //return indexClient.ix.checkinSord(clientInfo, sord, sordZ, LockC.NO);
        return _connection.ix().checkinSord(sord, sordZ, LockC.NO);
    }

    public DocMask checkoutDocMask(String maskName) {
        DocMask ret = null;
        try {
            //ret = indexClient.ix.checkoutDocMask(clientInfo, maskName, DocMaskC.mbAll, LockC.NO);
            ret = _connection.ix().checkoutDocMask(maskName, DocMaskC.mbAll, LockC.NO);
        } catch (RemoteException rex) {
        }
        return ret;
    }

    public StatusDoc getStatus(String user, String subName) throws RemoteException {
        String path = basePath + PATH_SEP + user + PATH_SEP + subName + PATH_SEP + subName + "_status";

        Sord statusSord = getSord(path);

        if (statusSord == null) {
            path = basePath + PATH_SEP + user + PATH_SEP + subName;

            Sord subSord = getSord(path);

            if (subSord == null) {
                throw new RemoteException("Subscription no longer available in ELO");
            }

            StatusDoc doc = new StatusDoc();
            doc.setName(subName + "_status");
            doc.setLastRun(null);
            doc.setExitCode(0);
            doc.setUser(user);

            return doc;
        }

        DocReader reader = new DocReader();

        GetMethod get = new GetMethod(statusSord.getDocVersion().getUrl());
        HttpConnection con = null;

        try {
            con = new HttpConnection(get.getURI().getHost(), get.getURI().getPort());
        } catch (IOException ioex) {
            throw new RemoteException("Unable to retrieve status");
        }

        try {

            con.open();
            HttpState state = new HttpState();
            get.setFollowRedirects(true);

            get.execute(state, con);

            if (get.getStatusCode() != HttpStatus.SC_OK) {
                con.close();
                throw new IOException("Unable to reatrieve status document");
            }
            String content = get.getResponseBodyAsString();
            con.close();
            return reader.readStatus(content);
        } catch (IOException ioex) {
            con.close();
            throw new RemoteException("Unable to retrieve status");
        }
    }

    public FindResult getNewDocuments(String objId, boolean recursive) throws RemoteException {
        FindChildren findChildren = new FindChildren();

        findChildren.setParentId(objId);

        if (recursive) {
            findChildren.setEndLevel(-1);
        }

        FindInfo info = new FindInfo();
        info.setFindChildren(findChildren);

        //return indexClient.ix.findFirstSords(clientInfo, info, 100, SordC.mbLean);
        return _connection.ix().findFirstSords(info, 100, SordC.mbLean);
    }

    public FindResult getDocuments(FindResult result, int offset) throws RemoteException {

        //return indexClient.ix.findNextSords(clientInfo, result.getSearchId(), offset, 100, SordC.mbLean);
        return _connection.ix().findNextSords(result.getSearchId(), offset, 100, SordC.mbLean);
    }

    public String getUserEmail(String user) throws RemoteException {
        String[] users = {user};

        //UserInfo[] info = indexClient.ix.checkoutUsers(clientInfo, users, CheckoutUsersC.BY_IDS, LockC.NO);
        UserInfo[] info = _connection.ix().checkoutUsers(users, CheckoutUsersC.BY_IDS, LockC.NO);

        if (info != null && info.length > 0) {
            String[] props = info[0].getUserProps();
            String email = props[UserInfoC.PROP_NAME_EMAIL];

            String e4 = props[7];

            if (e4.toLowerCase().contains("sendmail")) {
                return email;
            } else {
                return null;
            }
        }

        throw new RemoteException("User '" + user + "' not found");
    }
    
    // +++ SL +++
    public String getIndexServerVersion() {
        return this._connection.getImplVersion();
        /*try {
            return this.indexClient.getImplVersion();
        } catch (de.elo.utils.net.RemoteException RE) {
            return null;
        }*/
    }
    
    public List<Sord> getSords(FindInfo fi) throws RemoteException
    {        
        List<Sord> sords = new ArrayList<Sord>();
        FindResult fr = null;
        
        int idx = 0;
        SordZ sordZ = new SordZ(SordC.mbName|SordC.mbObjKeys|SordC.mbDocVersion|SordC.mbMask);
        //fr = indexClient.ix.findFirstSords(clientInfo, fi, idx, sordZ);
        fr = _connection.ix().findFirstSords(fi, idx, sordZ);

        while(true)
        {               
            if(!fr.isMoreResults()) break;
            idx += fr.getSords().length;
            //fr = indexClient.ix.findNextSords(clientInfo, fr.getSearchId(), idx, 1000, sordZ);
            fr = _connection.ix().findNextSords(fr.getSearchId(), idx, 1000, sordZ);
            sords.addAll(Arrays.asList(fr.getSords()));
        }

        if(fr != null)
        {
            //indexClient.ix.findClose(clientInfo, fr.getSearchId());
            _connection.ix().findClose(fr.getSearchId());
        }
        
        return sords;
    }
    
    public List<WFDiagram> getWorkflowBySord(Sord sord, String templateName) throws RemoteException 
    {	                  
        List<WFDiagram> workflows = new ArrayList<WFDiagram>();
        FindWorkflowInfo findWorkflowInfo = new FindWorkflowInfo();  

        findWorkflowInfo.setObjId(Integer.toString(sord.getId()));  
        findWorkflowInfo.setTemplateId(templateName);
        findWorkflowInfo.setType(WFTypeC.ACTIVE);
        //FindResult fr = indexClient.ix.findFirstWorkflows(clientInfo, findWorkflowInfo, 0, WFDiagramC.mbAll);
        FindResult fr = _connection.ix().findFirstWorkflows(findWorkflowInfo, 0, WFDiagramC.mbAll);

        workflows.addAll(Arrays.asList(fr.getWorkflows()));

        while (fr.isMoreResults()) 
        {
            //fr = indexClient.ix.findNextWorkflows(clientInfo, fr.getSearchId(), workflows.size(), 100);
            fr = _connection.ix().findNextWorkflows(fr.getSearchId(), workflows.size(), 100);
            workflows.addAll(Arrays.asList(fr.getWorkflows()));
        }                         
	                
        return workflows;
    }
    
    public List<String> getTemplateNames() {
        List<String> ret = new ArrayList();
        if (this.templates == null) {
          try
          {
            this.templates = new ArrayList();
            FindWorkflowInfo findWorkflowInfo = new FindWorkflowInfo();

            findWorkflowInfo.setType(WFTypeC.TEMPLATE);
            //FindResult findResult = this.indexClient.ix.findFirstWorkflows(this.clientInfo, findWorkflowInfo, 100, WFDiagramC.mbAll);
            FindResult findResult = this._connection.ix().findFirstWorkflows(findWorkflowInfo, 100, WFDiagramC.mbAll);
            this.templates.addAll(Arrays.asList(findResult.getWorkflows()));
          }
          catch (RemoteException ex)
          {
            System.out.println("error: " + ex);
            ex.printStackTrace();
          }
        }
        for (WFDiagram wFDiagram : this.templates) {
          if (!wFDiagram.getName().startsWith("elowf")) {
            ret.add(wFDiagram.getName());
          }
        }
        return ret;
    }
                    
    // +++ END +++
}
