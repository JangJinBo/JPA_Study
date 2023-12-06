package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{

            Team team = new Team();
            team.setName("TeamA");
//            team.getMembers().add(member);
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team);
            em.persist(member);

//            team.getMembers().add(member);  // 양방향 연관관계 시 양쪽에 다 값을 세팅 해 줘야한다(changeTeam에서 세팅)

//            em.flush();
//            em.clear();   // 이 경우에는 1차캐시에 아무것도 없어서 select를 다시 하지만

            Team findTeam = em.find(Team.class, team.getId());  // 1차 캐시
            List<Member> members = findTeam.getMembers();

            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

            tx.commit();
            }
        catch (Exception e){
            tx.rollback();
        }finally {
            //영속성 컨텍스트를 종료
            em.close();

        }

        emf.close();
    }
}
