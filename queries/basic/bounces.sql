-- Needs adjusting for bounce logic:
-- SELECT COUNT(*) FROM clicks INNER JOIN servers ON clicks."ID" = servers."ID" AND clicks."Date" = servers."Entry Date" WHERE <bounce logic>;

SELECT COUNT(*) FROM clicks INNER JOIN servers ON clicks."ID" = servers."ID" AND clicks."Date" = servers."Entry Date" WHERE "Pages Viewed" < 2;