package xyz.cychen.chulichuli.browser.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface VideoRepository extends CrudRepository<VideoEntry, Integer> {

    @Query("SELECT CASE WHEN count(video) > 0 THEN true ELSE false END FROM VideoEntry video WHERE video.fileName = :fileName")
    @Transactional(readOnly = true)
    boolean existsByFileName(@Param("fileName") String fileName);
//    @Query("SELECT video FROM VideoEntry video WHERE video.displayName = :displayName")
//    @Transactional(readOnly = true)
//    Collection<VideoEntry> findByDisplayName(@Param("displayName") String displayName);

//    @Query("SELECT video.displayName FROM VideoEntry  video WHERE video.id = :id")
//    @Transactional(readOnly = true)
//    Optional<String> findDisplayName(@Param("id") Integer id);
}
