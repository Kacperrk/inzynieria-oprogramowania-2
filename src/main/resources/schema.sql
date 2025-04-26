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
