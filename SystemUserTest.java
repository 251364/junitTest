package com.genuine.mes.test;

/**
 * @Author wwj
 * @Date 2020-12-04 09:20
 */

import com.genuine.mes.system.MesSystemStarter;
import com.genuine.mes.system.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MesSystemStarter.class)
@Slf4j
public class SystemUserTest extends BaseService {

    @Autowired
    private WebApplicationContext webApplicationContext;//通过它获取需要的类实例
    private MockMvc mockMvc;


    //初始化
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }

    //分页
    @Test
    public void testQueryUserByPage() throws Exception {
        String example = "{\"groupName\":\"测\"}";
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/sysuser/page/1/10")
                        .content(example)
                        .characterEncoding("utf-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testQueryUserByPage 返回结果：{}", contentAsString);

    }

    //根据用户名查询
    @Test
    public void testQueryUserByUserAccount() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/sysuser/select/123")
                        .characterEncoding("utf-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testQueryUserByUserAccount 返回结果：{}", contentAsString);

    }

    //用户id查询关联的用户组信息
    @Test
    public void testQueryGroupByUserId() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/sysuser/selectRelation/1")
                        .characterEncoding("utf-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testQueryGroupByUserId 返回结果：{}", contentAsString);

    }

    //新增用户
    @Test
    public void testSave() throws Exception {
        String example = "{\"userAccount\":\" 456\",\"fullName\":\"依依\",\"language\":\"汉语\",\"notPwdNum\":\"10\",\"loginValidityTime\":\"8\",\"warnBeforeValidity\":\"2\",\"allowLoginTimes\":\"3\",\"groupIds\":[111,222]}";
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/sysuser/addUser")
                        .content(example)
                        .characterEncoding("utf-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testSave 返回结果：{}", contentAsString);

    }

    //修改用户信息
    @Test
    public void testUpdate() throws Exception {
        String example = "{\"userId\":1335088023607578624,\"userAccount\":\" 456\",\"states\":0,\"fullName\":\"王依依\",\"language\":\"汉语\",\"notPwdNum\":\"10\",\"loginValidityTime\":\"8\",\"warnBeforeValidity\":\"2\",\"allowLoginTimes\":\"3\",\"groupIds\":[111,222,333]}";
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/sysuser/updateUser")
                        .content(example)
                        .characterEncoding("utf-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testUpdate 返回结果：{}", contentAsString);

    }

    //启用，禁用(更改状态)
    @Test
    public void testUpdateStates() throws Exception {
        String example = "[{\"userAccount\":\"ttt\"},{\"userAccount\":\"kkk\"}]";
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/sysuser/updateStates/0")
                        .content(example)
                        .characterEncoding("utf-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testUpdateStates 返回结果：{}", contentAsString);

    }

    //初始化密码
    @Test
    public void testResetPwd() throws Exception {
        String example = "[{\"userAccount\":\"kkk\"},{\"userAccount\":\"ttt\"}]";
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/sysuser/reset/pwd")
                        .content(example)
                        .characterEncoding("utf-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testResetPwd 返回结果：{}", contentAsString);

    }

    //添加用户组关联
    @Test
    public void testRelationGroup() throws Exception {
        String example = "[{\"userId\":1,\"userAccount\":123,\"groupRelationIds\":[111,222,333],\"cancelRelationIds\":[222]},{\"userId\":2,\"userAccount\":\"kkk\",\"groupRelationIds\":[111,222,333,444],\"cancelRelationIds\":[111,222,333]}]";
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/sysuser/relationGroup")
                        .content(example)
                        .characterEncoding("utf-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testRelationGroup 返回结果：{}", contentAsString);

    }
}
