# syntax=docker/dockerfile:1
FROM php:apache
COPY ./html /var/www/html
EXPOSE 80
RUN echo 'Voici la réponse à la question 4.1' > /var/www/html/html32/question.html
RUN echo '<?php phpinfo(); ?>' > /var/www/html/html32/phpinfo.php
RUN apt-get -y update && apt-get install -y redis-tools
RUN echo "<?php print_r(exec('redis-cli -h 172.170.0.11 get var1')); ?>" > /var/www/html/html32/test.php
