<?php
    include("session.php");

    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $train_number = mysqli_real_escape_string($db, $_POST['train_number']);
        $sql = "SELECT train_number, rating, comments from Review where train_number = '$train_number'";
        $result = mysqli_query($db, $sql);
        if ($result == false) {
            echo "result is boolean and false";
        } else {
            while ($row = mysqli_fetch_array($result)) {
                $_SESSION['viewResults'][] = $row;
            }
        }

        header('Location: viewReview2.php');
    }
?>

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
<link rel="stylesheet" href="styles.css" />
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>

<div class="mdl-layout mdl-js-layout mdl-color--grey-100">
    <main class="mdl-grid">
        <div class="mdl-card mdl-shadow--6dp">
            <div class="mdl-card__title mdl-color--primary mdl-color-text--white">
                <h2 class="mdl-card__title-text">
                    View Review
                </h2>
            </div>
            <div class="mdl-card__supporting-text">
                <form action="" method="post" role="form">
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" name="train_number" type="number" id="train_number" required/>
                        <label class="mdl-textfield__label" for="trainNumber">Train Number</label>
                    </div>
                    <div class="mdl-card__actions mdl-card--border">
                        <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                            Next
                        </button>
                    </div>
                </form>
                <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
                    onclick="location.href='chooseFunctionality.html'";>
                    Back
                </button>
            </div>
        </div>
    </main>
</div>
