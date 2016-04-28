/**
 * Copyright (c) 2015-2016, Michael Yang 杨福海 (fuhai999@gmail.com).
 *
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jpress.controller.admin;

import java.util.Date;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;

import io.jpress.core.JBaseCRUDController;
import io.jpress.core.Jpress;
import io.jpress.core.annotation.UrlMapping;
import io.jpress.interceptor.UCodeInterceptor;
import io.jpress.model.Content;
import io.jpress.model.ModelSorter;
import io.jpress.template.Module;
import io.jpress.wechat.WechatReplay;

@UrlMapping(url = "/admin/wechat", viewPath = "/WEB-INF/admin/wechat")
public class _WechatController extends JBaseCRUDController<Content> {

	private String getModule() {
		return WechatReplay.MODULE;
	}

	private String getStatus() {
		return getPara("s");
	}

	@Override
	public void index() {
		super.index();
	}

	
	public void menu() {
		List<Content> list = Content.DAO.findByModule("wechat_menu", "order_number ASC");
		ModelSorter.sort(list);
		setAttr("wechat_menus", list);

		long id = getParaToLong("id", (long) 0);
		if (id > 0) {
			Content c = Content.DAO.findById(id);
			setAttr("wechat_menu", c);
		}
	}

	@Before(UCodeInterceptor.class)
	public void menuSave() {
		Content c = getModel(Content.class);
		c.setModule("wechat_menu");
		c.setModified(new Date());
		if (c.getCreated() == null ) {
			c.setCreated(new Date());
		}
		c.saveOrUpdate();
		renderAjaxResultForSuccess();
	}
	
	
	@Before(UCodeInterceptor.class)
	public void menuDel() {
		long id = getParaToLong("id", (long) 0);
		if (id > 0) {
			if (Content.DAO.deleteById(id)) {
				renderAjaxResultForSuccess();
			}
		}
		renderAjaxResultForError();
	}
	
	public void menuSync() {
		
	}
	
	

	@Override
	public Page<Content> onIndexDataLoad(int pageNumber, int pageSize) {
		if (getStatus() != null && !"".equals(getStatus().trim())) {
			return mDao.doPaginateByModuleAndStatus(pageNumber, pageSize, getModule(), getStatus());
		}
		return mDao.doPaginateByModuleInNormal(pageNumber, pageSize, getModule());
	}

	@Before(UCodeInterceptor.class)
	public void trash() {
		long id = getParaToLong("id");
		Content c = Content.DAO.findById(id);
		if (c != null) {
			c.setStatus(Content.STATUS_DELETE);
			c.saveOrUpdate();
			renderAjaxResultForSuccess("success");
		} else {
			renderAjaxResultForError("trash error!");
		}
	}

	@Before(UCodeInterceptor.class)
	public void batchTrash() {
		Long[] ids = getParaValuesToLong("dataItem");
		int count = mDao.batchTrash(ids);
		if (count > 0) {
			renderAjaxResultForSuccess("success");
		} else {
			renderAjaxResultForError("trash error!");
		}
	}

	@Before(UCodeInterceptor.class)
	public void delete() {
		long id = getParaToLong("id");
		Content c = Content.DAO.findById(id);
		if (c != null && c.isDelete()) {
			c.delete();
			renderAjaxResultForSuccess("success");
		} else {
			renderAjaxResultForError("restore error!");
		}
	}

	@Override
	public void edit() {
		keepPara();
		String moduleName = getModule();
		setAttr("m", moduleName);

		Module module = Jpress.currentTemplate().getModuleByName(moduleName);
		setAttr("module", module);

		String id = getPara("id");
		if (id != null) {
			setAttr("content", mDao.findById(id));
		}
		render("edit.html");
	}

}
