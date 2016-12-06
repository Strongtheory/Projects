<?php
   include("session.php");
?>
<html>
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
<link rel="stylesheet" href="styles.css" />
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>

<style>
.demo-card-wide.mdl-card {
  width: 700px;
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
        <div class="demo-card-wide mdl-card mdl-shadow--6dp" width="512px">
            <div class="mdl-card__title mdl-color--primary mdl-color-text--white">
                <h2 class="mdl-card__title-text">
                    Train Schedule
                </h2>
            </div>
            <div class="mdl-card__supporting-text">
                <div class="mdl-textfield mdl-js-textfield">
                    <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
                      <thead>
                        <tr>
                            <th class="mdl-data-table__cell--non-numeric">Train (Train Number)</th>
                            <th>Arrival Time</th>
                            <th>Departure Time</th>
                            <th>Station</th>
                        </tr>
                      </thead>
                      <tbody>
                        <?php
                        $results = $_SESSION['viewTrainResults'];
                        $length = count($results);
                        $counter = count($results);
                        while($length >= 0) {
                          if ($length < $counter) {
                            echo "<tr>";
                            echo "<td>".$results[$length]["train_number"]."</td>";
                            echo "<td>".$results[$length]["arrival_time"]."</td>";
                            echo "<td>".$results[$length]["departure_time"]."</td>";
                            echo "<td>".$results[$length]["stop_name"]."</td>";
                            echo "</tr>";
                          }
                          $length--;
                        }
                        unset($_SESSION['viewTrainResults']);?>
                      </tbody>
                    </table>
                </div>
                <div class="mdl-card__actions mdl-card--border">
                    <button type="submit" class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
                    onclick="location.href='chooseFunctionality.html'";>
                        Back
                    </button>
                </div>
            </div>
        </div>
    </main>
</div>
</html>