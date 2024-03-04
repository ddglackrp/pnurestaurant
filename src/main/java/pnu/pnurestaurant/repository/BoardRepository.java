package pnu.pnurestaurant.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.Board;
import pnu.pnurestaurant.domain.restaurant.FoodType;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    @Transactional
    public Long save(Board board){
        em.persist(board);
        return board.getId();
    }
    public Board findOne(Long id){
        return em.find(Board.class, id);
    }

    public List<Board> findAll(){
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    public List<Board> findAllWithRestaurant(){
        return em.createQuery("select b from Board b" +
                " join fetch b.restaurant", Board.class)
                .getResultList();
    }



}
