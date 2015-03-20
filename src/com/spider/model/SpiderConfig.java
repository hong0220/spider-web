package com.spider.model;

public class SpiderConfig implements java.io.Serializable {
	// 站点名字:比如 新浪新闻
	private String siteName;
	// 站点网址
	private String http;
	// url过滤正则
	private String Ruler;
	// 爬取深度
	private Integer depth;
	// 爬取线程
	private Integer thread;
	// 爬取时间间隔
	private Integer intervalTime;
	// 任务状态：是否启动->1启动0未开始
	private Integer status;
	// 新闻标题抽取规则
	private String titleRuler;
	// 内容标题抽取规则
	private String contentRuler;
	// 时间标题抽取规则
	private String timeRuler;
	// 来源标题抽取规则
	private String sourceRuler;
	// 评论标题抽取规则
	private String commentSumRuler;

	public SpiderConfig() {
		super();
	}

	public SpiderConfig(String siteName, String http, String ruler,
			Integer depth, Integer thread, Integer intervalTime,
			Integer status, String titleRuler, String contentRuler,
			String timeRuler, String sourceRuler, String commentSumRuler) {
		super();
		this.siteName = siteName;
		this.http = http;
		Ruler = ruler;
		this.depth = depth;
		this.thread = thread;
		this.intervalTime = intervalTime;
		this.status = status;
		this.titleRuler = titleRuler;
		this.contentRuler = contentRuler;
		this.timeRuler = timeRuler;
		this.sourceRuler = sourceRuler;
		this.commentSumRuler = commentSumRuler;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getHttp() {
		return http;
	}

	public void setHttp(String http) {
		this.http = http;
	}

	public String getRuler() {
		return Ruler;
	}

	public void setRuler(String ruler) {
		Ruler = ruler;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getThread() {
		return thread;
	}

	public void setThread(Integer thread) {
		this.thread = thread;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitleRuler() {
		return titleRuler;
	}

	public void setTitleRuler(String titleRuler) {
		this.titleRuler = titleRuler;
	}

	public String getContentRuler() {
		return contentRuler;
	}

	public void setContentRuler(String contentRuler) {
		this.contentRuler = contentRuler;
	}

	public String getTimeRuler() {
		return timeRuler;
	}

	public void setTimeRuler(String timeRuler) {
		this.timeRuler = timeRuler;
	}

	public String getSourceRuler() {
		return sourceRuler;
	}

	public void setSourceRuler(String sourceRuler) {
		this.sourceRuler = sourceRuler;
	}

	public String getCommentSumRuler() {
		return commentSumRuler;
	}

	public void setCommentSumRuler(String commentSumRuler) {
		this.commentSumRuler = commentSumRuler;
	}

	@Override
	public String toString() {
		return "SpiderConfig [siteName=" + siteName + ", http=" + http
				+ ", Ruler=" + Ruler + ", depth=" + depth + ", thread="
				+ thread + ", intervalTime=" + intervalTime + ", status="
				+ status + ", titleRuler=" + titleRuler + ", contentRuler="
				+ contentRuler + ", timeRuler=" + timeRuler + ", sourceRuler="
				+ sourceRuler + ", commentSumRuler=" + commentSumRuler + "]";
	}
}
