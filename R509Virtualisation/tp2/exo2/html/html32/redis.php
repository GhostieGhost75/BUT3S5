<p><a href='../index.html'>Retour a l'accueil</a></p>
<?php 
exec("redis-cli -h 172.170.0.11 KEYS '*'", $output);
print_r($output);
echo '<table>';
foreach ($output as $key) {
	$val = exec("redis-cli -h 172.170.0.11 get ".$key);
	echo "<tr><td>".$key."</td><td>".$val."</td></tr>";
}

echo '</table>';
?>
