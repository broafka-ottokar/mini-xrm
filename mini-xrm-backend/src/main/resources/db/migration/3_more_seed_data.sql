INSERT INTO partner (
    name,
    tax_number,
    headquarters,
    status
)
VALUES
(
    'Wombat Technologies',
    '123456789',
    'San Francisco, USA',
    'ACTIVE'
),
(
    'Acme Corporation',
    '987654321',
    'Houston, USA',
    'ACTIVE'
),
(
    'FooBar GmbH',
    '555555555',
    'Berlin, Germany',
    'INACTIVE'
),
(
    'Tech Solutions Ltd.',
    '111222333',
    'London, UK',
    'ACTIVE'
),
(
    'Innovatech Inc.',
    '444666777',
    'Toronto, Canada',
    'ACTIVE'
),
(
    'Global Dynamics',
    '888999000',
    'Sydney, Australia',
    'INACTIVE'
),
(
    'NextGen Software',
    '222333444',
    'Seattle, USA',
    'ACTIVE'
),
(
    'Cyberdyne Systems',
    '777888999',
    'Los Angeles, USA',
    'INACTIVE'
),
(
    'Stark Industries',
    '333444555',
    'New York, USA',
    'ACTIVE'
),
(
    'Wayne Enterprises',
    '666777888',
    'Gotham City, USA',
    'ACTIVE'
);

INSERT INTO partner_x_partner_tag (
    partner_id,
    partner_tag_id
) VALUES
(3, 1),
(3, 3),
(4, 1),
(4, 2),
(4, 4),
(5, 2),
(5, 3),
(6, 1),
(6, 4),
(7, 2),
(7, 3),
(8, 1),
(8, 4),
(9, 2),
(9, 3),
(10, 1),
(10, 4),
(11, 2),
(11, 3);

INSERT INTO activity (
    subject,
    type,
    description,
    duration_minutes,
    person_responsible,
    partner_id
) VALUES
(
    'Initial Meeting',
    'MEETING',
    'Discuss project requirements and timelines.',
    60,
    'John Doe',
    3
),
(
    'Design Phase',
    'WORK',
    'Create design mockups and prototypes.',
    120,
    'Jane Smith',
    4
),
(
    'Development Phase',
    'WORK',
    'Implement the core features of the project.',
    240,
    'Alice Johnson',
    5
),
(
    'Testing Phase',
    'WORK',
    'Conduct thorough testing and bug fixing.',
    180,
    'Bob Brown',
    6
),
(
    'Deployment',
    'WORK',
    'Deploy the application to production environment.',
    90,
    'Charlie Davis',
    7
),
(
    'Project Review',
    'MEETING',
    'Review project progress and next steps.',
    45,
    'Diana Evans',
    8
),
(
    'Client Feedback',
    'MEETING',
    'Gather feedback from the client on the delivered features.',
    30,
    'Frank Green',
    9
),
(
    'Maintenance',
    'WORK',
    'Perform regular maintenance and updates.',
    60,
    'Grace Harris',
    10
),
(
    'Final Presentation',
    'MEETING',
    'Present the final product to the client.',
    90,
    'Henry Lee',
    11
),
(
    'Documentation',
    'WORK',
    'Create comprehensive documentation for the project.',
    120,
    'Ivy Martinez',
    3
),
(
    'Code Review',
    'WORK',
    'Review code for quality and consistency.',
    60,
    'Jack Wilson',
    4
),
(
    'User Training',
    'WORK',
    'Train users on how to use the new system.',
    90,
    'Karen Taylor',
    5
),
(
    'Performance Optimization',
    'WORK',
    'Optimize the performance of the application.',
    120,
    'Leo Anderson',
    6
    ),
(
	'Security Audit',
    'WORK',
    'Conduct a security audit to identify vulnerabilities.',
    180,
    'Mia Thomas',
    7
),
(
	'Data Migration',
    'WORK',
    'Migrate data from the old system to the new system.',
    240,
    'Nathan Scott',
    8
),
(
    'User Acceptance Testing',
    'MEETING',
    'Conduct user acceptance testing with the client.',
    60,
    'Olivia Turner',
    9
),
(
    'Post-Deployment Support',
    'WORK',
    'Provide support after deployment to address any issues.',
    120,
    'Paul Walker',
    10
),
(
    'Project Retrospective',
    'MEETING',
    'Reflect on the project and identify areas for improvement.',
    45,
    'Quinn Young',
    11
);