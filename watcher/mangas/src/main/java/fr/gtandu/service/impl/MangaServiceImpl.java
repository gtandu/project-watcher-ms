package fr.gtandu.service.impl;

import fr.gtandu.mapper.MangaMapper;
import fr.gtandu.repository.MangaRepository;
import fr.gtandu.service.MangaService;
import fr.gtandu.shared.core.dto.MangaDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MangaServiceImpl implements MangaService {
    private final MangaRepository mangaRepository;
    private final MangaMapper mangaMapper;

    public MangaServiceImpl(MangaRepository mangaRepository, MangaMapper mangaMapper) {
        this.mangaRepository = mangaRepository;
        this.mangaMapper = mangaMapper;
    }

    @Override
    public Mono<MangaDto> getMangaById(String mangaId) {
        return mangaRepository.findById(mangaId).map(mangaMapper::toDto);
    }

    @Override
    public Flux<MangaDto> getAll() {
        return mangaRepository.findAll().map(mangaMapper::toDto);
    }

    @Override
    public Mono<MangaDto> createManga(MangaDto mangaDto) {
        return mangaRepository.save(mangaMapper.toDocument(mangaDto)).map(mangaMapper::toDto);
    }

    @Override
    public Mono<MangaDto> updateManga(MangaDto mangaDto) {
        return mangaRepository.save(mangaMapper.toDocument(mangaDto)).map(mangaMapper::toDto);
    }

    @Override
    public Mono<Void> deleteMangaById(String mangaId) {
        return mangaRepository.deleteById(mangaId);
    }
}
