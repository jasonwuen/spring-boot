package cn.net.wujun.springboot.demo.dao;

import cn.net.wujun.springboot.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by jasonwu on 2017/02/17.
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM tbl_user WHERE id = #{ id }")
    User getById(Long id);
}
