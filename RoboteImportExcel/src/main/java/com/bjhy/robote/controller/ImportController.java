package com.bjhy.robote.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bjhy.robote.service.KnowledgeService;
import com.bjhy.robote.utill.ExcelImportUtils;

/**
 * 导入控制器
 * 
 * @author chenhao
 *
 *         2018年5月17日
 */
@RestController
public class ImportController {

	@Autowired
	private KnowledgeService knowledgeService;

	// 导入
	@PostMapping(value = "batchImport")
	public String batchImportUserKnowledge(@RequestParam(value = "filename") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response,String JSESSIONID) throws IOException {

		// 判断文件是否为空
		if (file == null) {
			System.out.println("文件是空的！");
		}
		// 获取文件名
		String fileName = file.getOriginalFilename();

		// 验证文件名是否合格
		if (!ExcelImportUtils.validateExcel(fileName)) {
			System.out.println("文件必须是excel格式！");
		}

		// 进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
		long size = file.getSize();
		if (StringUtils.isEmpty(fileName) || size == 0) {
			System.out.println("文件不能为空！");
		}

		// 批量导入
		String message = knowledgeService.batchImport(fileName, file, JSESSIONID);

		return message;
	}
}
