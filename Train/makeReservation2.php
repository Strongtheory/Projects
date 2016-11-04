<?php
  include("session.php");

  if($_SERVER['REQUEST_METHOD'] == 'POST') {
    $cresults = $_SESSION['searchTrainResults'];
    $clength = count($cresults);
    if(isset($_SESSION['currentNumTickets'])) {
      $_SESSION['currentNumTickets'] = $_SESSION['currentNumTickets'] + 1;
      $cnt = $_SESSION['currentNumTickets'];
    } else {
      $_SESSION['currentNumTickets'] = 1;
      $cnt = 1;
    }
    for ($x = $clength-1; $x >= 0; $x--) {
      $results = $_SESSION['searchTrainResults'][$x];
      if(isset($_POST["checkbox".$x."a"])) {
        $_SESSION['chosenClass']= 1;
        $_SESSION['x'] = $x;
        $_SESSION['price'] = $results['first_class_price'];
        break;
      } else if (isset($_POST["checkbox".$x."b"])) {
        $_SESSION['chosenClass'] = 2;
        $_SESSION['x'] = $x;
        $_SESSION['price'] = $results['second_class_price'];
        break;
      }
    }
    header('location: makeReservation3.php');
  }

?>

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
<link rel="stylesheet" href="styles.css" />
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>


<style>
.demo-card-wide.mdl-card {
  width: 800px;
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
                    Select Departure
                </h2>
            </div>
            <div class="mdl-card__supporting-text">
                <form action="" method="post" role="form">
                    <div class="mdl-textfield mdl-js-textfield">
                        <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
                          <thead>
                            <tr>
                                <th align="center">Train (Train Number)</th>
                                <th>Time (Duration)</th>
                                <th style="text-align: center;">1st Class Price</th>
                                <th style="text-align: center;">2nd Class Price</th>
                            </tr>
                          </thead>
                          <tbody>
                            <?php
                            $results = $_SESSION['searchTrainResults'];
                            $length = count($results);
                            $counter = count($results);
                            while($length >= 0) {
                              if ($length < $counter) {
                                echo "<tr>";
                                echo "<td>".$results[$length]["train_number"]."</td>";
                                echo "<td><pre>".$results[$length]["Time"]." mins</pre></td>";
                                echo "<td><label class='mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect' for='checkbox".$length."a'>
                                <input type='submit' name='checkbox".$length."a'type='checkbox' id='checkbox".$length."a' class='mdl-checkbox__input'>
                                <span>".$results[$length]["first_class_price"]."</span>
                                </label>
                                </td>";
                                echo "<td><label class='mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect' for='checkbox".$length."b'>
                                <input type='submit' name='checkbox".$length."b' type='checkbox' id='checkbox".$length."b' class='mdl-checkbox__input'>
                                <span>".$results[$length]["second_class_price"]."</span>
                                </label>
                                </td>";
                                echo "</tr>";
                              }
                              $length--;
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
                </form>
                <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
                onclick="location.href='chooseFunctionality.html'";>
                    Quit
                </button>
            </div>
        </div>
    </main>
</div>
