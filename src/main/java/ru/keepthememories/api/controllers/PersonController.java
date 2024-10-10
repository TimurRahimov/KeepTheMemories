package ru.keepthememories.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import ru.keepthememories.api.requests.AddPersonRequest;
import ru.keepthememories.api.requests.GetPersonRequest;
import ru.keepthememories.api.requests.UpdatePersonRequest;
import ru.keepthememories.api.responses.AddPersonResponse;
import ru.keepthememories.api.responses.GetPersonResponse;
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
                request.patronymic()
        );

        return new AddPersonResponse(addedPerson.getPersonId());
    }

    @GetMapping
    public GetPersonResponse get(GetPersonRequest request) {
        Optional<Long> limit = request.limit();
        Optional<Long> offset = request.offset();
        Optional<Integer> personId = request.personId();

        return new GetPersonResponse(personId.isPresent() ? personService.get(personId.get()) :
                limit.isEmpty() ? personService.getAll() :
                        offset.isEmpty() ? personService.get(limit.get(), 0L) :
                                personService.get(limit.get(), offset.get()));
    }

    @PutMapping
    public void update(UpdatePersonRequest request) {
        Person.Builder personBuilder = Person.getBuilder();
        personBuilder.setPersonId(request.id());
        request.surname().ifPresent(personBuilder::setSurname);
        request.name().ifPresent(personBuilder::setName);
        request.patronymic().ifPresent(personBuilder::setPatronymic);

        personService.update(request.id(), personBuilder.build());
    }

}
