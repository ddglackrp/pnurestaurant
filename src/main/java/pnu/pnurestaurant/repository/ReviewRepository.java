package pnu.pnurestaurant.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.Review;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewRepository {

    private final EntityManager em;

    @Transactional
    public Long save(Review review){
        em.persist(review);
        return review.getId();
    }
}
