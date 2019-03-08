/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: HomeController
 * Author:   郭新晔
 * Date:     2018/12/18 0018 15:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.aleaf.model.Detail;
import top.aleaf.model.HostHolder;
import top.aleaf.model.PageBean;
import top.aleaf.model.Summary;
import top.aleaf.service.DetailService;
import top.aleaf.service.SummaryService;
import top.aleaf.util.PhoneInfoUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2018/12/18 0018
 */
@Controller
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private SummaryService summaryService;
    @Autowired
    private DetailService detailService;
    @Autowired
    private PageBean pageBean;
    @Autowired
    private HostHolder hostHolder;

    private List<Summary> getDataList(String keyword, Integer nowPage) {
        this.hostHolder.addSpace("nowPage", nowPage);
        if (keyword == null) {
            pageBean.setPageNum(this.summaryService.getPhoneCount());
            if (nowPage == 0) {
                pageBean.setNowPage(1);
                return this.summaryService.selectPhoneByOffsetAndLimit((pageBean.getNowPage() - 1) * pageBean.getPageSize(), pageBean.getPageSize());
            } else {
                pageBean.setNowPage(nowPage);
                return this.summaryService.selectPhoneByOffsetAndLimit((pageBean.getNowPage() - 1) * pageBean.getPageSize(), pageBean.getPageSize());
            }
        } else {
            pageBean.setPageNum(summaryService.getPhoneByKeywordCount(keyword));
            if (nowPage == 0) {
                pageBean.setNowPage(1);
                return this.summaryService.selectByKeyword(keyword, (pageBean.getNowPage() - 1) * pageBean.getPageSize(), pageBean.getPageSize());
            } else {
                pageBean.setNowPage(nowPage);
                return this.summaryService.selectByKeyword(keyword, (pageBean.getNowPage() - 1) * pageBean.getPageSize(), pageBean.getPageSize());
            }
        }
    }

    /**
     * 首页跳转
     * @param model     SpringMVC的数据模型
     * @return          跳转到home页面
     */
    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET})
    public String index(Model model) {
        List<Summary> summaryList = null;
        try {
            this.hostHolder.addSpace("mode", "page");
            summaryList = getDataList(null, 1);

            pageBean.setPageData(summaryList);
            model.addAttribute("pageBean", pageBean);
            model.addAttribute("hostHolder", hostHolder);
            LOGGER.info("首页数据初始化成功！");
        } catch (Exception e) {
            LOGGER.error("首页数据初始化失败！" + e.getMessage());
        }

        return "home";
    }

    /**
     * 分页的请求
     * @param model     SpringMVC的数据模型
     * @param nowPage   需要显示的为第几页
     * @return          跳转到home页面
     */
    @RequestMapping(path = {"/page/{page}"}, method = {RequestMethod.GET})
    public String page(Model model, @PathVariable("page") int nowPage) {
        List<Summary> summaryList = null;
        try {
            this.hostHolder.addSpace("mode", "page");
            summaryList = getDataList(null, nowPage);

            pageBean.setPageData(summaryList);
            model.addAttribute("pageBean", pageBean);
            model.addAttribute("hostHolder", hostHolder);
            LOGGER.info("首页数据初始化成功！");
        } catch (Exception e) {
            LOGGER.error("首页数据初始化失败！" + e.getMessage());
        }

        return "home";
    }

    /**
     * 通过ID查找智能手机详情-显示详情
     * @param id
     * @param detailId
     * @param model
     * @return
     */
    @RequestMapping(path = {"/detail/{id}/{detailId}"}, method = {RequestMethod.GET})
    public String showDetail(@PathVariable("id") int id, @PathVariable("detailId") int detailId, Model model) {
        try {
            Summary summary = this.summaryService.getPhoneById(id);
            Detail detail = this.detailService.getDetailById(detailId);
            model.addAttribute("summary", summary);
            model.addAttribute("detail", detail);
            LOGGER.info("详情数据初始化成功！");
            return "detail";
        } catch (Exception e) {
            LOGGER.error("详情数据初始化失败！");
        }
        return "/index?nowPage=1";
    }

    /**
     * 主页手机搜索
     * @param model
     * @param keyword   搜索关键字
     * @param nowPage   搜索跳转到第几页
     * @return
     */
    @RequestMapping(value = {"/search/{nowPage}"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String searchData(Model model, @RequestParam("keyword") String keyword, @PathVariable("nowPage") int nowPage) {
        try {
            this.hostHolder.addSpace("mode", "search");
            if (keyword == null) {
                keyword = this.hostHolder.getSpaceValue("search_key").toString();
            }
            this.hostHolder.addSpace("search_key", keyword);
            List<Summary> summaryList = getDataList(keyword, nowPage);

            pageBean.setPageData(summaryList);
            model.addAttribute("pageBean", pageBean);
            model.addAttribute("hostHolder", hostHolder);
            LOGGER.info("数据搜索成功！");
        } catch (Exception e) {
            LOGGER.error("数据搜索失败！" + e.getMessage());
        }

        return "home";
    }

    /**
     * 删除记录
     * @param id
     * @param mode
     * @param nowPage
     * @return
     */
    @RequestMapping(path = {"/delete/{id}"}, method = {RequestMethod.GET})
    public String deletePhone(@PathVariable("id") int id,
                              @RequestParam("mode") String mode, @RequestParam("nowPage") int nowPage) {
        try {
            this.summaryService.deletePhone(id);
            LOGGER.info("数据删除成功！");
        } catch (Exception e) {
            LOGGER.error("数据删除失败！" + e.getMessage());
        }
        if (String.valueOf(mode).equals("page")) {
            return "redirect:/page/" + nowPage;
        } else {
            String keyword = mode.substring(mode.lastIndexOf('@') + 1);
            return "redirect:/search/" + nowPage + "?keyword=" + keyword;
        }
    }

    /**
     * 更新概要数据
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(path = {"/summary/update"}, method = {RequestMethod.POST})
    public String updateSummary(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            String imageUrl = null;
            Summary summary = new Summary();
            summary.setId(Integer.parseInt(request.getParameter("id")));
            summary.setDetailId(Integer.parseInt(request.getParameter("detailId")));
            Summary tmp = this.summaryService.getPhoneById(summary.getId());
            summary.setName(request.getParameter("name"));
            summary.setBrand(request.getParameter("brand"));
            summary.setMemory(request.getParameter("memory"));
            summary.setStatus(1);
            imageUrl = this.summaryService.saveImage(file);
            if (imageUrl == null) {
                imageUrl = tmp.getHeadImg();
            }
            summary.setHeadImg(imageUrl);
            summary.setCpuType(request.getParameter("cpuType"));
            summary.setPrice(Float.parseFloat(request.getParameter("price")));
            summary.setScore(request.getParameter("score"));
            this.summaryService.updatePhone(summary);
            LOGGER.info("数据更新成功！" + summary.getId());
        } catch (Exception e) {
            LOGGER.error("数据更新失败！" + e.getMessage());
        }
        return "redirect:/detail/" + request.getParameter("id") + "/" + request.getParameter("detailId");
    }

    /**
     * 新增概要数据
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(path = {"/summary/add"}, method = {RequestMethod.POST})
    public String addSummary(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            String imageUrl = this.summaryService.saveImage(file);
            Summary summary = new Summary();
            summary.setDetailId(0);
            summary.setName(request.getParameter("name"));
            summary.setBrand(request.getParameter("brand"));
            summary.setMemory(request.getParameter("memory"));
            summary.setStatus(0);
            summary.setHeadImg(imageUrl);
            summary.setCpuType(request.getParameter("cpuType"));
            summary.setPrice(Float.parseFloat(request.getParameter("price")));
            summary.setScore(request.getParameter("score"));
            this.summaryService.addPhone(summary);
            LOGGER.info("数据添加成功！" + summary.getId());
        } catch (Exception e) {
            LOGGER.error("数据添加失败！" + e.getMessage());
        }
        return "redirect:/index";
    }

    /**
     * 新增详情记录
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(path = {"/detail/add"}, method = {RequestMethod.POST})
    public String addDetail(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        int summaryId = 0;
        int detailId = 0;
        try {
            String imageUrl = this.summaryService.saveImage(file);
            Detail detail = new Detail();
            if (request.getParameter("buyDate") != null && !"".equals(request.getParameter("buyDate").trim())) {
                detail.setBuyDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("buyDate")));
            }
            if (request.getParameter("networkType") != null && !"".equals(request.getParameter("networkType").trim())) {
                detail.setNetworkType(request.getParameter("networkType"));
            }
            if (request.getParameter("osType") != null && !"".equals(request.getParameter("osType").trim())) {
                detail.setOsType(request.getParameter("osType"));
            }
            if (request.getParameter("land") != null && !"".equals(request.getParameter("land").trim())) {
                detail.setLand(Float.parseFloat(request.getParameter("land")));
            }
            if (request.getParameter("coreNum") != null && !"".equals(request.getParameter("coreNum").trim())) {
                detail.setCoreNum(request.getParameter("coreNum"));
            }
            if (request.getParameter("powerType") != null && !"".equals(request.getParameter("powerType").trim())) {
                detail.setPowerType(request.getParameter("powerType"));
            }
            if (request.getParameter("ratio") != null && !"".equals(request.getParameter("ratio").trim())) {
                detail.setRatio(request.getParameter("ratio"));
            }
            if (request.getParameter("screenSize") != null && !"".equals(request.getParameter("screenSize").trim())) {
                detail.setScreenSize(Float.parseFloat(request.getParameter("screenSize")));
            }
            if (request.getParameter("fcType") != null && !"".equals(request.getParameter("fcType").trim())) {
                detail.setFcType(request.getParameter("fcType"));
            }
            if (request.getParameter("bcType") != null && !"".equals(request.getParameter("bcType").trim())) {
                detail.setBcType(request.getParameter("bcType"));
            }
            if (request.getParameter("otherFunction") != null && !"".equals(request.getParameter("otherFunction").trim())) {
                detail.setOtherFunction(request.getParameter("otherFunction"));
            }
            detail.setDetailImg(imageUrl);

            this.detailService.addPhoneDetail(detail);
            detailId = detail.getId();
            summaryId = Integer.parseInt(request.getParameter("summaryId"));
            this.summaryService.setDetailId(detailId, summaryId);
            LOGGER.info("数据添加成功！" + detailId);
        } catch (Exception e) {
            LOGGER.error("数据添加失败！" + e.getMessage());
            return "/index";
        }
        return "redirect:/detail/" + summaryId + "/" + detailId;
    }

    /**
     * 更新详情数据
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(path = {"/detail/update"}, method = {RequestMethod.POST})
    public String updateDetail(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        int summaryId = 0;
        int detailId = 0;
        try {
            String imageUrl = null;
            Detail detail = new Detail();
            Detail tmp = new Detail();
            tmp = this.detailService.getDetailById(Integer.parseInt(request.getParameter("id")));
            detail.setId(tmp.getId());
            if (request.getParameter("buyDate") != null && !"".equals(request.getParameter("buyDate").trim())) {
                detail.setBuyDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("buyDate")));
            } else {
                detail.setBuyDate(tmp.getBuyDate());
            }
            if (request.getParameter("networkType") != null && !"".equals(request.getParameter("networkType").trim())) {
                detail.setNetworkType(request.getParameter("networkType"));
            } else {
                detail.setNetworkType(tmp.getNetworkType());
            }
            if (request.getParameter("osType") != null && !"".equals(request.getParameter("osType").trim())) {
                detail.setOsType(request.getParameter("osType"));
            } else {
                detail.setOsType(tmp.getOsType());
            }
            if (request.getParameter("land") != null && !"".equals(request.getParameter("land").trim())) {
                detail.setLand(Float.parseFloat(request.getParameter("land")));
            } else {
                detail.setLand(tmp.getLand());
            }
            if (request.getParameter("coreNum") != null && !"".equals(request.getParameter("coreNum").trim())) {
                detail.setCoreNum(request.getParameter("coreNum"));
            } else {
                detail.setCoreNum(tmp.getCoreNum());
            }
            if (request.getParameter("powerType") != null && !"".equals(request.getParameter("powerType").trim())) {
                detail.setPowerType(request.getParameter("powerType"));
            } else {
                detail.setPowerType(tmp.getPowerType());
            }
            if (request.getParameter("ratio") != null && !"".equals(request.getParameter("ratio").trim())) {
                detail.setRatio(request.getParameter("ratio"));
            } else {
                detail.setRatio(tmp.getRatio());
            }
            if (request.getParameter("screenSize") != null && !"".equals(request.getParameter("screenSize").trim())) {
                detail.setScreenSize(Float.parseFloat(request.getParameter("screenSize")));
            } else {
                detail.setScreenSize(tmp.getScreenSize());
            }
            if (request.getParameter("fcType") != null && !"".equals(request.getParameter("fcType").trim())) {
                detail.setFcType(request.getParameter("fcType"));
            } else {
                detail.setFcType(tmp.getFcType());
            }
            if (request.getParameter("bcType") != null && !"".equals(request.getParameter("bcType").trim())) {
                detail.setBcType(request.getParameter("bcType"));
            } else {
                detail.setBcType(tmp.getBcType());
            }
            if (request.getParameter("otherFunction") != null && !"".equals(request.getParameter("otherFunction").trim())) {
                detail.setOtherFunction(request.getParameter("otherFunction"));
            } else {
                detail.setOtherFunction(tmp.getOtherFunction());
            }

            summaryId = Integer.parseInt(request.getParameter("summaryId"));
            detailId = detail.getId();

            imageUrl = this.summaryService.saveImage(file);
            if (imageUrl == null) {
                imageUrl = tmp.getDetailImg();
            }
            detail.setDetailImg(imageUrl);

            this.detailService.updateDetail(detail);
            LOGGER.info("数据更新成功！");
        } catch (Exception e) {
            LOGGER.error("数据更新失败！" + e.getMessage());
        }
        return "redirect:/detail/" + summaryId + "/" + detailId;
    }

    /**
     * 显示图片链接
     * @param imageName
     * @param response
     */
    @RequestMapping(path = {"/image"}, method = RequestMethod.GET)
    @ResponseBody
    public void getImage(@RequestParam(value = "name") String imageName,
                         HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(new File(PhoneInfoUtil.UPLOAD_IMAGE_PATH + imageName)), response.getOutputStream());
        } catch (Exception e) {
            LOGGER.error("图片加载失败！" + e.getMessage());
        }
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping(path = {"/uploadImage/"}, method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = this.summaryService.saveImage(file);
            if (imageUrl == null) {
                //失败code为1
                return PhoneInfoUtil.getJSONString(1, "图片上传失败！");
            }
            //成功code为0
            return PhoneInfoUtil.getJSONString(0, imageUrl);
        } catch (Exception e) {
            LOGGER.error("图片上传失败！");
            return PhoneInfoUtil.getJSONString(1, "图片上传失败！");
        }
    }

}