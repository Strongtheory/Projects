<?php
    include ("session.php");

    if($_SERVER['REQUEST_METHOD'] == 'POST') {
      $currentTicket = $_SESSION['numFound'];
      for ($x=$currentTicket; $x > 0; $x--) {
        if(isset($_POST["checkbox".$x."x"])) {
          $_SESSION['toUpdate'] = $_SESSION['foundReservations'][$x-1];
          header('Location: updateReservation3.php');
        }
      }
    }
?>

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
<link rel="stylesheet" href="styles.css" />
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>

<style>
.demo-card-wide.mdl-card {
  width: 1300px;
}
.demo-card-wide > .mdl-card__title {
  color: #fff;
}
</style>

<div class="mdl-layout mdl-js-layout mdl-color--grey-100">
    <main class="mdl-grid">
        <div class="demo-card-wide mdl-card mdl-shadow--6dp" width="512px">
            <div class="mdl-card__title mdl-color--primary mdl-color-text--white">
                <h2 class="mdl-card__title-text">
                    Update Reservation
                </h2>
            </div>
            <div class="mdl-card__supporting-text">
              <form role="form" action="" method="post">
                <div class="mdl-textfield mdl-js-textfield">
                    <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
                      <thead>
                        <tr>
                            <th class="mdl-data-table__cell--non-numeric">Train</th>
                            <th>Train (Duration)</th>
                            <th>Departs From</th>
                            <th>Arrives At</th>
                            <th>Class</th>
                            <th>Price</th>
                            <th>No of Baggages</th>
                            <th>Passenger Name</th>
                        </tr>
                      </thead>
                      <tbody>
                        <?php
                        $currentTicket = $_SESSION['numFound'];
                        for($x=$currentTicket; $x > 0; $x--) {
                          $results = $_SESSION['foundReservations'][$x-1];
                            echo "<tr>";
                            echo "<td>".$results['train_number']."</td>";
                            echo "<td><pre>".$results['departure_date']."\n".$results["Time"]." mins</pre></td>";
                            echo "<td>".$results["DepartsFrom"]."</td>";
                            echo "<td>".$results["ArrivesAt"]."</td>";
                            echo "<td>".$results["class"]."</td>";
                            if ($results['class'] == 1) {
                              echo "<td>".$results["first_class_price"]."</td>";
                            } else if ($results['class'] == 2) {
                              echo "<td>".$results["second_class_price"]."</td>";
                            }
                            echo "<td>".$results["num_of_baggages"]."</td>";
                            echo "<td>".$results["passenger_name"]."</td>";
                            echo "<td><label class='mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect' for='checkbox".$x."x'>
                            <input type='submit' name='checkbox".$x."x' type='checkbox' id='checkbox".$x."x' class='mdl-checkbox__input'>
                            <span> Update </span>
                            </label>
                            </td>";
                            echo "</tr>";
                        }

                        ?>
                      </tbody>
                    </table>
                </div>
                <div class="mdl-card__actions mdl-card--border">
                    <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                        Next
                    </button>
                </div>
            </div>
          </form>
          <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
            onclick="location.href='chooseFunctionality.html'";>
            Quit
          </button>
        </div>
    </main>
</div>
