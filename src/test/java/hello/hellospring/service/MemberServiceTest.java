package hello.hellospring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    void tearDown() {
        memberRepository.clearStore();
    }

    @Test
    void join() throws Exception {
        //Given
        Member member = new Member();
        member.setName("spring");

        //When
        Long savedId = memberService.join(member);

        //Then
        Member findMember = memberRepository.findById(savedId).orElseThrow();
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void 중복회원예외() throws Exception {
        //Given
        Member member = new Member();
        member.setName("spring");

        Member member1 = new Member();
        member1.setName("spring");

        memberService.join(member);

        //When
        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> memberService.join(member1));

        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findMembers() {
        //Given
        Member member = new Member();
        member.setName("spring");
        memberService.join(member);

        //When
        List<Member> members = memberService.findMembers();

        //Then
        assertThat(List.of(member)).isEqualTo(members);
    }

    @Test
    void findOne() {
        //Given
        Member member = new Member();
        member.setName("spring");
        memberService.join(member);

        //When
        Member member1 = memberService.findOne(member.getId()).orElseThrow();

        //Then
        assertThat(member1).isEqualTo(member);
    }
}