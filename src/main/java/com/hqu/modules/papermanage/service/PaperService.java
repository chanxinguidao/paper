/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.hqu.modules.papermanage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.hqu.modules.papermanage.entity.Paper;
import com.hqu.modules.papermanage.mapper.PaperMapper;
import com.hqu.modules.papermanage.mapper.PaperSelfMapper;

/**
 * 论文管理Service
 * @author dongqida
 * @version 2018-08-29
 */
@Service
@Transactional(readOnly = true)
public class PaperService extends CrudService<PaperMapper, Paper> {
	
	@Autowired
	private PaperSelfMapper paperSelfMapper;
	
	public Paper get(String id) {
		return super.get(id);
	}
	
	public List<Paper> findList(Paper paper) {
		return super.findList(paper);
	}
	
	public Page<Paper> findPage(Page<Paper> page, Paper paper) {
		return super.findPage(page, paper);
	}
	
	@Transactional(readOnly = false)
	public void save(Paper paper) {
		super.save(paper);
	}
	
	@Transactional(readOnly = false)
	public void delete(Paper paper) {
		super.delete(paper);
	}
	@Transactional(readOnly = false)
	public int updatePaperPath(Map<String, Object> map) {
		System.out.println("+-+");
		return paperSelfMapper.updatePaperPath(map);
	}
	@Transactional(readOnly = false)
	public int withdrawPaper(Map<String,Object> map){
		return mapper.withdrawPaper(map);
	}
}