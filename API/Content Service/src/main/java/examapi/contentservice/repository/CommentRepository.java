package examapi.contentservice.repository;

import examapi.contentservice.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment as c where c.post.id = :postId and c.approved = true")
    List<Comment> getCommentsForPost(long postId);

    //Needed just for testing
    Optional<Comment> findByContent(String content);

}
