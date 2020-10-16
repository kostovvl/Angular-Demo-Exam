package examapi.contentservice.repository;

import examapi.contentservice.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post as p where p.approved = true")
    List<Post> findAllApproved();

    @Query("select p from Post as p where p.approved = true and p.category.id = :categoryId")
    List<Post> findAllApprovedByCategory(long categoryId);

    Optional<Post> findByTitle(String title);

}
