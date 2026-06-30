INSERT INTO payment_histories
    (payment_date, reason, amount, subscription_id, subscriber_id)
VALUES

-- John - ChatGPT (old subscription)
('2026-01-10 09:00:00', 'ACTIVATED', 20.00, 1, 2),
('2026-04-15 18:00:00', 'CANCELLED', 20.00, 1, 2),

-- John - ChatGPT (new subscription)
('2026-05-01 09:00:00', 'ACTIVATED', 20.00, 1, 2),

-- John - Netflix
('2026-02-15 10:30:00', 'ACTIVATED', 18.99, 2, 2),

-- Alice - GitHub
('2026-01-20 08:00:00', 'ACTIVATED', 10.00, 5, 3),

-- Alice - Spotify
('2026-04-05 11:15:00', 'ACTIVATED', 9.99, 3, 3),
('2026-05-10 09:30:00', 'PAUSED', 9.99, 3, 3),

-- Alice - Notion AI
('2026-06-01 12:00:00', 'ACTIVATED', 8.00, 10, 3),

-- Bob - YouTube Premium
('2026-02-01 14:00:00', 'ACTIVATED', 12.99, 4, 4),

-- Bob - Microsoft 365 (old)
('2026-03-12 15:30:00', 'ACTIVATED', 99.99, 7, 4),
('2027-02-20 10:00:00', 'CANCELLED', 99.99, 7, 4),

-- Bob - Microsoft 365 (new)
('2027-04-01 09:00:00', 'ACTIVATED', 99.99, 7, 4),

-- Emma - Adobe
('2026-02-18 13:20:00', 'ACTIVATED', 59.99, 6, 5),

-- Emma - Duolingo
('2026-07-10 09:45:00', 'ACTIVATED', 899.00, 8, 5),

('2026-07-14 18:15:00', 'PAUSED', 899.00, 8, 5),
-- Emma - Amazon Prime
('2026-08-03 16:00:00', 'ACTIVATED', 14.99, 9, 5),

-- Michael - Netflix
('2026-03-05 18:00:00', 'ACTIVATED', 18.99, 2, 6),

-- Michael - Spotify
('2026-05-15 10:00:00', 'ACTIVATED', 9.99, 3, 6),

-- Michael - Duolingo
('2027-01-08 09:00:00', 'ACTIVATED', 899.00, 8, 6);
