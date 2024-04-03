-- Needs adjusting for bounce logic:
-- SELECT (SELECT COUNT(*) FROM clicks INNER JOIN servers ON clicks."ID" = servers."ID" AND clicks."Date" = servers."Entry Date" WHERE <bounce logic>) * 1.0 / (SELECT COUNT(*) FROM clicks);

SELECT (SELECT COUNT(*) FROM clicks INNER JOIN servers ON clicks."ID" = servers."ID" AND clicks."Date" = servers."Entry Date" WHERE "Pages Viewed" < 2) * 1.0 / (SELECT COUNT(*) FROM clicks);