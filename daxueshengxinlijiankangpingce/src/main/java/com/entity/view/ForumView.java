package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.ForumEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 论坛
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("forum")
public class ForumView extends ForumEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表
	/**
	* 帖子状态的值
	*/
	@ColumnInfo(comment="帖子状态的字典表值",type="varchar(200)")
	private String forumStateValue;

	//级联表 用户
		/**
		* 用户姓名
		*/

		@ColumnInfo(comment="用户姓名",type="varchar(200)")
		private String yonghuName;
		/**
		* 用户头像
		*/

		@ColumnInfo(comment="用户头像",type="varchar(200)")
		private String yonghuPhoto;
		/**
		* 身份证号
		*/

		@ColumnInfo(comment="身份证号",type="varchar(200)")
		private String yonghuIdNumber;
		/**
		* 联系方式
		*/

		@ColumnInfo(comment="联系方式",type="varchar(200)")
		private String yonghuPhone;
		/**
		* 电子邮箱
		*/

		@ColumnInfo(comment="电子邮箱",type="varchar(200)")
		private String yonghuEmail;
		/**
		* 逻辑删除
		*/

		@ColumnInfo(comment="逻辑删除",type="int(11)")
		private Integer yonghuDelete;
	//级联表 咨询师
		/**
		* 咨询师姓名
		*/

		@ColumnInfo(comment="咨询师姓名",type="varchar(200)")
		private String zhixunshiName;
		/**
		* 咨询师头像
		*/

		@ColumnInfo(comment="咨询师头像",type="varchar(200)")
		private String zhixunshiPhoto;
		/**
		* 身份证号
		*/

		@ColumnInfo(comment="身份证号",type="varchar(200)")
		private String zhixunshiIdNumber;
		/**
		* 联系方式
		*/

		@ColumnInfo(comment="联系方式",type="varchar(200)")
		private String zhixunshiPhone;
		/**
		* 电子邮箱
		*/

		@ColumnInfo(comment="电子邮箱",type="varchar(200)")
		private String zhixunshiEmail;
		/**
		* 从业时长
		*/

		@ColumnInfo(comment="从业时长",type="varchar(200)")
		private String zhixunshiCongye;
		/**
		* 擅长
		*/

		@ColumnInfo(comment="擅长",type="varchar(200)")
		private String zhixunshiShanchang;
		/**
		* 个人简介
		*/

		@ColumnInfo(comment="个人简介",type="text")
		private String zhixunshiContent;
		/**
		* 逻辑删除
		*/

		@ColumnInfo(comment="逻辑删除",type="int(11)")
		private Integer zhixunshiDelete;
	//级联表 管理员
		/**
		* 用户名
		*/
		@ColumnInfo(comment="用户名",type="varchar(100)")
		private String uusername;
		/**
		* 密码
		*/
		@ColumnInfo(comment="密码",type="varchar(100)")
		private String upassword;
		/**
		* 角色
		*/
		@ColumnInfo(comment="角色",type="varchar(100)")
		private String urole;
		/**
		* 新增时间
		*/
		@ColumnInfo(comment="新增时间",type="timestamp")
		private Date uaddtime;

	//重复字段
			/**
			* 重复字段 的types
			*/
			@ColumnInfo(comment="重复字段 的types",type="Integer")
			private Integer sexTypes;
				@ColumnInfo(comment="重复字段 的值",type="varchar(200)")
				private String sexValue;


	public ForumView() {

	}

	public ForumView(ForumEntity forumEntity) {
		try {
			BeanUtils.copyProperties(this, forumEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	//当前表的
	/**
	* 获取： 帖子状态的值
	*/
	public String getForumStateValue() {
		return forumStateValue;
	}
	/**
	* 设置： 帖子状态的值
	*/
	public void setForumStateValue(String forumStateValue) {
		this.forumStateValue = forumStateValue;
	}


	//级联表的get和set 用户

		/**
		* 获取： 用户姓名
		*/
		public String getYonghuName() {
			return yonghuName;
		}
		/**
		* 设置： 用户姓名
		*/
		public void setYonghuName(String yonghuName) {
			this.yonghuName = yonghuName;
		}

		/**
		* 获取： 用户头像
		*/
		public String getYonghuPhoto() {
			return yonghuPhoto;
		}
		/**
		* 设置： 用户头像
		*/
		public void setYonghuPhoto(String yonghuPhoto) {
			this.yonghuPhoto = yonghuPhoto;
		}

		/**
		* 获取： 身份证号
		*/
		public String getYonghuIdNumber() {
			return yonghuIdNumber;
		}
		/**
		* 设置： 身份证号
		*/
		public void setYonghuIdNumber(String yonghuIdNumber) {
			this.yonghuIdNumber = yonghuIdNumber;
		}

		/**
		* 获取： 联系方式
		*/
		public String getYonghuPhone() {
			return yonghuPhone;
		}
		/**
		* 设置： 联系方式
		*/
		public void setYonghuPhone(String yonghuPhone) {
			this.yonghuPhone = yonghuPhone;
		}

		/**
		* 获取： 电子邮箱
		*/
		public String getYonghuEmail() {
			return yonghuEmail;
		}
		/**
		* 设置： 电子邮箱
		*/
		public void setYonghuEmail(String yonghuEmail) {
			this.yonghuEmail = yonghuEmail;
		}

		/**
		* 获取： 逻辑删除
		*/
		public Integer getYonghuDelete() {
			return yonghuDelete;
		}
		/**
		* 设置： 逻辑删除
		*/
		public void setYonghuDelete(Integer yonghuDelete) {
			this.yonghuDelete = yonghuDelete;
		}
	//级联表的get和set 咨询师

		/**
		* 获取： 咨询师姓名
		*/
		public String getZhixunshiName() {
			return zhixunshiName;
		}
		/**
		* 设置： 咨询师姓名
		*/
		public void setZhixunshiName(String zhixunshiName) {
			this.zhixunshiName = zhixunshiName;
		}

		/**
		* 获取： 咨询师头像
		*/
		public String getZhixunshiPhoto() {
			return zhixunshiPhoto;
		}
		/**
		* 设置： 咨询师头像
		*/
		public void setZhixunshiPhoto(String zhixunshiPhoto) {
			this.zhixunshiPhoto = zhixunshiPhoto;
		}

		/**
		* 获取： 身份证号
		*/
		public String getZhixunshiIdNumber() {
			return zhixunshiIdNumber;
		}
		/**
		* 设置： 身份证号
		*/
		public void setZhixunshiIdNumber(String zhixunshiIdNumber) {
			this.zhixunshiIdNumber = zhixunshiIdNumber;
		}

		/**
		* 获取： 联系方式
		*/
		public String getZhixunshiPhone() {
			return zhixunshiPhone;
		}
		/**
		* 设置： 联系方式
		*/
		public void setZhixunshiPhone(String zhixunshiPhone) {
			this.zhixunshiPhone = zhixunshiPhone;
		}

		/**
		* 获取： 电子邮箱
		*/
		public String getZhixunshiEmail() {
			return zhixunshiEmail;
		}
		/**
		* 设置： 电子邮箱
		*/
		public void setZhixunshiEmail(String zhixunshiEmail) {
			this.zhixunshiEmail = zhixunshiEmail;
		}

		/**
		* 获取： 从业时长
		*/
		public String getZhixunshiCongye() {
			return zhixunshiCongye;
		}
		/**
		* 设置： 从业时长
		*/
		public void setZhixunshiCongye(String zhixunshiCongye) {
			this.zhixunshiCongye = zhixunshiCongye;
		}

		/**
		* 获取： 擅长
		*/
		public String getZhixunshiShanchang() {
			return zhixunshiShanchang;
		}
		/**
		* 设置： 擅长
		*/
		public void setZhixunshiShanchang(String zhixunshiShanchang) {
			this.zhixunshiShanchang = zhixunshiShanchang;
		}

		/**
		* 获取： 个人简介
		*/
		public String getZhixunshiContent() {
			return zhixunshiContent;
		}
		/**
		* 设置： 个人简介
		*/
		public void setZhixunshiContent(String zhixunshiContent) {
			this.zhixunshiContent = zhixunshiContent;
		}

		/**
		* 获取： 逻辑删除
		*/
		public Integer getZhixunshiDelete() {
			return zhixunshiDelete;
		}
		/**
		* 设置： 逻辑删除
		*/
		public void setZhixunshiDelete(Integer zhixunshiDelete) {
			this.zhixunshiDelete = zhixunshiDelete;
		}
	//级联表的get和set 管理员
		/**
		* 获取： 用户名
		*/
		public String getUusername() {
			return uusername;
		}
		/**
		* 设置： 用户名
		*/
		public void setUusername(String uusername) {
			this.uusername = uusername;
		}
		/**
		* 获取： 密码
		*/
		public String getUpassword() {
			return upassword;
		}
		/**
		* 设置： 密码
		*/
		public void setUpassword(String upassword) {
			this.upassword = upassword;
		}
		/**
		* 获取： 角色
		*/
		public String getUrole() {
			return urole;
		}
		/**
		* 设置： 角色
		*/
		public void setUrole(String urole) {
			this.urole = urole;
		}
		/**
		* 获取： 新增时间
		*/
		public Date getUaddtime() {
			return uaddtime;
		}
		/**
		* 设置： 新增时间
		*/
		public void setUaddtime(Date uaddtime) {
			this.uaddtime = uaddtime;
		}

	//重复字段
			/**
			* 获取： 重复字段 的types
			*/
			public Integer getSexTypes() {
			return sexTypes;
			}
			/**
			* 设置： 重复字段 的types
			*/
			public void setSexTypes(Integer sexTypes) {
			this.sexTypes = sexTypes;
			}
				public String getSexValue() {
				return sexValue;
				}
				public void setSexValue(String sexValue) {
				this.sexValue = sexValue;
				}

	@Override
	public String toString() {
		return "ForumView{" +
			", forumStateValue=" + forumStateValue +
			", zhixunshiName=" + zhixunshiName +
			", zhixunshiPhoto=" + zhixunshiPhoto +
			", zhixunshiIdNumber=" + zhixunshiIdNumber +
			", zhixunshiPhone=" + zhixunshiPhone +
			", zhixunshiEmail=" + zhixunshiEmail +
			", zhixunshiCongye=" + zhixunshiCongye +
			", zhixunshiShanchang=" + zhixunshiShanchang +
			", zhixunshiContent=" + zhixunshiContent +
			", zhixunshiDelete=" + zhixunshiDelete +
			", yonghuName=" + yonghuName +
			", yonghuPhoto=" + yonghuPhoto +
			", yonghuIdNumber=" + yonghuIdNumber +
			", yonghuPhone=" + yonghuPhone +
			", yonghuEmail=" + yonghuEmail +
			", yonghuDelete=" + yonghuDelete +
			"} " + super.toString();
	}
}
