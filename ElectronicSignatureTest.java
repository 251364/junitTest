package com.genuine.mes.test;

import cn.hutool.json.JSONUtil;
import com.genuine.mes.system.MesSystemStarter;
import com.genuine.mes.system.entity.ElectronicSignature;
import com.genuine.mes.system.vo.ElectronicSignatureVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Author wwj
 * @Date 2020-12-04 13:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MesSystemStarter.class)
@Slf4j
public class ElectronicSignatureTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testPageOfElectronicSignature() throws Exception {
        ElectronicSignatureVo vo = new ElectronicSignatureVo();
        vo.setModule("用户");
        vo.setName("me");
        vo.setSysAuthorityName("测试");
        vo.setAuthId(39L);

        String param = JSONUtil.toJsonPrettyStr(vo);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/electronic/signature/page/1/10")
                        .content(param)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testPageOfElectronicSignature 返回结果：{}", contentAsString);
    }

    @Test
    public void testGetInfoByName() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/electronic/signature/name")
                        .param("name", "me")
                        .characterEncoding("UTF-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testGetInfoByName 返回结果：{}", contentAsString);
    }

    @Test
    public void testUpdateElectronicSignature() throws Exception {
        //{"orderMap":null,"id":"1","esId":"1","name":"anme","type":1,"revise":"N",
        // "module":"用户管理","createUserId":1,
        // "createTime":"2020-11-21 13:57:27","delFlag":0,"tenantId":"1"}]}
        ElectronicSignature es = new ElectronicSignature();
        es.setId(1L);
        es.setEsId(1L);
        es.setType(1);
        es.setName("测试权限保存1");
        es.setRevise("Y");
        es.setModule("用户测试管理");
        //参数
        String param = JSONUtil.toJsonPrettyStr(es);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/electronic/signature/update")
                        .content(param)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testUpdateElectronicSignature 返回结果：{}", contentAsString);
    }
}
