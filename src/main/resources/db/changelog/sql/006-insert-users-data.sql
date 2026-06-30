INSERT INTO users
    (username, email, password, role, created_at, updated_at)
VALUES ('admin', 'admin@subtracker.com', '$2a$12$MgUCLcb.XHBsR3twZW2QxOLYdi/OdH7bP7Jz2HjwgycbIkv7sbb/a', 'ADMIN',
        '2026-01-01 09:00:00', '2026-01-01 09:00:00'),

       ('john_doe', 'john@gmail.com', '$2a$12$Jx1lRIH1g.w1EExIC./Qfu4Fy2IdpqBAz0jVAS7A2YFAMnW.StVyq', 'USER',
        '2026-01-02 10:15:00', '2026-01-02 10:15:00'),

       ('alice_smith', 'alice@gmail.com', '$2a$12$XnoXyao3dxQ3LZ06O8ka7OE9Uo68D2owvVY3VwzILSCRmPubSAkLy', 'USER',
        '2026-01-03 11:20:00', '2026-01-03 11:20:00'),

       ('bob_johnson', 'bob@gmail.com', '$2a$12$NYMH0l2M80wj133ZuI13aub3YE4ZlDMmx93dzbKe5ZI7k0/K9iryC', 'USER',
        '2026-01-04 13:40:00', '2026-01-04 13:40:00'),

       ('emma_wilson', 'emma@gmail.com', '$2a$12$bublKS5mtTQEscDl9X28.es.Z1/dQG18i33wJaS1oV1aWMegVFcbS', 'USER',
        '2026-01-05 14:10:00', '2026-01-05 14:10:00'),

       ('michael_brown', 'michael@gmail.com', '$2a$12$4JCLz7corhG2D8qKAVWrC.tlSCvuuzGSkfwpIxBfyDFJP7xrIATdu', 'USER',
        '2026-01-06 16:30:00', '2026-01-06 16:30:00');
