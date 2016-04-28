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
package io.jpress.model.base;

import io.jpress.core.JModel;
import io.jpress.model.Metadata;

import java.util.List;
import java.math.BigInteger;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 *  Auto generated by JPress, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseAttachment<M extends BaseAttachment<M>> extends JModel<M> implements IBean {

	public static final String CACHE_NAME = "attachment";
	public static final String METADATA_TYPE = "attachment";

	public void removeCache(String key){
		CacheKit.remove(CACHE_NAME, key);
	}

	public void putCache(Object key,Object value){
		CacheKit.put(CACHE_NAME, key, value);
	}

	public M getCache(Object key){
		return CacheKit.get(CACHE_NAME, key);
	}

	public Metadata findMetadata(String key){
		return Metadata.findByTypeAndIdAndKey(METADATA_TYPE, getId(), key);
	}

	public List<Metadata> findMetadataList(){
		return Metadata.findListByTypeAndId(METADATA_TYPE, getId());
	}

	public M findFirstFromMetadata(String key,Object value){
		Metadata md = Metadata.findFirstByTypeAndValue(METADATA_TYPE, key, value);
		if(md != null){
			BigInteger id = md.getObjectId();
			return findById(id);
		}
		return null;
	}

	public Metadata createMetadata(){
		Metadata md = new Metadata();
		md.setObjectId(getId());
		md.setObjectType(METADATA_TYPE);
		return md;
	}

	public Metadata createMetadata(String key,String value){
		Metadata md = new Metadata();
		md.setObjectId(getId());
		md.setObjectType(METADATA_TYPE);
		md.setMetaKey(key);
		md.setMetaValue(value);
		return md;
	}

	public void setId(java.math.BigInteger id) {
		set("id", id);
	}

	public java.math.BigInteger getId() {
		return get("id");
	}

	public void setTitle(java.lang.String title) {
		set("title", title);
	}

	public java.lang.String getTitle() {
		return get("title");
	}

	public void setUserId(java.math.BigInteger userId) {
		set("user_id", userId);
	}

	public java.math.BigInteger getUserId() {
		return get("user_id");
	}

	public void setContentId(java.lang.Long contentId) {
		set("content_id", contentId);
	}

	public java.lang.Long getContentId() {
		return get("content_id");
	}

	public void setPath(java.lang.String path) {
		set("path", path);
	}

	public java.lang.String getPath() {
		return get("path");
	}

	public void setMimeType(java.lang.String mimeType) {
		set("mime_type", mimeType);
	}

	public java.lang.String getMimeType() {
		return get("mime_type");
	}

	public void setSuffix(java.lang.String suffix) {
		set("suffix", suffix);
	}

	public java.lang.String getSuffix() {
		return get("suffix");
	}

	public void setCreated(java.util.Date created) {
		set("created", created);
	}

	public java.util.Date getCreated() {
		return get("created");
	}

}
