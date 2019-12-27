<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
        <meta charset="ISO-8859-1">
        <title>Study plan</title>
        <style><%@include file='css/style.css'%></style>
</head>

<body>
	
	<div class="title_1"><h2>Enter your study plan</h2></div> 
	
	<div class="form_container">
    
        <div class="btn_container"><input type="button" id="button" value="Add some work"></div>

        <form name="form" action="teacher_cabinet" method="post">

            <div id="works_container"> </div>

            <div class="btn_container"><input type="submit" value="Submit"></div>

        </form>
		
            <script>
            var count = 1;
            button.addEventListener("click", function(){
                var input = document.createElement('INPUT');
                input.type = 'date';
                input.setAttribute("data-id", "id" + count);
                input.name = "dates[]";
                document.querySelector('#works_container').appendChild(input);
                count++;
            });
            </script>
	
	</div>
	
</body>

</html>