package test.test.repository;

import org.apache.ibatis.annotations.*;
import test.test.domain.Border;

import java.util.List;

@Mapper
public interface BorderMapper {

    @Insert("insert into border(title, contents) values(#{border.title}, #{border.contents})")
    int Write(@Param("border") Border write);

    @Select("select * from border")
    @Results(id="BorderMap", value={
            @Result(property = "num", column = "num"),
            @Result(property = "title", column = "title")
    })
    List<Border> borderList();


}
