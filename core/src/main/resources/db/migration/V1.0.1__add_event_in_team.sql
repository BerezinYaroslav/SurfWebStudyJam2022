alter table teams add column
    event_id uuid constraint teams_events_event_id_fk references events (id);