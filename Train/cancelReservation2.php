<?php
    include ("session.php");

    $username = $_SESSION['login_user'];
    if (isset($_POST['submit'])) {
        $rid = $_SESSION['rid'];
        $tcQuery = "SELECT * FROM Reservation WHERE reservation_id = '$rid'";
        $tcResult = mysqli_query($db, $tcQuery);
        $row = mysqli_fetch_array($tcResult);
        if ($row['is_cancelled'] == 0) {
          $tc = $row['total_cost'];
          $reservesQuery = "SELECT *, DATEDIFF(departure_date, NOW()) as diff FROM Reserves WHERE reservation_id = '$rid' ORDER BY departure_date ASC LIMIT 1";
          $reservesResult = mysqli_query($db, $reservesQuery);
          $rr = mysqli_fetch_array($reservesResult);
          $newcost = 0;
          if (intval($rr['diff']) > 1 && intval($rr['diff']) < 7) {
            $newcost = $tc * 0.5 + 50;
          } else if (intval($rr['diff']) >= 7) {
            $newcost = $tc * 0.2 + 50;
          } else {
            echo "Too late to cancel!";
            $newcost = $tc;
          }
          $updateQ = "UPDATE Reservation SET total_cost = '$newcost', is_cancelled=b'1' WHERE reservation_id = '$rid'";
          $updateResult = mysqli_query($db, $updateQ);

        } else {
          echo "Already cancelled!";
        }

        unset($_SESSION['foundReservations']);
        unset($_SESSION['numFound']);
        unset($_SESSION['rid']);

        header('location: chooseFunctionality.html');
      }
?>

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
<link rel="stylesheet" href="styles.css" />
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>

<style>
.demo-card-wide.mdl-card {
  width: 1260px;
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
                    Cancel Reservation
                </h2>
            </div>
            <div class="mdl-card__supporting-text">
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
                            $currentTicket = $_SESSION['numFound'];

                            for($x = $currentTicket; $x > 0; $x--) {
                                $results = $_SESSION['foundReservations'][$x-1];
                                echo "<tr>";
                                echo "<td>".$results['train_number']."</td>";
                                echo "<td><pre>".$results["Time"]." mins</pre></td>";
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
                            }
                        ?>
                    </tbody>
                </table>
                <div class="mdl-card__actions mdl-card--border">
                    <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
                    onclick="location.href='chooseFunctionality.html'";>
                        Quit
                    </button>
                    <form role="form" method="post" action="">
                    <button type="submit" name="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                        Submit
                    </button>
                  </form>
                </div>
            </div>
        </div>
    </main>
</div>
