package ru.keepthememories.api.controllers.birth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.keepthememories.api.controllers.birth.requests.AddBirthRequest;
import ru.keepthememories.api.controllers.birth.requests.GetBirthRequest;
import ru.keepthememories.api.controllers.birth.responses.AddBirthResponse;
import ru.keepthememories.api.controllers.birth.responses.GetBirthResponse;
import ru.keepthememories.api.handlers.QueryHandler;
import ru.keepthememories.services.BirthService;

@RestController
@RequestMapping("/birth")
public class BirthController {

    private final BirthService birthService;

    BirthController(BirthService birthService) {
        this.birthService = birthService;
    }

    @PostMapping
    public AddBirthResponse add(AddBirthRequest request) {
        return new AddBirthResponse(birthService.add(request.personId(),
                request.date(),
                request.biologicalMotherId(),
                request.biologicalFatherId()));
    }

    @GetMapping
    public GetBirthResponse get(GetBirthRequest request) {
        return new GetBirthResponse(QueryHandler.query(
                this.birthService,
                request.id(),
                request.limit(),
                request.offset()));
    }

}
