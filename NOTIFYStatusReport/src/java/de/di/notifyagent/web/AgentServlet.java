package de.di.notifyagent.web;

import de.di.notifyagent.app.Application;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 
 * @author A. Sopicki
 * reviewed Cioaba Stefan
 */
public class AgentServlet extends HttpServlet {

    public static final String agentAttribute = "de.di.notifyagent.agent";
    public static java.util.List<String> errorStatus = null;

    @Override
    public void init() throws ServletException {
        Application agent = (Application) getServletContext().getAttribute(AgentServlet.agentAttribute);
        if (agent == null) {
            try {
                String path = getServletContext().getRealPath("/conf");
                if (path == null) {
                    getServletContext().log("Config directory missing!");
                    throw new Exception("Config directory missing! Service initialisation aborted.");
                }
                File configDir = new File(path);
                agent = new Application(configDir.toURI().toURL());
                getServletContext().setAttribute(AgentServlet.agentAttribute, agent);
                agent.start();
                errorStatus = agent.getErrorStatus();
            } catch (Exception ex) {
                getServletContext().log("Startup failed due to Exception. ", ex);
                if (errorStatus == null) {
                    errorStatus = new java.util.ArrayList<String>();
                    errorStatus.add(ex.getMessage());
                }
            }
        }
    }

    @Override
    public void destroy() {
        Application agent = (Application) getServletContext().getAttribute(AgentServlet.agentAttribute);
        if (agent != null) {
            agent.shutdown();
            try {
                agent.join(5000);
            } catch (Exception iex) {
                getServletContext().log("Failedto destry due to Exception. ", iex);
            }
            getServletContext().removeAttribute(AgentServlet.agentAttribute);
        }
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "MAILsender controller servlet";
    }
    // </editor-fold>
}
