package com.genuine.mes.test;

/**
 * @Author wwj
 * @Date 2020-12-07 09:20
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
public class SystemUserGroupTest extends BaseService {

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
    public void testQueryGroupPage() throws Exception {
        String example = "{\"groupCode\":\"rtt\"}";
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/sysgroup/page/1/10")
                        .content(example)
                        .characterEncoding("utf-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testQueryGroupPage 返回结果：{}", contentAsString);
    }

    //根据用户组名查询用户组信息
    @Test
    public void testQueryGroupByGroupName() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/sysgroup/select/测试")
                        .characterEncoding("utf-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testQueryGroupByGroupName 返回结果：{}", contentAsString);
    }

    //新增用户组
    @Test
    public void testSave() throws Exception {
        String example = "{\"groupCode\":\"  www\",\"groupName\":\"sss\",\"authorityIds\":[1330739330179796992,1330739330334986240]}";
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/sysgroup/addGroup")
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

    //修改用户组信息
    @Test
    public void testUpdate() throws Exception {
        String example = "{\"groupId\":\"1335775209898250240\",\"groupCode\":\"  www   \",\"groupName\":\"修改\",\"authorityIds\":[1330739330179796992]}";
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/sysgroup/updateGroup")
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

    //删除用户组
    @Test
    public void testDelete() throws Exception {
        String example = "{\"groupId\":1335775209898250240}";
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/sysgroup/deleteGroup")
                        .content(example)
                        .characterEncoding("utf-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testDelete 返回结果：{}", contentAsString);
    }

}
