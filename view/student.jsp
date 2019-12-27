<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ page import=" model.User"%>
<%@ page import=" database.FilesToDB"%>
<%@ page import=" model.Student"%>
<%@ page import="java.util.ArrayList"%>


<!DOCTYPE html>
<html>

<head>
	<meta charset="RU">
	<title>Student page</title>
		<script><%@include file='js/anychart-base.min.js' %></script>
		<script><%@include file='js/jquery-3.2.1.min.js' %></script>
		<script><%@include file='js/diagram.js' %></script>
		<script><%@include file='js/userpage.js' %></script>
		<style><%@include file='css/teacherpage.css' %></style>
</head>

<body>

	<div class="student_name">
		<h2> <% 
			String name = ((User)request.getAttribute("user")).getName(); 
			out.println(name);
		%></h2>
	</div>

	<div id="marks_table">
		<table border ="1" width="500" align="center"> 
         <tr > 
          <th><b>Subject</b></th> 
          <th colspan="10"><b>Marks</b></th> 
         </tr> 
        <%ArrayList<Student> std = (ArrayList<Student>)request.getAttribute("students"); 
        for(int i=0; i<std.size();i++){%>
            <tr> 
                <td id="cell"><%=std.get(i).getSubject()%></td> 
                <%for(int j=0; j<std.get(i).getMark().size();j++) {
                if(!std.get(i).getWork().get(j).equals("null")){
                
                	FilesToDB ftodb = new FilesToDB();
                	int flag = ftodb.getReportStatus(std.get(i).getWork().get(j));
                	System.out.println(flag);
                %>
                	<td id="<%=std.get(i).getWork().get(j)%>" class="<%=flag%>"><%=std.get(i).getMark().get(j).toString()%></td> 
                <%} 
                else{ %>
                <td id="cell" class=""><%=std.get(i).getMark().get(j).toString()%></td>
                <%} %>
                <%} %>
            </tr> 
            <%}%> 
        </table>
        
        <div class="hover_bkgr_fricc">
		    <span class="helper"></span>
		    <div class="popup">
		        <div class="popupCloseButton">x</div>
		        <form method="post" action="lk" enctype="multipart/form-data">
			        Select file to upload:
			        <br />
			        <input class="input-file-popup" type="file" name="file"/>
			        <input hidden="true" id="work_id" class="input-popup" type="text" name="work_id" value=""/>
			        <button class="diag_button" type="submit">Submit</button>
			    </form>
		    </div>
		</div>
		
        <div class="diag_button"> 
        	
        	<button class="btn" id="btn" onClick="diagForStudent();"> Create diagram </button>
        
        	<a class="btn" id="btn" href="logout"> Logout</a>
        	
        </div>
        
        
	</div>
	
	<div id="container" style="width: 500px; height: 400px;"></div>
	
</body>
</html>