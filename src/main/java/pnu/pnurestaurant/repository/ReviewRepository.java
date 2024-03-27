package pnu.pnurestaurant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pnu.pnurestaurant.domain.Review;

import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r" +
            " join fetch r.member" +
            " join fetch r.restaurant" +
            " where r.id = :id")
    Optional<Review> findByIdWithAllRelation(@Param("id") Long id);


    @Query("select r from Review r" +
            " join fetch r.member" +
            " join fetch r.restaurant rr" +
            " where rr.id = :id")
    Page<Review> findReviewsByRestaurantId(@Param("id") Long id, Pageable pageable);

}
