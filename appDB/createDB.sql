--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: user; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA "user";


ALTER SCHEMA "user" OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: appuser; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.appuser (
    id integer NOT NULL,
    name text NOT NULL,
    passwordsalt text NOT NULL,
    passwordhash text NOT NULL,
    role text NOT NULL
);


ALTER TABLE public.appuser OWNER TO postgres;

--
-- Name: appuser_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.appuser_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.appuser_id_seq OWNER TO postgres;

--
-- Name: appuser_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.appuser_id_seq OWNED BY public.appuser.id;


--
-- Name: food_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.food_category (
    idfood_category integer NOT NULL,
    food_category_name character varying(45)
);


ALTER TABLE public.food_category OWNER TO postgres;

--
-- Name: food_category_idfood_category_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.food_category_idfood_category_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.food_category_idfood_category_seq OWNER TO postgres;

--
-- Name: food_category_idfood_category_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.food_category_idfood_category_seq OWNED BY public.food_category.idfood_category;


--
-- Name: ingredients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ingredients (
    ingredient_id integer NOT NULL,
    ingredient_name character varying(45),
    nutrition_profile_id integer
);


ALTER TABLE public.ingredients OWNER TO postgres;

--
-- Name: ingredients_ingredient_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ingredients_ingredient_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ingredients_ingredient_id_seq OWNER TO postgres;

--
-- Name: ingredients_ingredient_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ingredients_ingredient_id_seq OWNED BY public.ingredients.ingredient_id;


--
-- Name: measurement_qty; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.measurement_qty (
    measurement_qty_id integer NOT NULL,
    qty_amount double precision
);


ALTER TABLE public.measurement_qty OWNER TO postgres;

--
-- Name: measurement_qty_measurement_qty_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.measurement_qty_measurement_qty_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.measurement_qty_measurement_qty_id_seq OWNER TO postgres;

--
-- Name: measurement_qty_measurement_qty_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.measurement_qty_measurement_qty_id_seq OWNED BY public.measurement_qty.measurement_qty_id;


--
-- Name: measurement_units; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.measurement_units (
    measurement_units_id integer NOT NULL,
    measurement_description character varying(20)
);


ALTER TABLE public.measurement_units OWNER TO postgres;

--
-- Name: measurement_units_measurement_units_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.measurement_units_measurement_units_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.measurement_units_measurement_units_id_seq OWNER TO postgres;

--
-- Name: measurement_units_measurement_units_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.measurement_units_measurement_units_id_seq OWNED BY public.measurement_units.measurement_units_id;


--
-- Name: recipe_ingredients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recipe_ingredients (
    recipe_ingredients_id integer NOT NULL,
    ingredients_ingredient_id integer NOT NULL,
    measurement_qty_id integer NOT NULL,
    recipes_recipe_id integer NOT NULL,
    measurement_units_id integer
);


ALTER TABLE public.recipe_ingredients OWNER TO postgres;

--
-- Name: my_serial; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.my_serial
    AS integer
    START WITH 6
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.my_serial OWNER TO postgres;

--
-- Name: my_serial; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.my_serial OWNED BY public.recipe_ingredients.recipe_ingredients_id;


--
-- Name: nutrition_profile; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nutrition_profile (
    nutrition_profile_id integer NOT NULL,
    kcal_per100 double precision,
    protein_per100 double precision,
    carbohydrates_per100 double precision,
    fat_per100 double precision
);


ALTER TABLE public.nutrition_profile OWNER TO postgres;

--
-- Name: nutrition_profile_nutrition_profile_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.nutrition_profile_nutrition_profile_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.nutrition_profile_nutrition_profile_id_seq OWNER TO postgres;

--
-- Name: nutrition_profile_nutrition_profile_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.nutrition_profile_nutrition_profile_id_seq OWNED BY public.nutrition_profile.nutrition_profile_id;


--
-- Name: recipes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recipes (
    recipe_id integer NOT NULL,
    food_category_idfood_category integer NOT NULL,
    recipe_name character varying(45),
    recipe_description character varying,
    recipe_kcal double precision,
    recipe_shortdescription character varying
);


ALTER TABLE public.recipes OWNER TO postgres;

--
-- Name: recipes_recipe_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.recipes_recipe_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.recipes_recipe_id_seq OWNER TO postgres;

--
-- Name: recipes_recipe_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.recipes_recipe_id_seq OWNED BY public.recipes.recipe_id;


--
-- Name: appuser id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appuser ALTER COLUMN id SET DEFAULT nextval('public.appuser_id_seq'::regclass);


--
-- Name: food_category idfood_category; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.food_category ALTER COLUMN idfood_category SET DEFAULT nextval('public.food_category_idfood_category_seq'::regclass);


--
-- Name: ingredients ingredient_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredients ALTER COLUMN ingredient_id SET DEFAULT nextval('public.ingredients_ingredient_id_seq'::regclass);


--
-- Name: measurement_qty measurement_qty_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.measurement_qty ALTER COLUMN measurement_qty_id SET DEFAULT nextval('public.measurement_qty_measurement_qty_id_seq'::regclass);


--
-- Name: measurement_units measurement_units_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.measurement_units ALTER COLUMN measurement_units_id SET DEFAULT nextval('public.measurement_units_measurement_units_id_seq'::regclass);


--
-- Name: nutrition_profile nutrition_profile_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nutrition_profile ALTER COLUMN nutrition_profile_id SET DEFAULT nextval('public.nutrition_profile_nutrition_profile_id_seq'::regclass);


--
-- Name: recipe_ingredients recipe_ingredients_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe_ingredients ALTER COLUMN recipe_ingredients_id SET DEFAULT nextval('public.my_serial'::regclass);


--
-- Name: recipes recipe_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipes ALTER COLUMN recipe_id SET DEFAULT nextval('public.recipes_recipe_id_seq'::regclass);


--
-- Data for Name: appuser; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.appuser (id, name, passwordsalt, passwordhash, role) FROM stdin;
1	KarolAdmin	𰈵𮞊𗭅𤞍🍲⍇꓆𪤆𥮄Ḫ丸𨝍脰𒑑𫄘𥛮𡋺𦻳	58bdfd40db923c6fc7031ac6431b0d4aff67f4ce	ADMIN
2	KarolUser	𦚬䋔楽𥸩𧢚𣂘譇𪀲𨕹⛖Ԫ𪰪𤘭卶𪩨菤𘓈𖫨喱𬡋	595e4df73bd5b4aaeee836414ae8705b6f358621	USER
\.


--
-- Data for Name: food_category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.food_category (idfood_category, food_category_name) FROM stdin;
1	Obiad
2	Śniadanie
3	Drugie śniadanie
4	Podwieczorek
5	Kolacja
\.


--
-- Data for Name: ingredients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ingredients (ingredient_id, ingredient_name, nutrition_profile_id) FROM stdin;
6	Makaron Biały	5
7	Mięso mielone z wołowiny	6
8	Passata pomidorowa	7
9	Ryż	8
10	Kurczak	9
\.


--
-- Data for Name: measurement_qty; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.measurement_qty (measurement_qty_id, qty_amount) FROM stdin;
1	150
2	100
3	300
4	120
5	180
6	50
7	200
8	50
9	200
10	1000
11	100
12	100
13	100
14	100
15	100
16	100
17	100
18	100
19	100
20	100
21	100
22	100
23	100
24	100
25	100
26	100
27	100
28	100
29	100
30	100
31	100
32	20
33	200
34	500
35	500
36	100
37	20
38	10
39	50
40	50
41	50
42	50
\.


--
-- Data for Name: measurement_units; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.measurement_units (measurement_units_id, measurement_description) FROM stdin;
1	gram
\.


--
-- Data for Name: nutrition_profile; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.nutrition_profile (nutrition_profile_id, kcal_per100, protein_per100, carbohydrates_per100, fat_per100) FROM stdin;
5	101	3.6	19.9	0.4
6	254	17.17	0	20
7	30	2	5	0
8	130	2.4	28	0.3
10	345	15.2	68	1.3
11	345	15	68	1.3
9	241	27	0	14
33	111	11	33	22
34	1234	21	43	32
\.


--
-- Data for Name: recipe_ingredients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.recipe_ingredients (recipe_ingredients_id, ingredients_ingredient_id, measurement_qty_id, recipes_recipe_id, measurement_units_id) FROM stdin;
1	6	1	1	1
2	7	2	1	1
3	8	3	1	1
4	9	4	2	1
5	10	5	2	1
8	6	36	36	1
9	9	37	36	1
10	10	38	36	1
12	7	40	38	1
13	7	41	38	1
14	6	42	39	1
\.


--
-- Data for Name: recipes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.recipes (recipe_id, food_category_idfood_category, recipe_name, recipe_description, recipe_kcal, recipe_shortdescription) FROM stdin;
1	1	Spaghetti Bolognese	1. ssssssssss 2. sssssss 3. ccccccccccc 4. ddddddd	\N	Danie kuchni włoskiej
2	1	Risotto z kurczakiem	1. testestest 2. testestesttestestest 3. testestesttestestest 4. testestesttestestest	\N	Smaczne risotto z kurczakiem
36	1	Kebab	1\n2\n3\n4	151	Bardzo dobre jedzonko
38	2	sdad	dsf	254	sxcsdf
39	2	testsetset	dsfdsfdsfdsfdsf	51	estestes
\.


--
-- Name: appuser_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.appuser_id_seq', 2, true);


--
-- Name: food_category_idfood_category_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.food_category_idfood_category_seq', 5, true);


--
-- Name: ingredients_ingredient_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ingredients_ingredient_id_seq', 53, true);


--
-- Name: measurement_qty_measurement_qty_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.measurement_qty_measurement_qty_id_seq', 42, true);


--
-- Name: measurement_units_measurement_units_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.measurement_units_measurement_units_id_seq', 1, false);


--
-- Name: my_serial; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.my_serial', 14, true);


--
-- Name: nutrition_profile_nutrition_profile_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.nutrition_profile_nutrition_profile_id_seq', 56, true);


--
-- Name: recipes_recipe_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recipes_recipe_id_seq', 39, true);


--
-- Name: appuser appuser_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appuser
    ADD CONSTRAINT appuser_pkey PRIMARY KEY (id);


--
-- Name: food_category food_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.food_category
    ADD CONSTRAINT food_category_pkey PRIMARY KEY (idfood_category);


--
-- Name: ingredients ingredients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredients
    ADD CONSTRAINT ingredients_pkey PRIMARY KEY (ingredient_id);


--
-- Name: measurement_qty measurement_qty_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.measurement_qty
    ADD CONSTRAINT measurement_qty_pkey PRIMARY KEY (measurement_qty_id);


--
-- Name: measurement_units measurement_units_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.measurement_units
    ADD CONSTRAINT measurement_units_pkey PRIMARY KEY (measurement_units_id);


--
-- Name: nutrition_profile nutrition_profile_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nutrition_profile
    ADD CONSTRAINT nutrition_profile_pkey PRIMARY KEY (nutrition_profile_id);


--
-- Name: recipe_ingredients recipe_ingredients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe_ingredients
    ADD CONSTRAINT recipe_ingredients_pkey PRIMARY KEY (recipe_ingredients_id);


--
-- Name: recipes recipes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipes
    ADD CONSTRAINT recipes_pkey PRIMARY KEY (recipe_id);


--
-- Name: ifk_rel_07; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX ifk_rel_07 ON public.ingredients USING btree (nutrition_profile_id);


--
-- Name: ifk_rel_09; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX ifk_rel_09 ON public.recipes USING btree (food_category_idfood_category);


--
-- Name: ingredients_fkindex1; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX ingredients_fkindex1 ON public.ingredients USING btree (nutrition_profile_id);


--
-- Name: recipe_ingredients_fkindex1; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX recipe_ingredients_fkindex1 ON public.recipe_ingredients USING btree (recipes_recipe_id);


--
-- Name: recipe_ingredients_fkindex2; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX recipe_ingredients_fkindex2 ON public.recipe_ingredients USING btree (measurement_units_id);


--
-- Name: recipe_ingredients_fkindex3; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX recipe_ingredients_fkindex3 ON public.recipe_ingredients USING btree (measurement_qty_id);


--
-- Name: recipe_ingredients_fkindex4; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX recipe_ingredients_fkindex4 ON public.recipe_ingredients USING btree (ingredients_ingredient_id);


--
-- Name: recipes_fkindex1; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX recipes_fkindex1 ON public.recipes USING btree (food_category_idfood_category);


--
-- Name: ingredients ingredients_nutrition_profile_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredients
    ADD CONSTRAINT ingredients_nutrition_profile_id_fkey FOREIGN KEY (nutrition_profile_id) REFERENCES public.nutrition_profile(nutrition_profile_id);


--
-- Name: recipe_ingredients recipe_ingredients_ingredients_ingredient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe_ingredients
    ADD CONSTRAINT recipe_ingredients_ingredients_ingredient_id_fkey FOREIGN KEY (ingredients_ingredient_id) REFERENCES public.ingredients(ingredient_id);


--
-- Name: recipe_ingredients recipe_ingredients_measurement_qty_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe_ingredients
    ADD CONSTRAINT recipe_ingredients_measurement_qty_id_fkey FOREIGN KEY (measurement_qty_id) REFERENCES public.measurement_qty(measurement_qty_id);


--
-- Name: recipe_ingredients recipe_ingredients_measurement_units_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe_ingredients
    ADD CONSTRAINT recipe_ingredients_measurement_units_id_fkey FOREIGN KEY (measurement_units_id) REFERENCES public.measurement_units(measurement_units_id);


--
-- Name: recipe_ingredients recipe_ingredients_recipes_recipe_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe_ingredients
    ADD CONSTRAINT recipe_ingredients_recipes_recipe_id_fkey FOREIGN KEY (recipes_recipe_id) REFERENCES public.recipes(recipe_id);


--
-- Name: recipes recipes_food_category_idfood_category_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipes
    ADD CONSTRAINT recipes_food_category_idfood_category_fkey FOREIGN KEY (food_category_idfood_category) REFERENCES public.food_category(idfood_category);


--
-- PostgreSQL database dump complete
--

