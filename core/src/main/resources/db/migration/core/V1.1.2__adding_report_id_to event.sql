alter table events
    add column if not exists
        report_file_id uuid default null;