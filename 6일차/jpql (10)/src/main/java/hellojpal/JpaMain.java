package hellojpal;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{

            Team teamA = new Team();
            teamA.setName("TeamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);


            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamA);

            Member member3 = new Member();
            member3.setUsername("member2");
            member3.setTeam(teamB);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);


            em.flush();
            em.clear();

            String query = "select t from Team t join fetch t.members";
            List<Team> teams = em.createQuery(query, Team.class)
                    .getResultList();

            for(Team team : teams) {
                System.out.println("teamname = " + team.getName() + ", team = " + team);
                for (Member member : team.getMembers()) {
                    //페치 조인으로 팀과 회원을 함께 조회해서 지연 로딩 발생 안함
                    System.out.println("-> username = " + member.getUsername() + ", member = " + member);
                }
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
