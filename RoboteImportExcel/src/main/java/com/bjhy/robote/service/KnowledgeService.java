package com.bjhy.robote.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.bjhy.robote.domain.ExcelDomain;
import com.bjhy.robote.utill.ExcelImportUtils;

/**
 * 
 * @author chenhao
 *
 *         2018年5月16日
 */
@Service
public class KnowledgeService {

	//public static final String JSESSIONID = "1jwwt6nnk8hj41s46tjy5bd61x";
	// api url地址
	public static final String URL = "http://192.168.0.94:7778/code-robot/property";

	@Autowired
	private HttpClientService clientService;

	/**
	 * 上传excel文件到临时目录后并开始解析
	 * 
	 * @param fileName
	 * @param file
	 * @param userName
	 * @return
	 */
	public String batchImport(String fileName, MultipartFile mfile,String JSESSIONID) {

		File uploadDir = new File("E:\\fileupload");
		// 创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
		if (!uploadDir.exists())
			uploadDir.mkdirs();
		// 新建一个文件
		File tempFile = new File("E:\\fileupload\\" + new Date().getTime() + ".xlsx");
		// 初始化输入流
		InputStream is = null;
		try {
			// 将上传的文件写入新建的文件中
			mfile.transferTo(tempFile);

			// 根据新建的文件实例化输入流
			is = new FileInputStream(tempFile);

			// 根据版本选择创建Workbook的方式
			Workbook wb = null;
			// 根据文件名判断文件是2003版本还是2007版本
			if (ExcelImportUtils.isExcel2007(fileName)) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = new HSSFWorkbook(is);
			}
			// 根据excel里面的内容读取知识库信息
			return readExcelValue(wb, tempFile,JSESSIONID);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					e.printStackTrace();
				}
			}
		}
		return "导入出错！请检查数据格式！";
	}

	/**
	 * 解析Excel里面的数据
	 * 
	 * @param wb
	 * @return
	 */
	@SuppressWarnings("unused")
	private String readExcelValue(Workbook wb, File tempFile,String JSESSIONID) {

		// 错误信息接收器
		String errorMsg = "";
		//信息
		StringBuilder msg=new StringBuilder();
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		// 得到Excel的行数
		int totalRows = sheet.getPhysicalNumberOfRows();
		// 总列数
		int totalCells = 0;
		// 得到Excel的列数(前提是有行数)，从第二行算起
		if (totalRows >= 2 && sheet.getRow(1) != null) {
			totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
		}
		List<ExcelDomain> excelDomainList = new ArrayList<ExcelDomain>();
		ExcelDomain excelDomain;

		String br = "<br/>";

		// 循环Excel行数,从第二行开始。标题不入库
		for (int r = 1; r < totalRows; r++) {
			String rowMessage = "";
			Row row = sheet.getRow(r);
			if (row == null) {
				errorMsg += br + "第" + (r + 1) + "行数据有问题，请仔细检查！";
				continue;
			}
			// 创建对象
			excelDomain = new ExcelDomain();
			// 循环Excel的列
			for (int c = 0; c < totalCells; c++) {
				Cell cell = row.getCell(c);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (null != cell) {
					if (c == 0) {
						String id = cell.getStringCellValue();
						excelDomain.setId(id);
					} else if (c == 1) {
						String pxNum = cell.getStringCellValue();
						excelDomain.setPxNum(pxNum);
					} else if (c == 2) {
						String name = cell.getStringCellValue();
						excelDomain.setName(name);
					} else if (c == 3) {
						String fieldName = cell.getStringCellValue();
						excelDomain.setFieldName(fieldName);
					} else if (c == 4) {
						String dataType = cell.getStringCellValue();
						excelDomain.setDataType(dataType);
					} else if (c == 5) {
						String inputType = cell.getStringCellValue();
						excelDomain.setInputType(inputType);
					} else if (c == 6) {
						String inputValue = cell.getStringCellValue();
						excelDomain.setInputValue(inputValue);
					} else if (c == 7) {
						String domainId = cell.getStringCellValue();
						excelDomain.setDomainId(domainId);
					}
				} else {
					rowMessage += "第" + (c + 1) + "列数据有问题，请仔细检查";
				}
			}
			msg.append(doPost(excelDomain,JSESSIONID));
			msg.append(br);
		}

		// 删除上传的临时文件
		if (tempFile.exists()) {
			tempFile.delete();
		}

		return String.valueOf(msg);
	}

	// 调用POST请求
	private String doPost(ExcelDomain excelDomain,String JSESSIONID) {
		// post请求
		// 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("id", "");
		params.add("name", excelDomain.getName());
		params.add("fieldName", excelDomain.getFieldName());
		params.add("dataType", excelDomain.getDataType());
		params.add("inputType", excelDomain.getInputType());
		params.add("inputValue", "");
		params.add("pxNum", excelDomain.getPxNum());
		params.add("domain.id", excelDomain.getDomainId());
		String msg = clientService.doPost(URL, params, JSESSIONID);
		return msg;
	}
}
