<%--
  Created by IntelliJ IDEA.
  User: wupeng
  Date: 2020/9/8
  Time: 17:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + request.getContextPath() + "/";
%>
<script>
    $.ajax({
        url:"",
        data:{

        },
        type:"",
        dataType:"json",
        success:function (data) {

        }
    })
</script>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
</head>
<body>

//创建时间：当前系统时间
String  createTime= DateTimeUtil.getSysTime();
//创建人：当前登录用户
String createBy=((User)request.getSession().getAttribute("user")).getName();
</body>
</html>
