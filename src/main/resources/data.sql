-- GROUPS
INSERT INTO groups (name) VALUES
                              ('1A'),
                              ('2B'),
                              ('3C'),
                              ('4D'),
                              ('5E'),
                              ('6F');

-- PARENTS
INSERT INTO parents (first_name, last_name, email, phone, address) VALUES
                                                                       ('Katarzyna', 'Kowalska', 'k.kowalska@email.com', '123456789', 'ul. Słoneczna 15, Warszawa'),
                                                                       ('Marek', 'Nowak', 'm.nowak@email.com', '987654321', 'ul. Kwiatowa 8, Kraków'),
                                                                       ('Agnieszka', 'Wiśniewska', 'a.wisniewska@email.com', '555666777', 'ul. Parkowa 22, Gdańsk'),
                                                                       ('Tomasz', 'Zając', 't.zajac@email.com', '112233445', 'ul. Leśna 9, Łódź'),
                                                                       ('Ewelina', 'Kubiak', 'e.kubiak@email.com', '667788990', 'ul. Jesionowa 33, Wrocław'),
                                                                       ('Dariusz', 'Maj', 'd.maj@email.com', '778899001', 'ul. Klonowa 2, Poznań');

-- PRINCIPALS
INSERT INTO principals (first_name, last_name, email, phone) VALUES
                                                                 ('Barbara', 'Kowalczyk', 'b.kowalczyk@szkola.edu.pl', '111222333'),
                                                                 ('Andrzej', 'Mazur', 'a.mazur@szkola.edu.pl', '444555666'),
                                                                 ('Elżbieta', 'Pawlak', 'e.pawlak@szkola.edu.pl', '777888999'),
                                                                 ('Michał', 'Wrona', 'm.wrona@szkola.edu.pl', '123123123'),
                                                                 ('Grażyna', 'Lis', 'g.lis@szkola.edu.pl', '321321321'),
                                                                 ('Kamil', 'Czajka', 'k.czajka@szkola.edu.pl', '456456456');

-- STUDENTS
INSERT INTO students (first_name, last_name, group_id, parent_id) VALUES
                                                                      ('Anna', 'Kowalska', 1, 1),
                                                                      ('Jan', 'Nowak', 2, 2),
                                                                      ('Ewa', 'Wiśniewska', 3, 3),
                                                                      ('Krzysztof', 'Zając', 4, 4),
                                                                      ('Magdalena', 'Kubiak', 5, 5),
                                                                      ('Paweł', 'Maj', 6, 6);

-- TEACHERS
INSERT INTO teachers (first_name, last_name) VALUES
                                                 ('Maria', 'Zielińska'),
                                                 ('Piotr', 'Błaszczykowski'),
                                                 ('Tomasz', 'Grosik'),
                                                 ('Natalia', 'Krawczyk'),
                                                 ('Andrzej', 'Lewandowski'),
                                                 ('Joanna', 'Król');

-- SUBJECTS
INSERT INTO subjects (name) VALUES
                                ('Matematyka'),
                                ('Historia'),
                                ('Biologia'),
                                ('Fizyka'),
                                ('Chemia'),
                                ('Geografia');

-- LESSONS
INSERT INTO lessons (subject_id, teacher_id, group_id) VALUES
                                                           (1, 1, 1),
                                                           (2, 2, 2),
                                                           (3, 3, 3),
                                                           (4, 4, 4),
                                                           (5, 5, 5),
                                                           (6, 6, 6);

-- SCHEDULES
INSERT INTO schedules (group_id, lessons_id, teacher_id, day_of_week, start_time, end_time) VALUES
                                                                                                (1, 1, 1, 'MONDAY', '08:00', '08:45'),
                                                                                                (2, 2, 2, 'TUESDAY', '09:00', '09:45'),
                                                                                                (3, 3, 3, 'WEDNESDAY', '10:00', '10:45'),
                                                                                                (4, 4, 4, 'THURSDAY', '11:00', '11:45'),
                                                                                                (5, 5, 5, 'FRIDAY', '12:00', '12:45'),
                                                                                                (6, 6, 6, 'MONDAY', '13:00', '13:45');

-- GRADES
INSERT INTO grades (student_id, lesson_id, value, comment) VALUES
                                                               (1, 1, 'FIVE', 'Bardzo dobra praca domowa'),
                                                               (2, 2, 'THREE', 'Może być lepiej'),
                                                               (3, 3, 'FOUR', 'Dobre przygotowanie do lekcji'),
                                                               (4, 4, 'TWO', 'Brak pracy domowej'),
                                                               (5, 5, 'FIVE', 'Super aktywność'),
                                                               (6, 6, 'FOUR', 'Poprawna odpowiedź ustna');

-- APPLICATIONS
INSERT INTO applications (title, description, type, status, student_id, parent_id, created_date) VALUES
                                                                                                     ('Usprawiedliwienie nieobecności', 'Proszę o usprawiedliwienie nieobecności mojego dziecka.', 'ABSENCE_EXCUSE', 'PENDING', 1, 1, '2024-05-16 10:30:00'),
                                                                                                     ('Wniosek o dodatkowe zajęcia', 'Proszę o zapisanie mojego dziecka na dodatkowe zajęcia z matematyki.', 'ADDITIONAL_CLASSES', 'APPROVED', 2, 2, '2024-05-10 14:20:00'),
                                                                                                     ('Prośba o zaświadczenie', 'Proszę o wystawienie zaświadczenia o uczęszczaniu do szkoły.', 'CERTIFICATE_REQUEST', 'IN_REVIEW', 3, 3, '2024-05-18 09:15:00'),
                                                                                                     ('Usprawiedliwienie spóźnienia', 'Proszę o usprawiedliwienie spóźnienia mojego dziecka.', 'LATENESS_EXCUSE', 'PENDING', 4, 4, '2024-05-20 11:00:00'),
                                                                                                     ('Prośba o zmianę klasy', 'Proszę o przeniesienie mojego dziecka do innej klasy.', 'CLASS_CHANGE', 'REJECTED', 5, 5, '2024-05-21 08:40:00'),
                                                                                                     ('Wniosek o urlop', 'Proszę o zgodę na urlop rodzinny w czasie zajęć.', 'LEAVE_REQUEST', 'APPROVED', 6, 6, '2024-05-22 15:00:00');
