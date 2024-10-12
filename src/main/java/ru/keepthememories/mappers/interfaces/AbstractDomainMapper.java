package ru.keepthememories.mappers.interfaces;

import java.util.Optional;

public interface AbstractDomainMapper<Dto, Entity> {

    Entity toEntity(Dto model);

    Dto toDto(Entity entity);

    Optional<Entity> toEntity(Optional<Dto> model);

    Optional<Dto> toDto(Optional<Entity> entity);

}
