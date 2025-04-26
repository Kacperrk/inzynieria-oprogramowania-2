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
