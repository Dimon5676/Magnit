package ru.magnit.Practice.repos;

import org.springframework.data.repository.CrudRepository;
import ru.magnit.Practice.models.Idea;

public interface IdeaRepository extends CrudRepository<Idea, Long> {

}
