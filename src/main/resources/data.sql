INSERT INTO trip (trip_price_per_weeksek, hotel_name, destination_city, destination_country)
VALUES
    (8000.00, 'Ocean Paradise', 'Lisbon', 'Portugal'),
    (9200.00, 'Nordic Escape', 'Reykjavik', 'Iceland'),
    (7500.00, 'Beachfront Hotel', 'Malaga', 'Spain'),
    (6200.00, 'Urban Inn', 'Paris', 'France'),
    (5800.00, 'Mountain Lodge', 'Innsbruck', 'Austria'),
    (6700.00, 'Cultural Retreat', 'Prague', 'Czech Republic');

INSERT INTO travel_booking (travel_customer, departure_date, duration_weeks, cancelled, total_pricesek, trip_id)
VALUES
    -- Existing users
    ('niklaseinarsson', '2025-11-10', 1, false, 8000.00, 1),
    ('benjaminportsmouth', '2025-12-01', 2, false, 18400.00, 2),
    ('christofferfrisk', '2025-10-20', 1, false, 7500.00, 3),
    ('niklaseinarsson', '2025-05-01', 1, false, 6200.00, 4),
    ('benjaminportsmouth', '2025-04-15', 2, false, 11600.00, 5),
    ('christofferfrisk', '2025-03-10', 1, false, 6700.00, 6),
    ('niklaseinarsson', '2025-10-15', 1, true, 8000.00, 1),
    ('benjaminportsmouth', '2025-05-20', 2, true, 18400.00, 2),
    ('christofferfrisk', '2025-04-01', 1, true, 7500.00, 3),
    ('sofiaandersson', '2025-08-05', 1, false, 8000.00, 1),
    ('larsberg', '2025-07-12', 2, false, 18400.00, 2),
    ('elinakarlsson', '2025-09-18', 1, false, 7500.00, 3),
    ('sofiaandersson', '2025-06-25', 1, true, 6200.00, 4),
    ('larsberg', '2025-05-30', 2, true, 11600.00, 5),
    ('elinakarlsson', '2025-03-22', 1, true, 6700.00, 6);
