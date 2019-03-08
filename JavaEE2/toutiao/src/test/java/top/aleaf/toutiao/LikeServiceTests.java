/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: LikeServiceTests
 * Author:   郭新晔
 * Date:     2019/1/13 0013 20:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 〈〉
 * 1. 初始化数据
 * 2. 执行要测试的业务
 * 3. 验证测试的数据
 * 4. 清理数据
 * @create 2019/1/13 0013
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
@WebAppConfiguration
public class LikeServiceTests {
    @Test
    public void testLikeA(){
        System.out.println("testLikeA");
    }

    @Test
    public void testLikeB(){
        System.out.println("testLikeB");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testException(){
        //异常测试
        throw new IndexOutOfBoundsException("超出");
    }

    //压力测试
    //软件压力测试的基本思路很简单：
    // 不是在常规条件下运行手动或自动测试，
    // 而是在计算机数量较少或系统资源匮乏的条件下运行测试。
    // 通常要进行软件压力测试的资源包括内部内存、CPU 可用性、磁盘空间和网络带宽。

    @Before
    public void setUp(){
        //初始化数据
        System.out.println("setUp");
    }

    @After
    public void tearDown(){
        //收尾工作
        System.out.println("tearDown");
    }

    @BeforeClass
    public static void beforeClass(){
        System.out.println("beforeClass");
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("afterClass");
    }
}