package com.cvsintellect.service;

import com.scribtex.clsi.ClsiService;
import com.scribtex.model.ClsiOutputFile;
import com.scribtex.model.ClsiOutputLogFile;
import com.scribtex.model.ClsiServiceCompileResponse;

import java.util.ArrayList;

public class LatexService {
	private static final String CLSI_DOMAIN = "localhost:3319";
	private static final String CLSI_TOKEN = "bc53e7e76d42edc22ab003f220348a3c";

	public static String getPDFLink(String texFile, String compiler) throws Exception {
		ClsiService clsiService = new ClsiService("http://" + CLSI_DOMAIN + "/clsi/compile", CLSI_TOKEN);

		ClsiServiceCompileResponse clsiServiceResponse = clsiService.compile("GHwuFbBo8gWde34X5jpjwAyz6Ck_", "UntitledDocument", texFile, compiler, "pdf");
		ArrayList<ClsiOutputFile> outputFiles = clsiServiceResponse.getOutputFiles();

		if (outputFiles == null || outputFiles.size() == 0) {
			ArrayList<ClsiOutputLogFile> outputLogs = clsiServiceResponse.getOutputLogs();
			if (outputLogs != null && !outputLogs.isEmpty()) {
				return outputLogs.get(0).getUrl();
			}
			throw new RuntimeException("Empty response from server");
		}

		return outputFiles.get(0).getUrl();
	}
}
