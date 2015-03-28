package com.spider.utils.test;

import com.spider.utils.RegexUtil;

public class TT {
	public static void main2(String[] args) {
		String str = "//@张雨绮: 太有才！[威武] //@电影白鹿原:不敢说“以一敌四”，国产电影也需要空间...//@电影白鹿原:不敢说“以一敌四”，国产电影也需要空间...//@电影白鹿原:不敢说“以一敌四”，国产电影也需要空间...";
		System.out.println(str);
		str = str.split("////@")[0].trim();

		System.out.println(str);
		System.out.println(str.startsWith("//@"));
		System.out.println(RegexUtil.getData("^//@[\\S]*:(.*?)//@", str));
	}

	public static void main(String[] args) {
		String str = "[奥特曼]//@张雨绮:太有才！[威武] //@电影白鹿原:不敢说“以一敌四”，国产电影也需要空间...";
		System.out.println(RegexUtil.getData("^(.*?)//@", str));

		String str2 = "回复@江畔饮酒垂钓:我素质高不高，至少我不喷脏话，有理说理，同样，怎么全世界得跟你保持一致不成，不一致就是渣男渣女？";
		System.out.println(RegexUtil.getData("^回复@[\\S]*:(.*)", str2));
	}
}
