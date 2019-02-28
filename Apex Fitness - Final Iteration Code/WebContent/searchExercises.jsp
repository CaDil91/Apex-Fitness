<!DOCTYPE html>
<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
<meta charset="utf-8">
<title>APEX Fitness</title>
<link rel="stylesheet" href="styles/styles.css" type="text/css" />
</head>
<body>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$('#checkBtn').click(function() {
				
				var checkboxes = document.querySelectorAll('input[type="checkbox"]');
				var checkedOne = Array.prototype.slice.call(checkboxes).some(x => x.checked);
	
				if (!checkedOne) {
					alert("You must select at least one Body Target.");
					return false;
				}
	
			});
		});
		
		// Script borrowed from: 
		// http://stackoverflow.com/questions/386281/how-to-implement-select-all-check-box-in-html
		function selectAll(source) {
		  checkboxes = document.getElementsByName('checkboxBodyTargets');
		  for(var i=0, n=checkboxes.length;i<n;i++) {
		    checkboxes[i].checked = source.checked;
		  }
		}
	</script>

	<ul id="menuBar">
		<li><a href="home.jsp">Home</a></li>
		<li><a class="active" href="#">Create Workout</a></li>
		<li><a href="${pageContext.request.contextPath}/EquipmentController">Equipment</a></li>
		<li><a href="workoutPlanner.jsp">Workout Planner</a></li>
		<li><a href="workoutHistory.jsp">Workout History</a></li>
		<li><a href="${pageContext.request.contextPath}/LogoutController">Logout</a></li>
		<li><a href="profile.jsp">Profile</a></li>
	</ul>
	<br>

	<table cellspacing="0" cellpadding="0" class="table1">
		<tr>
			<td class="apex">
				
				<h1>APEX Fitness</h1>
				<p>
					To create a workout, first select which Body Targets you want to target. <br>
					${message} 
				</p>
				
				<label>Select All</label>
				<input type="checkbox" onClick="selectAll(this)"><br><br>
		
					<form action="SearchExercisesController" method="post">
						<input type="hidden" name="bodyTargets" value="submit"> 
						<label>Shoulders</label>
						<input type="checkbox" name="checkboxBodyTargets" value="SH"><br>
						<label>Chest</label>
						<input type="checkbox" name="checkboxBodyTargets" value="CH"><br>
						<label>Back</label>
						<input type="checkbox" name="checkboxBodyTargets" value="BA"><br>
						<label>Biceps</label>
						<input type="checkbox" name="checkboxBodyTargets" value="BI"><br>
						<label>Triceps</label>
						<input type="checkbox" name="checkboxBodyTargets" value="TR"><br>
						<label>Legs</label>
						<input type="checkbox" name="checkboxBodyTargets" value="LE"><br>
						<label>Abs</label>
						<input type="checkbox" name="checkboxBodyTargets" value="AB"><br>
						<label>Cardio</label>
						<input type="checkbox" name="checkboxBodyTargets" value="CA"><br>
						<input type="submit" value="Search" style="height: 30px; width: 70px" id="checkBtn">
					</form>
			</td>
		</tr>
	</table>
</body>
</html>