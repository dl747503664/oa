package com.oa.sys.service;
import java.util.List;
import java.util.Map;

import com.oa.common.vo.Node;
import com.oa.sys.entity.SysDept;

public interface SysDeptService {
	int saveObject(SysDept entity);
	int updateObject(SysDept entity);
	
	List<Node> findZTreeNodes();
	List<Map<String,Object>> findObjects();
	
	int deleteObject(Integer id);
}
