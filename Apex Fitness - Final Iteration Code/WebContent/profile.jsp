<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
    <title>APEX Fitness</title>
    <link type="text/css" rel="stylesheet" href="styles/styles.css">
</head>
<body>

	<ul id="menuBar">
	    <li><a href="home.jsp">Home</a></li>
	    <li><a href="searchExercises.jsp">Create Workout</a></li>
	    <li><a href="${pageContext.request.contextPath}/EquipmentController">Equipment</a></li>
	    <li><a href="workoutPlanner.jsp">Workout Planner</a></li>
	    <li><a href="workoutHistory.jsp">Workout History</a></li>
	    <li><a href="${pageContext.request.contextPath}/LogoutController">Logout</a></li>
		<li><a class="active" href="#">Profile</a></li>
	</ul>

	<sql:setDataSource var="myEquipment" driver="com.mysql.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/apex" user="root" password="ics499" />
	
	<!-- Database query to find number of completed workouts -->
	<sql:query var="completed" dataSource="${myEquipment}">
        SELECT * FROM workouts where completed = 1 and email = '${sessionScope.customer.email}';
    </sql:query>
    
    <!-- Database query to find number of incomplete workouts -->
    <sql:query var="created" dataSource="${myEquipment}">
        SELECT * FROM workouts where email = '${sessionScope.customer.email}';
    </sql:query>

	<!-- Getting the number of completed workouts -->
	<c:set var="numCompleted" value="0"/>
	<c:forEach var="item" items="${completed.rows}" varStatus="loop">
		<c:set var="numCompleted" value="${numCompleted + 1}"/>	
	</c:forEach>
	
	<!-- Getting the number of incomplete workouts -->
	<c:set var="numCreated" value="0"/>
	<c:forEach var="item" items="${created.rows}" varStatus="loop">
		<c:set var="numCreated" value="${numCreated + 1}"/>	
	</c:forEach>
	
	<div style="text-align: center; font-size: 28px">
		<h1>Profile</h1>
	</div>
	
	<table cellspacing="0" cellpadding="0" class="profileTable">
		<tr>
			<td class="profileTD">
				<form class="profilePage">
					<div class="profileLabel">
						<label>
							Name: ${sessionScope.customer.firstName} ${sessionScope.customer.lastName}
						</label><br>
						<label>Age: ${sessionScope.customer.age}</label><br>
						<label>Email: ${sessionScope.customer.email}</label><br>
						<label>Reminder:
							<c:choose>
								<c:when test="${sessionScope.customer.reminder == false}">
									Off
								</c:when>
								<c:otherwise>
									On
								</c:otherwise>								
							</c:choose>
						</label><br>
						<label>Goal: 	
							<c:choose>
								<c:when test="${sessionScope.customer.goal == 'WL'}">
									Weight Loss
								</c:when>
								<c:when test="${sessionScope.customer.goal == 'MG'}">
									Muscle Gain
								</c:when>
								<c:when test="${sessionScope.customer.goal == 'WLMG'}">
									Weight Loss & Muscle Gain
								</c:when>
								<c:otherwise>
									None
								</c:otherwise>
							</c:choose>
							
							</label><br>
						<label>Weight (in pounds): ${sessionScope.customer.weight}</label><br>
						<label>Height (in inches): ${sessionScope.customer.height}</label><br>
						<label>Completed Workouts: <c:out value="${numCompleted}" /></label><br>
						<label>Created Workouts: <c:out value="${numCreated}" /></label><br>
						<label><a href="https://youtu.be/oNf606dfCb0?t=1m48s" target="_BLANK">
							MOTIVATION</a></label>
					</div>  
				</form>
			</td>
		</tr>
	</table>  
</body>
</html>