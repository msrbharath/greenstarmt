# Run the MySQL container, with a database named 'users' and credentials
# for a users-service user which can access it.
ECHO  "Starting DB..."
--network="host"

//dockerx run --name greenstardb -d -e MYSQL_ROOT_PASSWORD=passwordroot -e MYSQL_DATABASE=greenstar -e MYSQL_USER=dbuser -e MYSQL_PASSWORD=dbuser123  --link greenstardb:mysql -p 9411:9411 mysql:5.7

# Wait for the database service to start up.
ECHO  "Waiting for DB to start up..."
dockerx exec greenstar mysqladmin --silent --wait=30 -udbuser -pdbuser123 ping || exit 1

# Run the setup script.
ECHO  "Setting up initial data..."
dockerx exec -i greenstar mysql -udbuser -pdbuser123 greenstar < setup.sql


dockerx exec -it greenstardb bash

mysql -u dbuser -p

mysql -u root -p


CREATE USER 'dbuser'@'localhost' IDENTIFIED BY 'dbuser123';
CREATE USER 'dbuser'@'172.18.2.50' IDENTIFIED BY 'dbuser123';

GRANT ALL ON *.* TO 'dbuser'@'172.18.2.50';
GRANT ALL ON *.* TO 'dbuser'@'localhost';

SHOW VARIABLES LIKE 'validate_password%';
SET GLOBAL validate_password_policy  = LOW;

)v0.VIvrmyA4
UPDATE mysql.user SET Password=PASSWORD('password') WHERE User='root'; 

SET PASSWORD = PASSWORD('Akil@1314');

dockerx run --name greenstardb -d -e MYSQL_ROOT_PASSWORD=passwordroot -e MYSQL_DATABASE=greenstar -e MYSQL_USER=dbuser -e MYSQL_PASSWORD=dbuser123 --network=greenapp-network -p 9411:9411 mysql:5.7


dockerx stop greenstardb
dockerx start greenstardb
dockerx restart greenstardb
dockerx rm greenstardb

docker network create greenapp-network

dockerx ps --filter "name=greenstardb"
dockerx ps --filter "name=greenstarapp-discovery-service"
dockerx ps --filter "name=greenstarapp-school-service-docker"

/etc/mysql
echo 'bind-address = 0.0.0.0' >> my.cnf
cat my.cnf

https://stackoverflow.com/questions/24319662/from-inside-of-a-docker-container-how-do-i-connect-to-the-localhost-of-the-mach