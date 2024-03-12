package fr.gtandu.service.impl;

import fr.gtandu.exception.MangaAlreadyExistException;
import fr.gtandu.exception.MangaNotFoundException;
import fr.gtandu.media.dto.MangaDto;
import fr.gtandu.media.mapper.MangaMapper;
import fr.gtandu.repository.MangaRepository;
import fr.gtandu.service.MangaService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class MangaServiceImpl implements MangaService {

    private final MangaRepository mangaRepository;
    private final MangaMapper mangaMapper;

    public MangaServiceImpl(MangaRepository mangaRepository, MangaMapper mangaMapper) {
        this.mangaRepository = mangaRepository;
        this.mangaMapper = mangaMapper;
    }

    @Override
    public MangaDto createManga(@NonNull MangaDto mangaDto) throws MangaAlreadyExistException {
        if (null != mangaDto.getId() && existsById(mangaDto.getId())) {
            throw new MangaAlreadyExistException();
        } else {
            return mangaMapper.toDto(mangaRepository.save(mangaMapper.toEntity(mangaDto)));
        }
    }

    @Override
    public MangaDto updateManga(@NonNull MangaDto mangaDto) throws MangaNotFoundException {
        if (null != mangaDto.getId() && existsById(mangaDto.getId())) {
            return mangaMapper.toDto(mangaRepository.save(mangaMapper.toEntity(mangaDto)));
        }
        throw new MangaNotFoundException();
    }

    @Override
    public boolean deleteMangaById(@NonNull Long mangaId) {
        return mangaRepository.deleteByMangaId(mangaId) == 1;
    }

    @Override
    public List<MangaDto> searchByName(String searchKey) {
        // TODO
        // Recherche en bdd
        // Si moins de 5 resultats completer avec une recherche dans manga dex

        if (StringUtils.isNotEmpty(searchKey)) {
            List<MangaDto> mangaDtoList = mangaMapper.toDtoList(mangaRepository.findByNameStartingWith(searchKey));
            if (mangaDtoList.size() <= 5) {
                // Completer avec Manga Dex
            }
            return mangaDtoList;

        }

        return Collections.emptyList();
    }

    @Override
    public boolean existsById(@NonNull Long id) {
        return mangaRepository.existsById(id);
    }
}
