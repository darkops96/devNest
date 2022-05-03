/* proxysql user */
CREATE USER IF NOT EXISTS 'monitor'@'%' IDENTIFIED BY 'monitor';

/* mysql exporter user */
CREATE USER IF NOT EXISTS 'exporter'@'%' IDENTIFIED BY 'password' WITH MAX_USER_CONNECTIONS 3;
GRANT PROCESS, REPLICATION CLIENT, SELECT ON *.* TO 'exporter'@'%';

FLUSH PRIVILEGES;

/* start replication */
CHANGE MASTER TO MASTER_HOST='mysql-1',MASTER_USER='slave-sql',MASTER_PASSWORD='devnestRep',MASTER_AUTO_POSITION=1;
START SLAVE;