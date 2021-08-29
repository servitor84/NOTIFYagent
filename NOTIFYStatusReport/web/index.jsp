<%@page import="java.util.Locale"%><%

if ( request.getLocale().equals(Locale.GERMAN) || request.getLocale().equals(Locale.GERMANY) ) {
  response.sendRedirect("index.html");
} else {
  response.sendRedirect("index_en.html");
}
%>