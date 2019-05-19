package top.aleaf.controller;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.aleaf.controller.vo.WebResponse;
import top.aleaf.model.enumModel.ChartDataType;
import top.aleaf.model.enumModel.DataChartType;
import top.aleaf.model.enumModel.UserRoleEnum;
import top.aleaf.model.option.Option;
import top.aleaf.service.ApprovalService;
import top.aleaf.service.MessageService;
import top.aleaf.service.ScoreService;
import top.aleaf.service.UserService;
import top.aleaf.utils.BisheUtil;
import top.aleaf.utils.ConstantUtil;
import top.aleaf.utils.DateUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 〈网站前台〉
 *
 * @author 郭新晔
 * @create 2019/5/6 0006
 * Author:   郭新晔
 */
@Controller
public class WebsiteHomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsiteHomeController.class);

    @Resource
    private BaseController baseController;
    @Resource
    private ApprovalService approvalService;
    @Resource
    private ScoreService scoreService;
    @Resource
    private UserService userService;
    @Resource
    private MessageService messageService;

    /**
     * 主页排行榜
     * 文案：我们的成绩（优秀的用户体验）
     *
     * @param model model
     * @return 跳转链接
     */
    @RequestMapping(path = {"/", "/home"})
    public String indexLeaderboard(Model model) {
        try {
            //分数排行榜
            model.addAttribute("scoreTop", this.scoreService.getScoreTop());
            //审核通过项目数量排行榜
            model.addAttribute("numberTop", this.scoreService.getNumberTop());
            //审核排行榜
            model.addAttribute("approvalTop", baseController.numberOfStatisticalReviews());
        } catch (Exception e) {
            LOGGER.error("主页排行榜加载出错");
            e.printStackTrace();
        }
        return "home";
    }

    /**
     * 平台数据显示（运营数据）
     * 文案：我们的优势（全面的数据服务）
     *
     * @return 结果
     */
    @GetMapping("/dataShow")
    @ResponseBody
    public WebResponse<Map<String, String>> platformDataShow() {
        WebResponse<Map<String, String>> result = new WebResponse<>();
        try {
            Map<String, String> data = Maps.newHashMap();

            //日期
            data.put("now", DateUtils.formatDate(DateUtils.YYYY_MM_DD_Z, new Date()));
            //教师数量
            data.put("teacherNum", String.valueOf(this.userService.getAllCount(UserRoleEnum.TEACHER.getId())));
            //科技分总计
            List<Map<String, Double>> typeValueMapList = baseController.projectDataAnalysis();
            double scoreSum = typeValueMapList.get(0).values().stream().mapToDouble(Double::doubleValue).sum();
            data.put("scoreSum", BisheUtil.doubleFormat(scoreSum, ConstantUtil.DOUBLE_PATTERN));
            //项目总数
            data.put("projectNum", ((int) typeValueMapList.get(1).values().stream().mapToDouble(Double::doubleValue).sum()) + "");
            //被审核的项目数量
            data.put("approvalProjectNum", this.approvalService.getAllCount() + "");
            //用户数量
            data.put("userNum", this.userService.getAllCount(-1) + "");
            //消息数量
            data.put("messageNum", this.messageService.getAllCount(-1) + "");

            result.setSuccess(true);
            result.setData(data);
        } catch (Exception e) {
            LOGGER.error("主页平台数据显示加载出错");
            result.setSuccess(false);
            result.setMsg("平台数据显示出错");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 数据统计
     * 文案：我们的特色（依托大数据智能分析）
     *
     * @param type     图表类型
     * @param category 数据类型
     * @return 结果
     */
    @RequestMapping("/dataStatistics")
    @ResponseBody
    public WebResponse<Option> dataStatistics(@RequestParam("type") int type,
                                              @RequestParam("category") int category) {
        WebResponse<Option> result = new WebResponse<>();
        try {
            if (!DataChartType.valueMap.containsKey(type) || !ChartDataType.valueMap.containsKey(category)) {
                result.setSuccess(false);
                result.setMsg("参数错误");
                return result;
            }
            Option option;
            Map<String, Double> map;
            List<Map<String, Double>> typeValueMapList = baseController.projectDataAnalysis();
            if (DataChartType.BAR.getValue() == type) {
                if (ChartDataType.SCORE_ENTITY_TYPE.getValue() == category) {
                    map = typeValueMapList.get(0);
                    option = baseController.homeVisualizationBar(map, ChartDataType.SCORE_ENTITY_TYPE);
                } else {
                    map = typeValueMapList.get(1);
                    option = baseController.homeVisualizationBar(map, ChartDataType.NUMBER_ENTITY_TYPE);
                }
            } else {
                if (ChartDataType.SCORE_ENTITY_TYPE.getValue() == category) {
                    map = typeValueMapList.get(0);
                    option = baseController.homeVisualizationPie(map, ChartDataType.SCORE_ENTITY_TYPE);
                } else {
                    map = typeValueMapList.get(1);
                    option = baseController.homeVisualizationPie(map, ChartDataType.NUMBER_ENTITY_TYPE);
                }
            }
            result.setSuccess(true);
            result.setData(option);
        } catch (Exception e) {
            LOGGER.error("主页数据统计加载出错");
            result.setSuccess(false);
            result.setMsg("数据统计加载出错");
            e.printStackTrace();
        }
        return result;
    }

}