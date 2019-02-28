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

	<!-- Script code burrowed from http://www.w3schools.com/howto/howto_js_filter_lists.asp -->
	<script>
		function myFunction() {
		    // Declare variables
		    var input, filter, ul, li, a, i;
		    input = document.getElementById('addList');
		    filter = input.value.toUpperCase();
		    ul = document.getElementById("myUL");
		    li = ul.getElementsByTagName('li');
		
		    // Loop through all list items, and hide those who don't match the search query
		    for (i = 0; i < li.length; i++) {
		        a = li[i].getElementsByTagName("a")[0];
		        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
		            li[i].style.display = "";
		        } else {
		            li[i].style.display = "none";
		        }
		    }
		}

		function myFunctionTwo() {
		    // Declare variables
		    var input, filter, ul, li, a, i;
		    input = document.getElementById('removeList');
		    filter = input.value.toUpperCase();
		    ul = document.getElementById("myULTwo");
		    li = ul.getElementsByTagName('li');
		
		    // Loop through all list items, and hide those who don't match the search query
		    for (i = 0; i < li.length; i++) {
		        a = li[i].getElementsByTagName("a")[0];
		        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
		            li[i].style.display = "";
		        } else {
		            li[i].style.display = "none";
		        }
		    }
		}

		function addEquipment(eq){
			document.getElementById('addList').value = eq;
		}	
	
		function removeEquipment(eq){
			document.getElementById('removeList').value = eq;
		}	
		
		function showImage(image) {
			window.open(image);
		}
	</script>

	<sql:setDataSource var="myEquipment" driver="com.mysql.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/apex" user="root" password="ics499" />

	<sql:query var="equipmentList" dataSource="${myEquipment}">
        SELECT * FROM equipment;
    </sql:query>

	<ul id="menuBar">
		<li><a href="home.jsp">Home</a></li>
		<li><a href="searchExercises.jsp">Create Workout</a></li>
		<li><a href="#" class="active" >Equipment</a></li>
		<li><a href="workoutPlanner.jsp">Workout Planner</a></li>
		<li><a href="workoutHistory.jsp">Workout History</a></li>
		<li><a href="${pageContext.request.contextPath}/LogoutController">Logout</a></li>
		<li><a href="profile.jsp">Profile</a></li>
	</ul>
	${message}
	
	<div style="text-aling:center"><h1>APEX Fitness</h1></div>
	<br>
	
	<section>	
	    <div id="one">
		    <div>	
		    	<form action="EquipmentController" method="post">
					<input type="hidden" name="action" value="add">
					<label>All Equipment</label>
		    		<input type="text" id="addList" name="addItem" onkeyup="myFunction()" 
		    			autocomplete="off" placeholder="Add exercises..."> 
					<input type="Submit" value="Add">
				</form>	    
				<ul id="myUL">
				  <c:forEach var="item" items="${equipmentList.rows}">
						<li onClick="addEquipment('${item.eq_name}')" 
							ondblclick="showImage('images/${item.eq_description}')">
							<a><c:out value="${item.eq_name}" /></a>
						</li>
					</c:forEach>
				</ul>
		    </div>		   
	    </div>
	    
	    <div id="two"> 
	    	<form action="EquipmentController" method="post">
				<input type="hidden" name="action" value="remove">
				<label>Your Equipment</label>
		    	<input type="text" id="removeList" name="removeItem" onkeyup="myFunctionTwo()" 
		    		autocomplete="off" placeholder="Remove exercises..."> 
				<input type="Submit" value="Remove">
			</form>	   	
			<ul id="myULTwo">		
			  <c:forEach items="${custEq}" var="item">
					<li onClick="removeEquipment('${item.equipmentName}')">
						<a><c:out value="${item.equipmentName}"/></a>
					</li>
			  </c:forEach>
			</ul>
	    </div>
	</section>
</body>
</html>