create table backup.backup_history(
  id bigint identity,
  destination_id varchar(200),
  source_id varchar(200),
  started_at bigint,
  finished_at bigint,
  exit_code integer,
  transfered_files_size bigint,
  files_size bigint,
  total_transfered_size bigint
);