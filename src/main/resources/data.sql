INSERT INTO groups (name) VALUES
    ('1A'),
    ('2B'),
    ('3C');

INSERT INTO students (first_name, last_name, group_id) VALUES
    ('Anna', 'Kowalska', 1),
    ('Jan', 'Nowak', 2),
    ('Ewa', 'Wiśniewska', 3);

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

INSERT INTO grades (student_id, lesson_id, value, comment) VALUES
    (1, 1, '5', 'Bardzo dobra praca domowa'),
    (2, 2, '3', 'Może być lepiej'),
    (3, 3, '4', 'Dobre przygotowanie do lekcji');
