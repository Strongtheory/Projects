<?php
   include("session.php");

   if($_SERVER["REQUEST_METHOD"] == "POST") {
      // username and password sent from form
      $myemail = mysqli_real_escape_string($db,$_POST['schoolEmail']);
      $username = $_SESSION['login_user'];
      $substr = mb_substr($myemail, strlen($myemail) - 4 , strlen($myemail)-1);

      if($substr === ".edu") {
        $sql = "UPDATE Customer SET is_student = b'1' WHERE username = '$username'";
        $result = mysqli_query($db,$sql);
        echo "Successfully confirmed student status.";
      } else {
        $error = "Your email is not associated with an academic institute.";
      }
   }
?>

<html>

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
<link rel="stylesheet" href="styles.css" />
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>

<div class="mdl-layout mdl-js-layout mdl-color--grey-100">
    <main class="mdl-grid">
        <div class="mdl-card mdl-shadow--6dp">
            <div class="mdl-card__title mdl-color--primary mdl-color-text--white">
                <h2 class="mdl-card__title-text">
                    Add School Info
                </h2>
            </div>
            <div class="mdl-card__supporting-text">
                <form action="" method="post" role="form">
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" type="text" name="schoolEmail" id="schoolEmail" required/>
                        <label class="mdl-textfield__label" for="schoolEmail">School Email Address:</label>
                    </div>
                    <div class="mdl-card__actions mdl-card--border">
                        <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                            Submit
                        </button>
                    </div>
                </form>
                <button onclick="location.href='chooseFunctionality.html'"; class="mdl-button mdl-js-button mdl-button--primary">
                      Back
                </button>
            </div>
        </div>
    </main>
</div>
</html>
