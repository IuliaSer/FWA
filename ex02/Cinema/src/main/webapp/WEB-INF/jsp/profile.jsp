<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page import="edu.school21.cinema.models.User" %>
<%@ page import="java.io.File" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="edu.school21.cinema.models.Auth" %>
<%@ page import="java.util.List" %>
<%@ page import="java.nio.file.attribute.BasicFileAttributes" %>
<%@ page import="java.nio.file.Path" %>
<%@ page import="java.nio.file.Paths" %>
<%@ page import="java.time.ZoneId" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <h1>Profile</h1>
</head>
<style type="text/css">
    #imageForm {
        float: left; /* Обтекание картинки текстом */
    }
    #text {
        margin-left: 310px; /* Отступ от левого края */
    }
    #table1 {
        margin-left: 310px; /* Отступ от левого края */
    }
</style>

<style>
    table, th, td {
        border:1px solid black;
    }
</style>

<body>
    <div id="imageForm">
        <img src ="data:image/png;base64, <%= request.getAttribute("image") %>" width = "300" height = "300" >
        <br> <br>
    <form method="post" action="<%= request.getContextPath() %>/images" enctype="multipart/form-data">
        <input type="file" name="file" />
        <input type="submit" value="Upload" />
    </form>
    </div>

    <div id="text">
        <% User user = (User) request.getSession().getAttribute("user");%>
        <%= user.getFirstName() %>
        <%= user.getLastName() %>
        <br>
        <%= user.getEmail() %>
        <br>
    </div>

    <%
        List<Auth> list = (List<Auth>)request.getSession().getAttribute("auth");
    %>
    <div id="table1">
    <table style="width:80%">
        <tr>
            <td>User id</td>
            <td>Date</td>
            <td>Ip</td>
        </tr>

        <%if(!list.isEmpty()) { %>
            <%for(Auth auth: list) { %>
                <tr>
                    <td> <%= auth.getUser_id() %> </td>
                    <td> <%= auth.getDate() %> </td>
                    <td> <%= auth.getIp() %> </td>
                </tr>
            <%}%>
        <%}%>
        </table>
    </div>
    <br> <br>

    <%
        String imagePath = (String) request.getSession().getAttribute("imagePath");
        File folder = new File(imagePath);
        File[] listOfFiles = folder.listFiles();
    %>

    <div class="table2">
        <table style="width: 100%">
            <th>File name</th>
            <th>Size</th>
            <th>MIME</th>
            <th>TIME</th>
                <%for(File file: listOfFiles) {
                    Path path = Paths.get(file.getAbsolutePath());
                    BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
                    String create_time = attr.creationTime().toInstant().atZone(ZoneId.systemDefault()).toString();
                %>
                    <tbody>
                        <td>
                            <a href="images/<%=user.getId()%>/<%=file.getName()%>"><%=file.getName()%></a>
                        </td>
                        <td>
                            <%=file.length()%> kb
                        </td>
                        <td>
                            <%=Files.probeContentType(file.toPath())%>
                        </td>
                        <td>
                            <%=create_time%>
                        </td>
                    </tbody>
                <%}%>

        </table>
    </div>


    <br> <br>
    <br> <br>
    <br> <br>
    <form method="post" action="<%= request.getContextPath() %>/" enctype="multipart/form-data">
            <input type="submit" value="Logout" >
    </form>

</body>
</html>


