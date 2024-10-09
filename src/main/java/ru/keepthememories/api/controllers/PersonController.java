package ru.keepthememories.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import ru.keepthememories.api.requests.AddPersonRequest;
import ru.keepthememories.api.requests.GetPersonRequest;
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
        Optional<Long> count = request.count();
        Optional<Integer> personId = request.personId();

        return new GetPersonResponse(personId.isPresent() ? personService.get(personId.get()) :
                count.isEmpty() ? personService.getAll() : personService.get(count.get()));
    }

}
