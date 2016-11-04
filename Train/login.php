<?php
   include("config.php");
   session_start();
   
   if($_SERVER["REQUEST_METHOD"] == "POST") {
      // username and password sent from form 
      
      $myusername = mysqli_real_escape_string($db,$_POST['username']);
      $mypassword = mysqli_real_escape_string($db,$_POST['password']); 
      
      $sql = "SELECT username FROM customer WHERE username = '$myusername' and password = '$mypassword'";
      $result = mysqli_query($db,$sql);
      $row = mysqli_fetch_array($result,MYSQLI_ASSOC);
      $active = $row['active'];
      
      $count = mysqli_num_rows($result);
      
      // If result matched $myusername and $mypassword, table row must be 1 row
		
      if($count == 1) {
         session_register("myusername");
         $_SESSION['login_user'] = $myusername;
         
         header("location: register.php");
      }else {
         $error = "Your Login Name or Password is invalid";
      }
   }
?>


<html> 

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
<link rel="stylesheet" href="styles.css" />
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>

<div ng-show="confirm">
    Already logged in. Go to home page <a class="mdl-navigation__link" href="#/gate">Home</a>
</div>

<div ng-hide="confirm">
    <div class="mdl-layout mdl-js-layout mdl-color--grey-100">
        <main class="mdl-grid">
            <div class="mdl-card mdl-shadow--6dp">
                <div class="mdl-card__title mdl-color--primary mdl-color-text--white">
                    <h2 class="mdl-card__title-text">
                        GT Train Login Page
                    </h2>
                </div>
                <div class="mdl-card__supporting-text">
                    <form role="form" ng-submit="submit()">
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="text" ng-model="username" id="username" required/>
                            <label class="mdl-textfield__label" for="username">Username</label>
                        </div>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="password" ng-model="password" id="password" required/>
                            <label class="mdl-textfield__label" for="password">Password</label>
                        </div>
                        <div class="mdl-card__actions mdl-card--border">
                            <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                                Login
                            </button>
                        </div>
                    </form>
                    <a class="mdl-navigation__link" href="#/register">
                        Register Here
                    </a>
                </div>
            </div>
        </main>
    </div>
</div>
</html>
