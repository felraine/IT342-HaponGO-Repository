package edu.cit.hapongo.repository;

import edu.cit.hapongo.model.Dictionary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

    @Query(value = "SELECT * FROM db_felrainekylea_69912.dictionary LIMIT 10", nativeQuery = true)
    List<Dictionary> findTop10();
}
