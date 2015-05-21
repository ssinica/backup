create table backup.backup_history(
  id bigint identity,
  destination_id varchar(200),
  source_id varchar(200),
  startedAt bigint,
  endedAt bigint,
  exitCode integer
);