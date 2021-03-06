/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.hqu.modules.papergrade.web;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.LineData;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Funnel;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.hqu.modules.papergrade.service.PaperGradeService;
import com.jeeplus.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 论文等级统计Controller
 * @author zll
 * @version 2018-10-12
 */
@Controller
@RequestMapping(value = "${adminPath}/papergrade/paperGrade")
public class PaperGradeController extends BaseController {

	@Autowired
	PaperGradeService paperGradeService;

    @RequestMapping(value = {"index",""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "/modules/papergrade/paperGradeForm";
    }

    /* 柱状图 */
    @ResponseBody
    @RequestMapping("bar")
    public GsonOption getOption(@RequestParam(value = "beginDate",required = false)String beginDate, @RequestParam(value = "endDate",required = false)String endDate){

        int numA = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "5");
        int numB = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "4");
        int numC = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "3");
        int numD = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "2");
        int numE = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "1");
        GsonOption option = new GsonOption();
        option.title().text("论文等级统计");
        option.tooltip().trigger(Trigger.axis);
        option.legend("论文等级");  // 图例
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.pie, Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        option.xAxis(new CategoryAxis().data("优秀","良好","中等","及格","不及格"));
        option.yAxis(new ValueAxis());

        Bar bar = new Bar("论文等级");
        bar.data(numA, numB, numC, numD, numE);
        bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
        bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));
/*
        Bar bar2 = new Bar("回复个数");
        bar2.data(HfZxNum,HfJyNum,Hfnum);
        bar2.markPoint().data(new PointData("年最高", 182).xAxis(7).yAxis(183).symbolSize(18), new PointData("年最低", 2).xAxis(11).yAxis(3));
        bar2.markLine().data(new PointData().type(MarkType.average).name("平均值"));
*/
        option.series(bar);
        return option;
    }

    /* 饼图 */
    @ResponseBody
    @RequestMapping("pie")
    public GsonOption getOption1(@RequestParam(value = "beginDate",required = false)String beginDate,@RequestParam(value = "endDate",required = false)String endDate){
        GsonOption option = new GsonOption();
        int numA = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "5");
        int numB = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "4");
        int numC = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "3");
        int numD = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "2");
        int numE = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "1");
        //时间轴
        option.timeline().data("2018-01-01", "2018-02-01", "2018-03-01", "2018-04-01", "2018-05-01",
                new LineData("2018-06-01", "emptyStart6", 8), "2018-07-01", "2018-08-01", "2018-09-01", "2018-10-01",
                "2018-11-01", new LineData("2018-12-01", "star6", 8));
        option.timeline().autoPlay(true);
        //timeline变态的地方在于多个Option  // 是的。
        Option basic = new Option();
        basic.title().text("论文等级比例变化");
        basic.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");
        basic.legend().data("优秀","良好","中等","及格","不及格");
        basic.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage, new MagicType(Magic.pie, Magic.funnel)
                .option(new MagicType.Option().funnel(
                        new Funnel().x("25%").width("50%").funnelAlign(X.left).max(1548))));

        Pie pie1=new Pie().name("论文等级比例").data(
                new PieData("优秀", numA),
                new PieData("良好", numB),
                new PieData("中等", numC),
                new PieData("及格", numD),
                new PieData("不及格", numE));
        Pie pie = pie1.center("50%", "45%").radius("50%");
        pie.label().normal().show(true).formatter("{b}{c}({d}%)");
        basic.series(pie);
        //加入
        option.options(basic);
        //构造11个数据
        Option[] os = new Option[11];
        for (int i = 0; i < os.length; i++) {
            os[i] = new Option().series(pie1);
        }
        option.options(os);
        return option;
    }

    /* 折线图 */
    @ResponseBody
    @RequestMapping("line")
    public GsonOption getOption2(@RequestParam(value = "beginDate",required = false)String beginDate,@RequestParam(value = "endDate",required = false)String endDate){
        int numA = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "5");
        int numB = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "4");
        int numC = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "3");
        int numD = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "2");
        int numE = paperGradeService.getNumByDate(beginDate, endDate, "t_order", "psdj", "1");

        GsonOption option = new GsonOption();
        option.tooltip().trigger(Trigger.axis);
        option.legend("论文等级");
        option.toolbox().show(true);
        option.calculable(true);
        option.xAxis(new CategoryAxis().boundaryGap(false).data("优秀","良好","中等","及格","不及格"));
        option.yAxis(new ValueAxis());
        option.series(new Line().smooth(true).name("等级").stack("总量").symbol(Symbol.none).data(numA, numB, numC, numD, numE));
        //实现不了js的这个效果
        //line.itemStyle.normal.areaStyle = new AreaStyle();
        //option.series(new Line().smooth(true).name("回复个数").stack("总量").symbol("image://http://echarts.baidu.com/doc/asset/ico/favicon.png").symbolSize(8).data(HfZxNum,HfJyNum,Hfnum));

       /* line = new Line();
        line.name = "邮件营销";
        line.stack = "总量";
        line.symbol = Symbol.none;
        line.smooth = true;
        //实现不了js的这个效果
        //line.itemStyle.normal.areaStyle = new AreaStyle();
        line.addData(120, 132, 301, 134,new LineData(90,Symbol.droplet,5),230,210);
        option.series.add(line);

        line = new Line();
        line.name = "邮件营销";
        line.stack = "总量";
        line.symbol = Symbol.none;
        line.smooth = true;
        //实现不了js的这个效果
        //line.itemStyle.normal.areaStyle = new AreaStyle();
        line.addData(120, 132, 301, 134,new LineData(90,Symbol.droplet,5),230,210);
        option.series.add(line);*/

        return option;
    }


//	@ModelAttribute
//	public PaperGrade get(@RequestParam(required=false) String id) {
//		PaperGrade entity = null;
//		if (StringUtils.isNotBlank(id)){
//			entity = paperGradeService.get(id);
//		}
//		if (entity == null){
//			entity = new PaperGrade();
//		}
//		return entity;
//	}
	
//	/**
//	 * 论文等级统计列表页面
//	 */
//	@RequiresPermissions("papergrade:paperGrade:list")
//	@RequestMapping(value = {"list", ""})
//	public String list(PaperGrade paperGrade, Model model) {
//		model.addAttribute("paperGrade", paperGrade);
//		return "modules/papergrade/paperGradeList";
//	}
//
//		/**
//	 * 论文等级统计列表数据
//	 */
//	@ResponseBody
//	@RequiresPermissions("papergrade:paperGrade:list")
//	@RequestMapping(value = "data")
//	public Map<String, Object> data(PaperGrade paperGrade, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<PaperGrade> page = paperGradeService.findPage(new Page<PaperGrade>(request, response), paperGrade);
//		return getBootstrapData(page);
//	}
//
//	/**
//	 * 查看，增加，编辑论文等级统计表单页面
//	 */
//	@RequiresPermissions(value={"papergrade:paperGrade:view","papergrade:paperGrade:add","papergrade:paperGrade:edit"},logical=Logical.OR)
//	@RequestMapping(value = "form/{mode}")
//	public String form(@PathVariable String mode, PaperGrade paperGrade, Model model) {
//		model.addAttribute("paperGrade", paperGrade);
//		model.addAttribute("mode", mode);
//		return "modules/papergrade/paperGradeForm";
//	}
//
//	/**
//	 * 保存论文等级统计
//	 */
//	@ResponseBody
//	@RequiresPermissions(value={"papergrade:paperGrade:add","papergrade:paperGrade:edit"},logical=Logical.OR)
//	@RequestMapping(value = "save")
//	public AjaxJson save(PaperGrade paperGrade, Model model) throws Exception{
//		AjaxJson j = new AjaxJson();
//		/**
//		 * 后台hibernate-validation插件校验
//		 */
//		String errMsg = beanValidator(paperGrade);
//		if (StringUtils.isNotBlank(errMsg)){
//			j.setSuccess(false);
//			j.setMsg(errMsg);
//			return j;
//		}
//		//新增或编辑表单保存
//		paperGradeService.save(paperGrade);//保存
//		j.setSuccess(true);
//		j.setMsg("保存论文等级统计成功");
//		return j;
//	}
//
//	/**
//	 * 删除论文等级统计
//	 */
//	@ResponseBody
//	@RequiresPermissions("papergrade:paperGrade:del")
//	@RequestMapping(value = "delete")
//	public AjaxJson delete(PaperGrade paperGrade) {
//		AjaxJson j = new AjaxJson();
//		paperGradeService.delete(paperGrade);
//		j.setMsg("删除论文等级统计成功");
//		return j;
//	}
//
//	/**
//	 * 批量删除论文等级统计
//	 */
//	@ResponseBody
//	@RequiresPermissions("papergrade:paperGrade:del")
//	@RequestMapping(value = "deleteAll")
//	public AjaxJson deleteAll(String ids) {
//		AjaxJson j = new AjaxJson();
//		String idArray[] =ids.split(",");
//		for(String id : idArray){
//			paperGradeService.delete(paperGradeService.get(id));
//		}
//		j.setMsg("删除论文等级统计成功");
//		return j;
//	}
//
//	/**
//	 * 导出excel文件
//	 */
//	@ResponseBody
//	@RequiresPermissions("papergrade:paperGrade:export")
//    @RequestMapping(value = "export")
//    public AjaxJson exportFile(PaperGrade paperGrade, HttpServletRequest request, HttpServletResponse response) {
//		AjaxJson j = new AjaxJson();
//		try {
//            String fileName = "论文等级统计"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
//            Page<PaperGrade> page = paperGradeService.findPage(new Page<PaperGrade>(request, response, -1), paperGrade);
//    		new ExportExcel("论文等级统计", PaperGrade.class).setDataList(page.getList()).write(response, fileName).dispose();
//    		j.setSuccess(true);
//    		j.setMsg("导出成功！");
//    		return j;
//		} catch (Exception e) {
//			j.setSuccess(false);
//			j.setMsg("导出论文等级统计记录失败！失败信息："+e.getMessage());
//		}
//			return j;
//    }
//
//	/**
//	 * 导入Excel数据
//
//	 */
//	@ResponseBody
//	@RequiresPermissions("papergrade:paperGrade:import")
//    @RequestMapping(value = "import")
//   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
//		AjaxJson j = new AjaxJson();
//		try {
//			int successNum = 0;
//			int failureNum = 0;
//			StringBuilder failureMsg = new StringBuilder();
//			ImportExcel ei = new ImportExcel(file, 1, 0);
//			List<PaperGrade> list = ei.getDataList(PaperGrade.class);
//			for (PaperGrade paperGrade : list){
//				try{
//					paperGradeService.save(paperGrade);
//					successNum++;
//				}catch(ConstraintViolationException ex){
//					failureNum++;
//				}catch (Exception ex) {
//					failureNum++;
//				}
//			}
//			if (failureNum>0){
//				failureMsg.insert(0, "，失败 "+failureNum+" 条论文等级统计记录。");
//			}
//			j.setMsg( "已成功导入 "+successNum+" 条论文等级统计记录"+failureMsg);
//		} catch (Exception e) {
//			j.setSuccess(false);
//			j.setMsg("导入论文等级统计失败！失败信息："+e.getMessage());
//		}
//		return j;
//    }
//
//	/**
//	 * 下载导入论文等级统计数据模板
//	 */
//	@ResponseBody
//	@RequiresPermissions("papergrade:paperGrade:import")
//    @RequestMapping(value = "import/template")
//     public AjaxJson importFileTemplate(HttpServletResponse response) {
//		AjaxJson j = new AjaxJson();
//		try {
//            String fileName = "论文等级统计数据导入模板.xlsx";
//    		List<PaperGrade> list = Lists.newArrayList();
//    		new ExportExcel("论文等级统计数据", PaperGrade.class, 1).setDataList(list).write(response, fileName).dispose();
//    		return null;
//		} catch (Exception e) {
//			j.setSuccess(false);
//			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
//		}
//		return j;
//    }

}