package cn.net.wujun.springboot.demo.dao;

import cn.net.wujun.springboot.demo.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by jasonwu on 2017/02/17.
 */
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO tbl_user(id, name, sex, gmt_create) VALUES(#{id}, #{name}, #{sex}, " +
            "#{gmtCreate})")
    int insert(User user);

    @Update("UPDATE tbl_user SET name=#{name}, sex=#{sex}, gmt_create=#{gmtCreate} WHERE id=#{id}")
    int update(User user);

    @Delete("DELETE FROM tbl_user WHERE id = #{id}")
    int delete(Long id);

    @Results({
            @Result(property = "gmtCreate", column = "gmt_create")
    })
    @Select("SELECT * FROM tbl_user WHERE id = #{id}")
    User getById(Long id);

    @Results({
            @Result(property = "gmtCreate", column = "gmt_create")
    })
    @Select("SELECT * FROM tbl_user")
    List<User> selectPage();

    @Select("SELECT count(id) FROM tbl_user")
    int count();
}
