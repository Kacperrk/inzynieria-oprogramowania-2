DROP INDEX IF EXISTS idx_lessons_subject_id;
DROP INDEX IF EXISTS idx_lessons_teacher_id;
DROP INDEX IF EXISTS idx_lessons_group_id;
DROP INDEX IF EXISTS idx_grades_student_id;
DROP INDEX IF EXISTS idx_grades_lesson_id;
DROP INDEX IF EXISTS idx_students_group_id;

DROP TABLE IF EXISTS grades;
DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS subjects;
DROP TABLE IF EXISTS teachers;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS groups;


CREATE TABLE groups (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(10) NOT NULL UNIQUE, -- '1A', '2B'
    archived BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE students (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    group_id BIGINT REFERENCES groups(id) ON DELETE RESTRICT,
    archived BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE teachers (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    archived BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE subjects (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    archived BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE lessons (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    subject_id BIGINT NOT NULL REFERENCES subjects(id) ON DELETE RESTRICT,
    teacher_id BIGINT NOT NULL REFERENCES teachers(id) ON DELETE RESTRICT,
    group_id BIGINT NOT NULL REFERENCES groups(id) ON DELETE RESTRICT,
    archived BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE grades (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES students(id) ON DELETE RESTRICT,
    lesson_id BIGINT NOT NULL REFERENCES lessons(id) ON DELETE RESTRICT,
    value VARCHAR(20) NOT NULL DEFAULT 'ONE', -- enum: 'ONE', 'TWO', 'THREE', 'FOUR', 'FIVE', 'SIX'
    comment TEXT,
    archived BOOLEAN NOT NULL DEFAULT FALSE
);


CREATE INDEX idx_lessons_subject_id ON lessons(subject_id);
CREATE INDEX idx_lessons_teacher_id ON lessons(teacher_id);
CREATE INDEX idx_lessons_group_id ON lessons(group_id);
CREATE INDEX idx_grades_student_id ON grades(student_id);
CREATE INDEX idx_grades_lesson_id ON grades(lesson_id);
CREATE INDEX idx_students_group_id ON students(group_id);
