INSERT INTO user_subscriptions
(status, type, started_date, next_payment_date, is_deleted, subscription_id, subscriber_id)
VALUES

-- John Doe
('CANCELLED', 'MONTHLY',
 '2026-01-10 09:00:00',
 '2026-02-10 09:00:00',
 false, 1, 2),

('ACTIVE', 'MONTHLY',
 '2026-05-01 09:00:00',
 '2026-06-01 09:00:00',
 false, 1, 2),

('ACTIVE', 'MONTHLY',
 '2026-02-15 10:30:00',
 '2026-03-15 10:30:00',
 false, 2, 2),


-- Alice Smith
('ACTIVE', 'ANNUALLY',
 '2026-01-20 08:00:00',
 '2027-01-20 08:00:00',
 false, 5, 3),

('PAUSED', 'MONTHLY',
 '2026-04-05 11:15:00',
 '2026-05-05 11:15:00',
 false, 3, 3),

('ACTIVE', 'MONTHLY',
 '2026-06-01 12:00:00',
 '2026-07-01 12:00:00',
 false, 10, 3),


-- Bob Johnson
('ACTIVE', 'MONTHLY',
 '2026-02-01 14:00:00',
 '2026-03-01 14:00:00',
 false, 4, 4),

('CANCELLED', 'ANNUALLY',
 '2026-03-12 15:30:00',
 '2027-03-12 15:30:00',
 false, 7, 4),

('ACTIVE', 'MONTHLY',
 '2027-04-01 09:00:00',
 '2027-05-01 09:00:00',
 false, 7, 4),


-- Emma Wilson
('ACTIVE', 'MONTHLY',
 '2026-02-18 13:20:00',
 '2026-03-18 13:20:00',
 false, 6, 5),

('PAUSED', 'WEEKLY',
 '2026-07-10 09:45:00',
 '2026-07-17 09:45:00',
 false, 8, 5),

('ACTIVE', 'MONTHLY',
 '2026-08-03 16:00:00',
 '2026-09-03 16:00:00',
 false, 9, 5),


-- Michael Brown
('ACTIVE', 'MONTHLY',
 '2026-03-05 18:00:00',
 '2026-04-05 18:00:00',
 false, 2, 6),

('ACTIVE', 'MONTHLY',
 '2026-05-15 10:00:00',
 '2026-06-15 10:00:00',
 false, 3, 6),

('ACTIVE', 'WEEKLY',
 '2027-01-08 09:00:00',
 '2027-01-15 09:00:00',
 false, 8, 6);
