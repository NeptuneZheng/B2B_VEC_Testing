package cs.b2b.mapping.e2e.demo;

import java.io.File;
import java.sql.Connection;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import cs.b2b.beluga.api.BelugaApiUtil;
import cs.b2b.beluga.api.EDIProcessResult;
import cs.b2b.beluga.common.fileparser.UIFFileParser;
import cs.b2b.core.common.classloader.GroovyClassDefinition;
import cs.b2b.core.common.classloader.GroovyScriptHelper;
import cs.b2b.mapping.e2e.util.BelugaOceanHelper;
import cs.b2b.mapping.e2e.util.CommonUtil;
import cs.b2b.mapping.e2e.util.ConnectionForTester;
import cs.b2b.mapping.e2e.util.DemoBase;
import cs.b2b.mapping.e2e.util.LocalFileUtil;
import groovy.lang.GroovyObject;

public class DemoGroovy_Xml2EDI extends DemoBase {

	//1, provide your test file here
	static String testInputFileNamePath = "./demo/CT_JCPENNYYUSEN/1001_ASC-in_EDI2016111808262393-60.in.xml";
	///CSB2BEDIGroovyDevelopment/demo/CT_DSGOOGS/in_DSGOODS_CS210_DnD.xml
	
	//2, all mapping scripts
	//2.1, mapping JavaBean common script
	static String javaBeanCommonScriptFile = "./src/cs/b2b/core/mapping/bean/MessageBeanCS2XmlCommon.groovy";
	//2.2, mapping JavaBean message type script
	static String javaBeanMessageTypeScriptFile = "./src/cs/b2b/core/mapping/bean/ct/ContainerMovement.groovy";
	//2.3, Mapping Util library script file, if not use it, then not need to provide here
	static String mappingUtilScriptFile = "./src/cs/b2b/core/mapping/util/MappingUtil.groovy";
	//2.4, message type common groovy script
	static String mappingUtilMessageTypeCommonScriptFile = "./src/cs/b2b/core/mapping/util/MappingUtil_CT_O_Common.groovy";
	//2.5, pmt groovy script
	static String pmtMappingScriptFile = "./src/cs/b2b/mapping/scripts/CUS_CS2CTXML_315_JCPENNYYUSEN.groovy";
	
	public void demo() throws Exception {
		
		String[] runtimeParameters = new String[]{
				"AppSessionID="+System.currentTimeMillis(), 
				"OriginalSourceFileName="+new File(testInputFileNamePath).getName(),
				"MSG_REQ_ID=EDI-TEST-001-DUMMY",
				//for BelugaOcean EDI setting, use PMT info in table table B2B_EDI_TP_LBC_SETTING
				"TP_ID=DSGOODS", 
				"MSG_TYPE_ID=CT", 
				"DIR_ID=O",
				"MSG_FMT_ID=X.12"
		};
		
		//Mapping Part
		GroovyClassDefinition groovyDefClass = null;
		//db connection if you need to use
		Connection conn = null;
				
		try {
			long start = Calendar.getInstance().getTimeInMillis();
			
			//20161222 david
			LinkedHashMap<String, String> scripts = new LinkedHashMap<String, String>();
			//1, put message type javabean common script
			String cs2CommonBean = "cs.b2b.core.mapping.bean."+(new File(javaBeanCommonScriptFile).getName());
			scripts.put(cs2CommonBean, LocalFileUtil.readBigFile(javaBeanCommonScriptFile));
			//2, put message type javabean message script
			scripts.put(new File(javaBeanMessageTypeScriptFile).getName(), LocalFileUtil.readBigFile(javaBeanMessageTypeScriptFile));
			//3, put general mapping util script
			scripts.put(new File(mappingUtilScriptFile).getName(), LocalFileUtil.readBigFile(mappingUtilScriptFile));
			//4, put message type common script
			String pmtMappingUtil =new File(mappingUtilMessageTypeCommonScriptFile).getName();
			pmtMappingUtil = "cs.b2b.core.mapping.util.MappingUtil_CT_O_Common.groovy";
			scripts.put(pmtMappingUtil, LocalFileUtil.readBigFile(mappingUtilMessageTypeCommonScriptFile));
			//5, put pmt mapping script
			String pmtScriptName = new File(pmtMappingScriptFile).getName();
			scripts.put(pmtScriptName, LocalFileUtil.readBigFile(pmtMappingScriptFile));
			
			groovyDefClass = GroovyScriptHelper.getClassDef(scripts);
			
			GroovyObject instance = groovyDefClass==null?null:groovyDefClass.getInstance();
		
			
			String testInputFileBody = LocalFileUtil.readBigFile(testInputFileNamePath);
			ConnectionForTester testDBConn = new ConnectionForTester();
			conn = testDBConn.getB2BEDIQA1_DEV_DBConn();
			Object[] invokeParams = new Object[] { testInputFileBody, runtimeParameters, conn };
			
			String output = instance==null?null:(String) instance.invokeMethod("mapping", invokeParams);
			
			if (output==null || output.trim().length()==0) {
				throw new Exception("Mapping output is empty.");
			}
			
			long end = Calendar.getInstance().getTimeInMillis();
			
			//20170301 - definition updated to get from db setting - b2b_edi_beluga_cfg
			BelugaOceanHelper bhelper = new BelugaOceanHelper();
			bhelper.getBelugaOceanDefinitionSettingStr(runtimeParameters, conn);
			String definitionBody = bhelper.getDefinitionBody();
			if (! CommonUtil.isEmpty(definitionBody)) {
				UIFFileParser parser = new UIFFileParser();
				EDIProcessResult ediResult = parser.xml2edi(output, definitionBody, bhelper.getBelugaOceanConfigSettingStr());
				
				output = ediResult.outputString==null?"":ediResult.outputString.toString();
				
				BelugaApiUtil bapiutil = new BelugaApiUtil();
				String validationSummary = bapiutil.getErrorSummary(ediResult);
				List<String> validationInfos = bapiutil.getTxnWarning(ediResult);
				
				//20170207 update beluga to txn object mode
				if (validationSummary!=null && validationSummary.length()>0) {
					System.out.println("-----------------------------");
					System.out.println("Beluga Warning Info for Error Transaction: ");
					System.out.println("Summary: "+validationSummary);
					for(int i=0; validationInfos!=null && i<validationInfos.size(); i++) {
						System.out.println(validationInfos.get(i));
					}
					System.out.println("-----------------------------");
				}
				
				//20170109, david, add beluga handle edi control number
				if (output!=null && output.length()>0) {
					output = bhelper.setEDICtrlNumber(output, conn);
				}
			}
			
			println(">>"+new File(pmtMappingScriptFile).getName()+", cost: "+(end-start)+" ms, result:");
			println(output);
			
		} finally {
			if (conn!=null) {
				try { conn.close(); } catch (Exception e) {}
			}
			//important, clean up to avoid memory leak
			if (groovyDefClass!=null) {
				groovyDefClass.close();
			}
		}
	}
	
	
}
