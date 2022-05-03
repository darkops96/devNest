/* proxysql user */
CREATE USER IF NOT EXISTS 'devnestMonitor'@'%' IDENTIFIED BY 'monitor';
GRANT ALL PRIVILEGES ON *.* TO 'devnestMonitor'@'%';

/* mysql exporter user */
CREATE USER IF NOT EXISTS 'devnestExporter'@'%' IDENTIFIED BY 'exporter' WITH MAX_USER_CONNECTIONS 2;
GRANT PROCESS, REPLICATION CLIENT, SELECT ON *.* TO 'exporter'@'%';

/* app user */
CREATE USER 'devnest'@'%' IDENTIFIED BY 'devnest1234';
GRANT ALL PRIVILEGES ON *.* TO 'devnest'@'%';

FLUSH PRIVILEGES;

/* start replication */
CHANGE MASTER TO MASTER_HOST='mysql-1',MASTER_USER='slave-sql',MASTER_PASSWORD='devnestRep',MASTER_AUTO_POSITION=1;
START SLAVE;
