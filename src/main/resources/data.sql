SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('user_subscriptions_id_seq', (SELECT MAX(id) FROM user_subscriptions));
SELECT setval('subscriptions_id_seq', (SELECT MAX(id) FROM subscriptions));
SELECT setval('payment_histories_id_seq', (SELECT MAX(id) FROM payment_histories));



INSERT INTO users
    (id, username, email, password, user_type, created_at, updated_at)
VALUES (1, 'admin', 'admin@subtracker.com', '$2a$12$MgUCLcb.XHBsR3twZW2QxOLYdi/OdH7bP7Jz2HjwgycbIkv7sbb/a', 'ADMIN', '2026-01-01 09:00:00', '2026-01-01 09:00:00'),

       (2, 'john_doe', 'john@gmail.com', '$2a$12$Jx1lRIH1g.w1EExIC./Qfu4Fy2IdpqBAz0jVAS7A2YFAMnW.StVyq', 'USER', '2026-01-02 10:15:00', '2026-01-02 10:15:00'),

       (3, 'alice_smith', 'alice@gmail.com', '$2a$12$XnoXyao3dxQ3LZ06O8ka7OE9Uo68D2owvVY3VwzILSCRmPubSAkLy', 'USER', '2026-01-03 11:20:00', '2026-01-03 11:20:00'),

       (4, 'bob_johnson', 'bob@gmail.com', '$2a$12$NYMH0l2M80wj133ZuI13aub3YE4ZlDMmx93dzbKe5ZI7k0/K9iryC', 'USER', '2026-01-04 13:40:00', '2026-01-04 13:40:00'),

       (5, 'emma_wilson', 'emma@gmail.com', '$2a$12$bublKS5mtTQEscDl9X28.es.Z1/dQG18i33wJaS1oV1aWMegVFcbS', 'USER', '2026-01-05 14:10:00', '2026-01-05 14:10:00'),

       (6, 'michael_brown', 'michael@gmail.com', '$2a$12$4JCLz7corhG2D8qKAVWrC.tlSCvuuzGSkfwpIxBfyDFJP7xrIATdu', 'USER', '2026-01-06 16:30:00', '2026-01-06 16:30:00');


INSERT INTO subscriptions
    (id, name, price, currency, category)
VALUES (1, 'ChatGPT Plus', 20.00, 'USD', 'AI'),

       (2, 'Netflix Premium', 18.99, 'USD', 'Streaming'),

       (3, 'Spotify Premium', 9.99, 'EUR', 'Music'),

       (4, 'YouTube Premium', 12.99, 'USD', 'Streaming'),

       (5, 'GitHub Pro', 10.00, 'USD', 'Development'),

       (6, 'Adobe Creative Cloud', 59.99, 'EUR', 'Design'),

       (7, 'Microsoft 365', 99.99, 'USD', 'Productivity'),

       (8, 'Duolingo Super', 899.00, 'USD', 'Education'),

       (9, 'Amazon Prime', 14.99, 'USD', 'Shopping'),

       (10, 'Notion AI', 8.00, 'EUR', 'Productivity');



INSERT INTO user_subscriptions
(id, status, type, started_date, next_payment_date, is_deleted, subscription_id, subscriber_id)
VALUES

-- John Doe
(1, 'CANCELLED', 'MONTHLY',
 '2026-01-10 09:00:00',
 '2026-02-10 09:00:00',
 false, 1, 2),

(2, 'ACTIVE', 'MONTHLY',
 '2026-05-01 09:00:00',
 '2026-06-01 09:00:00',
 false, 1, 2),

(3, 'ACTIVE', 'MONTHLY',
 '2026-02-15 10:30:00',
 '2026-03-15 10:30:00',
 false, 2, 2),


-- Alice Smith
(4, 'ACTIVE', 'ANNUALLY',
 '2026-01-20 08:00:00',
 '2027-01-20 08:00:00',
 false, 5, 3),

(5, 'PAUSED', 'MONTHLY',
 '2026-04-05 11:15:00',
 '2026-05-05 11:15:00',
 false, 3, 3),

(6, 'ACTIVE', 'MONTHLY',
 '2026-06-01 12:00:00',
 '2026-07-01 12:00:00',
 false, 10, 3),


-- Bob Johnson
(7, 'ACTIVE', 'MONTHLY',
 '2026-02-01 14:00:00',
 '2026-03-01 14:00:00',
 false, 4, 4),

(8, 'CANCELLED', 'ANNUALLY',
 '2026-03-12 15:30:00',
 '2027-03-12 15:30:00',
 false, 7, 4),

(9, 'ACTIVE', 'MONTHLY',
 '2027-04-01 09:00:00',
 '2027-05-01 09:00:00',
 false, 7, 4),


-- Emma Wilson
(10, 'ACTIVE', 'MONTHLY',
 '2026-02-18 13:20:00',
 '2026-03-18 13:20:00',
 false, 6, 5),

(11, 'PAUSED', 'WEEKLY',
 '2026-07-10 09:45:00',
 '2026-07-17 09:45:00',
 false, 8, 5),

(12, 'ACTIVE', 'MONTHLY',
 '2026-08-03 16:00:00',
 '2026-09-03 16:00:00',
 false, 9, 5),


-- Michael Brown
(13, 'ACTIVE', 'MONTHLY',
 '2026-03-05 18:00:00',
 '2026-04-05 18:00:00',
 false, 2, 6),

(14, 'ACTIVE', 'MONTHLY',
 '2026-05-15 10:00:00',
 '2026-06-15 10:00:00',
 false, 3, 6),

(15, 'ACTIVE', 'WEEKLY',
 '2027-01-08 09:00:00',
 '2027-01-15 09:00:00',
 false, 8, 6);


INSERT INTO payment_histories
    (id, payment_date, reason, amount, subscription_id, subscriber_id)
VALUES

-- John - ChatGPT (old subscription)
(1, '2026-01-10 09:00:00', 'ACTIVATED', 20.00, 1, 2),
(2, '2026-04-15 18:00:00', 'CANCELLED', 20.00, 1, 2),

-- John - ChatGPT (new subscription)
(3, '2026-05-01 09:00:00', 'ACTIVATED', 20.00, 1, 2),

-- John - Netflix
(4, '2026-02-15 10:30:00', 'ACTIVATED', 18.99, 2, 2),

-- Alice - GitHub
(5, '2026-01-20 08:00:00', 'ACTIVATED', 10.00, 5, 3),

-- Alice - Spotify
(6, '2026-04-05 11:15:00', 'ACTIVATED', 9.99, 3, 3),
(7, '2026-05-10 09:30:00', 'PAUSED', 9.99, 3, 3),

-- Alice - Notion AI
(8, '2026-06-01 12:00:00', 'ACTIVATED', 8.00, 10, 3),

-- Bob - YouTube Premium
(9, '2026-02-01 14:00:00', 'ACTIVATED', 12.99, 4, 4),

-- Bob - Microsoft 365 (old)
(10, '2026-03-12 15:30:00', 'ACTIVATED', 99.99, 7, 4),
(11, '2027-02-20 10:00:00', 'CANCELLED', 99.99, 7, 4),

-- Bob - Microsoft 365 (new)
(12, '2027-04-01 09:00:00', 'ACTIVATED', 99.99, 7, 4),

-- Emma - Adobe
(13, '2026-02-18 13:20:00', 'ACTIVATED', 59.99, 6, 5),

-- Emma - Duolingo
(14, '2026-07-10 09:45:00', 'ACTIVATED', 899.00, 8, 5),
(15, '2026-07-14 18:15:00', 'PAUSED', 899.00, 8, 5),

-- Emma - Amazon Prime
(16, '2026-08-03 16:00:00', 'ACTIVATED', 14.99, 9, 5),

-- Michael - Netflix
(17, '2026-03-05 18:00:00', 'ACTIVATED', 18.99, 2, 6),

-- Michael - Spotify
(18, '2026-05-15 10:00:00', 'ACTIVATED', 9.99, 3, 6),

-- Michael - Duolingo
(19, '2027-01-08 09:00:00', 'ACTIVATED', 899.00, 8, 6);
