package ru.keepthememories.api.controllers.birth;

import org.springframework.web.bind.annotation.*;
import ru.keepthememories.api.controllers.birth.requests.*;
import ru.keepthememories.api.controllers.birth.responses.*;
import ru.keepthememories.api.handlers.QueryHandler;
import ru.keepthememories.api.mappers.UpdateBirthRequestMapper;
import ru.keepthememories.api.requests.DefaultQueryRequest;
import ru.keepthememories.services.BirthService;

@RestController
@RequestMapping("/birth")
public class BirthController {

    private final BirthService birthService;
    private final UpdateBirthRequestMapper updateMapper;

    BirthController(BirthService birthService,
                    UpdateBirthRequestMapper updateMapper) {
        this.birthService = birthService;
        this.updateMapper = updateMapper;
    }

    @PostMapping
    public AddBirthResponse add(@RequestBody AddBirthRequest request) {
        return new AddBirthResponse(birthService.add(
                request.id(),
                request.date(),
                request.biologicalMotherId(),
                request.biologicalFatherId()));
    }

    @GetMapping
    public GetBirthResponse get(DefaultQueryRequest request) {
        return new GetBirthResponse(QueryHandler.query(birthService, request));
    }

    @PutMapping
    public void update(UpdateBirthRequest request) {
        birthService.update(request.id(), updateMapper.toDto(request));
    }

}
