package edu.cit.hapongo.service;

import edu.cit.hapongo.model.LessonContent;
import edu.cit.hapongo.repository.LessonContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonContentService {

    @Autowired
    private LessonContentRepository lessonContentRepository;

    public List<LessonContent> getAllLessonContents() {
        return lessonContentRepository.findAll();
    }

    public Optional<LessonContent> getLessonContentById(int id) {
        return lessonContentRepository.findById(id);
    }

    public LessonContent addLessonContent(LessonContent lessonContent) {
        return lessonContentRepository.save(lessonContent);
    }

    public LessonContent updateLessonContent(int id, LessonContent updatedContent) {
        return lessonContentRepository.findById(id).map(existing -> {
            existing.setLesson(updatedContent.getLesson());
            existing.setJapaneseWord(updatedContent.getJapaneseWord());
            existing.setPronunciation(updatedContent.getPronunciation());
            existing.setEnglishWord(updatedContent.getEnglishWord());
            return lessonContentRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("LessonContent not found with id: " + id));
    }

    public void deleteLessonContent(int id) {
        if (!lessonContentRepository.existsById(id)) {
            throw new RuntimeException("LessonContent not found with id: " + id);
        }
        lessonContentRepository.deleteById(id);
    }

    public List<LessonContent> getLessonContentsByLessonId(int lessonId) {
        return lessonContentRepository.findByLesson_LessonId(lessonId);
    }
}
