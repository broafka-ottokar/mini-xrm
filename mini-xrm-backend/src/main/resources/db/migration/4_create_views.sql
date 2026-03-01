ALTER VIEW person_responsible_report RENAME TO person_responsible_report_v;

CREATE VIEW partner_v
    AS
SELECT id,
       name,
       tax_number,
       headquarters,
  CASE status
  WHEN 'ACTIVE'
  THEN 'Active'
  WHEN 'INACTIVE'
  THEN 'Inactive'
   END AS status
  FROM partner
 WHERE deleted = FALSE;
