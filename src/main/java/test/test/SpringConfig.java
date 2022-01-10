package test.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.test.contoroller.MemberController;
import test.test.repository.*;
import test.test.service.BorderService;
import test.test.service.MemberService;

import javax.sql.DataSource;

//스프링빈에 등록
@Configuration
public class SpringConfig {

    //DataSource 사용시 설정
    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    //

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public BorderService borderService() {
        return new BorderService(borderRepository());
    }

    @Bean
    public BorderRepository borderRepository() {
        return new JdbcBorderRepository(dataSource);
    }


    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository(); // 내부 메모리에서 db 돌림
        return new JdbcMemberRepository(dataSource); //Jdbc 메모리 사용
    }
}



