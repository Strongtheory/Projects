<?php
    include ("session.php");
    // train number
    if (isset($_POST['train'])) {
        $trainNumber = mysqli_real_escape_string($db, $_POST['train']);
        $rating = trim(mysqli_real_escape_string($db, $_POST['rating']));
        $comments = mysqli_real_escape_string($db, $_POST['commentcunts']);
        $username = $_SESSION['login_user'];
        $sql = "INSERT INTO Review (username, train_number, comments, rating) VALUES ('$username', '$trainNumber', '$comments', '$rating')";
        $result = mysqli_query($db, $sql);
        if ($result) {
            echo "Successfully added Review.";
        } else {
            echo "Please try again.";
        }
    }
?>

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
<link rel="stylesheet" href="styles.css" />
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<link href="//cdn.muicss.com/mui-0.5.4/css/mui.min.css" rel="stylesheet" type="text/css" />
<script src="//cdn.muicss.com/mui-0.5.4/js/mui.min.js"></script>

<div class="mdl-layout mdl-js-layout mdl-color--grey-100">
    <main class="mdl-grid">
        <div class="mdl-card mdl-shadow--6dp">
            <div class="mdl-card__title mdl-color--primary mdl-color-text--white">
                <h2 class="mdl-card__title-text">
                    Give Review
                </h2>
            </div>
            <div class="mdl-card__supporting-text">
                <form action="" method="post" role="form">
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" name="train" type="number" id="trainNumber" required/>
                        <label class="mdl-textfield__label" for="trainNumber">TrainNumber</label>
                    </div>
                    <div class="mui-select">
                        <select name="rating" id="rating">
                            <option></option>
                            <option>Excellent</option>
                            <option>Good</option>
                            <option>Neutral</option>
                            <option>Bad</option>
                            <option>Very Bad</option>
                        </select>
                        <label class="mdl-textfield__label" for="rating">Rating</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" name="commentcunts" type="text" id="comments" required/>
                        <label class="mdl-textfield__label" for="comments">Comment</label>
                    </div>
                    <div class="mdl-card__actions mdl-card--border">
                        <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                            Submit
                        </button>
                    </div>
                </form>
                <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" onclick="location.href='chooseFunctionality.html'";>
                    Back
                </button>
            </div>
        </div>
    </main>
</div>
