<!DOCTYPE html>
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
<meta charset="utf-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>APEX Fitness</title>
<link rel="stylesheet" href="styles/styles.css" type="text/css" />
</head>

	<!-- Code burrowed from: 
		http://stackoverflow.com/questions/22238368/multiple-checkboxes-at-least-1-required
		http://stackoverflow.com/questions/11787665/making-sure-at-least-one-checkbox-is-checked 
	-->
	<script type="text/javascript">
		$(document).ready(function() {
			$('#checkBtn').click(function() {
				
				var checkboxes = document.querySelectorAll('input[type="checkbox"]');
				var checkedOne = Array.prototype.slice.call(checkboxes).some(x => x.checked);
	
				if (!checkedOne) {
					alert("You must select at least one exercise.");
					return false;
				}
	
			});
		});
	</script>
<body>
	<ul id="menuBar">
		<li><a href="home.jsp">Home</a></li>
		<li><a class="active" href="searchExercises.jsp">Create Workout</a></li>
		<li><a href="${pageContext.request.contextPath}/EquipmentController">Equipment</a></li>
		<li><a href="workoutPlanner.jsp">Workout Planner</a></li>
		<li><a href="workoutHistory.jsp">Workout History</a></li>
		<li><a href="${pageContext.request.contextPath}/LogoutController">Logout</a></li>
		<li><a href="profile.jsp">Profile</a></li>
	</ul>
	${message}
	<br>
	<br>
 	 
 	 <div style="font-size: 28px">
 	 	<div style="text-align:center"><h1>APEX Fitness</h1></div>
 	 	
 	 	Select any number of exercises and give your workout a name. <br>
 	 	Filter(s) Selected:
 	 
	 	 <c:forEach items="${bodyTargetStrings}" var="values" varStatus="status">
	 	 	<c:choose>
	 	 		<c:when test="${status.last}">
	 	 			<c:out value="${values}"/>
	 	 		</c:when>
	 	 		<c:otherwise>
	 	 			<c:out value="${values}"/>,
	 	 		</c:otherwise>
	 	 	</c:choose>
	 	 </c:forEach>
 	 </div>
 	 <br>
 	 
 	<form action="CreateWorkoutController" method="post" class="apex" >
		
		Recommended: <br>
		<c:if test="${recommended.isEmpty()}" >
				No recommended exercises were found. <br>
		</c:if>
			
		<c:forEach items="${recommended}" var="values">
			<a href="images/${values}.PNG" target="_blank"><c:out value="${values}" /></a>
			<input type="checkbox" name="selectedExercises" value="${values}">
			<br>
		</c:forEach>
		
		<br>
		Exercises:<br>
		<c:forEach items="${exercises}" var="values">
			<a href="images/${values}.PNG" target="_blank"><c:out value="${values}" /></a>
			<input type="checkbox" name="selectedExercises" value="${values}">
			<br>
		</c:forEach>
		<br>
	
		<label>Workout Name:</label>
		<input type="hidden" name="action" value="add">
		<input type="text" name="workoutName" required> 
		<input	type="submit" value="Submit" style="height: 30px; width: 70px" id="checkBtn">
		
	</form>
</body>
</html>