<?php
  include ("session.php");

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
                    Confirmation
                </h2>
            </div>
            <div class="mdl-card__supporting-text">
                <table class="mdl-data-table mdl-js-data-table mdl-shadow--0dp">
                    <thead>
                    <tr>
                        <th>Reservation ID</th>
                        <?php
                          $confirmation_code = $_SESSION['reservation_id'];
                          unset($_SESSION['reservation_id']);
                          echo "<th>".$confirmation_code."</th>";
                        ?>
                    </tr>
                    </thead>
                </table>
                <p></p>
                <span>Thank you for your purchase! Please save reservation ID for your records.</span>
            </div>
            <div class="mdl-card__actions mdl-card--border">
                <button class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
                onclick="location.href='chooseFunctionality.html'";>
                    Done
                </button>
            </div>
        </div>
    </main>
</div>
