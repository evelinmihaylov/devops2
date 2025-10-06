# Use the latest MySQL image
FROM mysql/mysql-server:latest

# Set the working directory
WORKDIR /tmp

# Copy all SQL files to /tmp
COPY test_db/*.sql /tmp/
COPY test_db/*.dump /tmp/

# Initialize database on startup
COPY test_db/employees.sql /docker-entrypoint-initdb.d

# Set root password
ENV MYSQL_ROOT_PASSWORD example