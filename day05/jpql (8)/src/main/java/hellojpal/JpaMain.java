package hellojpal;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            em.createQuery("select o.address from Order o", Address.class).getResultList();

            tx.commit();
        }
        catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            //영속성 컨텍스트를 종료
            em.close();
        }
        emf.close();
    }



}
