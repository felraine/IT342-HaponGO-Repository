package edu.cit.hapongo.controller;

import edu.cit.hapongo.model.LessonContent;
import edu.cit.hapongo.service.LessonContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesson-contents")
public class LessonContentController {

    @Autowired
    private LessonContentService lessonContentService;

    // Get all LessonContents
    // This endpoint retrieves all lesson contents from the database.
    @GetMapping
    public List<LessonContent> getAllLessonContents() {
        return lessonContentService.getAllLessonContents();
    }

    // Get a LessonContent by its ID
    // This endpoint retrieves a specific lesson content by its ID.
    @GetMapping("/{id}")
    public LessonContent getLessonContentById(@PathVariable int id) {
        return lessonContentService.getLessonContentById(id)
                .orElseThrow(() -> new RuntimeException("LessonContent not found with id: " + id));
    }

    // Create a new LessonContent
    // This endpoint creates a new lesson content in the database.
    @PostMapping
    public LessonContent createLessonContent(@RequestBody LessonContent lessonContent) {
        return lessonContentService.addLessonContent(lessonContent);
    }

    // Update an existing LessonContent
    // This endpoint updates an existing lesson content in the database.
    @PutMapping("/{id}")
    public LessonContent updateLessonContent(@PathVariable int id, @RequestBody LessonContent updatedContent) {
        return lessonContentService.updateLessonContent(id, updatedContent);
    }

    // Delete a LessonContent by its ID
    // This endpoint deletes a specific lesson content by its ID.
    @DeleteMapping("/{id}")
    public String deleteLessonContent(@PathVariable int id) {
        lessonContentService.deleteLessonContent(id);
        return "LessonContent with id " + id + " has been deleted.";
    }

    // Get all LessonContents by lessonId
    // This endpoint retrieves all lesson contents associated with a specific lesson ID.
    @GetMapping("/lesson/{lessonId}")
    public List<LessonContent> getLessonContentsByLessonId(@PathVariable int lessonId) {
        return lessonContentService.getLessonContentsByLessonId(lessonId);
    }
}
