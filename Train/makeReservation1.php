<?php
  include("session.php");

  if(!empty($_POST['departsFrom'])) {
    $departsFull = mysqli_real_escape_string($db,$_POST['departsFrom']);
    $departs = trim(explode("(", $departsFull)[0]);
    $arrivesFull = mysqli_real_escape_string($db,$_POST['arrivesAt']);
    $arrives = trim(explode("(", $arrivesFull)[0]);
    $date = mysqli_real_escape_string($db, $_POST['date']);
    $_SESSION['makeReservationDate'] = $date;
    $_SESSION['departsFrom'] = $departs;
    $_SESSION['arrivesAt'] = $arrives;

    $query = "SELECT Route.train_number, CONCAT(DES.departure_time,' - ', ARS.arrival_time, '\n', TIMEDIFF(ARS.arrival_time, DES.departure_time)) as Time, DES.departure_time, ARS.arrival_time, TIMEDIFF(ARS.arrival_time, DES.departure_time) as Duration, Route.first_class_price, Route.second_class_price
      FROM (Route
      INNER JOIN Stop as ARS ON Route.train_number = ARS.train_number
      INNER JOIN Stop as DES ON Route.train_number = DES.train_number)
      WHERE DES.stop_name = '$departs' AND
      ARS.stop_name = '$arrives' AND
      TIMEDIFF(ARS.arrival_time, DES.departure_time) > '00:00:00'";
    $result = mysqli_query($db, $query);
    if(!$result) {
      echo "Result is boolean and false";
    } else {
      while($row = mysqli_fetch_array($result)) {
        $_SESSION['searchTrainResults'][] = $row;
        header('Location: makeReservation2.php');
      }
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
                    Search Trains
                </h2>
            </div>

            <div class="mdl-card__supporting-text">
                <form action="" method="post" role="form">
                    <div class="mui-select">
                       <select type="text" name="departsFrom">
                         <option>Depart From</option>
                         <?php
                         $sql = mysqli_query($db, "SELECT station_name, location FROM Station");
                         while ($row = mysqli_fetch_array($sql)) {
                           echo "<option>".$row['station_name']. " (". $row['location'] .")". "</option>";
                         }
                         ?>
                       </select>
                    </div>
                    <div class="mui-select">
                      <select type="text" name="arrivesAt">
                        <option>Arrives At</option>
                        <?php
                        $sql = mysqli_query($db, "SELECT station_name, location FROM Station");
                        while ($row = mysqli_fetch_array($sql)) {
                          echo "<option>".$row['station_name']. " (". $row['location'] .")". "</option>";
                        }
                        ?>
                      </select>
                    </div>
                    <div class="mdl-textfield mdl-js-textfield">
                        <input class="mdl-textfield__input" name="date" type="text" id="date" required/>
                        <label class="mdl-textfield__label" for="date">Date (yyyy-mm-dd)</label>
                    </div>
                    <div class="mdl-card__actions mdl-card--border">
                        <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                            Search
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
