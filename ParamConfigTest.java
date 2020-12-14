package com.genuine.mes.test;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.genuine.mes.system.MesSystemStarter;
import com.genuine.mes.system.service.ParamConfigService;
import com.genuine.mes.system.vo.ParamConfigVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MesSystemStarter.class)
@Slf4j
public class ParamConfigTest {

    @Autowired
    private ParamConfigService paramConfigService;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        //MockMvcBuilders.webAppContextSetup(WebApplicationContext context)：指定WebApplicationContext，将会从该上下文获取相应的控制器并得到相应的MockMvc；
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }

    @Test
    public void testSave() {
        ParamConfigVo byParamName = paramConfigService.findByParamName("Parameter_Group");
        System.out.println(byParamName);
    }

    @Test
    public void testPageParamConfig() throws Exception {
        ParamConfigVo paramConfigVo = new ParamConfigVo();
        paramConfigVo.setParamName("test");
        paramConfigVo.setParamType("String");
        paramConfigVo.setEnableStatus("Y");
        paramConfigVo.setParamVersion("1.0");

        String param = JSONUtil.toJsonPrettyStr(paramConfigVo);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/paramConfig/1/10")
                        .content(param)
                        .characterEncoding("utf-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //返回值
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testPageParamConfig 返回结果：{}", contentAsString);
    }

    @Test
    public void testSaveParamConfig() throws Exception {
        ParamConfigVo paramConfigVo = new ParamConfigVo();
        paramConfigVo.setParamName("test");
        paramConfigVo.setParamType("String");
        paramConfigVo.setEnableStatus("Y");
        paramConfigVo.setParamVersion("1.0");
        paramConfigVo.setParamValue("");
        paramConfigVo.setRemark1("setRemark1");
        paramConfigVo.setRemark2("vsetRemark2");
        paramConfigVo.setParamGroupName("ceshi");

        String param = JSONUtil.toJsonPrettyStr(paramConfigVo);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/paramConfig")
                        .content(param)
                        .characterEncoding("utf-8")
                        .contentType("application/json")
                        .accept("application/json;charset=UTF-8")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testSaveParamConfig 返回结果：{}", contentAsString);
    }

    @Test
    public void testSelectByParamName() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/paramConfig/name/Default_Accuracy")
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testSelectByParamName 返回结果：{}", contentAsString);
    }

    @Test
    public void testSelectByIdParamConfig() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/paramConfig/id/40")
                                .characterEncoding("UTF-8")
                                .contentType("application/json")
                                .accept("application/json;charset=UTF-8")
//                        // 设置返回值类型为utf-8，否则默认为ISO-8859-1
//                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info("testSelectByIdParamConfig 返回结果：{}", contentAsString);
    }

    /**
     * 1、mockMvc.perform执行一个请求。
     * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
     * 3、ResultActions.param添加请求传值
     * 4、ResultActions.accept(MediaType.TEXT_HTML_VALUE))设置返回类型
     * 5、ResultActions.andExpect添加执行完成后的断言。
     * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
     * 比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
     * 5、ResultActions.andReturn表示执行完成后返回相应的结果。
     */

    @Test
    public void testUploadExel() throws Exception {
//		String path = "E:\\MES\\MES20201128\\00_2_1.系统参数矩阵.xlsx";
        String path = "F:\\00_2_1.系统参数矩阵.xlsx";
        File file = new File(path);
        log.info("----------------------------------------------{}", file.exists());
//		InputStream iso = this.getClass().getClassLoader().getResourceAsStream(path);
        InputStream is = ResourceUtil.getStream(path);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "00_2_1.系统参数矩阵.xlsx", "xlsx", is);

        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.multipart("/paramConfig/upload/excel")
                        .file(mockMultipartFile)
                        .param("type", "0")
                        //设置返回类型
                        .accept("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String respResult = mvcResult.getResponse().getContentAsString();
        log.info("testUploadExel 返回结果：{}", respResult);
    }
}
