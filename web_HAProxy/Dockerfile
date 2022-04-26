FROM haproxy:1.7
COPY haproxy.cfg /usr/local/etc/haproxy/haproxy.cfg
RUN mkdir /etc/ssl/
COPY cert.pem /etc/ssl/cert.pem
RUN mkdir /run/haproxy/