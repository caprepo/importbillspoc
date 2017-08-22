create table public.invoice
   (
    invoice_id integer NOT NULL,
    invoice_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    invoice_location character varying(200) COLLATE pg_catalog."default" NOT NULL,
    invoice_creation_date timestamp COLLATE pg_catalog."default" NOT NULL
    PRIMARY KEY(invoice_id)
    )
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;
