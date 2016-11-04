<?php
  include ("session.php");

  $username = $_SESSION['login_user'];

  //query is_student
  $stuQuery = "SELECT is_student FROM Customer WHERE username = '$username'";
  $stuResults = mysqli_query($db, $stuQuery);
  $is_student = 0;
  while($row = mysqli_fetch_array($stuResults)) {
    $is_student = $row['is_student'];
  }

  //query cards
  $cardQuery = "SELECT card_number FROM PaymentInfo WHERE username='$username'";
  $cardResults = mysqli_query($db,$cardQuery);
  while($row = mysqli_fetch_array($cardResults)) {
    $_SESSION['cards'][] = $row;
  }

  //check if post has been issued
  if($_SERVER['REQUEST_METHOD'] == 'POST') {
    $currentTicket = $_SESSION['currentNumTickets'];
    for ($x=$currentTicket; $x > 0; $x--) {
      if(isset($_POST["checkbox".$x."x"])) {
        unset($_SESSION['chosenTrain'][$x-1]);
        $_SESSION['currentNumTickets'] = $_SESSION['currentNumTickets']-1;
      }
    }

    if(isset($_POST['submit'])) {
      $card = mysqli_real_escape_string($db, $_POST['cardToUse']);
      $total = intval($_SESSION['totalcost']);

      $addReservation = "INSERT INTO Reservation (username, total_cost, card_number) VALUES ('$username', '$total', '$card')";
      $addResult = mysqli_query($db, $addReservation);
      $RID = mysqli_insert_id($db);

      $currentTicket = $_SESSION['currentNumTickets'];
      for ($x=$currentTicket; $x > 0; $x--) {
        $results = $_SESSION['chosenTrain'][$x-1];
        $trainNum = $results["train_number"];
        $class = intval($results["chosenClass"]);
        $depDate = trim($results["date"]);
        $passName = $results["chosenPassName"];
        $numBags = $results["chosenNumBags"];
        $dep = $results["departsFrom"];
        $arrive = $results["arrivesAt"];
        $reservesQuery = "INSERT INTO Reserves (reservation_id, train_number, class, departure_date, passenger_name, num_of_baggages, departs_from, arrives_at) VALUES ('$RID', '$trainNum', '$class', '$depDate', '$passName', '$numBags', '$dep', '$arrive')";
        $reservesResult = mysqli_query($db,$reservesQuery);
      }

      unset($_SESSION['chosenTrain']);
      unset($_SESSION['currentNumTickets']);
      unset($_SESSION['totalcost']);
      unset($_SESSION['cards']);
      $_SESSION['reservation_id'] = $RID;
      header('location: makeReservation5.php');
    }

  }


 ?>

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
<link rel="stylesheet" href="styles.css" />
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<link href="//cdn.muicss.com/mui-0.5.4/css/mui.min.css" rel="stylesheet" type="text/css" />
<script src="//cdn.muicss.com/mui-0.5.4/js/mui.min.js"></script>

<style>
.demo-card-wide.mdl-card {
  width: 1600px;
}
.demo-card-wide > .mdl-card__title {
  color: #fff;
}
</style>

<div class="mdl-layout mdl-js-layout mdl-color--grey-100">
    <main class="mdl-grid">
        <div class="demo-card-wide mdl-card mdl-shadow--6dp">
            <div class="mdl-card__title mdl-color--primary mdl-color-text--white">
                <h2 class="mdl-card__title-text">
                    Make Reservation
                </h2>
            </div>
            <div class="mdl-card__supporting-text">
                <form role="form" action="" method="post">
                    <div class="mdl-textfield mdl-js-textfield">
                        <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
                          <thead>
                            <tr>
                                <th style="text-align: center;">Train (Train Number)</th>
                                <th style="text-align: center;">Time (Duration)</th>
                                <th style="text-align: center;">Departs From</th>
                                <th style="text-align: center;">Arrives At</th>
                                <th style="text-align: center;">Class</th>
                                <th style="text-align: center;">Price</th>
                                <th style="text-align: center;">No. of Baggages</th>
                                <th style="text-align: center;">Passenger Name</th>
                                <th style="text-align: center;">Remove</th>
                            </tr>
                          </thead>
                          <tbody>
                            <?php
                            $currentTicket = $_SESSION['currentNumTickets'];
                            for($x=$currentTicket; $x > 0; $x--) {
                              $results = $_SESSION['chosenTrain'][$x-1];
                                echo "<tr>";
                                echo "<td>".$results['train_number']."</td>";
                                echo "<td><pre>".$results[date]." - ".$results["Time"]." mins</pre></td>";
                                echo "<td><pre>".$results["departsFrom"]."</pre></td>";
                                echo "<td><pre>".$results["arrivesAt"]."</pre></td>";
                                echo "<td><pre>".$results["chosenClass"]."</pre></td>";
                                echo "<td><pre>".$results["price"]."</pre></td>";
                                echo "<td><pre>".$results["chosenNumBags"]."</pre></td>";
                                echo "<td><pre>".$results["chosenPassName"]."</pre></td>";
                                echo "<td><label class='mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect' for='checkbox".$x."x'>
                                <input type='submit' name='checkbox".$x."x'type='checkbox' id='checkbox".$x."x' class='mdl-checkbox__input'>
                                <span> Remove </span>
                                </label>
                                </td>";
                                echo "</tr>";
                            }

                            ?>
                          </tbody>
                        </table>
                    </div>
                    <p>
                        <div>
                          <?php
                            if($is_student) {
                              echo "<span class='mdl-layout-title'>Student Discount has been applied.</span>";
                            } else {
                              echo "<span class='mdl-layout-title'>Student Discount has not been applied.</span>";
                            }
                          ?>
                        </div>

                    <div class="mui-divider"></div>

                    <div class="mdl-textfield mdl-js-textfield">
                        <table class="mdl-data-table mdl-js-data-table mdl-shadow--0dp">
                          <thead>
                            <tr>
                                <th>TOTAL COST</th>
                                <?php
                                $currentTicket = $_SESSION['currentNumTickets'];
                                echo $currenTicket;
                                for($x=$currentTicket; $x > 0; $x--) {
                                  $results = $_SESSION['chosenTrain'][$x-1];
                                  $totalcost += $results["price"];
                                  if (intval($results["chosenNumBags"]) >= 2) {
                                    $totalcost += (intval($results["chosenNumBags"]) - 2)*30;
                                  }
                                }
                                if(is_student) {
                                  $totalcost = $totalcost*0.8;
                                }
                                $_SESSION['totalcost'] = $totalcost;
                                echo "<th>".$totalcost."</th>"
                                ?>
                            </tr>
                          </thead>
                        </table>
                    </div>

                    <div class="mui-divider"></div>

                    <div width="200px" class="mui-select">
                      <select type="number" name="cardToUse" id="cards" required>
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

                    <div class="mui-divider"></div>

                    <a class="mdl-navigation__link" href="paymentInfo.php">
                        Add Card
                    </a>
                    <div class="mui-divider"></div>
                    <button type="submit" name="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                        Submit
                    </button>

                </form>
                <div class="mdl-card__actions mdl-card--border">
                    <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
                    onclick="location.href='chooseFunctionality.html'";>
                        Back
                    </button>
                    <button onclick="location.href='makeReservation1.php'"; class="mdl-button mdl-js-button mdl-button--primary">
                          ADD MORE TICKETS
                    </button>

                </div>
            </div>
        </div>
    </main>
</div>
