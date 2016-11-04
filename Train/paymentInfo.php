<?php
    include("session.php");
    //populate card section
    $username = $_SESSION['login_user'];
    $cardQuery = "SELECT card_number FROM PaymentInfo WHERE username='$username'";
    $cardResults = mysqli_query($db,$cardQuery);
    while($row = mysqli_fetch_array($cardResults)) {
      $_SESSION['cards'][] = $row;
    }

    //add card section
    date_default_timezone_set('UTC');
    if(!empty($_POST['cardNumber'])) {
      $name = mysqli_real_escape_string($db,$_POST['name']);
      $cardNumber = mysqli_real_escape_string($db,$_POST['cardNumber']);
      $cvv = mysqli_real_escape_string($db,$_POST['cvv']);
      $expiration = mysqli_real_escape_string($db,$_POST['expirationdate']);
      $username = $_SESSION['login_user'];
      $today = date("Y-m-d");

      if (strtotime($expiration) > strtotime($today)) {
        $sql = "INSERT INTO PaymentInfo (username, card_number, cvv, expiration_date, name_on_card) VALUES ('$username', '$cardNumber', '$cvv', '$expiration', '$name')";
        $result = mysqli_query($db,$sql);
        if($result) {
          echo "Sucessfully added card!";
        } else {
          echo "Please try again. Card not accepted.";
        }
      }
    }

    if(!empty($_POST['cardToDelete'])) {
      $delete = mysqli_real_escape_string($db,$_POST['cardToDelete']);
      $todelete = intval($delete);
      $sqlDelete = "DELETE FROM PaymentInfo WHERE card_number = '$todelete' AND username = '$username'";
      $deleteResults = mysqli_query($db,$sqlDelete);
      if($deleteResults) {
        echo "Successfully deleted payment!";
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
                    Payment Information
                </h2>
            </div>

            <div class="mdl-card__supporting-text">
                <div class="mdl-card__title">
                    <h2 class="mdl-card__title-text">Add a Card</h2>
                </div>
                <form action="" method="post" role="form">
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" name="name" type="text" id="name" required/>
                        <label class="mdl-textfield__label" for="name">Name on Card</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" name="cardNumber" type="number" id="number" required/>
                        <label class="mdl-textfield__label" for="number">Card Number</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" name="cvv" type="number" id="cvv" required/>
                        <label class="mdl-textfield__label" for="cvv">CVV</label>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" name="expirationdate" type="text" id="expDate" required/>
                        <label class="mdl-textfield__label" for="expDate">Expiration Date (yyyy-mm-dd)</label>
                    </div>
                    <div class="mdl-card__actions mdl-card--border">
                        <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                            Submit
                        </button>
                    </div>
                </form>

                <div class="mui-divider"></div>
                <div class="mdl-card__title">
                    <h2 class="mdl-card__title-text">Delete a Card</h2>
                </div>
                    <form action="" method="post" role="form">
                      <div class="mui-select">
                      <select type="number" name="cardToDelete" id="cards">
                        <option>Select Card</option>
                        <?php
                        $cards = $_SESSION['cards'];
                        $length = count($cards);
                        $counter = count($cards);
                        while($length >= 0) {
                          if ($length < $counter) {
                            echo "<option>".$cards[$length]['card_number']."</option>";
                          }
                          $length--;
                        }
                        unset($_SESSION['cards']);?>
                      </select>
                      </div>
                      <button name="deleteCardSubmit" ype="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                      Submit
                      </button>
                    </form>
                    <button onclick="location.href='chooseFunctionality.html'"; class="mdl-button mdl-js-button mdl-button--primary">
                          BACK
                    </button>

            </div>
        </div>
    </main>
</div>
