package test.test.repository;

import test.test.domain.Border;

import java.util.List;

public interface BorderRepository {

    List<Border> borderList(String title); // 게시판 리스트
    Border write(Border border); //글쓰기
    Object borderRead(int num); // 해당 글 읽기


}
