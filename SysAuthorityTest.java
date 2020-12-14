package com.genuine.mes.test;

import cn.hutool.json.JSONUtil;
import com.genuine.mes.system.MesSystemStarter;
import com.genuine.mes.system.entity.SysAuthority;
import com.genuine.mes.system.vo.SysAuthorityVo;
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
 * @Date 2020-12-04 09:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MesSystemStarter.class)
@Slf4j
public class SysAuthorityTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testPageOfSysAuthority() throws Exception {
        SysAuthorityVo sysAuthorityVo = new SysAuthorityVo();
        sysAuthorityVo.setModule("用户");

        String param = JSONUtil.toJsonPrettyStr(sysAuthorityVo);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/sys/authority/page/1/10")
                        .content(param)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testPageOfSysAuthority 返回结果：{}", contentAsString);
    }

    @Test
    public void testSelectByAuthorityId() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/sys/authority/authorityId/4")
                        .characterEncoding("UTF-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testSelectByAuthorityId 返回结果：{}", contentAsString);
    }

    @Test
    public void testSaveSysAuthority() throws Exception {
        SysAuthority sysAuthority = new SysAuthority();
        sysAuthority.setAuthId(39999);
        sysAuthority.setName("测试权限保存");
        sysAuthority.setSourceType(0);
        sysAuthority.setValidate("Y");
        sysAuthority.setRevise("Y");
        sysAuthority.setModule("用户测试管理");
        sysAuthority.setRemark("备注");
        //参数
        String param = JSONUtil.toJsonPrettyStr(sysAuthority);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/sys/authority/save")
                        .content(param)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testSaveSysAuthority 返回结果：{}", contentAsString);
    }

    @Test
    public void testValidByAuthId() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/sys/authority/valid/authId")
                        .characterEncoding("UTF-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testValidByAuthId 返回结果：{}", contentAsString);
    }

    @Test
    public void testUpdateSysAuthority() throws Exception {
        SysAuthority sysAuthority = new SysAuthority();
        sysAuthority.setId(36L);
        sysAuthority.setAuthorityId(1334719839612309504L);
        sysAuthority.setAuthId(39999);
        sysAuthority.setName("测试权限保存1");
        sysAuthority.setSourceType(0);
        sysAuthority.setValidate("N");
        sysAuthority.setRevise("Y");
        sysAuthority.setModule("用户测试管理");
        sysAuthority.setRemark("备注1");
        //参数
        String param = JSONUtil.toJsonPrettyStr(sysAuthority);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/sys/authority/update")
                        .content(param)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testUpdateSysAuthority 返回结果：{}", contentAsString);
    }

    @Test
    public void testGetByEsId() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/sys/authority/esId/1")
                        .characterEncoding("UTF-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testGetByEsId 返回结果：{}", contentAsString);
    }
}
