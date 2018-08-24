package com.bluewhite.portal.system.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.portal.common.ClearCascadeJSON;
import com.bluewhite.portal.common.Constants;
import com.bluewhite.portal.common.entity.CommonResponse;
import com.bluewhite.portal.file.entity.Files;
import com.bluewhite.portal.file.service.FilesService;
import com.bluewhite.portal.product.entity.Product;
import com.bluewhite.portal.product.service.ProductService;

@Controller
@RequestMapping("/view")
public class ViewAction {
	
	@Autowired
	private FilesService filesService;
	
	@Autowired
	private ProductService service;
	
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Product.class, "id","number", "name", "price", "remark", "details", "type", "files")
				.addRetainTerm(Files.class, "id", "name", "size", "url", "type");
	}
	
	/**
	 * 后台登陆跳转
	 * @param request
	 * @param prodcut
	 * @return
	 */
	@GetMapping(value = "/backLogin")
	public String login() {
		return Constants.LOGIN_URL;
	}
	
	/**
	 * 后台首页跳转
	 * @param request
	 * @param prodcut
	 * @return
	 */
	@GetMapping(value = "/backIndex")
	public String backIndex() {
		return Constants.INDEX_BACK_URL;
	}
	
	
	/**
	 *	前台根据不同菜单跳转不同的jsp
	 */
	@GetMapping(value = "/menusToUrl")
	public String menusToJsp(HttpServletRequest request,String url,String paramName , String paramNum) {
		System.out.println(url+"&"+paramName+"="+paramNum);
		return url+"&"+paramName+"="+paramNum;
	}
	
	
	/**
	 * 前台获取图片列表
	 * @param request
	 * @param prodcut
	 * @return
	 */
	@GetMapping(value = "/files/getPicture")
	@ResponseBody
	public CommonResponse getPicture(HttpServletRequest request,String locationType) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(Files.class,"name","url","type","id","title","content")
				.format(filesService.findByType(locationType)).toJSON());
		cr.setMessage("成功");
		return cr;
	}
	
	
	/**
	 * 
	 * 产品详情
	 * @param request
	 * @param prodcut
	 * @return
	 */
	@GetMapping(value = "/product/getProduct")
	public CommonResponse getProduct(HttpServletRequest request, Long id) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(service.findOne(id).get()).toJSON());
		cr.setMessage("查找成功");
		return cr;
	}

}
