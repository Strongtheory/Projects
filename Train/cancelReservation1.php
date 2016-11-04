<?php
    include ("session.php");

    $username = $_SESSION['login_user'];

    if ($_SERVER["REQUEST_METHOD"] == "POST") {
      $RID = mysqli_real_escape_string($db, $_POST['reservation_id']);
      $_SESSION['rid'] = $RID;
      $departQ = "SELECT departs_from FROM Reserves WHERE reservation_id = '$RID'";
      $arriveQ = "SELECT arrives_at FROM Reserves WHERE reservation_id = '$RID'";
      $trainQ = "SELECT train_number FROM Reserves WHERE reservation_id = '$RID'";

      $dResult = mysqli_query($db, $departQ);
      $aResult = mysqli_query($db, $arriveQ);
      $tResult = mysqli_query($db, $trainQ);

      $counter = 0;
      while($row = mysqli_fetch_array($dResult)) {
        $row2 = mysqli_fetch_array($aResult);
        $row3 = mysqli_fetch_array($tResult);
        $departsFrom = $row['departs_from'];
        $arrivesAt = $row2['arrives_at'];
        $trainNumber = $row3['train_number'];

        $largeQuery = "SELECT Route.train_number, Reserves.class, concat(Reserves.departure_date, ' \n', DES.departure_time, ' - ' , ARS.arrival_time, '\n', timediff(ARS.arrival_time, DES.departure_time)) as Time,
Concat(DStation.location, ' \n(', DES.stop_name, ')') as DepartsFrom,
Concat(AStation.location, ' \n(', ARS.stop_name, ')') as ArrivesAt,
Route.first_class_price, Route.second_class_price, Reserves.num_of_baggages, Reserves.passenger_name
from Route inner join Reserves on Route.train_number = Reserves.train_number
                        inner join Reservation on Reserves.reservation_id = Reservation.reservation_id
                                inner join Stop as ARS on Route.train_number = ARS.train_number
                                        inner join Stop DES on Route.train_number = DES.train_number
                                                inner join Station as AStation on ARS.stop_name = AStation.station_name
                                                                inner join Station as DStation on DES.stop_name = DStation.station_name
                                                                         where Reserves.reservation_id = '$RID' and
                                                                              Reserves.train_number = '$trainNumber' and
                                                                                Reservation.username = '$username' and
                                                                                        DES.stop_name = '$departsFrom' and
                                                                                                ARS.stop_name = '$arrivesAt'";
        $largeResult = mysqli_query($db, $largeQuery);
        $_SESSION['foundReservations'][] = mysqli_fetch_array($largeResult);
        $counter++;
      }

      $_SESSION['numFound'] = $counter;
      header('location: cancelReservation2.php');
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
                    Cancel Reservation
                </h2>
            </div>
            <div class="mdl-card__supporting-text">
                <form action="" method="post" role="form">
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" name="reservation_id" type="number" id="reservationID" required/>
                        <label class="mdl-textfield__label" for="reservationID">Reservation ID</label>
                    </div>
                    <div class="mdl-card__actions mdl-card--border">
                        <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                            Search
                        </button>
                    </div>
                </form>
                <button onclick="location.href='chooseFunctionality.html'"; class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                    Back
                </button>
            </div>
        </div>
    </main>
</div>
