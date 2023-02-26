CREATE SEQUENCE cinema.event_ticket_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
	
CREATE TABLE cinema.event_ticket
(
    id bigint NOT NULL DEFAULT nextval('cinema.event_ticket_id_seq'::regclass),
    ticket_id bigint NOT NULL,
    event_id bigint NOT NULL,
    CONSTRAINT event_ticket_pkey PRIMARY KEY (id),
    CONSTRAINT event_ticket_event_id_fk FOREIGN KEY (event_id)
        REFERENCES cinema.events (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT event_ticket_ticket_id_fk FOREIGN KEY (ticket_id)
        REFERENCES cinema.tickets (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);