String user = Sessions.getCurrent().getAttribute("userId");

// Check if user is logged in or not
if (user == null) Executions.sendRedirect("login.zul");
