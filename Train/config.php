<?php
  define('DB_SERVER', 'localhost');
  define('DB_USERNAME', 'newuser');
  define('DB_PASSWORD', 'password');
  define('DB_DATABASE', 'cs4400_Team_70');
  $db = mysqli_connect(DB_SERVER,DB_USERNAME,DB_PASSWORD,DB_DATABASE);
  if (!$db)
  	die();
  if (mysqli_connect_errno()) {
  	echo "Failed to connect " . mysqli_connect_error();
  }
?>
