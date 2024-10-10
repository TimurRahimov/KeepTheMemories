package ru.keepthememories.api.controllers.person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import ru.keepthememories.api.controllers.person.requests.AddPersonRequest;
import ru.keepthememories.api.controllers.person.requests.DeletePersonRequest;
import ru.keepthememories.api.controllers.person.requests.GetPersonRequest;
import ru.keepthememories.api.controllers.person.requests.UpdatePersonRequest;
import ru.keepthememories.api.controllers.person.responses.AddPersonResponse;
import ru.keepthememories.api.controllers.person.responses.GetPersonResponse;
import ru.keepthememories.api.handlers.QueryHandler;
import ru.keepthememories.domain.models.Person;
import ru.keepthememories.services.PersonService;


@RestController
@RequestMapping("/person")
public class PersonController {

    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;

    PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public AddPersonResponse add(@RequestBody AddPersonRequest request) {
        Person addedPerson = personService.add(
                request.surname(),
                request.name(),
                request.patronymic(),
                request.sex()
        );

        return new AddPersonResponse(addedPerson.getPersonId());
    }

    @GetMapping
    public GetPersonResponse get(GetPersonRequest request) {
        Optional<Integer> id = request.id();
        Optional<Long> limit = request.limit();
        Optional<Long> offset = request.offset();

        return new GetPersonResponse(QueryHandler.query(this.personService, id, limit, offset));
    }

    @DeleteMapping
    public void delete(DeletePersonRequest request) {
        Integer personId = request.personId();
        personService.delete(personId);
    }

    @PutMapping
    public void update(UpdatePersonRequest request) {
        personService.update(request.id(),
                Person.builder()
                        .personId(request.id())
                        .surname(request.surname())
                        .name(request.name())
                        .patronymic(request.patronymic())
                        .sex(request.sex().orElse(null))
                        .build()
        );
    }

}
