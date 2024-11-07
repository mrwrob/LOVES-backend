package jajarowi.loves.assignee.service;

import jajarowi.loves.assignee.entity.Assignee;
import jajarowi.loves.assignee.repository.AssigneeRepository;
import jajarowi.loves.user.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssigneeService {

    private final AssigneeRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AssigneeService(AssigneeRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Assignee> findAll() {
        List<User> users = repository.findAll();

        return users.stream()
                .filter(x -> x instanceof Assignee)
                .map(x -> (Assignee) x)
                .sorted(Comparator.comparing(User::getLogin))
                .limit(7)
                .collect(Collectors.toList());
    }

    public List<Assignee> findAllWithPattern(String pattern) {
        List<User> users = repository.findAll();

        return users.stream()
                .filter(x -> x instanceof Assignee)
                .map(x -> (Assignee) x)
                .filter(x -> x.getLogin().contains(pattern))
                .sorted(Comparator.comparing(User::getLogin))
                .limit(7)
                .collect(Collectors.toList());
    }

    public Optional<Assignee> find(String login) {
        Optional<User> user = repository.findByLogin(login);
        if (user.isPresent() && user.get() instanceof Assignee) {
            return Optional.of((Assignee) user.get());
        } else {
            return Optional.empty();
        }
    }

    public Optional<Assignee> find(Long id){
        Optional<User> user = repository.findById(id);
        if (user.isPresent() && user.get() instanceof Assignee) {
            return Optional.of((Assignee) user.get());
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public Assignee create(Assignee assignee)
    {
        assignee.setPassword(passwordEncoder.encode(assignee.getPassword()));
        return repository.save(assignee);
    }

}
