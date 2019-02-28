<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>APEX Fitness</title>
<link rel="stylesheet" href="styles/styles.css" type="text/css" />
</head>
<body>
    <table cellspacing="0" cellpadding="0" class="table1">
        <tr>
            <td class="apex"><br>
                <div style="text-align:center"><h1>APEX Fitness</h1></div>
                <br>
                
               ${message}
                <form action="LoginController" method="post">
                    <input type="hidden" name="action" value="login"> 
                    <label>Email:</label>
                    <input type="email" name="email" required> </br> 
                    <label>Password:</label>
                    <input type="password" name="password" required> </br> <label>&nbsp;</label>
                    <input type="submit" value="Sign in" style="height: 30px; width: 70px;">
                    <br>
                    <br>
                    <br>
                    <br>
                    <a href="createNewAccount.jsp">Create New Account</a>
                </form> 
            </td>
        </tr>
    </table>
</body>
</html>