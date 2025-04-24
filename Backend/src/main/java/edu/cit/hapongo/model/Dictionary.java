package edu.cit.hapongo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dictionary")
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "english_word", nullable = false)
    private String englishWord;

    @Column(name = "japanese_kanji", nullable = false)
    private String japaneseKanji;

    @Column(name = "japanese_reading", nullable = false)
    private String japaneseReading;

    // Constructors
    public Dictionary() {}

    public Dictionary(String englishWord, String japaneseKanji, String japaneseReading) {
        this.englishWord = englishWord;
        this.japaneseKanji = japaneseKanji;
        this.japaneseReading = japaneseReading;
    }

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getJapaneseKanji() {
        return japaneseKanji;
    }

    public void setJapaneseKanji(String japaneseKanji) {
        this.japaneseKanji = japaneseKanji;
    }

    public String getJapaneseReading() {
        return japaneseReading;
    }

    public void setJapaneseReading(String japaneseReading) {
        this.japaneseReading = japaneseReading;
    }
}
