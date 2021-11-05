#!/bin/bash
set -e

psql -v --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE ROLE readwrite;
	GRANT CONNECT ON DATABASE local_trading TO readwrite;

	CREATE SCHEMA IF NOT EXISTS sch_trading;
	GRANT USAGE ON SCHEMA sch_trading TO readwrite;

	GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, TRUNCATE ON ALL TABLES IN SCHEMA sch_trading TO readwrite;

	CREATE USER user_trading WITH PASSWORD 'tradinglocalp4ss';
	GRANT readwrite TO user_trading;

	GRANT USAGE, CREATE, TRUNCATE, REFERENCES ON SCHEMA sch_trading TO user_trading;
EOSQL
