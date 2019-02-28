<!DOCTYPE html>
<html>
   <head>
      <meta charset="utf-8">
      <title>Create New Account</title>
      <link rel="stylesheet" href="styles/styles.css" type="text/css"/>
   </head>
   <body>
      <table style="clear: both; border-collapse: collapse" class="table1">
         <tr>
            <td class="apex">
            
            <div style="text-aling: center">
               <h1>Create New Account</h1>
            </div>
               <br>
               <form action="CreateAccountController" method="post" class="account">
                  <input type="hidden" name="action" value="add">
                  <label>First Name:</label>
                  <input type="text" name="firstName" maxlength="20" required><br>
                  <label>Last Name:</label>
                  <input type="text" name="lastName" maxlength="20" required><br>
                  <label>Age:</label>
                  <input type="number" name="age" value="18" required><br>
                  <label>E-mail:</label>
                  <input type="email" name="email" maxlength="50" required><br>
                  <label>Password:</label>
                  <input type="password" name="password" pattern=".{8,50}" 
                  	required title="8 characters minimum, 50 characters maximum."><br>
                  <label>Reminder?:</label>
                  <input type="radio" name="reminderMail" value="yes" required>Yes
                  <input type="radio" name="reminderMail" value="no" required>No<br>
                  <label>Goal:</label>
                  <input type="checkbox" name="goal" value="WL">Weight Loss
                  <input type="checkbox" name="goal" value="MG">Muscle Gain<br>
                  <label>Weight:</label>
                  <input type="number" name="weight" value="150" min="0" required><br>
                  <label>Height(inches):</label>
                  <input type="number" name="height" value="70" min="0" required><br>
                  <br>
                  <label>&nbsp;</label>
                  <input type="submit" value="Sign Up" style="height:30px; width:70px">
                  <input type="reset" value="Reset" style="height:30px; width:70px">
               </form>
            </td>
         </tr>
      </table>
   </body>
</html>