package edu.cit.hapongo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import edu.cit.hapongo.model.Dictionary;
import edu.cit.hapongo.repository.DictionaryRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class DictionaryService {

    @Autowired
    private DictionaryRepository repository;

    public List<Dictionary> findAll() {
        return repository.findAll();
    }

    public List<Dictionary> findTop100() {
        Pageable limit = PageRequest.of(0, 100);
        return repository.findAll(limit).getContent();
    }

    public Optional<Dictionary> findById(int id) {
        return repository.findById(id);
    }

    public Dictionary save(Dictionary japaneseWord) {
        return repository.save(japaneseWord);
    }

    public Dictionary update(int id, Dictionary updatedWord) {
        return repository.findById(id).map(existing -> {
            existing.setEnglishWord(updatedWord.getEnglishWord());
            existing.setJapaneseKanji(updatedWord.getJapaneseKanji());
            existing.setJapaneseReading(updatedWord.getJapaneseReading());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Word not found with id " + id));
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
