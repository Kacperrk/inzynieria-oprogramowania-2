INSERT INTO school_class (name) VALUES
    ('1A'),
    ('2B'),
    ('3C');

INSERT INTO student (first_name, last_name, birth_date, school_class_id) VALUES
    ('Anna', 'Kowalska', '2008-05-20', 1),
    ('Bartek', 'Nowak', '2007-11-12', 2),
    ('Celina', 'Wiśniewska', '2009-03-03', 1);

INSERT INTO teacher (first_name, last_name, specialization) VALUES
    ('Jan', 'Zieliński', 'Matematyka'),
    ('Maria', 'Dąbrowska', 'Biologia');

INSERT INTO subject (name) VALUES
    ('Matematyka'),
    ('Biologia');

INSERT INTO lesson (teacher_id, subject_id, school_class_id, day_of_week, start_time) VALUES
    (1, 1, 1, 'Poniedziałek', '08:00'),
    (2, 2, 1, 'Wtorek', '10:00'),
    (1, 1, 2, 'Środa', '09:00');

INSERT INTO grade (student_id, lesson_id, value, grade_date) VALUES
    (1, 1, 4.5, '2024-10-10'),
    (3, 2, 5.0, '2024-10-11'),
    (2, 3, 3.5, '2024-10-12');
