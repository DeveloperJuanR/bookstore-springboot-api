--
-- PostgreSQL database dump
--

-- Dumped from database version 16.8
-- Dumped by pg_dump version 16.4

-- Started on 2025-06-26 17:58:19

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
-- TOC entry 5 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: azure_pg_admin
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO azure_pg_admin;

--
-- TOC entry 4162 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: azure_pg_admin
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 234 (class 1259 OID 25084)
-- Name: author; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.author (
    author_id bigint NOT NULL,
    biography text NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL
);


ALTER TABLE public.author OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 25083)
-- Name: author_author_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.author_author_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.author_author_id_seq OWNER TO postgres;

--
-- TOC entry 4163 (class 0 OID 0)
-- Dependencies: 233
-- Name: author_author_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.author_author_id_seq OWNED BY public.author.author_id;


--
-- TOC entry 221 (class 1259 OID 24822)
-- Name: authors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.authors (
    author_id bigint NOT NULL,
    first_name character varying(50),
    last_name character varying(50),
    biography text
);


ALTER TABLE public.authors OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 24821)
-- Name: authors_author_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.authors_author_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.authors_author_id_seq OWNER TO postgres;

--
-- TOC entry 4164 (class 0 OID 0)
-- Dependencies: 220
-- Name: authors_author_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.authors_author_id_seq OWNED BY public.authors.author_id;


--
-- TOC entry 222 (class 1259 OID 24830)
-- Name: book_authors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book_authors (
    isbn character(13) NOT NULL,
    author_id integer NOT NULL
);


ALTER TABLE public.book_authors OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 25006)
-- Name: bookdto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bookdto (
    isbn character varying(255) NOT NULL,
    title character varying(255)
);


ALTER TABLE public.bookdto OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 24766)
-- Name: books; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.books (
    isbn character(13) NOT NULL,
    title character varying(255),
    description text,
    price numeric(10,2),
    genre character varying(100),
    year_published smallint,
    copies_sold integer,
    publisher_publisher_id bigint,
    author character varying(255),
    author_id bigint,
    publisher_id bigint,
    author_author_id bigint
);


ALTER TABLE public.books OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 24914)
-- Name: credit_cards; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.credit_cards (
    card_number character(16) NOT NULL,
    user_id integer,
    expiration_date date,
    cvv character(3)
);


ALTER TABLE public.credit_cards OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 24760)
-- Name: publishers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.publishers (
    publisher_id integer NOT NULL,
    publisher_name character varying(255),
    name character varying(255),
    address character varying(255)
);


ALTER TABLE public.publishers OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 24759)
-- Name: publishers_publisher_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.publishers_publisher_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.publishers_publisher_id_seq OWNER TO postgres;

--
-- TOC entry 4165 (class 0 OID 0)
-- Dependencies: 215
-- Name: publishers_publisher_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.publishers_publisher_id_seq OWNED BY public.publishers.publisher_id;


--
-- TOC entry 223 (class 1259 OID 24845)
-- Name: ratings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ratings (
    user_id integer NOT NULL,
    isbn character(13) NOT NULL,
    rating smallint
);


ALTER TABLE public.ratings OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 24872)
-- Name: shopping_cart_items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.shopping_cart_items (
    cart_id integer NOT NULL,
    isbn character(13) NOT NULL,
    quantity integer
);


ALTER TABLE public.shopping_cart_items OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 24861)
-- Name: shopping_carts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.shopping_carts (
    cart_id integer NOT NULL,
    user_id integer
);


ALTER TABLE public.shopping_carts OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 24860)
-- Name: shopping_carts_cart_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.shopping_carts_cart_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.shopping_carts_cart_id_seq OWNER TO postgres;

--
-- TOC entry 4166 (class 0 OID 0)
-- Dependencies: 224
-- Name: shopping_carts_cart_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.shopping_carts_cart_id_seq OWNED BY public.shopping_carts.cart_id;


--
-- TOC entry 219 (class 1259 OID 24813)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    username character varying(50),
    password character varying(255),
    first_name character varying(50),
    last_name character varying(50),
    address character varying(255),
    email character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 24812)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 4167 (class 0 OID 0)
-- Dependencies: 218
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 231 (class 1259 OID 25004)
-- Name: wishlist_items_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.wishlist_items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.wishlist_items_id_seq OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 24899)
-- Name: wishlist_items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.wishlist_items (
    wishlist_id integer NOT NULL,
    isbn character(13) NOT NULL,
    quantity integer NOT NULL,
    id bigint DEFAULT nextval('public.wishlist_items_id_seq'::regclass) NOT NULL
);


ALTER TABLE public.wishlist_items OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 24888)
-- Name: wishlists; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.wishlists (
    wishlist_id integer NOT NULL,
    user_id integer,
    name character varying(255) NOT NULL
);


ALTER TABLE public.wishlists OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 24887)
-- Name: wishlists_wishlist_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.wishlists_wishlist_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.wishlists_wishlist_id_seq OWNER TO postgres;

--
-- TOC entry 4168 (class 0 OID 0)
-- Dependencies: 227
-- Name: wishlists_wishlist_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.wishlists_wishlist_id_seq OWNED BY public.wishlists.wishlist_id;


--
-- TOC entry 3973 (class 2604 OID 25087)
-- Name: author author_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.author ALTER COLUMN author_id SET DEFAULT nextval('public.author_author_id_seq'::regclass);


--
-- TOC entry 3969 (class 2604 OID 25018)
-- Name: authors author_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authors ALTER COLUMN author_id SET DEFAULT nextval('public.authors_author_id_seq'::regclass);


--
-- TOC entry 3967 (class 2604 OID 24763)
-- Name: publishers publisher_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publishers ALTER COLUMN publisher_id SET DEFAULT nextval('public.publishers_publisher_id_seq'::regclass);


--
-- TOC entry 3970 (class 2604 OID 24864)
-- Name: shopping_carts cart_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shopping_carts ALTER COLUMN cart_id SET DEFAULT nextval('public.shopping_carts_cart_id_seq'::regclass);


--
-- TOC entry 3968 (class 2604 OID 24816)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 3971 (class 2604 OID 24891)
-- Name: wishlists wishlist_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wishlists ALTER COLUMN wishlist_id SET DEFAULT nextval('public.wishlists_wishlist_id_seq'::regclass);


--
-- TOC entry 3999 (class 2606 OID 25091)
-- Name: author author_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.author
    ADD CONSTRAINT author_pkey PRIMARY KEY (author_id);


--
-- TOC entry 3981 (class 2606 OID 25020)
-- Name: authors authors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (author_id);


--
-- TOC entry 3983 (class 2606 OID 24834)
-- Name: book_authors book_authors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_authors
    ADD CONSTRAINT book_authors_pkey PRIMARY KEY (isbn, author_id);


--
-- TOC entry 3997 (class 2606 OID 25012)
-- Name: bookdto bookdto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bookdto
    ADD CONSTRAINT bookdto_pkey PRIMARY KEY (isbn);


--
-- TOC entry 3977 (class 2606 OID 24772)
-- Name: books books_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (isbn);


--
-- TOC entry 3995 (class 2606 OID 24918)
-- Name: credit_cards credit_cards_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_cards
    ADD CONSTRAINT credit_cards_pkey PRIMARY KEY (card_number);


--
-- TOC entry 3975 (class 2606 OID 24765)
-- Name: publishers publishers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publishers
    ADD CONSTRAINT publishers_pkey PRIMARY KEY (publisher_id);


--
-- TOC entry 3985 (class 2606 OID 24849)
-- Name: ratings ratings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ratings
    ADD CONSTRAINT ratings_pkey PRIMARY KEY (user_id, isbn);


--
-- TOC entry 3989 (class 2606 OID 24876)
-- Name: shopping_cart_items shopping_cart_items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shopping_cart_items
    ADD CONSTRAINT shopping_cart_items_pkey PRIMARY KEY (cart_id, isbn);


--
-- TOC entry 3987 (class 2606 OID 24866)
-- Name: shopping_carts shopping_carts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shopping_carts
    ADD CONSTRAINT shopping_carts_pkey PRIMARY KEY (cart_id);


--
-- TOC entry 3979 (class 2606 OID 24820)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3993 (class 2606 OID 24998)
-- Name: wishlist_items wishlist_items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wishlist_items
    ADD CONSTRAINT wishlist_items_pkey PRIMARY KEY (id);


--
-- TOC entry 3991 (class 2606 OID 24893)
-- Name: wishlists wishlists_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wishlists
    ADD CONSTRAINT wishlists_pkey PRIMARY KEY (wishlist_id);


--
-- TOC entry 4013 (class 2606 OID 24919)
-- Name: credit_cards credit_cards_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credit_cards
    ADD CONSTRAINT credit_cards_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 4002 (class 2606 OID 25137)
-- Name: book_authors fk_book_authors_author_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_authors
    ADD CONSTRAINT fk_book_authors_author_id FOREIGN KEY (author_id) REFERENCES public.authors(author_id) NOT VALID;


--
-- TOC entry 4003 (class 2606 OID 25132)
-- Name: book_authors fk_book_authors_book_isbn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_authors
    ADD CONSTRAINT fk_book_authors_book_isbn FOREIGN KEY (isbn) REFERENCES public.books(isbn) NOT VALID;


--
-- TOC entry 4010 (class 2606 OID 24985)
-- Name: wishlist_items fk_wishlist; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wishlist_items
    ADD CONSTRAINT fk_wishlist FOREIGN KEY (wishlist_id) REFERENCES public.wishlists(wishlist_id) ON DELETE CASCADE;


--
-- TOC entry 4000 (class 2606 OID 25071)
-- Name: books fkayy5edfrqnegqj3882nce6qo8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT fkayy5edfrqnegqj3882nce6qo8 FOREIGN KEY (publisher_id) REFERENCES public.publishers(publisher_id);


--
-- TOC entry 4001 (class 2606 OID 24930)
-- Name: books fkoltlf3sjjgw24j37h1o4nmg95; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT fkoltlf3sjjgw24j37h1o4nmg95 FOREIGN KEY (publisher_publisher_id) REFERENCES public.publishers(publisher_id);


--
-- TOC entry 4004 (class 2606 OID 24855)
-- Name: ratings ratings_isbn_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ratings
    ADD CONSTRAINT ratings_isbn_fkey FOREIGN KEY (isbn) REFERENCES public.books(isbn);


--
-- TOC entry 4005 (class 2606 OID 24850)
-- Name: ratings ratings_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ratings
    ADD CONSTRAINT ratings_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 4007 (class 2606 OID 24877)
-- Name: shopping_cart_items shopping_cart_items_cart_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shopping_cart_items
    ADD CONSTRAINT shopping_cart_items_cart_id_fkey FOREIGN KEY (cart_id) REFERENCES public.shopping_carts(cart_id);


--
-- TOC entry 4008 (class 2606 OID 24882)
-- Name: shopping_cart_items shopping_cart_items_isbn_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shopping_cart_items
    ADD CONSTRAINT shopping_cart_items_isbn_fkey FOREIGN KEY (isbn) REFERENCES public.books(isbn);


--
-- TOC entry 4006 (class 2606 OID 24867)
-- Name: shopping_carts shopping_carts_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shopping_carts
    ADD CONSTRAINT shopping_carts_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 4011 (class 2606 OID 24909)
-- Name: wishlist_items wishlist_items_isbn_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wishlist_items
    ADD CONSTRAINT wishlist_items_isbn_fkey FOREIGN KEY (isbn) REFERENCES public.books(isbn);


--
-- TOC entry 4012 (class 2606 OID 24904)
-- Name: wishlist_items wishlist_items_wishlist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wishlist_items
    ADD CONSTRAINT wishlist_items_wishlist_id_fkey FOREIGN KEY (wishlist_id) REFERENCES public.wishlists(wishlist_id);


--
-- TOC entry 4009 (class 2606 OID 24894)
-- Name: wishlists wishlists_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.wishlists
    ADD CONSTRAINT wishlists_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);


-- Completed on 2025-06-26 17:58:27

--
-- PostgreSQL database dump complete
--

