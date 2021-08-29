<%-- 
    Document   : lizenz
    Created on : 14.10.2008, 12:43:33
    Author     : A. Sopicki
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"
        import="de.di.notifyagent.app.Application,de.di.notifyagent.web.AgentServlet"
        %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<fmt:bundle basename="de.di.notifyagent.web.resource.servlet_de">
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <meta http-equiv="refresh" content="5; URL=<%= request.getRequestURL().toString()%>" />
            <title><fmt:message key="page.title.text"></fmt:message></title>
            <style type="text/css">
                #footer {
                    margin-top: 3em;
                    font-size: 0.8em;
                }

                #logo {
                    text-align: left;
                }

                body {
                    background-color: #E9E9E9;
                    font-family: Arial,sans-serif;
                }
                div.filelist{
                    margin-top: 3em;
                    font-size: 0.8em;
                }
                div.status {
                    font-weight: bold;
                    color: green;
                    margin-bottom: 1em;
                }
                div.warning {
                    font-weight: bold;
                    color: red;
                }
                span.configurator {
                    font-size: 0.5em;
                }
                span.filename {
                    font-size: 0.8em;
                }
                span.headline {
                    font-size: 1.2em;
                }
                span.refresh {
                    font-size: 0.8em;
                }
                table.configurator {
                    width: 800px;
                    margin-bottom: 1em;
                }

                table.status {
                    border-collapse: collapse;
                }
                table.status td {
                    background-color: #cbcdcf;
                    border: 1px solid gray;
                    padding-top: 0.3em;
                    padding-bottom: 0.3em;
                    padding-left: 0.7em;
                    padding-right: 0.7em;
                }
                table.status{
                    width:600px;
                    margin-left:auto;
                    margin-right:auto;
                }
                table.status-top{
                    width:600px;
                    margin-left:auto;
                    margin-right:auto;
                }
                table.status td.name {
                    font-weight: bold;
                }
                table.status td.value {
                    text-align: right;
                }
                table.fullwidth {
                    width: 100%;
                }
                td.name{text-align: left; margin-left: 5px;}
            </style>
        </head>
        <body>
     
            <%
                Application mapper = (Application) application.getAttribute(AgentServlet.agentAttribute);
                if (mapper != null && mapper.isAlive()) {
                    java.util.Map<String, String> status = mapper.getStatus();                    
                    String productname = status.get("product_name");                                                                                                    
                    java.util.List<String> sendImmediatelyValues = mapper.loadSendImmediatelyValues();
                    // Loglevel                 
                    String loglevel = Application.getSettings().getProperty("Basic.LogLevel");                   

                    boolean dispatcher = Boolean.parseBoolean(status.get("dispatcher_status"));

                    if (dispatcher) {
            %>           
            <table class="status-top">
        
                <tr>
                    <td width = "10" height ="50">
                        <img src="images/ok.gif" height = "25">
                    </td>
                    <td width="5">
                    </td>
                    <td>
                        <div class="name">
                            <fmt:message key="system.active.text">
                                <fmt:param value="<%= productname%>" />
                            </fmt:message>
                        </div>
                    </td>
                </tr>
            </table>

            <div class="status"><hr></div>
            <table class="status">
                <tr>
                    <th colspan="2" align="left"><fmt:message key="system.properties.title.text"></fmt:message>
                        <span class="refresh">(<fmt:message key="system.refresh.text" ></fmt:message>)</span></th>
                </tr>
                <tr>
                    <td class="name"><fmt:message key="system.properties.product.text"></fmt:message></td><td class="value"><%= productname%></td>
                </tr>
                <tr>
                    <td class="name"><fmt:message key="system.properties.version.text"></fmt:message></td><td class="value"><%= status.get("version")%></td> <!-- ) settings.getProperty("app.version")-->
                </tr>
                
                <tr>
                    <td class="name"><fmt:message key="system.properties.dispatcher_state.text"></fmt:message></td><td class="value"><%
                        String state = status.get("dispatcher_state");
                        if (state.equals("Idle")) {
                        %><fmt:message key="system.state.idle.text"></fmt:message><%            } else if (state.equals("Processing")) {
                        %><fmt:message key="system.state.processing.text"></fmt:message><%                } else {
                        %><fmt:message key="system.state.finished.text"></fmt:message><%                        }

                        %></td>
                </tr>
                
                 <!-- logLevel -->
                <tr>
                      <td class="name"><fmt:message key="system.properties.loglevel"></fmt:message></td><td class="value"><%                       
                        if (loglevel.equals("OFF")) {
                        %><fmt:message key="system.properties.off.text"></fmt:message><%            
                        } 
                        else if (loglevel.equals("FATAL")) {
                        %><fmt:message key="system.properties.fatal.text"></fmt:message><%                
                        } 
                        else if(loglevel.equals("ERROR")) {
                        %><fmt:message key="system.properties.error.text"></fmt:message><%                        
                        }
                        else if(loglevel.equals("WARN")) {
                        %><fmt:message key="system.properties.warn.text"></fmt:message><%                        
                        }
                        else if(loglevel.equals("INFO")) {
                        %><fmt:message key="system.properties.info.text"></fmt:message><%                        
                        }
                        else if(loglevel.equals("DEBUG")) {
                        %><fmt:message key="system.properties.debug.text"></fmt:message><%                        
                        }
                        else if(loglevel.equals("TRACE")) {
                        %><fmt:message key="system.properties.trace.text"></fmt:message><%                        
                        }
                        else if(loglevel.equals("ALL")) {
                        %><fmt:message key="system.properties.all.text"></fmt:message><%                        
                        }

                        %></td><!--%= logLevel%-->
                </tr>
                
            </table>
            <br /><br />

            <br /><br />
            <table class="status">
                <thead>
                    <tr>
                        <th style="text-align: left;"><fmt:message key="system.profiles.label.text"></fmt:message>
                            (<fmt:message key="system.profiles.count.text"></fmt:message>
                            <%= mapper.getProfileList().size()%>)</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="name"><fmt:message key="profile.name"></fmt:message></td>
                        <td class="name"><fmt:message key="profile.type"></fmt:message></td>
                        <td class="name"><fmt:message key="profile.test"></fmt:message></td>
                    </tr>
                    <%
                        // SL - For the third column
                        // int i = sendImmediatelyValues.size()-1;
                        String[] tests = mapper.getTests();
                        // ---
                        for (String key : mapper.getProfileList().keySet()) {                                                       
                    %>
                    <tr>                        
                        <td><%= key%></td>
                        <td><%= mapper.getProfileList().get(key)%></td>    
                        <!-- SL - -->
                        <!-- <td></td> -->
                        <td>
                            <%                            
                            if (mapper.getMode().get(key).equals("TRUE")) { %>
                                <fmt:message key="profile.test.mode.test"></fmt:message>
                            <%
                            } 
                            else if (mapper.getMode().get(key).equals("FALSE")) { %>
                               <fmt:message key="profile.test.mode.ok"></fmt:message>
                            <%
                            } 
                            %>
                        </td>
                        <% //--i; %>
                        <!-- // %> --- -->                    
                     </tr>
                    <% 
                        }
                    %>
                </tbody>
            </table>
            <%
            } else {%>
            <table>
                
                <tr>
                    <td width="10" height="50">
                        <img src="images/unknown_error.gif" height="25">
                    </td>
                    <td width="5">
                    </td>
                    <td>
                        <div class="warning"><fmt:message key="system.error.text"></fmt:message></div>
                    </td>
                </tr>
            </table>


            <table class="status">
                <tr>
                    <td class="name"><fmt:message key="system.properties.product.text" ></fmt:message></td><td class="value"><%= productname%></td>
                </tr>
                <tr>
                    <td class="name"><fmt:message key="system.properties.version.text" ></fmt:message></td><td class="value"><%= status.get("version")%></td>
                </tr>
            </table>
            <%  }
            } else {%>
            <table>
               
                <tr>
                    <td width="10" height="50">
                        <img src="images/error.gif" height = "25">
                    </td>
                    <td width="5">
                    </td>
                    <td valign="middle">
                        <%
                            java.util.List<String> errorStatus = AgentServlet.errorStatus;
                            if (errorStatus != null && errorStatus.size() > 0) {
                        %>
                        <div class="warning" valign="middle"><fmt:message key="system.exception.text" ></fmt:message></div>
                        <div valign="middle">
                            <%
                                java.lang.StringBuilder builder = new StringBuilder();
                                for (String msg : errorStatus) {
                                    builder.append(msg.replaceAll("\n", "<br />"));
                                    builder.append("<br />");
                                }

                                out.println(builder.toString());
                            %>
                        </div>
                        <%
                        } else {
                        %>
                        <div class="warning" valign="middle"><fmt:message key="system.inactive.text"></fmt:message></div>
                        <%                            }
                        %>
                    </td>
                </tr>
            </table>

            <%
                    application.removeAttribute(AgentServlet.agentAttribute);
                }%>


            <div id="footer">

                <table class="fullwidth">

                    <tr>
                        <td>

                        </td>
                    </tr>
                </table>
            </div>
        </body>
    </html>
</fmt:bundle>