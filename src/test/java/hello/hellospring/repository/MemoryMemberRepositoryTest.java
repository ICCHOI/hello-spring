package hello.hellospring.repository;

import static org.assertj.core.api.Assertions.assertThat;

import hello.hellospring.domain.Member;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    void afterEach() {
        repository.clearStore();
    }

    @Test
    void save_test() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Member savedMember = repository.save(member);

        //then
        assertThat(member.getId()).isEqualTo(savedMember.getId());
    }

    @Test
    void findById_test() {
        //given
        Member member = new Member();
        member.setName("spring");
        repository.save(member);

        //when
        Optional<Member> byId = repository.findById(member.getId());

        //then
        assertThat(byId.orElseThrow().getId()).isEqualTo(member.getId());
    }

    @Test
    void findByName_test() {
        //given
        Member member = new Member();
        member.setName("spring");
        repository.save(member);

        //when
        Optional<Member> byName = repository.findByName("spring");

        //then
        assertThat(byName.orElseThrow().getName()).isEqualTo(member.getName());
    }

    @Test
    void findAll_test() {
        //given
        Member member = new Member();
        member.setName("spring");
        repository.save(member);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> memberList = List.of(member, member2);

        //when
        List<Member> all = repository.findAll();

        //then
        assertThat(all).isEqualTo(memberList);
    }
}