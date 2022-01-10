package test.test.repository;

import test.test.domain.Border;

import java.util.List;

public interface BorderRepository {

    Border write(Border border); //글쓰기
    List<Border> borderList(); //글 리스트

    Object borderRead(int num); // 해당 글 읽기
    List<Border> borderList(String title); // 글 제목으로 검색

}
