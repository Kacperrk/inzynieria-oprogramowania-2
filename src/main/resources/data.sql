INSERT INTO groups (name) VALUES
    ('1A'),
    ('2B'),
    ('3C');

INSERT INTO parents (first_name, last_name, email, phone, address) VALUES
    ('Katarzyna', 'Kowalska', 'k.kowalska@email.com', '123456789', 'ul. Słoneczna 15, Warszawa'),
    ('Marek', 'Nowak', 'm.nowak@email.com', '987654321', 'ul. Kwiatowa 8, Kraków'),
    ('Agnieszka', 'Wiśniewska', 'a.wisniewska@email.com', '555666777', 'ul. Parkowa 22, Gdańsk');

INSERT INTO principals (first_name, last_name, email, phone) VALUES
    ('Barbara', 'Kowalczyk', 'b.kowalczyk@szkola.edu.pl', '111222333'),
    ('Andrzej', 'Mazur', 'a.mazur@szkola.edu.pl', '444555666'),
    ('Elżbieta', 'Pawlak', 'e.pawlak@szkola.edu.pl', '777888999');

INSERT INTO students (first_name, last_name, group_id, parent_id) VALUES
    ('Anna', 'Kowalska', 1, 1),
    ('Jan', 'Nowak', 2, 2),
    ('Ewa', 'Wiśniewska', 3, 3);

INSERT INTO teachers (first_name, last_name) VALUES
    ('Maria', 'Zielińska'),
    ('Piotr', 'Błaszczykowski'),
    ('Tomasz', 'Grosik');

INSERT INTO subjects (name) VALUES
    ('Matematyka'),
    ('Historia'),
    ('Biologia');

INSERT INTO lessons (subject_id, teacher_id, group_id) VALUES
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3);

INSERT INTO schedules (group_id, lessons_id, teacher_id, day_of_week, start_time, end_time) VALUES
    (1, 1, 1, 'MONDAY', '08:00', '08:45'),
    (2, 2, 2, 'TUESDAY', '09:00', '09:45'),
    (3, 3, 3, 'WEDNESDAY', '10:00', '10:45');

INSERT INTO grades (student_id, lesson_id, value, comment) VALUES
    (1, 1, 'FIVE', 'Bardzo dobra praca domowa'),
    (2, 2, 'THREE', 'Może być lepiej'),
    (3, 3, 'FOUR', 'Dobre przygotowanie do lekcji');

INSERT INTO applications (title, description, type, status, student_id, parent_id, created_date) VALUES
    ('Usprawiedliwienie nieobecności', 'Proszę o usprawiedliwienie nieobecności mojego dziecka.', 'ABSENCE_EXCUSE', 'PENDING', 1, 1, '2024-05-16 10:30:00'),
    ('Wniosek o dodatkowe zajęcia', 'Proszę o zapisanie mojego dziecka na dodatkowe zajęcia z matematyki.', 'ADDITIONAL_CLASSES', 'APPROVED', 2, 2, '2024-05-10 14:20:00'),
    ('Prośba o zaświadczenie', 'Proszę o wystawienie zaświadczenia o uczęszczaniu do szkoły.', 'CERTIFICATE_REQUEST', 'IN_REVIEW', 3, 3, '2024-05-18 09:15:00');