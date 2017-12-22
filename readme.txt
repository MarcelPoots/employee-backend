
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
) WITH bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
    AND comment = ''
    AND compaction = {'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
    AND compression = {'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1.0
    AND dclocal_read_repair_chance = 0.1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair_chance = 0.0
    AND speculative_retry = '99PERCENTILE';
    
CREATE INDEX IF NOT EXISTS user_username ON example.users (uname);
CREATE CUSTOM INDEX IF NOT EXISTS users_lname_idx_1 ON example.users (lname) USING 'org.apache.cassandra.index.sasi.SASIIndex' WITH OPTIONS = {'mode': 'CONTAINS'};
CREATE CUSTOM INDEX IF NOT EXISTS users_lname_idx ON example.users (lname) USING 'org.apache.cassandra.index.sasi.SASIIndex';


Now you can use the very cool query:

cqlsh:example> select * from users where lname like '%oot%';

 user_id | fname  | lname | uname
---------+--------+-------+-------
      49 | Marcel | Poots |  null

