<?php
    include ("session.php");
?>

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
<link rel="stylesheet" href="styles.css" />
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>

<style>
.demo-card-wide.mdl-card {
  width: 450px;
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
                    View Revenue Report
                </h2>
            </div>
            <div class="mdl-card__supporting-text">
                <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
                    <thead>
                        <tr>
                            <th>Month</th>
                            <th>Train Number</th>
                            <th>Number of Reservations</th>
                        </tr>
                    </thead>
                    <tbody>
                      <?php
                      $sql = "(SELECT month(departure_date) as m, train_number as train, count(train_number) as total
                  from Reserves x , Reservation y
                  where x.reservation_id = y.reservation_id and
                  month(departure_date) = month(now())
                  GROUP BY Train)

                  union

                  (select month(departure_date) as m, train_number as train, count(train_number) as total
                  from Reserves x , Reservation y
                  where x.reservation_id = y.reservation_id and
                  month(departure_date) = month(now()) - 1
                  GROUP BY Train)

                  UNION

                  (select month(departure_date) as m, train_number as train, count(train_number) as total
                  from Reserves x , Reservation y
                  where x.reservation_id = y.reservation_id and
                  month(departure_date) = month(now()) - 2
                  GROUP BY Train)
                  ORDER BY m DESC, total DESC";

                      $result = mysqli_query($db, $sql);
                      $currentMonth = 5;
                      while($row = mysqli_fetch_array($result)) {
                        echo "<tr>";
                        if($row['m'] != $currentMonth) {
                          $currentMonth--;
                          echo "<td>".$currentMonth."</td>";
                        } else {
                          echo "<td>  -  </td>";
                        }
                        echo "<td>".$row["train"]."</td>";
                        echo "<td>".$row["total"]."</td>";
                        echo "</tr>";
                      }
                      ?>
                    </tbody>
                </table>
                <p></p>
                <div class="mdl-card__actions mdl-card--border">
                    <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"onclick="location.href='chooseFunctionalityManager.html'";>
                        Back
                    </button>
                </div>
            </div>
        </div>
    </main>
</div>
