    CREATE OR REPLACE FUNCTION normalize_text(text)
    RETURNS text AS $$
    BEGIN
    RETURN LOWER(UNACCENT($1));
    END;
    $$ LANGUAGE plpgsql;
    CREATE EXTENSION IF NOT EXISTS unaccent;

DROP FUNCTION IF EXISTS normalize_text(text);

|