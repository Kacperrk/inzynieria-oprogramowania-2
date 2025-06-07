DROP INDEX IF EXISTS idx_lessons_subject_id;
DROP INDEX IF EXISTS idx_lessons_teacher_id;
DROP INDEX IF EXISTS idx_lessons_group_id;
DROP INDEX IF EXISTS idx_grades_student_id;
DROP INDEX IF EXISTS idx_grades_lesson_id;
DROP INDEX IF EXISTS idx_students_group_id;
DROP INDEX IF EXISTS idx_students_parent_id;
DROP INDEX IF EXISTS idx_schedules_group_id;
DROP INDEX IF EXISTS idx_schedules_teacher_id;
DROP INDEX IF EXISTS idx_applications_student_id;
DROP INDEX IF EXISTS idx_applications_parent_id;

DROP TABLE IF EXISTS applications;
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS grades;
DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS subjects;
DROP TABLE IF EXISTS teachers;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS parents;
DROP TABLE IF EXISTS principals;


CREATE TABLE groups (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(10) NOT NULL UNIQUE, -- '1A', '2B'
    archived BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE parents (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address TEXT,
    archived BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE principals (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    archived BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE students (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    group_id BIGINT REFERENCES groups(id) ON DELETE RESTRICT,
    parent_id BIGINT REFERENCES parents(id) ON DELETE RESTRICT,
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

CREATE TABLE schedules (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    group_id BIGINT NOT NULL REFERENCES groups(id) ON DELETE RESTRICT,
    lessons_id BIGINT NOT NULL REFERENCES subjects(id) ON DELETE RESTRICT,
    teacher_id BIGINT NOT NULL REFERENCES teachers(id) ON DELETE RESTRICT,
    day_of_week VARCHAR(20) NOT NULL,
    start_time VARCHAR(10) NOT NULL,
    end_time VARCHAR(10) NOT NULL,
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

CREATE TABLE applications (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    type VARCHAR(50) NOT NULL, -- enum: 'ABSENCE_EXCUSE', 'ADDITIONAL_CLASSES', 'CERTIFICATE_REQUEST', 'GRADE_APPEAL', 'OTHER'
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- enum: 'PENDING', 'APPROVED', 'REJECTED', 'IN_REVIEW'
    student_id BIGINT NOT NULL REFERENCES students(id) ON DELETE RESTRICT,
    parent_id BIGINT REFERENCES parents(id) ON DELETE RESTRICT,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    processed_date TIMESTAMP,
    response TEXT,
    archived BOOLEAN NOT NULL DEFAULT FALSE
);


CREATE INDEX idx_lessons_subject_id ON lessons(subject_id);
CREATE INDEX idx_lessons_teacher_id ON lessons(teacher_id);
CREATE INDEX idx_lessons_group_id ON lessons(group_id);
CREATE INDEX idx_grades_student_id ON grades(student_id);
CREATE INDEX idx_grades_lesson_id ON grades(lesson_id);
CREATE INDEX idx_students_group_id ON students(group_id);
CREATE INDEX idx_students_parent_id ON students(parent_id);
CREATE INDEX idx_schedules_group_id ON schedules(group_id);
CREATE INDEX idx_schedules_teacher_id ON schedules(teacher_id);
CREATE INDEX idx_applications_student_id ON applications(student_id);
CREATE INDEX idx_applications_parent_id ON applications(parent_id);