DROP TABLE IF EXISTS grade;
DROP TABLE IF EXISTS lesson;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS subject;
DROP TABLE IF EXISTS teacher;
DROP TABLE IF EXISTS school_class;


CREATE TABLE school_class (
    school_class_id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE student (
    student_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    school_class_id INT NOT NULL REFERENCES school_class(school_class_id)
);

CREATE TABLE teacher (
    teacher_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    specialization VARCHAR(100)
);

CREATE TABLE subject (
    subject_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE lesson (
    lesson_id SERIAL PRIMARY KEY,
    teacher_id INT NOT NULL REFERENCES teacher(teacher_id),
    subject_id INT NOT NULL REFERENCES subject(subject_id),
    school_class_id INT NOT NULL REFERENCES school_class(school_class_id),
    day_of_week VARCHAR(15) NOT NULL,
    start_time TIME NOT NULL
);

CREATE TABLE grade (
    grade_id SERIAL PRIMARY KEY,
    student_id INT NOT NULL REFERENCES student(student_id),
    lesson_id INT NOT NULL REFERENCES lesson(lesson_id),
    value NUMERIC(2,1) NOT NULL,
    grade_date DATE NOT NULL
);


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


-- 1. List of students with school_class name
SELECT s.first_name, s.last_name, c.name AS school_class_name
FROM student s
    JOIN school_class c ON s.school_class_id = c.school_class_id;

-- 2. Grades with subject and teacher information
SELECT
    s.first_name, s.last_name,
    sub.name AS subject_name,
    t.first_name AS teacher_first_name,
    g.value
FROM grade g
    JOIN student s ON g.student_id = s.student_id
    JOIN lesson l ON g.lesson_id = l.lesson_id
    JOIN teacher t ON l.teacher_id = t.teacher_id
    JOIN subject sub ON l.subject_id = sub.subject_id;

-- 3. school_class 1A schedule
SELECT
    l.day_of_week,
    l.start_time,
    sub.name AS subject_name,
    t.first_name || ' ' || t.last_name AS teacher_name
FROM lesson l
    JOIN school_class c ON l.school_class_id = c.school_class_id
    JOIN teacher t ON l.teacher_id = t.teacher_id
    JOIN subject sub ON l.subject_id = sub.subject_id
WHERE c.name = '1A'
ORDER BY l.day_of_week, l.start_time;
