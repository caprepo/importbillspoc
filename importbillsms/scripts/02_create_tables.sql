create table public.invoice
   (
    invoice_id integer NOT NULL,
    invoice_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    invoice_location character varying(200) COLLATE pg_catalog."default" NOT NULL,
    invoice_creation_date timestamp NOT NULL,
    PRIMARY KEY(invoice_id)
    )
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

CREATE TABLE image_upload
(
  image_stored_flag boolean DEFAULT false,
  vision_api_resp character varying(250)[],
  image_size bigint,
  image_name character varying(255),
  image_id serial NOT NULL,
  image_google_storage_loc character varying
)
WITH (
  OIDS=FALSE
);
ALTER TABLE image_upload
  OWNER TO postgres;