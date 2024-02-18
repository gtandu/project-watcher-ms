package fr.gtandu.shared.core.mapper;

import fr.gtandu.shared.core.document.UserDocument;
import fr.gtandu.shared.core.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ReadingMediaMapper.class})
public interface UserMapper {
    UserDto toDto(UserDocument userDocument);

    UserDocument toDocument(UserDto userDto);
}
