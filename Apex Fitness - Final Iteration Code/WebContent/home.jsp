<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>APEX Fitness</title>
<link type="text/css" rel="stylesheet" href="styles/styles.css">

</head>
<body>

	<ul id="menuBar">
		<li><a class="active" href="#">Home</a></li>
		<li><a href="searchExercises.jsp">Create Workout</a></li>
		<li><a href="${pageContext.request.contextPath}/EquipmentController">Equipment</a></li>
		<li><a href="workoutPlanner.jsp">Workout Planner</a></li>
		<li><a href="workoutHistory.jsp">Workout History</a></li>
		<li><a href="${pageContext.request.contextPath}/LogoutController">Logout</a></li>
		<li><a href="profile.jsp">Profile</a></li>
	</ul>

	<table cellspacing="0" cellpadding="0" class="table1">
		<tr>
			<td class="apex"><br>
				<div style="text-align:center">
					<h1>APEX Fitness</h1>
				</div>
				<br>
				<p>Welcome ${sessionScope.customer.firstName}</p>
			</td>
		</tr>
	</table>

	${message}

</body>
</html>