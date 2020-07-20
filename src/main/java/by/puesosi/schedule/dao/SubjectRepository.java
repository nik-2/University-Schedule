package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Subject findFirstByDayAndGroupAndStartLessonTimeAndEndLessonTimeAndWeekNumberInAndSubjectNameAndInstitution(DayOfWeek day, Group group, String start, String end, List<Week> weeks, String subjectName, Institution institution);

    List<Subject> findAllByGroupAndDay(Group group, DayOfWeek dayOfWeek);

    List<Subject> findAllByGroupName(String groupName);

    List<Subject> findAllByGroupNameAndInstitution(String groupName, Institution institution);

    List<Subject> findAllByGroupNameAndDayName(String group, String day);
}
