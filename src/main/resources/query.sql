-- 1. Wyświetl imię i nazwisko ucznia, nazwę przedmiotu i ocenę
SELECT
    s.first_name || ' ' || s.last_name AS student_name,
    sub.name AS subject_name,
    g.value AS grade
FROM grades g
         JOIN students s ON g.student_id = s.id
         JOIN lessons l ON g.lesson_id = l.id
         JOIN subjects sub ON l.subject_id = sub.id;

-- 2. Wyświetl wszystkich nauczycieli wraz z grupami, które uczą (bez duplikatów)
SELECT DISTINCT
    t.first_name || ' ' || t.last_name AS teacher_name,
    g.name AS group_name
FROM lessons l
         JOIN teachers t ON l.teacher_id = t.id
         JOIN groups g ON l.group_id = g.id;

-- 3. Wyświetl uczniów i komentarze do ich ocen z nazwą przedmiotu i nauczycielem
SELECT
    s.first_name || ' ' || s.last_name AS student_name,
    sub.name AS subject_name,
    t.first_name || ' ' || t.last_name AS teacher_name,
    g.value AS grade,
    g.comment
FROM grades g
         JOIN students s ON g.student_id = s.id
         JOIN lessons l ON g.lesson_id = l.id
         JOIN subjects sub ON l.subject_id = sub.id
         JOIN teachers t ON l.teacher_id = t.id;
