package com.webcollector.util;

import com.sleepycat.je.DatabaseConfig;

public class BerkeleyDBUtils {
	public static DatabaseConfig defaultDBConfig;
	static {
		defaultDBConfig = createDefaultDBConfig();
	}

	public static DatabaseConfig createDefaultDBConfig() {
		DatabaseConfig databaseConfig = new DatabaseConfig();
		databaseConfig.setAllowCreate(true);
		databaseConfig.setDeferredWrite(true);
		return databaseConfig;
	}
}
