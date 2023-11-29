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
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);


            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);


            em.flush();
            em.clear();

            String query = "select t from Team t join fetch t.members";
            List<Team> result = em.createQuery(query, Team.class)
                    .setFirstResult(0)  // 페치 조인에서 페이징을 하면 경고 로그를 남기고 메모리에서 페이징(위험)
                    .setMaxResults(1)   // WARN: HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!
                    .getResultList();

            for(Team team : result) {
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
