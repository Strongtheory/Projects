<?php
  include("session.php");

  if($_SERVER['REQUEST_METHOD'] == 'POST') {
    $cnt = $_SERVER['currentNumTickets'];
    $numBags = mysqli_real_escape_string($db, $_POST['numBags']);
    $passengerName = mysqli_real_escape_string($db, $_POST['name']);

    $x = $_SESSION['x'];
    $results = $_SESSION['searchTrainResults'][$x];
    $results['departsFrom'] = $_SESSION['departsFrom'];
    $results['arrivesAt'] = $_SESSION['arrivesAt'];
    $results['date'] = $_SESSION['makeReservationDate'];
    $results['chosenNumBags'] = $numBags;
    $results['chosenPassName'] = $passengerName;
    $results['chosenClass'] = $_SESSION['chosenClass'];
    $results['price'] = $_SESSION['price'];
    $_SESSION['chosenTrain'][] = $results;
    unset($_SESSION['makeReservationDate']);
    unset($_SESSION['arrivesAt']);
    unset($_SESSION['departsFrom']);
    unset($_SESSION['searchTrainResults']);
    unset($_SESSION['x']);
    unset($_SESSION['makeReservationDate']);
    unset($_SESSION['chosenClass']);
    unset($_SESSION['price']);
    header('location: makeReservation4.php');
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
                    Travel Extras & Passenger Info
                </h2>
            </div>
            <div class="mdl-card__supporting-text">
                <form action="" method="post" role="form">
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" type="number" name="numBags" id="no_bags" required/>
                        <label class="mdl-textfield__label" for="no_bags">Number of Bags</label>
                    </div>
                    <label>
                        Every passenger can bring up to 4 baggage. 2 free of charge, the next 2 is $30 per bag.
                    </label>
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" type="text" name="name" id="passenger_name" required/>
                        <label class="mdl-textfield__label" for="passenger_name">Passenger's Name</label>
                    </div>
                    <div class="mdl-card__actions mdl-card--border">
                        <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                            Next
                        </button>
                    </div>
                </form>
                <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
                onclick="location.href='chooseFunctionality.html'";>
                    Quit
                </button>
            </div>
        </div>
    </main>
</div>
