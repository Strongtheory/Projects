<?php
   include("session.php");
   if($_SERVER["REQUEST_METHOD"] == "POST") {
      $myusername = mysqli_real_escape_string($db,$_POST['username']);
      $mypassword = mysqli_real_escape_string($db,$_POST['password']);
      $myemail = mysqli_real_escape_string($db,$_POST['email']);
      $sql = "INSERT INTO Customer (email, username, password, is_student) VALUES ('$myemail', '$myusername', '$mypassword', 0)";
      $result = mysqli_query($db,$sql);

      if ($result) {
        header('Location: loginCustomer.php');
      }
   }
?>


<html>
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
<link rel="stylesheet" href="styles.css" />
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>

<div class="mdl-layout mdl-js-layout mdl-color--grey-100">
    <main class="mdl-grid">
        <div class="mdl-card mdl-shadow--6dp">
            <div class="mdl-card__title mdl-color--primary mdl-color-text--white">
                <h2 class="mdl-card__title-text">
                    GT Train Register Page
                </h2>
            </div>
            <div class="mdl-card__supporting-text">
                <form role="form" action="" method="post">
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" type="text" name="username" id="username" required/>
                        <label class="mdl-textfield__label" for="username">Username</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" type="text" name="email" id="email" required/>
                        <label class="mdl-textfield__label" for="email">Email</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" type="password" name="password" id="password" required/>
                        <label class="mdl-textfield__label" for="password">Password</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" type="password" name="password" id="confirm" required/>
                        <label class="mdl-textfield__label" for="confirm">Confirm Password</label>
                    </div>
                    <div class="mdl-card__actions mdl-card--border">
                        <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                            Register
                        </button>
                    </div>
                </form>
                <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
                  onclick="location.href='loginCustomer.php'";>
                  Back
                </button>
            </div>
        </div>
    </main>
</div>
<html>
