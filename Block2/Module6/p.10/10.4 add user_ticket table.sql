CREATE SEQUENCE cinema.user_ticket_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE cinema.user_ticket
(
    id bigint NOT NULL DEFAULT nextval('cinema.user_ticket_id_seq'::regclass),
    ticket_id bigint NOT NULL,
    user_id bigint NOT NULL,
    purchase_date timestamp without time zone NOT NULL,
    CONSTRAINT user_ticket_pkey PRIMARY KEY (id),
    CONSTRAINT user_ticket_fk FOREIGN KEY (user_id)
        REFERENCES cinema.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT user_ticket_fk_1 FOREIGN KEY (ticket_id)
        REFERENCES cinema.tickets (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);