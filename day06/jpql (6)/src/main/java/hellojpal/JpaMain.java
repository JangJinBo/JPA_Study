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

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(10);

            em.persist(member1);
            em.persist(member2);


            em.flush();
            em.clear();

            String query = "select group_concat(m.username) from Member m";
            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }



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
