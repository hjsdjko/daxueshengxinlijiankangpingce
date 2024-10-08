
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 心理健康
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/xinlijiankang")
public class XinlijiankangController {
    private static final Logger logger = LoggerFactory.getLogger(XinlijiankangController.class);

    private static final String TABLE_NAME = "xinlijiankang";

    @Autowired
    private XinlijiankangService xinlijiankangService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private DictionaryService dictionaryService;//字典表
    @Autowired
    private ExampaperService exampaperService;//试卷表
    @Autowired
    private ExampapertopicService exampapertopicService;//试卷选题
    @Autowired
    private ExamquestionService examquestionService;//试题表
    @Autowired
    private ExamrecordService examrecordService;//考试记录表
    @Autowired
    private ExamredetailsService examredetailsService;//答题详情表
    @Autowired
    private ForumService forumService;//论坛
    @Autowired
    private NewsService newsService;//通知公告
    @Autowired
    private XinlijiankangCollectionService xinlijiankangCollectionService;//心理健康收藏
    @Autowired
    private XinlijiankangLiuyanService xinlijiankangLiuyanService;//心理健康留言
    @Autowired
    private YonghuService yonghuService;//用户
    @Autowired
    private ZhixunshiService zhixunshiService;//咨询师
    @Autowired
    private ZhixunshiChatService zhixunshiChatService;//心理咨询
    @Autowired
    private ZhixunshiYuyueService zhixunshiYuyueService;//咨询师预约
    @Autowired
    private UsersService usersService;//管理员


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        else if("咨询师".equals(role))
            params.put("zhixunshiId",request.getSession().getAttribute("userId"));
        params.put("xinlijiankangDeleteStart",1);params.put("xinlijiankangDeleteEnd",1);
        CommonUtil.checkMap(params);
        PageUtils page = xinlijiankangService.queryPage(params);

        //字典表数据转换
        List<XinlijiankangView> list =(List<XinlijiankangView>)page.getList();
        for(XinlijiankangView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        XinlijiankangEntity xinlijiankang = xinlijiankangService.selectById(id);
        if(xinlijiankang !=null){
            //entity转view
            XinlijiankangView view = new XinlijiankangView();
            BeanUtils.copyProperties( xinlijiankang , view );//把实体数据重构到view中
            //级联表 咨询师
            //级联表
            ZhixunshiEntity zhixunshi = zhixunshiService.selectById(xinlijiankang.getZhixunshiId());
            if(zhixunshi != null){
            BeanUtils.copyProperties( zhixunshi , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "zhixunshiId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setZhixunshiId(zhixunshi.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody XinlijiankangEntity xinlijiankang, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,xinlijiankang:{}",this.getClass().getName(),xinlijiankang.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("咨询师".equals(role))
            xinlijiankang.setZhixunshiId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        Wrapper<XinlijiankangEntity> queryWrapper = new EntityWrapper<XinlijiankangEntity>()
            .eq("zhixunshi_id", xinlijiankang.getZhixunshiId())
            .eq("xinlijiankang_name", xinlijiankang.getXinlijiankangName())
            .eq("xinlijiankang_video", xinlijiankang.getXinlijiankangVideo())
            .eq("xinlijiankang_types", xinlijiankang.getXinlijiankangTypes())
            .eq("shuju_types", xinlijiankang.getShujuTypes())
            .eq("zan_number", xinlijiankang.getZanNumber())
            .eq("cai_number", xinlijiankang.getCaiNumber())
            .eq("xinlijiankang_delete", xinlijiankang.getXinlijiankangDelete())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XinlijiankangEntity xinlijiankangEntity = xinlijiankangService.selectOne(queryWrapper);
        if(xinlijiankangEntity==null){
            xinlijiankang.setXinlijiankangDelete(1);
            xinlijiankang.setCreateTime(new Date());
            xinlijiankangService.insert(xinlijiankang);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody XinlijiankangEntity xinlijiankang, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,xinlijiankang:{}",this.getClass().getName(),xinlijiankang.toString());
        XinlijiankangEntity oldXinlijiankangEntity = xinlijiankangService.selectById(xinlijiankang.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("咨询师".equals(role))
//            xinlijiankang.setZhixunshiId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        if("".equals(xinlijiankang.getXinlijiankangPhoto()) || "null".equals(xinlijiankang.getXinlijiankangPhoto())){
                xinlijiankang.setXinlijiankangPhoto(null);
        }
        if("".equals(xinlijiankang.getXinlijiankangVideo()) || "null".equals(xinlijiankang.getXinlijiankangVideo())){
                xinlijiankang.setXinlijiankangVideo(null);
        }

            xinlijiankangService.updateById(xinlijiankang);//根据id更新
            return R.ok();
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<XinlijiankangEntity> oldXinlijiankangList =xinlijiankangService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        ArrayList<XinlijiankangEntity> list = new ArrayList<>();
        for(Integer id:ids){
            XinlijiankangEntity xinlijiankangEntity = new XinlijiankangEntity();
            xinlijiankangEntity.setId(id);
            xinlijiankangEntity.setXinlijiankangDelete(2);
            list.add(xinlijiankangEntity);
        }
        if(list != null && list.size() >0){
            xinlijiankangService.updateBatchById(list);
        }

        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<XinlijiankangEntity> xinlijiankangList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            XinlijiankangEntity xinlijiankangEntity = new XinlijiankangEntity();
//                            xinlijiankangEntity.setZhixunshiId(Integer.valueOf(data.get(0)));   //咨询师 要改的
//                            xinlijiankangEntity.setXinlijiankangName(data.get(0));                    //心理健康标题 要改的
//                            xinlijiankangEntity.setXinlijiankangPhoto("");//详情和图片
//                            xinlijiankangEntity.setXinlijiankangVideo(data.get(0));                    //心理健康视频 要改的
//                            xinlijiankangEntity.setXinlijiankangTypes(Integer.valueOf(data.get(0)));   //心理健康类型 要改的
//                            xinlijiankangEntity.setShujuTypes(Integer.valueOf(data.get(0)));   //数据类型 要改的
//                            xinlijiankangEntity.setZanNumber(Integer.valueOf(data.get(0)));   //赞 要改的
//                            xinlijiankangEntity.setCaiNumber(Integer.valueOf(data.get(0)));   //踩 要改的
//                            xinlijiankangEntity.setXinlijiankangDelete(1);//逻辑删除字段
//                            xinlijiankangEntity.setXinlijiankangContent("");//详情和图片
//                            xinlijiankangEntity.setCreateTime(date);//时间
                            xinlijiankangList.add(xinlijiankangEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        xinlijiankangService.insertBatch(xinlijiankangList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }



    /**
    * 个性推荐
    */
    @IgnoreAuth
    @RequestMapping("/gexingtuijian")
    public R gexingtuijian(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("gexingtuijian方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        CommonUtil.checkMap(params);
        List<XinlijiankangView> returnXinlijiankangViewList = new ArrayList<>();

        //查看收藏
        Map<String, Object> params1 = new HashMap<>(params);params1.put("sort","id");params1.put("yonghuId",request.getSession().getAttribute("userId"));
        PageUtils pageUtils = xinlijiankangCollectionService.queryPage(params1);
        List<XinlijiankangCollectionView> collectionViewsList =(List<XinlijiankangCollectionView>)pageUtils.getList();
        Map<Integer,Integer> typeMap=new HashMap<>();//购买的类型list
        for(XinlijiankangCollectionView collectionView:collectionViewsList){
            Integer xinlijiankangTypes = collectionView.getXinlijiankangTypes();
            if(typeMap.containsKey(xinlijiankangTypes)){
                typeMap.put(xinlijiankangTypes,typeMap.get(xinlijiankangTypes)+1);
            }else{
                typeMap.put(xinlijiankangTypes,1);
            }
        }
        List<Integer> typeList = new ArrayList<>();//排序后的有序的类型 按最多到最少
        typeMap.entrySet().stream().sorted((o1, o2) -> o2.getValue() - o1.getValue()).forEach(e -> typeList.add(e.getKey()));//排序
        Integer limit = Integer.valueOf(String.valueOf(params.get("limit")));
        for(Integer type:typeList){
            Map<String, Object> params2 = new HashMap<>(params);params2.put("xinlijiankangTypes",type);
            PageUtils pageUtils1 = xinlijiankangService.queryPage(params2);
            List<XinlijiankangView> xinlijiankangViewList =(List<XinlijiankangView>)pageUtils1.getList();
            returnXinlijiankangViewList.addAll(xinlijiankangViewList);
            if(returnXinlijiankangViewList.size()>= limit) break;//返回的推荐数量大于要的数量 跳出循环
        }
        //正常查询出来商品,用于补全推荐缺少的数据
        PageUtils page = xinlijiankangService.queryPage(params);
        if(returnXinlijiankangViewList.size()<limit){//返回数量还是小于要求数量
            int toAddNum = limit - returnXinlijiankangViewList.size();//要添加的数量
            List<XinlijiankangView> xinlijiankangViewList =(List<XinlijiankangView>)page.getList();
            for(XinlijiankangView xinlijiankangView:xinlijiankangViewList){
                Boolean addFlag = true;
                for(XinlijiankangView returnXinlijiankangView:returnXinlijiankangViewList){
                    if(returnXinlijiankangView.getId().intValue() ==xinlijiankangView.getId().intValue()) addFlag=false;//返回的数据中已存在此商品
                }
                if(addFlag){
                    toAddNum=toAddNum-1;
                    returnXinlijiankangViewList.add(xinlijiankangView);
                    if(toAddNum==0) break;//够数量了
                }
            }
        }else {
            returnXinlijiankangViewList = returnXinlijiankangViewList.subList(0, limit);
        }

        for(XinlijiankangView c:returnXinlijiankangViewList)
            dictionaryService.dictionaryConvert(c, request);
        page.setList(returnXinlijiankangViewList);
        return R.ok().put("data", page);
    }

    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = xinlijiankangService.queryPage(params);

        //字典表数据转换
        List<XinlijiankangView> list =(List<XinlijiankangView>)page.getList();
        for(XinlijiankangView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        XinlijiankangEntity xinlijiankang = xinlijiankangService.selectById(id);
            if(xinlijiankang !=null){


                //entity转view
                XinlijiankangView view = new XinlijiankangView();
                BeanUtils.copyProperties( xinlijiankang , view );//把实体数据重构到view中

                //级联表
                    ZhixunshiEntity zhixunshi = zhixunshiService.selectById(xinlijiankang.getZhixunshiId());
                if(zhixunshi != null){
                    BeanUtils.copyProperties( zhixunshi , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setZhixunshiId(zhixunshi.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody XinlijiankangEntity xinlijiankang, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,xinlijiankang:{}",this.getClass().getName(),xinlijiankang.toString());
        Wrapper<XinlijiankangEntity> queryWrapper = new EntityWrapper<XinlijiankangEntity>()
            .eq("zhixunshi_id", xinlijiankang.getZhixunshiId())
            .eq("xinlijiankang_name", xinlijiankang.getXinlijiankangName())
            .eq("xinlijiankang_video", xinlijiankang.getXinlijiankangVideo())
            .eq("xinlijiankang_types", xinlijiankang.getXinlijiankangTypes())
            .eq("shuju_types", xinlijiankang.getShujuTypes())
            .eq("zan_number", xinlijiankang.getZanNumber())
            .eq("cai_number", xinlijiankang.getCaiNumber())
            .eq("xinlijiankang_delete", xinlijiankang.getXinlijiankangDelete())
//            .notIn("xinlijiankang_types", new Integer[]{102})
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XinlijiankangEntity xinlijiankangEntity = xinlijiankangService.selectOne(queryWrapper);
        if(xinlijiankangEntity==null){
                xinlijiankang.setZanNumber(1);
                xinlijiankang.setCaiNumber(1);
            xinlijiankang.setXinlijiankangDelete(1);
            xinlijiankang.setCreateTime(new Date());
        xinlijiankangService.insert(xinlijiankang);

            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

}

