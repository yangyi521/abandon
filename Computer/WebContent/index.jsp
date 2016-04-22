<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统登录</title>
</head>
<body>
<form action="querybySN.action" method = "post" id="dev_sn" name="dev_sn">
<input type = "text" name="username" />
<input type = "text" name="password" />
<input type = "submit" value="登录" />
<input type="hidden" name="dev_SN" value="xingming"><a href="javascript:dev_sn.submit()"><font color="red">hidden</font></a>

</form>

</body>
</html>