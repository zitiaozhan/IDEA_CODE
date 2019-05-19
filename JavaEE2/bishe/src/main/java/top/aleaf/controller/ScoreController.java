package top.aleaf.controller;

import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.aleaf.model.*;
import top.aleaf.model.enumModel.UserRoleEnum;
import top.aleaf.service.ScoreService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/12 0012
 */
@Controller
public class ScoreController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ScoreController.class);
    @Resource
    private ScoreService scoreService;
    @Resource
    private HostHolder hostHolder;

    @RequestMapping(path = {"/score"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          Score score, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null || UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }
            List<Score> scoreList;
            if (UserRoleEnum.TEACHER.getValue().equals(localRole.getDetail())) {
                score.setUserNumber(localUser.getNumber());
            }
            scoreList = this.scoreService.getAll(score);

            ViewObject vo = new ViewObject();
            vo.set("scoreList", scoreList);
            vo.set("nowDate", new Date());
            model.addAttribute("vo", vo);

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(scoreList));
            model.addAttribute("score", score);

            if (msg != null) {
                model.addAttribute("msg", msg);
            }
        } catch (Exception e) {
            LOGGER.error("科技分列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/score";
    }

    @RequestMapping(path = {"/score/edit"}, method = RequestMethod.POST)
    public String editScore(Score score, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }
            boolean isSave = score.getId() == null;
            msg = this.scoreService.save(score) ?
                    (isSave ? "添加成功!" : "更新成功!") :
                    (isSave ? "添加失败!" : "更新失败!");
        } catch (Exception e) {
            LOGGER.error("科技分编辑出错");
            msg = "科技分编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/score";
    }

    @RequestMapping(path = {"/score/forEdit"})
    public String forUpdate(@RequestParam(value = "scoreId", required = false) Integer scoreId,
                            Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }
            if (scoreId != null) {
                Score score = this.scoreService.getByPrimaryKey(scoreId);
                model.addAttribute("score", score);
            }
        } catch (Exception e) {
            LOGGER.error("科技分数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/scoreEdit";
    }

    @RequestMapping(path = {"/score/delete/{scoreId}"})
    public String delete(@PathVariable("scoreId") int scoreId, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }
            msg = this.scoreService.delete(scoreId) ? "删除成功" : "删除失败";
        } catch (Exception e) {
            LOGGER.error("科技分删除出错");
            msg = "科技分删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/score";
    }

}