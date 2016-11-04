<?php
    include ("session.php");

?>

<html>
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
<link rel="stylesheet" href="styles.css" />
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>

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
                            <th>Revenue</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php
                        $sql = "SELECT MONTH(NOW()), SUM(Reservation.total_cost) FROM Reservation
                                WHERE Reservation.reservation_id in (
                                SELECT reservation_id FROM Reserves WHERE MONTH(departure_date) = MONTH(NOW()))
                                UNION
                                SELECT MONTH(NOW()) - 1, SUM(Reservation.total_cost) FROM Reservation
                                WHERE Reservation.reservation_id in (
                                SELECT reservation_id FROM Reserves WHERE MONTH(departure_date) = MONTH(NOW()) - 1)
                                UNION
                                SELECT MONTH(NOW()) - 2, SUM(Reservation.total_cost) FROM Reservation
                                WHERE Reservation.reservation_id in (
                                SELECT reservation_id FROM Reserves WHERE MONTH(departure_date) = MONTH(NOW()) - 2)";
                        $result = mysqli_query($db, $sql);
                        if ($result == false)
                            echo "Some error happened";
                        else
                            while ($row = mysqli_fetch_array($result)) {
                                echo "<tr>";
                                echo "<td>".$row["MONTH(NOW())"]."</td>";
                                echo "<td>".$row["SUM(Reservation.total_cost)"]."</td>";
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
</html>
