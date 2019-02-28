<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>APEX Fitness</title>
<link rel="stylesheet" href="styles/styles.css" type="text/css" />
</head>
<body>
	<ul id="menuBar">
		<li><a href="home.jsp">Home</a></li>
		<li><a href="searchExercises.jsp">Create Workout</a></li>
		<li><a href="${pageContext.request.contextPath}/EquipmentController">Equipment</a></li>
		<li><a class="active" href="#">Workout Planner</a></li>
		<li><a href="workoutHistory.jsp">Workout History</a></li>
		<li><a href="${pageContext.request.contextPath}/LogoutController">Logout</a></li>
		<li><a href="profile.jsp">Profile</a></li>
	</ul>

	<sql:setDataSource var="myWorkouts" driver="com.mysql.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/apex" user="root" password="ics499" />


	<sql:query var="workoutList" dataSource="${myWorkouts}">
        SELECT * FROM workouts WHERE email = '${customer.email}' and completed = 'false' 
        order by wk_name, createdDate;
    </sql:query>
    

	<table cellspacing="0" cellpadding="0" class="table1">
		<tr>
			<td class="apex">
			<div style="text-align:center">
				<h1>APEX Fitness</h1> 
			</div>
				<c:choose> 
					<c:when test="${workoutList.rowCount == 0}">
						<p>	You do not have any workouts available. Either create a new workout,
							or redo a previously completed workout.
						</p>
					</c:when>
					<c:when test="${workoutList.rowCount != 0}">
						
						<p>View your planned workouts.</p>

						<div class="account">
							<c:forEach var="item" items="${workoutList.rows}">
								<form action="CompleteWorkoutController" method="post">
									<label style="width: 350px">
									<c:out value="${item.wk_name}" />					
									</label>
									<input type="hidden" name="wkName" value="${item.wk_name}">
									<input type="hidden" name="wkId" value="${item.wk_id}">
									<input type="submit" name="workout" value="Do it!" 
										style="height: 40px; width: 80px"/>
									(<c:out value="${item.createdDate}" />)
								</form>
							</c:forEach>
						</div>
					</c:when>
				</c:choose>
			</td>
		</tr>
	</table>
</body>
</html>