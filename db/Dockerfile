# Use the official MySQL image from Docker Hub
FROM mysql:8.0

# Set the root password for MySQL
ENV MYSQL_ROOT_PASSWORD d3f4ult

# Create a new database
ENV MYSQL_DATABASE cloud1

# Copy the SQL script to initialize the database schema and tables
COPY init.sql /docker-entrypoint-initdb.d/
