
/* proxysql user */
CREATE USER IF NOT EXISTS 'devnestMonitor'@'%' IDENTIFIED BY 'monitor';

/* mysql exporter user */
CREATE USER IF NOT EXISTS 'devnestExporter'@'%' IDENTIFIED BY 'exporter' WITH MAX_USER_CONNECTIONS 2;
GRANT PROCESS, REPLICATION CLIENT, SELECT ON *.* TO 'devnestExporter'@'%';

/* slave user */
CREATE USER IF NOT EXISTS 'slave-sql'@'%' IDENTIFIED BY 'devnestRep';
GRANT REPLICATION SLAVE ON *.* TO 'slave-sql'@'%' WITH GRANT OPTION;

FLUSH PRIVILEGES;   