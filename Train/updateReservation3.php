<?php
  include("session.php");
  $count = 0;

  if(isset($_POST['date']) && isset($_POST['changeDate']) && $count == 0) {
    $var = $_POST['date'];
    $_SESSION['newDate'] = $var;
    $count++;
    }

    if(isset($_POST['submitAll'])) {
      $newDate = $_SESSION['newDate'];
      $RID = $_SESSION['toUpdate']['reservation_id'];
      $trainNo = $_SESSION['toUpdate']['train_number'];
      $reservesQuery = "UPDATE Reserves SET departure_date = '$newDate' WHERE reservation_id = '$RID' and train_number = '$trainNo'";
      $reservationQuery = "UPDATE Reservation SET total_cost = total_cost + 50 WHERE reservation_id = '$RID'";

      $r1result = mysqli_query($db, $reservesQuery);
      $r2result = mysqli_query($db, $reservationQuery);
      unset($_SESSION['toUpdate']);
      unset($_SESSION['numFound']);
      unset($_SESSION['newDate']);
      unset($_SESSION['foundReservations']);
      header('location: chooseFunctionality.html');
    }

?>
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
<link rel="stylesheet" href="styles.css" />
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>

<style>
.demo-card-wide.mdl-card {
  width: 1600px;
}
.demo-card-wide > .mdl-card__title {
  color: #fff;
}
.demo-card-wide > .mdl-card__menu {
  color: #fff;
}
</style>

<div class="mdl-layout mdl-js-layout mdl-color--grey-100">
    <main class="mdl-grid">
        <div class="demo-card-wide mdl-card mdl-shadow--6dp">
            <div class="mdl-card__title mdl-color--primary mdl-color-text--white">
                <h2 class="mdl-card__title-text">
                    Update Reservation
                </h2>
            </div>
            <div class="mdl-card__supporting-text">

                <div class="mdl-card__title">
                    Current Train Ticket
                </div>

                <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
                  <thead>
                    <tr>
                        <th>Train (Train Number)</th>
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
                    $results = $_SESSION['toUpdate'];
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
                        echo "</tr>";

                    ?>
                  </tbody>
                </table>

                <form role="form" action="" method="post">
                    <h6>Choose New Departure Date &nbsp; &nbsp; &nbsp;
                        <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" type="text" name="date" id="date" required/>
                        <label class="mdl-textfield__label" for="date">Date (yyyy-MM-dd)</label>
                        </div>
                        <input id="changeDate" name="changeDate" type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                            Search
                        </input>
                    </h6>

                <div class="mdl-card__title">
                    Updated Train Ticket
                </div>

                <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
                  <thead>
                    <tr>
                        <th>Train (Train Number)</th>
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
                    if(isset($_POST['date'])) {
                      $results = $_SESSION['toUpdate'];
                          echo "<tr>";
                          echo "<td>".$results['train_number']."</td>";
                          echo "<td><pre>".$_POST['date']."\n".$results["Time"]." mins</pre></td>";
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
                          echo "</tr>";
                    } else {
                      echo "<tr>";
                      echo "<td>   -    </td>";
                      echo "<td>   -    </td>";
                      echo "<td>   -    </td>";
                      echo "<td>   -    </td>";
                      echo "<td>   -    </td>";
                      echo "<td>   -    </td>";
                      echo "<td>   -    </td>";
                      echo "<td>   -    </td>";
                      echo "</tr>";
                    }

                    ?>
                  </tbody>
                </table>

                <h6>Change Fee &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" type="text" ng-model="date" id="fee" disabled/>
                        <label class="mdl-textfield__label" for="fee">50</label>
                    </div>
                </h6>
                <h6>Updated Total Cost &nbsp; &nbsp; &nbsp;
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" type="text" ng-model="date" id="cost" disabled/>
                        <label class="mdl-textfield__label" for="cost">
                          <?php
                            $results = $_SESSION['toUpdate'];
                            if(isset($_POST['date'])) {
                              echo $results['total_cost'] + 50;
                            }
                          ?>
                        </label>
                    </div>
                </h6>
                <div class="mdl-card__actions mdl-card--border">
                    <input type="submit" id="submitAll" name="submitAll" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"/>
                    </input>
                </div>
              </form>
              <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
              onclick="location.href='chooseFunctionality.html'";>
                  Back
              </button>
            </div>
        </div>
    </main>
</div>
