<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<title>APEX Fitness</title>
<link rel="stylesheet" href="styles/styles.css" type="text/css" />
</head>
<body>
	<ul id="menuBar">
		<li><a href="home.jsp">Home</a></li>
		<li><a href="searchExercises.jsp">Create Workout</a></li>
		<li><a href="${pageContext.request.contextPath}/EquipmentController">Equipment</a></li>
		<li><a href="workoutPlanner.jsp">Workout Planner</a></li>
		<li><a href="workoutHistory.jsp">Workout History</a></li>
		<li><a class="active" href="#">Redo Workouts</a></li>
		<li><a href="${pageContext.request.contextPath}/LogoutController">Logout</a></li>
		<li><a href="profile.jsp">Profile</a></li>
	</ul>

	<sql:setDataSource var="myWorkouts" driver="com.mysql.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/apex?useSSL=false" user="root" password="ics499" />

	<sql:query var="newWorkout" dataSource="${myWorkouts}">
        select workouts.wk_id, ex_name, reps, sets, weight, wk_name from workoutexercises
		inner join (workouts)
		where workouts.wk_id = '${id}' and workouts.email = '${customer.email}'
		and workouts.wk_id = workoutexercises.wk_id;
    </sql:query>

	<div style="text-align:center; font-size: 28px">
		<h1>APEX Fitness</h1>
		<p>
			View the results of your "${workoutName}" workout<br>
			To redo this workout, create a new name and select "Redo".<br>
		</p>
	</div>

	<table cellspacing="0" cellpadding="0" class="workoutTable">
		<tr>
			<td class="apex"><br>
				<form action="RedoWorkController" method="post">
					<table border="1" cellpadding="5">
						<tr>
							<th>Exercise</th>
							<th>Reps</th>
							<th>Sets</th>
							<th>Weight</th>
						</tr>
						<c:forEach var="item" items="${newWorkout.rows}">
							<tr>
								<td><a href="images/${item.ex_name}.PNG" target="_blank"><c:out value="${item.ex_name}" /></a>
								<input type="hidden" name="name" value="${item.ex_name}"></td>
								<td><input type="number" name="reps" min="0" value="${item.reps}" readonly
									style="height: 40px; width: 100px; background-color: #cccccc"></td>
								<td><input type="number" name="sets" min="0" value="${item.sets}" readonly
									style="height: 40px; width: 100px; background-color: #cccccc"></td>
								<td><input type="number" name="weight" min="0" value="${item.weight}" readonly
									style="height: 40px; width: 100px; background-color: #cccccc">
									<input type="hidden" name="workoutID" value="${item.wk_id}">
									<input type="hidden" name="workoutName" value="${item.wk_name}">
								</td>
							</tr>
						</c:forEach>	
					</table>
					<br>
					<label>Workout Name:</label>
					<input type="text" name="newWorkoutName" required>
					<input type="submit" name="workout" value="Redo" style="height: 40px; width: 100px">
				</form>
			</td>
		</tr>
	</table>
</body>
</html>