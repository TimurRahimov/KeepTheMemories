package ru.keepthememories.domain.interfaces.mappers;

public interface AbstractApiMapper<Request, Dto> {

    Dto toDto(Request request);

}
