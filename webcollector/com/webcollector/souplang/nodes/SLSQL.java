package com.webcollector.souplang.nodes;

import com.webcollector.souplang.nodes.InputTypeErrorException;
import com.webcollector.souplang.nodes.SLSQL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.webcollector.souplang.Context;
import com.webcollector.souplang.LangNode;
import com.webcollector.util.JDBCHelper;

public class SLSQL extends LangNode {
	public static final Logger LOG = LoggerFactory.getLogger(SLSQL.class);
	public String template = null;
	public String sql = null;
	public String params = null;
	public String[] paramarray = null;

	public void readParams(org.w3c.dom.Element xmlElement) {
		params = xmlElement.getAttribute("params").trim();
		if (params.isEmpty()) {
			params = null;
		} else {
			paramarray = params.split(",");
		}
	}

	public void readSql(org.w3c.dom.Element xmlElement) {
		sql = xmlElement.getAttribute("sql");
		if (sql.isEmpty()) {
			sql = null;
		}
	}

	public void readTemplate(org.w3c.dom.Element xmlElement) {
		template = xmlElement.getAttribute("template");
		if (template.isEmpty()) {
			template = null;
		}
	}

	@Override
	public Object process(Object input, Context context)
			throws InputTypeErrorException {
		if (input == null) {
			return null;
		}
		if (sql == null || template == null) {
			return null;
		}
		JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate(template);
		if (jdbcTemplate == null) {
			LOG.info("please create jdbctemplate");
			return null;
		}
		int paramsLength = paramarray.length;
		int updates;
		if (params != null) {
			String[] sqlParams = new String[paramsLength];
			for (int i = 0; i < paramsLength; i++) {
				sqlParams[i] = context.getString(paramarray[i]);
			}
			updates = jdbcTemplate.update(sql, sqlParams);

		} else {
			updates = jdbcTemplate.update(sql);
		}
		LOG.info(sql + params + "    result=" + updates);
		return null;
	}

	@Override
	public boolean validate(Object input) throws Exception {
		return true;
	}
}
