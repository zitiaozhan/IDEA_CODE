package top.aleaf.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import top.aleaf.controller.vo.EntityProjectVO;
import top.aleaf.model.*;
import top.aleaf.model.enumModel.ChartDataType;
import top.aleaf.model.enumModel.EntityType;
import top.aleaf.model.option.*;
import top.aleaf.service.*;
import top.aleaf.sync.EventModel;
import top.aleaf.sync.EventProducer;
import top.aleaf.sync.EventType;
import top.aleaf.utils.BisheUtil;
import top.aleaf.utils.ConstantUtil;
import top.aleaf.utils.DateUtils;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 〈基础Controller提供公用方法〉
 *
 * @create 2019/5/4 0004
 * Author:   郭新晔
 */
@Controller
public class BaseController {
    @Resource
    private CompanyProjectService companyProjectService;
    @Resource
    private GovernmentProjectService governmentProjectService;
    @Resource
    private PrizeService prizeService;
    @Resource
    private TeachingMaterialService teachingMaterialService;
    @Resource
    private PatentService patentService;
    @Resource
    private SoftwareWorkService softwareWorkService;
    @Resource
    private LectureService lectureService;
    @Resource
    private AcademicExchangeService academicExchangeService;
    @Resource
    private PaperService paperService;

    @Resource
    private EventProducer eventProducer;
    @Resource
    private ApprovalService approvalService;
    @Resource
    private UserService userService;
    @Resource
    private HostHolder hostHolder;

    /**
     * 生成EventModel并发送到消息队列
     *
     * @param entityId   项目id
     * @param entityType 项目类型
     * @param option     审批意见
     * @param status     项目更新后状态
     * @param eventType  事件类型
     */
    public void generalEventModelAndSend(int entityId, EntityType entityType, String option, int status, EventType eventType) {
        User localUser = hostHolder.getUser();
        if (localUser == null) {
            return;
        }
        //提取各项目不同的数据信息
        String projectName = "ERROR";
        String projectUrl = "";
        int createdId = 1;
        switch (entityType) {
            case COMPANY_PROJECT:
                CompanyProject companyProject = this.companyProjectService.getByPrimaryKey(entityId);
                projectName = companyProject.getName();
                projectUrl = "/companyProject?id=" + entityId;
                createdId = companyProject.getCreatedId();
                break;
            case GOVERNMENT_PROJECT:
                GovernmentProject governmentProject = this.governmentProjectService.getByPrimaryKey(entityId);
                projectName = governmentProject.getName();
                projectUrl = "/governmentProject?id=" + entityId;
                createdId = governmentProject.getCreatedId();
                break;
            case PRIZE:
                Prize prize = this.prizeService.getByPrimaryKey(entityId);
                projectName = prize.getName();
                projectUrl = "/prize?id=" + entityId;
                createdId = prize.getCreatedId();
                break;
            case TEACHING_MATERIAL:
                TeachingMaterial teachingMaterial = this.teachingMaterialService.getByPrimaryKey(entityId);
                projectName = teachingMaterial.getName();
                projectUrl = "/teachingMaterial?id=" + entityId;
                createdId = teachingMaterial.getCreatedId();
                break;
            case PATENT:
                Patent patent = this.patentService.getByPrimaryKey(entityId);
                projectName = patent.getName();
                projectUrl = "/patent?id=" + entityId;
                createdId = patent.getCreatedId();
                break;
            case SOFTWARE_WORK:
                SoftwareWork softwareWork = this.softwareWorkService.getByPrimaryKey(entityId);
                projectName = softwareWork.getName();
                projectUrl = "/softwareWork?id=" + entityId;
                createdId = softwareWork.getCreatedId();
                break;
            case LECTURE:
                Lecture lecture = this.lectureService.getByPrimaryKey(entityId);
                projectName = lecture.getName();
                projectUrl = "/lecture?id=" + entityId;
                createdId = lecture.getCreatedId();
                break;
            case ACADEMIC_EXCHANGE:
                AcademicExchange academicExchange = this.academicExchangeService.getByPrimaryKey(entityId);
                projectName = academicExchange.getMeetingName();
                projectUrl = "/academicExchange?id=" + entityId;
                createdId = academicExchange.getCreatedId();
                break;
            case PAPER:
                Paper paper = this.paperService.getByPrimaryKey(entityId);
                projectName = paper.getName();
                projectUrl = "/paper?id=" + entityId;
                createdId = paper.getCreatedId();
                break;
            default:
        }
        //填充数据
        EventModel eventModel = new EventModel();
        eventModel.setActorId(localUser.getId());
        eventModel.setEntityId(entityId);
        eventModel.setEntityType(entityType);
        eventModel.setEventOwnerId(createdId);
        eventModel.setEventType(eventType);
        eventModel.addExt("resultStatus", String.valueOf(status));
        eventModel.addExt("projectName", projectName);
        eventModel.addExt("projectUrl", projectUrl);
        if (null != option) {
            eventModel.addExt("option", option);
        }
        this.eventProducer.fireEvent(eventModel);
    }

    /**
     * 获取最近用户发布的项目
     *
     * @param userId 用户id
     * @return 项目详情
     */
    public List<EntityProjectVO> recentlyReleased(int userId) {
        List<EntityProjectVO> projectVOS = Lists.newArrayList();
        List<EntityProjectVO> result;
        //COMPANY_PROJECT
        this.companyProjectService.getByCreatedId(userId).forEach(item -> {
            EntityProjectVO entityProjectVO = new EntityProjectVO();
            entityProjectVO.setEntityName(item.getName());
            entityProjectVO.setDate(item.getCreatedDate().getTime() + "");
            entityProjectVO.setEntityType(EntityType.COMPANY_PROJECT.getDesc());
            projectVOS.add(entityProjectVO);
        });
        //GOVERNMENT_PROJECT
        this.governmentProjectService.getByCreatedId(userId).forEach(item -> {
            EntityProjectVO entityProjectVO = new EntityProjectVO();
            entityProjectVO.setEntityName(item.getName());
            entityProjectVO.setDate(item.getCreatedDate().getTime() + "");
            entityProjectVO.setEntityType(EntityType.GOVERNMENT_PROJECT.getDesc());
            projectVOS.add(entityProjectVO);
        });
        //PRIZE
        this.prizeService.getByCreatedId(userId).forEach(item -> {
            EntityProjectVO entityProjectVO = new EntityProjectVO();
            entityProjectVO.setEntityName(item.getName());
            entityProjectVO.setDate(item.getCreatedDate().getTime() + "");
            entityProjectVO.setEntityType(EntityType.PRIZE.getDesc());
            projectVOS.add(entityProjectVO);
        });
        //TEACHING_MATERIAL
        this.teachingMaterialService.getByCreatedId(userId).forEach(item -> {
            EntityProjectVO entityProjectVO = new EntityProjectVO();
            entityProjectVO.setEntityName(item.getName());
            entityProjectVO.setDate(item.getCreatedDate().getTime() + "");
            entityProjectVO.setEntityType(EntityType.TEACHING_MATERIAL.getDesc());
            projectVOS.add(entityProjectVO);
        });
        //PATENT
        this.patentService.getByCreatedId(userId).forEach(item -> {
            EntityProjectVO entityProjectVO = new EntityProjectVO();
            entityProjectVO.setEntityName(item.getName());
            entityProjectVO.setDate(item.getCreatedDate().getTime() + "");
            entityProjectVO.setEntityType(EntityType.PATENT.getDesc());
            projectVOS.add(entityProjectVO);
        });
        //SOFTWARE_WORK
        this.softwareWorkService.getByCreatedId(userId).forEach(item -> {
            EntityProjectVO entityProjectVO = new EntityProjectVO();
            entityProjectVO.setEntityName(item.getName());
            entityProjectVO.setDate(item.getCreatedDate().getTime() + "");
            entityProjectVO.setEntityType(EntityType.SOFTWARE_WORK.getDesc());
            projectVOS.add(entityProjectVO);
        });
        //LECTURE
        this.lectureService.getByCreatedId(userId).forEach(item -> {
            EntityProjectVO entityProjectVO = new EntityProjectVO();
            entityProjectVO.setEntityName(item.getName());
            entityProjectVO.setDate(item.getCreatedDate().getTime() + "");
            entityProjectVO.setEntityType(EntityType.LECTURE.getDesc());
            projectVOS.add(entityProjectVO);
        });
        //ACADEMIC_EXCHANGE
        this.academicExchangeService.getByCreatedId(userId).forEach(item -> {
            EntityProjectVO entityProjectVO = new EntityProjectVO();
            entityProjectVO.setEntityName(item.getMeetingName());
            entityProjectVO.setDate(item.getCreatedDate().getTime() + "");
            entityProjectVO.setEntityType(EntityType.ACADEMIC_EXCHANGE.getDesc());
            projectVOS.add(entityProjectVO);
        });
        //PAPER
        this.paperService.getByCreatedId(userId).forEach(item -> {
            EntityProjectVO entityProjectVO = new EntityProjectVO();
            entityProjectVO.setEntityName(item.getName());
            entityProjectVO.setDate(item.getCreatedDate().getTime() + "");
            entityProjectVO.setEntityType(EntityType.PAPER.getDesc());
            projectVOS.add(entityProjectVO);
        });
        result = projectVOS.stream().sorted(Comparator.comparing(EntityProjectVO::getDate).reversed()).limit(10).collect(Collectors.toList());
        result.forEach(item -> {
            item.setDate(DateUtils.dateDesc(new Date(Long.parseLong(item.getDate()))));
        });
        return result;
    }

    /**
     * 类型转换
     *
     * @param approval Approval对象
     * @return EntityProjectVO
     */
    public EntityProjectVO convertFromApproval(Approval approval) {
        if (null == approval) {
            return null;
        }
        EntityProjectVO projectVO = new EntityProjectVO();
        projectVO.setDate(DateUtils.dateDesc(approval.getDate()));
        int entityId = approval.getEntityId();
        switch (EntityType.valueMap.get(approval.getEntityType())) {
            case COMPANY_PROJECT:
                CompanyProject companyProject = this.companyProjectService.getByPrimaryKey(entityId);
                projectVO.setEntityName(companyProject.getName());
                projectVO.setEntityType(EntityType.COMPANY_PROJECT.getDesc());
                break;
            case GOVERNMENT_PROJECT:
                GovernmentProject governmentProject = this.governmentProjectService.getByPrimaryKey(entityId);
                projectVO.setEntityName(governmentProject.getName());
                projectVO.setEntityType(EntityType.GOVERNMENT_PROJECT.getDesc());
                break;
            case PRIZE:
                Prize prize = this.prizeService.getByPrimaryKey(entityId);
                projectVO.setEntityName(prize.getName());
                projectVO.setEntityType(EntityType.PRIZE.getDesc());
                break;
            case TEACHING_MATERIAL:
                TeachingMaterial teachingMaterial = this.teachingMaterialService.getByPrimaryKey(entityId);
                projectVO.setEntityName(teachingMaterial.getName());
                projectVO.setEntityType(EntityType.TEACHING_MATERIAL.getDesc());
                break;
            case PATENT:
                Patent patent = this.patentService.getByPrimaryKey(entityId);
                projectVO.setEntityName(patent.getName());
                projectVO.setEntityType(EntityType.PATENT.getDesc());
                break;
            case SOFTWARE_WORK:
                SoftwareWork softwareWork = this.softwareWorkService.getByPrimaryKey(entityId);
                projectVO.setEntityName(softwareWork.getName());
                projectVO.setEntityType(EntityType.SOFTWARE_WORK.getDesc());
                break;
            case LECTURE:
                Lecture lecture = this.lectureService.getByPrimaryKey(entityId);
                projectVO.setEntityName(lecture.getName());
                projectVO.setEntityType(EntityType.LECTURE.getDesc());
                break;
            case ACADEMIC_EXCHANGE:
                AcademicExchange academicExchange = this.academicExchangeService.getByPrimaryKey(entityId);
                projectVO.setEntityName(academicExchange.getMeetingName());
                projectVO.setEntityType(EntityType.ACADEMIC_EXCHANGE.getDesc());
                break;
            case PAPER:
                Paper paper = this.paperService.getByPrimaryKey(entityId);
                projectVO.setEntityName(paper.getName());
                projectVO.setEntityType(EntityType.PAPER.getDesc());
                break;
            default:
        }
        projectVO.setApprovalResult(approval.getRemark());
        return projectVO;
    }

    /**
     * 类型转换
     *
     * @param approvalList Approval列表
     * @return EntityProjectVO列表
     */
    public List<EntityProjectVO> convertFromApprovalList(List<Approval> approvalList) {
        List<EntityProjectVO> list = Lists.newArrayList();
        approvalList.forEach(item -> {
            list.add(convertFromApproval(item));
        });
        return list;
    }

    /**
     * 作者科技分数据可视化  柱状图
     *
     * @param list 数据列表
     * @return option
     */
    public Option scoreDataVisualizationBar(List<EntityProjectVO> list) {
        Option option = new Option();
        option.title(new Title("教师科技分来源", "数据统计", "left"))
                .tooltip(new BarTooltip())
                .legend(new BarLegend().addData("科技分（分）"))
                .grid(new Grid())
                .xAxis(new XAxis());
        //y轴
        YAxis yAxis = new YAxis();
        List<String> categories = Lists.newArrayList();
        yAxis.setData(categories);
        option.setYAxis(yAxis);
        //数据模型
        BarSerie barSerie = new BarSerie();
        barSerie.setName("科技分（分）");
        List<Double> scores = Lists.newArrayList();
        barSerie.setData(scores);
        //数据 填充
        list.forEach(item -> {
            categories.add(item.getEntityName().length() > 10 ? item.getEntityName().substring(0, 10) : item.getEntityName());
            scores.add(item.getMyScore());
        });
        option.series(barSerie);
        return option;
    }

    /**
     * 作者科技分数据可视化  饼状图
     *
     * @param list 数据列表
     * @return option
     */
    public Option scoreDataVisualizationPie(List<EntityProjectVO> list, boolean isManager) {
        Option option = new Option();
        option.title(new Title("教师科技分来源", "数据统计", "center"))
                .tooltip(new PieTooltip().trigger("item").formatter("{a} <br/>{b} : {c} ({d}%)"));
        //legend
        PieLegend legend = new PieLegend();
        legend.setOrient("vertical");
        legend.setLeft("left");
        Map<String, Double> typeScoreMap = Maps.newHashMap();
        //数据 填充
        if (isManager) {
            list.forEach(item -> {
                typeScoreMap.computeIfPresent(item.getEntityType(), (k, v) -> v + 1D);
                typeScoreMap.computeIfAbsent(item.getEntityType(), k -> {
                    typeScoreMap.put(k, 1D);
                    return item.getMyScore();
                });
            });
        } else {
            list.forEach(item -> {
                if (typeScoreMap.containsKey(item.getEntityType())) {
                    double score = typeScoreMap.get(item.getEntityType());
                    typeScoreMap.replace(item.getEntityType(), score + item.getMyScore());
                } else {
                    typeScoreMap.put(item.getEntityType(), item.getMyScore());
                }
                /*typeScoreMap.computeIfPresent(item.getEntityType(), (k, v) -> v + item.getMyScore());
                typeScoreMap.computeIfAbsent(item.getEntityType(), k -> {
                    typeScoreMap.put(k, item.getMyScore());
                    return item.getMyScore();
                });*/
            });
        }
        legend.setData(Lists.newArrayList(typeScoreMap.keySet()));
        option.setLegend(legend);
        //pie serie
        PieSerie pieSerie = new PieSerie();
        pieSerie.setName("科技分来源");
        if (isManager) {
            pieSerie.setName("审核项目数（个）");
            option.title(new Title("审核项目统计", "数据统计", "center"));
        }
        List<PieData> data = Lists.newArrayList();
        typeScoreMap.keySet().forEach(item -> {
            data.add(new PieData(typeScoreMap.get(item), item));
        });
        pieSerie.setData(data);
        option.series(pieSerie);

        return option;
    }

    /**
     * 所有项目的项目分类来源/所有科技分的分类来源  柱状图
     *
     * @param typeValueMap 数据
     * @param dataType     数据类型
     * @return 结果
     */
    public Option homeVisualizationBar(Map<String, Double> typeValueMap, ChartDataType dataType) {
        Option option = new Option();
        option.title(new Title(dataType.getExt1(), "数据统计", "left"))
                .tooltip(new BarTooltip())
                .legend(new BarLegend().addData(dataType.getExt2()))
                .grid(new Grid())
                .xAxis(new XAxis());
        //y轴
        YAxis yAxis = new YAxis();
        List<String> categories = Lists.newArrayList();
        yAxis.setData(categories);
        option.setYAxis(yAxis);
        //数据模型
        BarSerie barSerie = new BarSerie();
        barSerie.setName(dataType.getExt2());
        List<Double> scores = Lists.newArrayList();
        barSerie.setData(scores);
        //数据 填充
        typeValueMap.keySet().forEach(item -> {
            categories.add(item);
            scores.add(typeValueMap.get(item));
        });
        option.series(barSerie);
        return option;
    }

    /**
     * 所有项目的项目分类来源/所有科技分的分类来源  饼状图
     *
     * @param typeValueMap 数据
     * @param dataType     数据类型
     * @return 结果
     */
    public Option homeVisualizationPie(Map<String, Double> typeValueMap, ChartDataType dataType) {
        Option option = new Option();
        option.title(new Title(dataType.getExt1(), "数据统计", "center"))
                .tooltip(new PieTooltip().trigger("item").formatter("{a} <br/>{b} : {c} ({d}%)"));
        //legend
        PieLegend legend = new PieLegend();
        legend.setOrient("vertical");
        legend.setLeft("left");
        //数据 填充
        legend.setData(Lists.newArrayList(typeValueMap.keySet()));
        option.setLegend(legend);
        //pie serie
        PieSerie pieSerie = new PieSerie();
        pieSerie.setName(dataType.getExt1());
        pieSerie.setName(dataType.getExt2());
        option.title(new Title(dataType.getExt1(), "数据统计", "center"));
        List<PieData> data = Lists.newArrayList();
        typeValueMap.keySet().forEach(item -> {
            data.add(new PieData(typeValueMap.get(item), item));
        });
        pieSerie.setData(data);
        option.series(pieSerie);

        return option;
    }

    /**
     * 获取作者科技分来源
     *
     * @param score 编号
     * @return 列表
     */
    public List<EntityProjectVO> getAuthorScoreSource(Score score) {
        List<EntityProjectVO> list = Lists.newArrayList();
        List<EntityProjectVO> result = Lists.newArrayList();
        if (null == score) {
            return list;
        }

        UserScoreParseVO userScoreParseVO = JSON.parseObject(score.getScoreSource(), UserScoreParseVO.class);
        Map<ProjectScoreParseVO, Double> projectScoreMap = userScoreParseVO.getProjectScoreMap();
        projectScoreMap.keySet().forEach(item -> {
            EntityProjectVO projectVO = new EntityProjectVO();
            projectVO.setEntityName(item.getName());
            projectVO.setEntityType(EntityType.valueMap.get(item.getEntityType()).getDesc());
            projectVO.setDate(item.getParseDate().getTime() + "");
            projectVO.setMyScore(Double.parseDouble(BisheUtil.doubleFormat(projectScoreMap.get(item), ConstantUtil.DOUBLE_PATTERN)));
            projectVO.setProjectScore(Double.parseDouble(BisheUtil.doubleFormat(item.getProjectScore(), ConstantUtil.DOUBLE_PATTERN)));
            if (0 == projectVO.getProjectScore() || projectVO.getProjectScore().equals(projectVO.getMyScore())) {
                projectVO.setScale(100D);
            } else {
                projectVO.setScale(projectVO.getMyScore() / projectVO.getProjectScore() * 100);
            }
            list.add(projectVO);
        });
        result = list.stream().sorted(Comparator.comparing(EntityProjectVO::getDate).reversed()).collect(Collectors.toList());
        result.forEach(item -> {
            item.setDate(DateUtils.dateDesc(new Date(Long.parseLong(item.getDate()))));
        });
        return result;
    }

    /**
     * 统计管理员审核数量-排行榜
     *
     * @return 结果
     */
    public List<EntityProjectVO> numberOfStatisticalReviews() {
        List<EntityProjectVO> list = Lists.newArrayList();
        List<Approval> approvalList = this.approvalService.getUserByApprovalNum(10);

        approvalList.forEach(item -> {
            EntityProjectVO projectVO = new EntityProjectVO();
            User user = this.userService.getByPrimaryKey(item.getUserId());
            //装填管理员名字
            projectVO.setEntityName(user.getName());
            //装填管理员编号
            projectVO.setEntityType(user.getNumber());
            //装填审核数量
            projectVO.setMyScore(Double.valueOf(item.getResult()));
            list.add(projectVO);
        });

        return list;
    }

    /**
     * 统计科技分、项目数量 与项目类型的关系
     *
     * @return 结果
     */
    public List<Map<String, Double>> projectDataAnalysis() {
        List<Map<String, Double>> typeValueMapList = Lists.newArrayList();
        //类型与分数
        Map<String, Double> typeScoreMap = Maps.newHashMap();
        //类型与数量，可算出所有项目数量
        Map<String, Double> typeCountMap = Maps.newHashMap();

        double sum = 0;
        //COMPANY_PROJECT
        CompanyProject companyProject = new CompanyProject();
        companyProject.setStatus(null);
        companyProject.setScoreNotNull(true);
        for (CompanyProject item : this.companyProjectService.getAll(companyProject)) {
            ProjectScoreParseVO parseVO = JSON.parseObject(item.getResultScore(), ProjectScoreParseVO.class);
            if (null == parseVO.getProjectScore()) {
                continue;
            }
            sum += parseVO.getProjectScore();
        }
        typeScoreMap.put(EntityType.COMPANY_PROJECT.getDesc(), Double.parseDouble(BisheUtil.doubleFormat(sum, ConstantUtil.DOUBLE_PATTERN)));
        typeCountMap.put(EntityType.COMPANY_PROJECT.getDesc(), (double) this.companyProjectService.getAllCount(-1));
        //GOVERNMENT_PROJECT
        sum = 0;
        GovernmentProject governmentProject = new GovernmentProject();
        governmentProject.setStatus(null);
        governmentProject.setScoreNotNull(true);
        for (GovernmentProject item : this.governmentProjectService.getAll(governmentProject)) {
            ProjectScoreParseVO parseVO = JSON.parseObject(item.getResultScore(), ProjectScoreParseVO.class);
            if (null == parseVO.getProjectScore()) {
                continue;
            }
            sum += parseVO.getProjectScore();
        }
        typeScoreMap.put(EntityType.GOVERNMENT_PROJECT.getDesc(), Double.parseDouble(BisheUtil.doubleFormat(sum, ConstantUtil.DOUBLE_PATTERN)));
        typeCountMap.put(EntityType.GOVERNMENT_PROJECT.getDesc(), (double) this.governmentProjectService.getAllCount(-1));
        //PRIZE
        sum = 0;
        Prize prize = new Prize();
        prize.setStatus(null);
        prize.setScoreNotNull(true);
        for (Prize item : this.prizeService.getAll(prize)) {
            ProjectScoreParseVO parseVO = JSON.parseObject(item.getResultScore(), ProjectScoreParseVO.class);
            if (null == parseVO.getProjectScore()) {
                continue;
            }
            sum += parseVO.getProjectScore();
        }
        typeScoreMap.put(EntityType.PRIZE.getDesc(), Double.parseDouble(BisheUtil.doubleFormat(sum, ConstantUtil.DOUBLE_PATTERN)));
        typeCountMap.put(EntityType.PRIZE.getDesc(), (double) this.prizeService.getAllCount(-1));
        //TEACHING_MATERIAL
        sum = 0;
        TeachingMaterial teachingMaterial = new TeachingMaterial();
        teachingMaterial.setStatus(null);
        teachingMaterial.setScoreNotNull(true);
        for (TeachingMaterial item : this.teachingMaterialService.getAll(teachingMaterial)) {
            ProjectScoreParseVO parseVO = JSON.parseObject(item.getResultScore(), ProjectScoreParseVO.class);
            if (null == parseVO.getProjectScore()) {
                continue;
            }
            sum += parseVO.getProjectScore();
        }
        typeScoreMap.put(EntityType.TEACHING_MATERIAL.getDesc(), Double.parseDouble(BisheUtil.doubleFormat(sum, ConstantUtil.DOUBLE_PATTERN)));
        typeCountMap.put(EntityType.TEACHING_MATERIAL.getDesc(), (double) this.teachingMaterialService.getAllCount(-1));
        //PATENT
        sum = 0;
        Patent patent = new Patent();
        patent.setStatus(null);
        patent.setScoreNotNull(true);
        for (Patent item : this.patentService.getAll(patent)) {
            ProjectScoreParseVO parseVO = JSON.parseObject(item.getResultScore(), ProjectScoreParseVO.class);
            if (null == parseVO.getProjectScore()) {
                continue;
            }
            sum += parseVO.getProjectScore();
        }
        typeScoreMap.put(EntityType.PATENT.getDesc(), Double.parseDouble(BisheUtil.doubleFormat(sum, ConstantUtil.DOUBLE_PATTERN)));
        typeCountMap.put(EntityType.PATENT.getDesc(), (double) this.patentService.getAllCount(-1));
        //SOFTWARE_WORK
        sum = 0;
        SoftwareWork softwareWork = new SoftwareWork();
        softwareWork.setStatus(null);
        softwareWork.setScoreNotNull(true);
        for (SoftwareWork item : this.softwareWorkService.getAll(softwareWork)) {
            ProjectScoreParseVO parseVO = JSON.parseObject(item.getResultScore(), ProjectScoreParseVO.class);
            if (null == parseVO.getProjectScore()) {
                continue;
            }
            sum += parseVO.getProjectScore();
        }
        typeScoreMap.put(EntityType.SOFTWARE_WORK.getDesc(), Double.parseDouble(BisheUtil.doubleFormat(sum, ConstantUtil.DOUBLE_PATTERN)));
        typeCountMap.put(EntityType.SOFTWARE_WORK.getDesc(), (double) this.softwareWorkService.getAllCount(-1));
        //LECTURE
        sum = 0;
        Lecture lecture = new Lecture();
        lecture.setStatus(null);
        lecture.setScoreNotNull(true);
        for (Lecture item : this.lectureService.getAll(lecture)) {
            ProjectScoreParseVO parseVO = JSON.parseObject(item.getResultScore(), ProjectScoreParseVO.class);
            if (null == parseVO.getProjectScore()) {
                continue;
            }
            sum += parseVO.getProjectScore();
        }
        typeScoreMap.put(EntityType.LECTURE.getDesc(), Double.parseDouble(BisheUtil.doubleFormat(sum, ConstantUtil.DOUBLE_PATTERN)));
        typeCountMap.put(EntityType.LECTURE.getDesc(), (double) this.lectureService.getAllCount(-1));
        //ACADEMIC_EXCHANGE
        sum = 0;
        AcademicExchange academicExchange = new AcademicExchange();
        academicExchange.setStatus(null);
        academicExchange.setScoreNotNull(true);
        for (AcademicExchange item : this.academicExchangeService.getAll(academicExchange)) {
            ProjectScoreParseVO parseVO = JSON.parseObject(item.getResultScore(), ProjectScoreParseVO.class);
            if (null == parseVO.getProjectScore()) {
                continue;
            }
            sum += parseVO.getProjectScore();
        }
        typeScoreMap.put(EntityType.ACADEMIC_EXCHANGE.getDesc(), Double.parseDouble(BisheUtil.doubleFormat(sum, ConstantUtil.DOUBLE_PATTERN)));
        typeCountMap.put(EntityType.ACADEMIC_EXCHANGE.getDesc(), (double) this.academicExchangeService.getAllCount(-1));
        //PAPER
        sum = 0;
        Paper paper = new Paper();
        paper.setStatus(null);
        paper.setScoreNotNull(true);
        for (Paper item : this.paperService.getAll(paper)) {
            ProjectScoreParseVO parseVO = JSON.parseObject(item.getResultScore(), ProjectScoreParseVO.class);
            if (null == parseVO.getProjectScore()) {
                continue;
            }
            sum += parseVO.getProjectScore();
        }
        typeScoreMap.put(EntityType.PAPER.getDesc(), Double.parseDouble(BisheUtil.doubleFormat(sum, ConstantUtil.DOUBLE_PATTERN)));
        typeCountMap.put(EntityType.PAPER.getDesc(), (double) this.paperService.getAllCount(-1));

        typeValueMapList.add(typeScoreMap);
        typeValueMapList.add(typeCountMap);
        return typeValueMapList;
    }

}