package edu.cit.hapongo.controller;

import edu.cit.hapongo.model.Dictionary;
import edu.cit.hapongo.service.DictionaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService service;

    // Temporary Endpoint for testing
    @GetMapping("/getTenWords")
    public List<Dictionary> getAllWords() {
        return service.findTop10();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dictionary> getWordById(@PathVariable long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}")
    public Dictionary createWord(@RequestBody Dictionary word) {
        return service.save(word);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dictionary> updateWord(@PathVariable long id, @RequestBody Dictionary word) {
        try {
            return ResponseEntity.ok(service.update(id, word));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWord(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Dictionary> searchDictionary(@RequestParam String searchTerm) {
        return service.searchWord(searchTerm);
    }
}
