package ru.keepthememories.api.handlers;

import org.springframework.stereotype.Component;
import ru.keepthememories.api.requests.DefaultQueryRequest;
import ru.keepthememories.domain.interfaces.services.Gettable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class QueryHandler {

    public static <T> List<T> query(Gettable<T> gettable,
                                    Optional<Integer> id, Optional<Long> limit, Optional<Long> offset) {
        if (id.isPresent()) {
            return gettable.getById(id.get()).map(List::of).orElseGet(ArrayList::new);
        } else {
            if (limit.isEmpty()) {
                return gettable.getAll();
            } else if (offset.isEmpty()) {
                return gettable.getRange(limit.get(), 0L);
            } else {
                return gettable.getRange(limit.get(), offset.get());
            }
        }
    }

    public static <T> List<T> query(Gettable<T> gettable, DefaultQueryRequest request) {
        return query(gettable, request.id(), request.limit(), request.offset());
    }

}
