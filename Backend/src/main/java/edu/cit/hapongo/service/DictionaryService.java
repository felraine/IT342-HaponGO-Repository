package edu.cit.hapongo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.cit.hapongo.model.Dictionary;
import edu.cit.hapongo.repository.DictionaryRepository;
import java.util.List;
import java.util.Optional;

@Service
public class DictionaryService {

    @Autowired
    private DictionaryRepository repository;

    // WARNING: Do not use for now (very laggy lol)
    public List<Dictionary> findAll() {
        return repository.findAll();
    }

    // Temporary Service for Testing
    public List<Dictionary> findTop10() {
        return repository.findTop10();
    }

    public Optional<Dictionary> findById(long id) {
        return repository.findById(id);
    }

    public Dictionary save(Dictionary japaneseWord) {
        return repository.save(japaneseWord);
    }

    public Dictionary update(long id, Dictionary updatedWord) {
        return repository.findById(id).map(existing -> {
            existing.setEnglishWord(updatedWord.getEnglishWord());
            existing.setJapaneseKanji(updatedWord.getJapaneseKanji());
            existing.setJapaneseReading(updatedWord.getJapaneseReading());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Word not found with id " + id));
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    // search funtion
    public List<Dictionary> searchWord(String searchTerm) {
        String searchPattern = "%" + searchTerm + "%";
        return repository.searchWord(searchPattern);
    }
}
