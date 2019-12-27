<%@page import="model.User"%> 
<%@page import="model.Teacher"%> 
<%@page import="java.util.ArrayList"%> 
<%@page import="database.TeacherDB"%> 
<%@ page import=" database.FilesToDB"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Teacher</title>
	<script><%@include file='js/anychart-base.min.js' %></script>
	<script><%@include file='js/jquery-3.2.1.min.js' %></script>
	<script><%@include file='js/diagram.js' %></script>
	<script><%@include file='js/teacherpage.js' %></script>
	<style><%@include file='css/teacherpage.css' %></style>
</head>
<body >

	<div class="mark_list"><h2><%=request.getAttribute("name")%></h2></div>
	
	<div class="group_peaker">
	
   			<%
   			TeacherDB TDB= (TeacherDB)request.getAttribute("tdb");
	   		ArrayList<Teacher> std = TDB.select();
		    String group = new String();
		    for(int i=0; i < TDB.getNumberGroup();i++){
		   		group = String.valueOf(TDB.getGroup(i));
			%>
			<form method="get"><button class="btn" name="group" value=<%=group%> type="submit"><%=group%></button></form>
	  		<%}%>

	</div>
	
	<div>
	
		<!-- ------------------- MARK TABLE ------------------- -->

		<table id="mark_table" border ="1" width="600" align="center"> 
	         
	         <tr bgcolor=""> 
	         
	         	<th><b>Student</b></th> 
	          	<%
	          	for(int j=0; j < TDB.getNumberOfDates();j++){%>
	          		<th><b><%=TDB.getDate(j)%></b></th>   
	         	<%}%>
	             
	         </tr> 
	         
	        <%for(int i=0; i < std.size();i++){%>
	            <tr> 
	                <td><%=std.get(i).getStudent()%></td> 
	                
	                <%
	                	for(int j = 0; j < TDB.getNumberOfDates(); j++){
                
                			if(std.get(i).getMark().containsKey(TDB.getDate(j))) {
		                	
	                			String currentValue = new String(std.get(i).getStudent() + String.valueOf(TDB.getDate(j)));
		                	
	                    		String[] tmp= currentValue.split(" ");
		                    
	       			 			currentValue = tmp[0] + "_" + tmp[1] + "_" + String.valueOf(TDB.getCurrGroup()) + "_" + tmp[2] + "_" + String.valueOf(j);
		       			 	
       			 				if(!std.get(i).getWork().get(j).equals("null")){
	       			 					
	       			 				FilesToDB ftodb = new FilesToDB();
			                    	int flag = ftodb.getReportStatus(std.get(i).getWork().get(j));
	       			 			
       			 					currentValue += "_1";
	       			%> 
	       			 			
               				 		<td id="<%=new String(std.get(i).getWork().get(j))%>" class="<%=flag%>">
               				 	 
				                		<form method="post">
				                			<input class="in_cell_input" type="text" placeholder=<%=std.get(i).getMark().get(TDB.getDate(j))%> name="newMark1" value=""/>
				                			<button hidden="true" type="submit" name="currValue1" value=<%=currentValue%> ></button>
				                		</form>
		                		
		                			</td>
               				<%} else{ 
               					
                				currentValue+="_0";%>
                				
               					<td id="cell">
			                		<form method="post">
			                			<input type="text" placeholder=<%=std.get(i).getMark().get(TDB.getDate(j))%> name="newMark1" value=""/>
			                			<button hidden="true" type="submit" name="currValue1" value=<%=currentValue%> ></button>
			                		</form>
		                		</td>
                			<%} %>
		                	
	                 
	                 	<%}else{
	                	 
			                 String currentValue = std.get(i).getStudent() + String.valueOf(TDB.getDate(j));
			                 String[] tmp = currentValue.split(" ");
			    			 currentValue = tmp[0] + "_" + tmp[1] + String.valueOf(TDB.getCurrGroup()) + "_" + tmp[2] + "_" + String.valueOf(j);%>
	                	 
		                	 <% if(!std.get(i).getWork().get(j).equals("null")){
		                	 	currentValue+="_1";
		                	 	
		                	 	FilesToDB ftodb = new FilesToDB();
		                    	int flag = ftodb.getReportStatus(std.get(i).getWork().get(j));
		                	 %> 
               				 	<td id=<%=new String(std.get(i).getWork().get(j))%> class="<%=flag%>"> 
		                		<form method="post">
		                			<input class="in_cell_input" type="text" placeholder="" name="newMark1" value=""/>
		                			<button hidden="true" type="submit" name="currValue1" value=<%=currentValue%> >ok</button>
		                		</form>
		                	</td>
               				 <%} 
                			else{ 
                				currentValue += "_0";%>
               					<td id="cell"> 
		                		<form method="post">
		                			<input type="text" placeholder="" name="newMark1" value=""/>
		                			<button hidden="true" type="submit" name="currValue1" value=<%=currentValue%> >ok</button>
		                		</form>
		                	</td>
                			<%} %>
	                	 <%}%>
	           		<%}%>
	            </tr>
	            
		       <%}%> 
		</table>
		
		
		<!-- ------------------- POPUP ------------------- -->
		<div class="hover_bkgr_fricc">
		    <span class="helper"></span>
		    <div class="popup">
		        <div class="popupCloseButton">x</div>
		        
		        <form name="work_check_form" method="post">
		        	<input hidden="true" id="work_check" type="text" name="work_check" value="3"/>
			        <button class="btn" id="btn_work_check" >Accept</button>
			    </form>
			    
			    <form method="post">
		        	<input hidden="true" id="work_check1" type="text" name="work_check1" value="2"/>
			        <button class="btn" id="btn_work_check" type="submit">Disaccept</button>
			    </form>
			    
		    </div>
		</div>
		
		<!-- ------------------- DIAGRAMM CONSTRACTUR------------------- -->
		<div class="diag_button">
			<button class="btn" id="btn" onClick="diagForTeacher();"> Create diagram </button>
			<a class="btn" id="btn" href="logout"> Logout</a>
		</div>
	
		<!-- ------------------- REPORT DOWNLOAD BUTTON ------------------- -->
		<div hidden="true">
			<form method="get" target="_blank">
				<div class="diag_button">
					<input hidden="true" id="work_id" type="text" placeholder="" name="work_id" value=""/>
					<button class="btn" type="submit" id="btn_report">Get report</button>
				</div>
			</form>
		</div>
	
	</div>

	<div id="container" style="width: 500px; height: 400px;"></div>
	
</body>
</html>