package utils;

import java.io.IOException;

public class Test {
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		MybatisGeneratorUtil.url = "jdbc:mysql://localhost:3306/communityservicesys";
		MybatisGeneratorUtil.user = "root";
		MybatisGeneratorUtil.password = "iamtop";
		MybatisGeneratorUtil.javaPath = "com.model";
		MybatisGeneratorUtil.mapPath = "com.mapper";
		MybatisGeneratorUtil.daoPath = "com.dao";
		MybatisGeneratorUtil.tableName = "cs_sys_role";
		MybatisGeneratorUtil.domainName = "Role";
		
		MybatisGeneratorUtil.init();
	}
}
