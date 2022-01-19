package test.test.service;

import test.test.domain.Border;
import test.test.repository.BorderRepository;

import java.util.List;
import java.util.Optional;

public class BorderService {

    private final BorderRepository borderRepository;

    public BorderService(BorderRepository borderRepository) {
        this.borderRepository = borderRepository;
    }

    /**
     * 글 목록 & 검색
     */

    public List<Border> ListBorder(String title){
        return borderRepository.borderList(title);
    }


    /**
     * 글쓰기
     */
    public int write(Border border){
        borderRepository.write(border);
        return border.getNum();
    }


    /**
     * 글 읽기
     */
    public Optional<Border> read(int num){
       Object borderRead = borderRepository.borderRead(num);
//        return borderRead;
        return (Optional<Border>) borderRead;
    }

}
