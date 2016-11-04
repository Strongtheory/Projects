<?php
   include("session.php");

   if($_SERVER["REQUEST_METHOD"] == "POST") {
     $train_number = mysqli_real_escape_string($db,$_POST['train_number']);
     $sql = "Select train_number, arrival_time, departure_time, stop_name from Stop where train_number = '$train_number'";
     $result = mysqli_query($db,$sql);

     if($result === false) {
       echo "Result is boolean and false";
     } else {
       while($row = mysqli_fetch_array($result)) {
         $_SESSION['viewTrainResults'][] = $row;
       }
     }

     header('Location: viewTrain2.php');
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
                    View Train Schedule
                </h2>
            </div>
            <div class="mdl-card__supporting-text">
                <form action="" method="post" role="form">
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" type="text" name="train_number" id="train_number" required/>
                        <label class="mdl-textfield__label" for="train_number">Train Number:</label>
                    </div>
                    <div class="mdl-card__actions mdl-card--border">
                        <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
                        onclick="location.href='chooseFunctionality.html'";>
                            Back
                        </button>
                        <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                            Search
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </main>
</div>
</html>
