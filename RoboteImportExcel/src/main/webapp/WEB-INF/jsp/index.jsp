<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>代码机器人导入Excel</title>
<style type="text/css">
.main{
    text-align: center; /*让div内部文字居中*/
    background-color: #fff;
    border-radius: 20px;
    width: 300px;
    height: 350px;
    margin: auto;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
}

</style>
</head>
<body>
	<div style="padding: 50px;" class="main">
		<form class="form-horizontal" id="form_table" action="/batchImport" enctype="multipart/form-data" method="post">  
		   	JSESSIONID:<input type="text" name="JSESSIONID">
		   	<br/>
		   	<input class="form-input" type="file" name="filename"></input>  
		    <button type="submit" class="btn">开始导入</button>  
		</form> 
			<br/>
		<img alt="" src="images/robot.png" style="width: 500px;height: 500px;">
		 
	</div>
</body>
</html>