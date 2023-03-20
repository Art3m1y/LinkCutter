package ru.Art3m1y.LinkCutter.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.Art3m1y.LinkCutter.dtoes.LinkDto;
import ru.Art3m1y.LinkCutter.models.Link;

@Mapper
public interface LinkMapper {
    LinkMapper INSTANCE = Mappers.getMapper(LinkMapper.class);

    Link fromLinkDtoToLink(LinkDto shortenedLinkDto);
}
