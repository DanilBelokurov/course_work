<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>

	<title>Signup page</title>
	
	<script><%@include file='js/jquery-3.2.1.min.js' %></script>
	
	<script><%@include file='js/checker.js' %></script>
	
	<style><%@include file='css/main.css'%></style>

</head>

<body>

	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100 p-t-50 p-b-90">

				<form name="form" id="form" method="post" class="login100-form validate-formflex-sbflex-w">
					<input type="hidden" name="redirectId" value="${param.redirectId}" />
					<span class="login100-form-title p-b-51"> Signup </span>

					<div class="wrap-input100 validate-input m-b-16"
						data-validate="Name is required">
						<input class="input100" type="text" required placeholder="Name"
							name="name" autofocus required> <span class="focus-input100"></span>
					</div>

					<div class="wrap-input100 validate-input m-b-16"
						data-validate="Username is required">
						<input class="input100" type="text" required placeholder="Login"
							name="login" autofocus required"> <span class="focus-input100"></span>
					</div>

					<div class="wrap-input100 validate-input m-b-16"
						data-validate="Password is required">
						<input class="input100" type="password" required
							placeholder="Password" name="password" id="password"> <span
							class="focus-input100"></span>
					</div>

					<div class="wrap-input100 validate-input m-b-16"
						data-validate="Password is required">
						<input class="input100" type="password" required
							placeholder="Repeat your password" name="password2" id="password2"> <span
							class="focus-input100"></span>
					</div>

					<div class="wrap-input100 validate-input m-b-16" data-validate="Role is required">
						 <select id="prof" class="input100" name="role" >
						    <option value="Student" selected>Student</option>
						    <option class="teacher-option" value="Teacher">Teacher</option>
						  </select>
					</div>
					
					<div class="hover_bkgr_fricc">
					    <span class="helper"></span>
					    <div class="popup">
					        <div class="popupCloseButton">x</div>
					        <div class="btn_container"><input type="button" id="button" class="button-popup" value="Add some work"></div>
					        <div id="works_container"> </div>
					    </div>
					</div>
					
					<script>
		            var count = 1;
		            button.addEventListener("click", function(){
		            	
		                var input = document.createElement('INPUT');
		                var check = document.createElement('INPUT');
		                
		                var div = document.createElement('div');
		              	
		                var label = document.createElement('input');
		                
		                div.id = 'conteiner_for_checkbox';
		                
		                input.id = 'input-popup-dates';
		                input.type = 'date';
		                
		                check.type = 'checkbox';
		                check.name = 'checks[]';
		                
		                label.for = 'checkbox';
		                label.id = 'label_for_checkbox';
		                label.value = "report";
		                
		                input.setAttribute("data-id", "id" + count);
		                input.name = "dates[]";
		                
		                div.appendChild(check);
		                div.appendChild(label);
		                
		                document.querySelector('#works_container').appendChild(input);
		                document.querySelector('#works_container').appendChild(div);
		                count++;
		            });
		            </script>
					
					<script type="text/javascript">
						$(document).ready(function() {
							  $('#prof').change(function() {
							  $('.hover_bkgr_fricc').hide();
							    if ($("#prof :selected").val() != "Student") {
							      $("#subject").css("display", "inline-block");
							      $('.hover_bkgr_fricc').show();
							    } else {
							      $('.hover_bkgr_fricc').hide();
							      $("#subject").css("display", "none");
							    }
							  });
							  $('.popupCloseButton').click(function(){
							        $('.hover_bkgr_fricc').hide();
							    });
							});
					</script>
					
					<div class="wrap-input100 validate-input m-b-16" data-validate="Role is required">
						 <select id="subject" class="input100" name="subject" >
						    <option value="GTofC" selected>General theory of communication</option>
						    <option value="DSP">Digital signal processing</option>
						    <option value="TP">Programming technology</option>
						  </select>
					</div>
					
					<div class="wrap-input100 checkboxes_div m-b-16"
						data-validate="Password is required">
						<div class="checkboxes">
							<input type="checkbox" name="groups[]" value="5711">
							<label for="5711">5711</label>
						</div>
						
						<div class="checkboxes">
							<input type="checkbox" name="groups[]" value="5712">
							<label for="5712">5712</label>
						</div>
						
						<div class="checkboxes">
							<input type="checkbox" name="groups[]" value="5721">
							<label for="5721">5721</label>
						</div>
						
						<div class="checkboxes">
							<input type="checkbox" name="groups[]" value="5722">
							<label for="5722">5722</label>
						</div>
						
						<div class="checkboxes">
							<input type="checkbox" name="groups[]" value="5723">
							<label for="5723">5723</label>
						</div>
					</div>

					<div class="container-login100-form-btn m-t-17">
						<button class="login100-form-btn" type="submit" onClick="return checkForm();"value="signup">Signup</button>
					</div>

				</form>
			</div>
		</div>
	</div>



</body>
</html>
