package ai.leo.quickstart.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "user")
public class UserEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@Column(name="id",length=255)
	private String id;
	@Column
	private String loginname;
	@Column
	private String mail;
	@Column
	private String name;
	@Column
	private String password;
	@Column
	private String role;					//超级管理员,管理员,产品人员,技能人员,
	@Column
	private String company;
	@Column
	private String whetherChecked;
	
	@Column
	private long createTimeStamp;
	
	@Column
	private String createTime;
	private String gitlabAccountId;

	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getWhetherChecked() {
		return whetherChecked;
	}
	public void setWhetherChecked(String whetherChecked) {
		this.whetherChecked = whetherChecked;
	}
	public long getCreateTimeStamp() {
		return createTimeStamp;
	}
	public void setCreateTimeStamp(long createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getGitlabAccountId() {
		return gitlabAccountId;
	}

	public void setGitlabAccountId(String gitlabAccountId) {
		this.gitlabAccountId = gitlabAccountId;
	}
}
