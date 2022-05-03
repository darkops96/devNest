
/* proxysql user */
CREATE USER IF NOT EXISTS 'devnestMonitor'@'%' IDENTIFIED BY 'monitor';
GRANT ALL PRIVILEGES ON *.* TO 'devnestMonitor'@'%';

/* mysql exporter user */
CREATE USER IF NOT EXISTS 'devnestExporter'@'%' IDENTIFIED BY 'exporter' WITH MAX_USER_CONNECTIONS 2;
GRANT PROCESS, REPLICATION CLIENT, SELECT ON *.* TO 'devnestExporter'@'%';

/* slave user */
CREATE USER IF NOT EXISTS 'slave-sql'@'%' IDENTIFIED BY 'devnestRep';
GRANT REPLICATION SLAVE ON *.* TO 'slave-sql'@'%' WITH GRANT OPTION;

/* app user */
CREATE USER 'devnest'@'%' IDENTIFIED BY 'devnest1234';
GRANT ALL PRIVILEGES ON *.* TO 'devnest'@'%';

FLUSH PRIVILEGES;   