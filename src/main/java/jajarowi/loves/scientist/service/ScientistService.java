package jajarowi.loves.scientist.service;

import jajarowi.loves.scientist.entity.Scientist;
import jajarowi.loves.scientist.repository.ScientistRepository;
import jajarowi.loves.user.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScientistService {

    private final ScientistRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ScientistService(ScientistRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Scientist> find(String login) {
        Optional<User> user = repository.findByLogin(login);
        if (user.isPresent() && user.get() instanceof Scientist) {
            return Optional.of((Scientist) user.get());
        } else {
            return Optional.empty();
        }
    }

    public Optional<Scientist> find(long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent() && user.get() instanceof Scientist) {
            return Optional.of((Scientist) user.get());
        } else {
            return Optional.empty();
        }
    }


    @Transactional
    public List<Scientist> findAll() {
        List<User> users = repository.findAll();

        return users.stream()
                .filter(x -> x instanceof Scientist)
                .map(x -> (Scientist) x)
                .collect(Collectors.toList());
    }

    @Transactional
    public Scientist create(Scientist scientist)
    {
        scientist.setPassword(passwordEncoder.encode(scientist.getPassword()));
        return repository.save(scientist);
    }

}
