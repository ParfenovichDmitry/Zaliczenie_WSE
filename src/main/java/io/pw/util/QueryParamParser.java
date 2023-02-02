package io.pw.util;

import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pwykowski
 */
public class QueryParamParser {

	public List<UriParamParser> parse(URI uri) {
		List<UriParamParser> params = new ArrayList<>();

		String[] stringParams = uri.getQuery().split("&");

		for (String stringParam : stringParams) {
			String[] paramsAndValue = stringParam.split("=");
			params.add(new UriParamParser(paramsAndValue[0],paramsAndValue[1]));
		}

		return params;
	}
}
