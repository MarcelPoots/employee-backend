
Have your Cassandra woring at:

Connected to Test Cluster at 127.0.0.1:9042.

I use:

[cqlsh 5.0.1 | Cassandra 3.11.1 | CQL spec 3.4.4 | Native protocol v4]
Use HELP for help.

#CREATE KEYSPACE IF NOT EXISTS example WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};

cqlsh:example> describe tables

users

cqlsh:example> describe users;

CREATE TABLE IF NOT EXISTS example.users (
    user_id bigint PRIMARY KEY,
    fname text,
    lname text,
    uname text
);
    
CREATE INDEX IF NOT EXISTS user_username ON example.users (uname);
CREATE CUSTOM INDEX IF NOT EXISTS users_lname_idx_1 ON example.users (lname) USING 'org.apache.cassandra.index.sasi.SASIIndex' WITH OPTIONS = {'mode': 'CONTAINS'};
CREATE CUSTOM INDEX IF NOT EXISTS users_lname_idx ON example.users (lname) USING 'org.apache.cassandra.index.sasi.SASIIndex';

uy
Now you can use the very cool query:

cqlsh:example> select * from users where lname like '%oot%';

 user_id | fname  | lname | uname
---------+--------+-------+-------
      49 | Marcel | Poots |  null

CREATE TABLE IF NOT EXISTS male_first_names (
    id uuid PRIMARY KEY,
    name text
);

CREATE INDEX IF NOT EXISTS male_first_name ON example.male_first_names (name);
CREATE CUSTOM INDEX IF NOT EXISTS male_first_name_contains ON example.male_first_names (name) USING 'org.apache.cassandra.index.sasi.SASIIndex' WITH OPTIONS = {'mode': 'CONTAINS'};

CREATE TABLE IF NOT EXISTS female_first_names (
    id uuid PRIMARY KEY,
    name text
);

CREATE INDEX IF NOT EXISTS female_first_name ON example.female_first_names (name);
CREATE CUSTOM INDEX IF NOT EXISTS female_first_name_contains ON example.female_first_names (name) USING 'org.apache.cassandra.index.sasi.SASIIndex' WITH OPTIONS = {'mode': 'CONTAINS'};

CREATE TABLE IF NOT EXISTS family_names (
    id uuid PRIMARY KEY,
    name text
);

CREATE INDEX IF NOT EXISTS family_name ON example.family_names (name);
CREATE CUSTOM INDEX IF NOT EXISTS family_name_contains ON example.family_names (name) USING 'org.apache.cassandra.index.sasi.SASIIndex' WITH OPTIONS = {'mode': 'CONTAINS'};
