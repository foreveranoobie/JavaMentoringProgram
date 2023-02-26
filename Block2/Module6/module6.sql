--
-- PostgreSQL database dump
--

-- Dumped from database version 11.12
-- Dumped by pg_dump version 11.12

-- Started on 2021-10-05 17:13:45

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 9 (class 2615 OID 336344)
-- Name: cinema; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA cinema;


ALTER SCHEMA cinema OWNER TO postgres;

--
-- TOC entry 217 (class 1255 OID 342033)
-- Name: insert_data(bigint); Type: FUNCTION; Schema: cinema; Owner: postgres
--

CREATE FUNCTION cinema.insert_data(data_size bigint DEFAULT 1) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
	rand_category bigint;
	set_category category;
	event_create_date_rand timestamp;
	event_date_rand timestamp;
	user_create_date_rand timestamp;
	random_name varchar;
	random_surname varchar;
	random_email_domain varchar;
	username varchar;
	names_arr varchar[] = array ['Bob', 'John', 'Garry', 'Peter', 'Richard', 'Mary', 'Liza', 'Jack', 'Donald', 'Elizabeth'];
	surnames_arr varchar[] = array ['Doe', 'Smith', 'Harrison', 'Lennon', 'Williams', 'Brown', 'Thomas', 'Anderson', 'Garcia', 'Thompson'];
	email_domains_arr varchar[] = array ['@email.com', '@gmail.com', '@protonmail.com', '@yahoo.com'];
	user_id_arr bigint[];
	event_id_arr bigint[];
	event_date_arr timestamp[];
	user_update_date_arr timestamp[];
	current_user_order bigint;
	current_event_order bigint;
	current_user_id bigint;
	current_event_id bigint;
	last_ticket_id bigint;
begin
	delete from cinema.events;
	delete from cinema.users;
	delete from cinema.tickets;
	ALTER SEQUENCE cinema.events_id_seq RESTART with 1;
	ALTER SEQUENCE cinema.users_id_seq RESTART with 1;
	ALTER SEQUENCE cinema.tickets_id_seq RESTART with 1;

	FOR ind IN 1..data_size loop
		random_name := names_arr[round(random() * (array_length(names_arr, 1)-1) + 1)];
		random_surname := surnames_arr[round(random() * (array_length(surnames_arr, 1)-1) + 1)];
		random_email_domain = email_domains_arr[round(random() * (array_length(email_domains_arr, 1)-1)+1)];
		username := concat(concat(round(random() * 10000 + 0), concat(concat(random_name, '.'), random_surname)), round(random() * 10000 + 0));
	    user_create_date_rand := timestamp '2021-01-01 00:00:00' + random() * interval '180 day';
		event_create_date_rand := timestamp '2021-06-01 00:00:00' + random() * interval '90 day';
		event_date_rand := event_create_date_rand + random() * interval '30 day';
	
	-- Insertion into events table
		INSERT INTO cinema.events 
		(title, date, create_date, update_date)
		VALUES (concat('Film',ind), event_date_rand, event_create_date_rand, event_create_date_rand);
	
	-- Insertion into users table
		INSERT INTO cinema.users(username, first_name, last_name, age, email, create_date, update_date) VALUES (username, random_name, random_surname, round(random() * 60 + 15), concat(username, random_email_domain), user_create_date_rand, (user_create_date_rand + random() * interval '30 day')::timestamp);
	END LOOP;
	event_date_arr := array(select date from cinema.events order by id);
	user_update_date_arr := array(select update_date from cinema.users order by id);
	user_id_arr := ARRAY(SELECT id FROM cinema.users order by id);
	event_id_arr := ARRAY(SELECT id FROM cinema.events order by id);
	FOR ind IN 1..data_size loop
		rand_category := random() * 2 + 1;
		IF rand_category = 1 THEN
			set_category := 'STANDART';
		ELSIF rand_category = 2 THEN
			set_category := 'PREMIUM';
		ELSIF rand_category = 3 THEN
			set_category := 'ULTRA';
		END IF;
		loop
			current_user_order = round(random() * (array_length(user_id_arr, 1)-1) +1);
			current_event_order = round(random() * (array_length(event_id_arr, 1)-1) +1);
    		IF event_date_arr[current_event_order]::timestamp > user_update_date_arr[current_user_order]::timestamp THEN
        	EXIT;
    		END IF;
		END LOOP;
		current_user_id = user_id_arr[current_user_order];
		current_event_id = event_id_arr[current_event_order];
	-- Insertion into tickets table
		insert into cinema.tickets(event_id, user_id, category, place, cinema_name, cinema_address, purchase_date, create_date, update_date) 
		values(current_event_id, current_user_id, set_category, random() * 500 + 1, concat('Cinema', round(random() * 9 + 1)), concat(round(random() * 149 + 1), ' Street'), user_update_date_arr[current_user_order], user_update_date_arr[current_user_order] - random() * interval '45 day', user_update_date_arr[current_user_order]);
		last_ticket_id := currval('cinema.tickets_id_seq');
		for arr_ind in 1..3 LOOP
			update cinema.tickets set cinema_phone = array_append(cinema_phone, round(random() * 88888 + 11111)::varchar) where id = last_ticket_id;
			update cinema.tickets set cinema_facilities = array_append(cinema_facilities, concat('Facility', round(random() * 9 + 1)::varchar)::varchar) where id = last_ticket_id;	
		end loop;
	end loop;
END

$$;


ALTER FUNCTION cinema.insert_data(data_size bigint) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 201 (class 1259 OID 336360)
-- Name: events; Type: TABLE; Schema: cinema; Owner: postgres
--

CREATE TABLE cinema.events (
    id bigint NOT NULL,
    title character varying NOT NULL,
    date timestamp without time zone NOT NULL,
    create_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone NOT NULL
);


ALTER TABLE cinema.events OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 336358)
-- Name: events_id_seq; Type: SEQUENCE; Schema: cinema; Owner: postgres
--

CREATE SEQUENCE cinema.events_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cinema.events_id_seq OWNER TO postgres;

--
-- TOC entry 2854 (class 0 OID 0)
-- Dependencies: 200
-- Name: events_id_seq; Type: SEQUENCE OWNED BY; Schema: cinema; Owner: postgres
--

ALTER SEQUENCE cinema.events_id_seq OWNED BY cinema.events.id;


--
-- TOC entry 203 (class 1259 OID 336371)
-- Name: tickets; Type: TABLE; Schema: cinema; Owner: postgres
--

CREATE TABLE cinema.tickets (
    id bigint NOT NULL,
    event_id bigint NOT NULL,
    user_id bigint NOT NULL,
    place integer NOT NULL,
    cinema_name character varying NOT NULL,
    cinema_address character varying NOT NULL,
    cinema_phone character varying[],
    cinema_facilities character varying[],
    purchase_date timestamp without time zone NOT NULL,
    create_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone NOT NULL,
    category public.category NOT NULL
);


ALTER TABLE cinema.tickets OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 336369)
-- Name: tickets_id_seq; Type: SEQUENCE; Schema: cinema; Owner: postgres
--

CREATE SEQUENCE cinema.tickets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cinema.tickets_id_seq OWNER TO postgres;

--
-- TOC entry 2855 (class 0 OID 0)
-- Dependencies: 202
-- Name: tickets_id_seq; Type: SEQUENCE OWNED BY; Schema: cinema; Owner: postgres
--

ALTER SEQUENCE cinema.tickets_id_seq OWNED BY cinema.tickets.id;


--
-- TOC entry 199 (class 1259 OID 336347)
-- Name: users; Type: TABLE; Schema: cinema; Owner: postgres
--

CREATE TABLE cinema.users (
    id bigint NOT NULL,
    username character varying NOT NULL,
    first_name character varying,
    last_name character varying,
    age integer,
    email character varying NOT NULL,
    create_date timestamp without time zone NOT NULL,
    update_date timestamp without time zone NOT NULL
);


ALTER TABLE cinema.users OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 336345)
-- Name: users_id_seq; Type: SEQUENCE; Schema: cinema; Owner: postgres
--

CREATE SEQUENCE cinema.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cinema.users_id_seq OWNER TO postgres;

--
-- TOC entry 2856 (class 0 OID 0)
-- Dependencies: 198
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: cinema; Owner: postgres
--

ALTER SEQUENCE cinema.users_id_seq OWNED BY cinema.users.id;


--
-- TOC entry 2709 (class 2604 OID 336363)
-- Name: events id; Type: DEFAULT; Schema: cinema; Owner: postgres
--

ALTER TABLE ONLY cinema.events ALTER COLUMN id SET DEFAULT nextval('cinema.events_id_seq'::regclass);


--
-- TOC entry 2710 (class 2604 OID 336374)
-- Name: tickets id; Type: DEFAULT; Schema: cinema; Owner: postgres
--

ALTER TABLE ONLY cinema.tickets ALTER COLUMN id SET DEFAULT nextval('cinema.tickets_id_seq'::regclass);


--
-- TOC entry 2708 (class 2604 OID 336350)
-- Name: users id; Type: DEFAULT; Schema: cinema; Owner: postgres
--

ALTER TABLE ONLY cinema.users ALTER COLUMN id SET DEFAULT nextval('cinema.users_id_seq'::regclass);


--
-- TOC entry 2846 (class 0 OID 336360)
-- Dependencies: 201
-- Data for Name: events; Type: TABLE DATA; Schema: cinema; Owner: postgres
--

COPY cinema.events (id, title, date, create_date, update_date) FROM stdin;
1	Film1	2021-09-04 21:00:51.938081	2021-08-28 01:43:00.925477	2021-08-28 01:43:00.925477
2	Film2	2021-08-29 21:15:14.62453	2021-08-06 08:31:27.357268	2021-08-06 08:31:27.357268
3	Film3	2021-06-29 19:55:10.396233	2021-06-07 04:56:38.479682	2021-06-07 04:56:38.479682
4	Film4	2021-08-16 00:18:26.412506	2021-07-28 12:01:33.165055	2021-07-28 12:01:33.165055
5	Film5	2021-07-07 12:32:43.678265	2021-06-25 19:26:30.814814	2021-06-25 19:26:30.814814
6	Film6	2021-08-18 14:32:42.098846	2021-07-20 23:50:51.656163	2021-07-20 23:50:51.656163
7	Film7	2021-07-12 01:31:21.520137	2021-06-17 15:27:42.549904	2021-06-17 15:27:42.549904
8	Film8	2021-07-10 20:58:32.688303	2021-07-09 23:55:14.201429	2021-07-09 23:55:14.201429
9	Film9	2021-07-09 16:47:19.475271	2021-07-06 04:10:43.411061	2021-07-06 04:10:43.411061
10	Film10	2021-09-08 22:34:05.713332	2021-08-26 12:35:56.62069	2021-08-26 12:35:56.62069
\.


--
-- TOC entry 2848 (class 0 OID 336371)
-- Dependencies: 203
-- Data for Name: tickets; Type: TABLE DATA; Schema: cinema; Owner: postgres
--

COPY cinema.tickets (id, event_id, user_id, place, cinema_name, cinema_address, cinema_phone, cinema_facilities, purchase_date, create_date, update_date, category) FROM stdin;
1	2	3	414	Cinema8	142 Street	{87749,93646,70478}	{Facility7,Facility3,Facility7}	2021-03-29 15:28:44.107888	2021-03-20 12:28:20.131474	2021-03-29 15:28:44.107888	ULTRA
2	6	7	109	Cinema9	127 Street	{94093,69944,81249}	{Facility8,Facility3,Facility10}	2021-03-12 23:49:36.639551	2021-02-19 14:35:15.383148	2021-03-12 23:49:36.639551	STANDART
3	8	5	133	Cinema3	74 Street	{25383,51840,97187}	{Facility8,Facility6,Facility1}	2021-02-16 04:27:36.383318	2021-01-25 09:07:17.536553	2021-02-16 04:27:36.383318	ULTRA
4	8	1	281	Cinema7	15 Street	{39486,57219,82146}	{Facility4,Facility9,Facility2}	2021-07-03 22:14:20.589525	2021-06-02 10:57:12.136108	2021-07-03 22:14:20.589525	PREMIUM
5	2	1	242	Cinema7	4 Street	{15317,90089,39975}	{Facility2,Facility5,Facility8}	2021-07-03 22:14:20.589525	2021-06-21 00:47:35.528748	2021-07-03 22:14:20.589525	STANDART
6	7	3	355	Cinema7	142 Street	{53574,37730,35878}	{Facility8,Facility5,Facility8}	2021-03-29 15:28:44.107888	2021-03-11 21:51:56.669717	2021-03-29 15:28:44.107888	PREMIUM
7	6	7	204	Cinema7	85 Street	{16809,82769,57756}	{Facility6,Facility5,Facility9}	2021-03-12 23:49:36.639551	2021-02-08 02:18:10.132302	2021-03-12 23:49:36.639551	STANDART
8	6	10	240	Cinema6	63 Street	{50126,47566,39082}	{Facility1,Facility4,Facility4}	2021-05-22 02:39:09.322253	2021-05-10 06:39:53.672029	2021-05-22 02:39:09.322253	ULTRA
9	1	3	123	Cinema8	101 Street	{88528,33337,99687}	{Facility7,Facility8,Facility10}	2021-03-29 15:28:44.107888	2021-03-13 20:37:39.190535	2021-03-29 15:28:44.107888	ULTRA
10	2	9	316	Cinema5	94 Street	{55090,74772,50822}	{Facility3,Facility7,Facility3}	2021-06-02 07:00:03.069654	2021-05-09 02:38:52.098408	2021-06-02 07:00:03.069654	STANDART
\.


--
-- TOC entry 2844 (class 0 OID 336347)
-- Dependencies: 199
-- Data for Name: users; Type: TABLE DATA; Schema: cinema; Owner: postgres
--

COPY cinema.users (id, username, first_name, last_name, age, email, create_date, update_date) FROM stdin;
1	41Mary.Williams47	Mary	Williams	36	41Mary.Williams47@gmail.com	2021-06-10 08:14:55.741564	2021-07-03 22:14:20.589525
2	96Mary.Anderson70	Mary	Anderson	38	96Mary.Anderson70@gmail.com	2021-06-15 18:57:49.372612	2021-07-13 20:56:15.559214
3	1Liza.Doe47	Liza	Doe	51	1Liza.Doe47@yahoo.com	2021-03-16 06:44:15.202496	2021-03-29 15:28:44.107888
4	69Donald.Anderson9	Donald	Anderson	17	69Donald.Anderson9@gmail.com	2021-01-17 03:06:22.625806	2021-02-09 06:47:49.69057
5	53Liza.Garcia69	Liza	Garcia	21	53Liza.Garcia69@protonmail.com	2021-02-09 07:06:36.724892	2021-02-16 04:27:36.383318
6	8Richard.Brown37	Richard	Brown	16	8Richard.Brown37@protonmail.com	2021-06-26 20:07:22.968643	2021-07-15 09:10:15.695393
7	32John.Williams93	John	Williams	66	32John.Williams93@yahoo.com	2021-02-23 16:14:08.723245	2021-03-12 23:49:36.639551
8	89Liza.Brown60	Liza	Brown	68	89Liza.Brown60@protonmail.com	2021-04-15 01:12:05.256282	2021-04-16 15:14:44.623244
9	22John.Smith51	John	Smith	21	22John.Smith51@protonmail.com	2021-05-26 00:56:01.327976	2021-06-02 07:00:03.069654
10	15Mary.Thomas35	Mary	Thomas	63	15Mary.Thomas35@protonmail.com	2021-04-24 07:29:48.560754	2021-05-22 02:39:09.322253
\.


--
-- TOC entry 2857 (class 0 OID 0)
-- Dependencies: 200
-- Name: events_id_seq; Type: SEQUENCE SET; Schema: cinema; Owner: postgres
--

SELECT pg_catalog.setval('cinema.events_id_seq', 10, true);


--
-- TOC entry 2858 (class 0 OID 0)
-- Dependencies: 202
-- Name: tickets_id_seq; Type: SEQUENCE SET; Schema: cinema; Owner: postgres
--

SELECT pg_catalog.setval('cinema.tickets_id_seq', 10, true);


--
-- TOC entry 2859 (class 0 OID 0)
-- Dependencies: 198
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: cinema; Owner: postgres
--

SELECT pg_catalog.setval('cinema.users_id_seq', 10, true);


--
-- TOC entry 2716 (class 2606 OID 336368)
-- Name: events events_pk; Type: CONSTRAINT; Schema: cinema; Owner: postgres
--

ALTER TABLE ONLY cinema.events
    ADD CONSTRAINT events_pk PRIMARY KEY (id);


--
-- TOC entry 2718 (class 2606 OID 336376)
-- Name: tickets tickets_pk; Type: CONSTRAINT; Schema: cinema; Owner: postgres
--

ALTER TABLE ONLY cinema.tickets
    ADD CONSTRAINT tickets_pk PRIMARY KEY (id);


--
-- TOC entry 2712 (class 2606 OID 336357)
-- Name: users unique_username; Type: CONSTRAINT; Schema: cinema; Owner: postgres
--

ALTER TABLE ONLY cinema.users
    ADD CONSTRAINT unique_username UNIQUE (username);


--
-- TOC entry 2714 (class 2606 OID 336355)
-- Name: users users_pk; Type: CONSTRAINT; Schema: cinema; Owner: postgres
--

ALTER TABLE ONLY cinema.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- TOC entry 2721 (class 2620 OID 342091)
-- Name: tickets check_ticket_date; Type: TRIGGER; Schema: cinema; Owner: postgres
--

CREATE TRIGGER check_ticket_date BEFORE INSERT ON cinema.tickets FOR EACH ROW EXECUTE PROCEDURE public.check_ticket_date();


--
-- TOC entry 2720 (class 2606 OID 336393)
-- Name: tickets tickets_event_id_fk; Type: FK CONSTRAINT; Schema: cinema; Owner: postgres
--

ALTER TABLE ONLY cinema.tickets
    ADD CONSTRAINT tickets_event_id_fk FOREIGN KEY (event_id) REFERENCES cinema.events(id) ON DELETE CASCADE;


--
-- TOC entry 2719 (class 2606 OID 336388)
-- Name: tickets tickets_user_id_fk; Type: FK CONSTRAINT; Schema: cinema; Owner: postgres
--

ALTER TABLE ONLY cinema.tickets
    ADD CONSTRAINT tickets_user_id_fk FOREIGN KEY (user_id) REFERENCES cinema.users(id) ON DELETE CASCADE;


-- Completed on 2021-10-05 17:13:45

--
-- PostgreSQL database dump complete
--

