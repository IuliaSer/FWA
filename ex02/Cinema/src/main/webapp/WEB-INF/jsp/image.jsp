

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
</head>
<body>
<img src="data:image/png;base64,<%=request.getAttribute("image")%>" style="height: 30%; width: 30%;">
</body>
</html>