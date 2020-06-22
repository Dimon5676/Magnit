package ru.magnit.Practice.repos;

import org.springframework.data.repository.CrudRepository;
import ru.magnit.Practice.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
