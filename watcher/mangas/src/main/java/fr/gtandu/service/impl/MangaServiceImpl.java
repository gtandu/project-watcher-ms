package fr.gtandu.service.impl;

import fr.gtandu.exception.MangaNotFoundException;
import fr.gtandu.repository.MangaRepository;
import fr.gtandu.service.MangaService;
import fr.gtandu.shared.core.media.dto.MangaDto;
import fr.gtandu.shared.core.media.mapper.MangaMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    public MangaDto createManga(MangaDto mangaDto) {
        return mangaMapper.toDto(mangaRepository.save(mangaMapper.toEntity(mangaDto)));
    }

    @Override
    public MangaDto updateManga(MangaDto mangaDto) throws MangaNotFoundException {
        if (null != mangaDto && null != mangaDto.getId() && existsById(mangaDto.getId())) {
            return mangaMapper.toDto(mangaRepository.save(mangaMapper.toEntity(mangaDto)));
        }
        throw new MangaNotFoundException();
    }

    @Override
    public boolean deleteMangaById(Long mangaId) {
        if (null != mangaId) {
            return mangaRepository.deleteByMangaId(mangaId) == 1;
        } else {
            return false;
        }
    }

    @Override
    public List<MangaDto> searchByName(String searchKey) {
        // TODO
        // Recherche en bdd
        // Si moins de 5 resultats completer avec une recherche dans manga dex

        List<MangaDto> mangaDtoList = mangaMapper.toDtoList(mangaRepository.findByNameStartingWith(searchKey));
        if (mangaDtoList.size() <= 5) {
            // Completer avec Manga Dex
        }
        return mangaDtoList;
    }

    @Override
    public boolean existsById(Long id) {
        return mangaRepository.existsById(id);
    }
}
