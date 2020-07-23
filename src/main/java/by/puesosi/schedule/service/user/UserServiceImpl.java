package by.puesosi.schedule.service.user;

import by.puesosi.schedule.dao.GroupRepository;
import by.puesosi.schedule.dao.UserRepository;
import by.puesosi.schedule.entity.Group;
import by.puesosi.schedule.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService {

    private GroupRepository groupRepository;
    private UserRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param groupRepository the group repository
     * @param userRepository  the user repository
     */
    @Autowired
    public UserServiceImpl(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addGroupSchedule(User user, String cityName, String institutionName, String groupName) {
        Group group = groupRepository.findFirstByInstitution_NameAndCity_NameAndName(institutionName, cityName, groupName);
        List<Group> groups = user.getGroups();
        groups.add(group);
        user.setGroups(groups);
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteGroup(User user, String cityName, String institutionName, String groupName) {
        Group group = groupRepository.findFirstByInstitution_NameAndCity_NameAndName(institutionName, cityName, groupName);
        List<Group> groups = user.getGroups();
        groups.remove(group);
        user.setGroups(groups);
        userRepository.save(user);
    }

    @Override
    public boolean checkGroupAvailability(User user, String cityName, String institutionName, String groupName) {
        Group group = groupRepository.findFirstByInstitution_NameAndCity_NameAndName(institutionName, cityName, groupName);
        List<Group> groups = user.getGroups();
        return groups.contains(group);
    }

    @Override
    public boolean deleteGroupFromDB(String cityName, String institutionName, String groupName) {
        Group group = groupRepository.findFirstByInstitution_NameAndCity_NameAndName(institutionName, cityName, groupName);
        if(group == null){
            return true;
        }
        groupRepository.delete(group);
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getCurrentUser(Principal principal) {
        User user;
        if (principal instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
            Authentication authentication = oAuth2Authentication.getUserAuthentication();
            Map<String, String> details = (Map<String, String>) authentication.getDetails();
            user = findUserByEmail(details.get("email"));
        } else {
            user = findUserByUsername(principal.getName());
        }
        return user;
    }
}
