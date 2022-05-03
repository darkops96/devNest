FROM mysql:latest
COPY slave.cnf /etc/mysql/my.cnf
RUN chmod 644 /etc/mysql/my.cnf
COPY init.sql /docker-entrypoint-initdb.d
RUN chown -R mysql.mysql /var/lib/mysql
