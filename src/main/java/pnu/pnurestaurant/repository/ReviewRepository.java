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

    public Review findById(Long id){
        return em.find(Review.class, id);
    }

    public Review findByIdtoOne(Long id){
        return em.createQuery("select r from Review r" +
                " join fetch r.member" +
                " join fetch r.restaurant" +
                " where r.id = :id", Review.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public void deleteById(Long id){
        em.createQuery("delete from Review r where r.id = :id")
                .setParameter("id",id)
                .executeUpdate();
    }
}
