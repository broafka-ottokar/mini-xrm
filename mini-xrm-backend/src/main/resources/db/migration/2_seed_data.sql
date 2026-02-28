INSERT INTO partner_tag (
    name
)
VALUES
('Aktív'),
('Külföldi'),
('TOP'),
('Export')
;

INSERT INTO partner (
    name,
    tax_number,
    headquarters,
    status
)
VALUES
(
    'Actitop Corporation',
    '123456789',
    'Los Angeles, USA',
    'ACTIVE'
),
(
    'Vandelay Industries Importing and Exporting Inc.',
    '987654321',
    'New York, USA',
    'ACTIVE'
),
(
    'Initech',
    '555555555',
    'Dallas, USA',
    'INACTIVE'
);

INSERT INTO partner_x_partner_tag (
    partner_id,
    partner_tag_id
) VALUES
(1, 1),
(1, 3),
(2, 1),
(2, 2),
(2, 4);

INSERT INTO activity (
    subject,
    type,
    description,
    duration_minutes,
    person_responsible,
    partner_id
) VALUES
(
    'Meeting with Actitop Corporation',
    'MEETING',
    'Discussed potential partnership opportunities and product offerings.',
    60,
    'John Doe',
    1
),
(
    'Email to Vandelay Industries',
    'EMAIL',
    'Sent follow-up email with additional information about our services.',
    10,
    'Jane Smith',
    2
),
(
    'Call with Vandelay Industries',
    'CALL',
    'Follow-up call to discuss contract details and next steps.',
    30,
    'Jane Smith',
    2
),
(
    'Meeting with Vandelay Industries',
    'MEETING',
    'In-person meeting to finalize contract and discuss implementation plan.',
    90,
    'Jane Smith',
    2
),
(
    'Call with Actitop Corporation',
    'CALL',
    'Follow-up call to discuss potential partnership opportunities.',
    20,
    'Jane Smith',
    1
),
('Email to Actitop Corporation',
    'EMAIL',
    'Sent proposal for partnership and product offerings.',
    15,
    'Jane Smith',
    1
),
(
    'Meeting with Initech',
    'MEETING',
    'Initial meeting to understand their needs and explore potential solutions.',
    45,
    'Jane Smith',
    3
),
(
    'Email to Initech',
    'EMAIL',
    'Sent proposal for new software solution to address their needs.',
    15,
    'Emily Johnson',
    3
);