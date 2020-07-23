package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * The interface Subject repository.
 */
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    /**
     * Find first by day and group and start lesson time and end lesson time and week number in and subject name and institution subject.
     *
     * @param day         the day
     * @param group       the group
     * @param start       the start
     * @param end         the end
     * @param weeks       the weeks
     * @param subjectName the subject name
     * @param institution the institution
     * @return the subject
     */
    Subject findFirstByDayAndGroupAndStartLessonTimeAndEndLessonTimeAndWeekNumberInAndSubjectNameAndInstitution(DayOfWeek day, Group group, String start, String end, List<Week> weeks, String subjectName, Institution institution);

    /**
     * Find all by group and day list.
     *
     * @param group     the group
     * @param dayOfWeek the day of week
     * @return the list
     */
    List<Subject> findAllByGroupAndDay(Group group, DayOfWeek dayOfWeek);

    /**
     * Find all by group name list.
     *
     * @param groupName the group name
     * @return the list
     */
    List<Subject> findAllByGroupName(String groupName);

    /**
     * Find all by group name and institution list.
     *
     * @param groupName   the group name
     * @param institution the institution
     * @return the list
     */
    List<Subject> findAllByGroupNameAndInstitution(String groupName, Institution institution);

    /**
     * Find all by group name and day name list.
     *
     * @param group the group
     * @param day   the day
     * @return the list
     */
    List<Subject> findAllByGroupNameAndDayName(String group, String day);

    /**
     * Delete all by group.
     *
     * @param group the group
     */
    void deleteAllByGroup(Group group);
}
