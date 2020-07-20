package by.puesosi.schedule.service;

import by.puesosi.schedule.dao.*;
import by.puesosi.schedule.entity.*;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    private AuditoryRepository auditoryRepository;
    private DayOfWeekRepository dayOfWeekRepository;
    private EmployeeRepository employeeRepository;
    private GroupRepository groupRepository;
    private SubjectRepository subjectRepository;
    private WeekRepository weekRepository;
    private CityRepository cityRepository;
    private InstitutionRepository institutionRepository;
    private UserRepository userRepository;

    public ServiceImpl() {
    }

    @Autowired
    public ServiceImpl(AuditoryRepository auditoryRepository, DayOfWeekRepository dayOfWeekRepository,
                       EmployeeRepository employeeRepository, GroupRepository groupRepository,
                       SubjectRepository subjectRepository, WeekRepository weekRepository,
                       CityRepository cityRepository, InstitutionRepository institutionRepository,
                       UserRepository userRepository) {
        this.auditoryRepository = auditoryRepository;
        this.dayOfWeekRepository = dayOfWeekRepository;
        this.employeeRepository = employeeRepository;
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
        this.weekRepository = weekRepository;
        this.cityRepository = cityRepository;
        this.institutionRepository = institutionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrMail) throws UsernameNotFoundException {
        User user = userRepository.findFirstByUsernameOrEmail(usernameOrMail, usernameOrMail);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        user.setLastVisit(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

    @Override
    public void checkBSUIRSchedule() {
        List<Group> groups = groupRepository.findAll();
        for (Group group : groups) {
            getGroupSchedule(group.getName());
        }
    }

    @Override
    public HashMap<String, List<Subject>> findGroupSchedule(String group, String city, String institution) {
        HashMap<String, List<Subject>> subjectMap = new HashMap<>();
        City currentCity = cityRepository.findCityByName(city);
        Institution currentInstitution = institutionRepository.findInstitutionByCityAndName(currentCity, institution);
        List<Subject> subjects = subjectRepository.findAllByGroupNameAndInstitution(group, currentInstitution);
        if (!subjects.isEmpty()) {
            List<DayOfWeek> dayOfWeeks = dayOfWeekRepository.findAll();
            for (DayOfWeek dayOfWeek : dayOfWeeks) {
                String dayOfWeekStr = dayOfWeek.getName();
                subjectMap.put(dayOfWeekStr, subjectRepository.findAllByGroupNameAndDayName(group, dayOfWeekStr));
            }
        } else {
            String answer = getGroupSchedule(group);
            if (answer.equals("Success")) {
                List<DayOfWeek> dayOfWeeks = dayOfWeekRepository.findAll();
                for (DayOfWeek dayOfWeek : dayOfWeeks) {
                    String dayOfWeekStr = dayOfWeek.getName();
                    subjectMap.put(dayOfWeekStr, subjectRepository.findAllByGroupNameAndDayName(group, dayOfWeekStr));
                }
            }
        }
        return subjectMap;
    }

    @Override
    public List<City> findCity() {
        return cityRepository.findAll();
    }

    @Override
    public List<Institution> findInstitutions(String city) {
        return institutionRepository.findInstitutionByCityName(city);
    }

    @Override
    public boolean checkCity(String city) {
        City currentCity = cityRepository.findCityByName(city);
        return currentCity != null;
    }

    @Override
    public boolean checkInstitution(String city, String institution) {
        Institution currentInstitution = institutionRepository.findInstitutionByCityNameAndName(city, institution);
        return currentInstitution != null;
    }

    private String getGroupSchedule(String group) {
        return getHtmlElements(group);
    }

    private String getHtmlElements(String group) {
        String baseUrl = "https://iis.bsuir.by/";
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().isJavaScriptEnabled();
        String result = null;
        try {
            String searchUrl = baseUrl + "schedule;groupName=" + URLEncoder.encode(group, "UTF-8");
            HtmlPage page = client.getPage(searchUrl);
            client.waitForBackgroundJavaScript(10000);
            result = checkHtmlElements(page.getByXPath("//div[@class='ng-tns-c4-0 ng-star-inserted']"), group);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String checkHtmlElements(List<HtmlElement> items, String group) {
        if (items.isEmpty()) {
            return "Error";
        } else {
            parseHtmlElements(items, group);
            return "Success";
        }
    }

    private void parseHtmlElements(List<HtmlElement> items, String group) {
        int c6Number = 4;
        for (HtmlElement htmlItem : items) {

            Group currentGroup = checkAndGetCurrentGroup(group);

            DayOfWeek day = checkAndGetDayOfWeek(htmlItem);

            City currentCity = cityRepository.findCityByName("Минск");
            Institution currentInstitution = institutionRepository.findInstitutionByCityAndName(currentCity, "БГУИР");

            List<HtmlElement> subjectList = htmlItem.getByXPath(".//div[@class='ng-tns-c6-" + c6Number + " ng-star-inserted']");

            for (HtmlElement subjectItem : subjectList) {
                HtmlElement startLessonTime = subjectItem.getFirstByXPath(".//span[@class='startLessonTime']");
                HtmlElement endLessonTime = subjectItem.getFirstByXPath(".//span[@class='endLessonTime']");
                List<HtmlElement> weekNumbers = subjectItem.getByXPath(".//div[@class='week-number ng-tns-c6-" + c6Number + " ng-star-inserted']/span[@class='ng-tns-c6-" + c6Number + " ng-star-inserted']");
                HtmlElement subjectName = subjectItem.getFirstByXPath(".//div[@class='schedule-item']/div[@class='subject-employee-block ng-tns-c6-" + c6Number + " ng-star-inserted']/span[@class='subject']");
                List<HtmlElement> employees = subjectItem.getByXPath(".//span[@class='employee ng-tns-c6-" + c6Number + " ng-star-inserted']/a[@class='ng-tns-c6-" + c6Number + "']");
                HtmlElement note = subjectItem.getFirstByXPath(".//span[@class='note']");
                List<HtmlElement> auditoriums = subjectItem.getByXPath(".//div[@class='schedule-item']/div[@class='auditory']/span[@class='ng-tns-c6-" + c6Number + " ng-star-inserted']");
                HtmlElement subgroup = subjectItem.getFirstByXPath(".//span[@class='subgroup']");

                List<Week> weeks = checkSaveAndGetWeeks(weekNumbers);

                List<Employee> employeesList = checkSaveAndGetEmployeeList(employees);

                List<Auditory> auditoriumsList = checkSaveAndGetAuditoriumsList(auditoriums);

                String subgroupString = checkAndSaveSubgroup(subgroup);

                Subject subject = subjectRepository.findFirstByDayAndGroupAndStartLessonTimeAndEndLessonTimeAndWeekNumberInAndSubjectNameAndInstitution(day, currentGroup, startLessonTime.asText(), endLessonTime.asText(), weeks, subjectName.asText(), currentInstitution);
                int numberOfDifference = 0;
                if (subject == null) {
                    subject = new Subject();
                    subject.setStartLessonTime(startLessonTime.asText());
                    subject.setEndLessonTime(endLessonTime.asText());
                    subject.setWeekNumber(weeks);
                    subject.setSubjectName(subjectName.asText());
                    subject.setEmployee(employeesList);
                    subject.setNote(note.asText());
                    subject.setAuditory(auditoriumsList);
                    subject.setSubgroup(subgroupString);
                    subject.setDay(day);
                    subject.setGroup(currentGroup);
                    subject.setInstitution(currentInstitution);
                    subjectRepository.save(subject);
                } else {
                    if (!subject.getStartLessonTime().equals(startLessonTime.asText())) {
                        numberOfDifference++;
                    }
                    if (!subject.getEndLessonTime().equals(endLessonTime.asText())) {
                        numberOfDifference++;
                    }
                    if (!subject.getWeekNumber().equals(weeks)) {
                        numberOfDifference++;
                    }
                    if (!subject.getSubjectName().equals(subjectName.asText())) {
                        numberOfDifference++;
                    }
                    if (!subject.getEmployee().equals(employeesList)) {
                        numberOfDifference++;
                    }
                    if (!subject.getNote().equals(note.asText())) {
                        numberOfDifference++;
                    }
                    if (!subject.getAuditory().equals(auditoriumsList)) {
                        numberOfDifference++;
                    }
                    if (!subject.getSubgroup().equals(subgroupString)) {
                        numberOfDifference++;
                    }
                    if (!subject.getDay().equals(day)) {
                        numberOfDifference++;
                    }
                    if (!subject.getGroup().equals(currentGroup)) {
                        numberOfDifference++;
                    }
                    if (!subject.getInstitution().equals(currentInstitution)) {
                        numberOfDifference++;
                    }
                    if (numberOfDifference != 0) {
                        subjectRepository.save(subject);
                    }
                }
            }
            c6Number++;
        }
    }

    private Group checkAndGetCurrentGroup(String group) {
        Group currentGroup = groupRepository.findFirstByName(group);
        if (currentGroup == null) {
            currentGroup = new Group();
            currentGroup.setName(group);
            groupRepository.save(currentGroup);
        }
        return currentGroup;
    }

    private DayOfWeek checkAndGetDayOfWeek(HtmlElement htmlItem) {
        HtmlElement dayOfWeek = htmlItem.getFirstByXPath(".//div[@class='day-of-week']");
        DayOfWeek day = dayOfWeekRepository.findFirstByName(dayOfWeek.asText());
        if (day == null) {
            day = new DayOfWeek();
            day.setName(dayOfWeek.asText());
            dayOfWeekRepository.save(day);
        }
        return day;
    }

    private List<Week> checkSaveAndGetWeeks(List<HtmlElement> weekNumbers) {
        List<Week> weeks = new ArrayList<>();
        for (HtmlElement weekNumber : weekNumbers) {
            Week week = weekRepository.findFirstByWeekNumber(Integer.parseInt(weekNumber.asText()));
            if (week == null) {
                week = new Week();
                week.setWeekNumber(Integer.parseInt(weekNumber.asText()));
                weekRepository.save(week);
            }
            weeks.add(week);
        }
        if (weeks.size() == 0) {
            Week week = weekRepository.findFirstByWeekNumber(0);
            if (week == null) {
                week = new Week();
                week.setWeekNumber(0);
                weekRepository.save(week);
            }
            weeks.add(week);
        }
        return weeks;
    }

    private List<Employee> checkSaveAndGetEmployeeList(List<HtmlElement> employees) {
        List<Employee> employeesList = new ArrayList<>();
        if (employees != null) {
            for (int i = 0; i < employees.size(); i = i + 2) {
                Employee employee = employeeRepository.findFirstByEmployeeName(employees.get(i).asText());
                if (employee == null) {
                    employee = new Employee();
                    employee.setEmployeeName(employees.get(i).asText());
                    if (!employees.get(i + 1).asText().equals("")) {
                        employee.setEmployeeMail(employees.get(i + 1).asText());
                    }
                    employeeRepository.save(employee);
                }
                employeesList.add(employee);
            }
        }
        return employeesList;
    }

    private List<Auditory> checkSaveAndGetAuditoriumsList(List<HtmlElement> auditoriums) {
        List<Auditory> auditoriumsList = new ArrayList<>();
        if (auditoriums != null) {
            for (HtmlElement auditorium : auditoriums) {
                Auditory auditory = auditoryRepository.findFirstByName(auditorium.asText().replace("\r\n", ""));
                if (auditory == null) {
                    auditory = new Auditory();
                    auditory.setName(auditorium.asText().replace("\r\n", ""));
                    auditoryRepository.save(auditory);
                }
                auditoriumsList.add(auditory);
            }
        }
        return auditoriumsList;
    }

    private String checkAndSaveSubgroup(HtmlElement subgroup) {
        String subgroupString = subgroup.asText();
        if (!subgroupString.equals("")) {
            subgroupString = subgroupString.substring(0, 1);
        }
        return subgroupString;
    }
}
