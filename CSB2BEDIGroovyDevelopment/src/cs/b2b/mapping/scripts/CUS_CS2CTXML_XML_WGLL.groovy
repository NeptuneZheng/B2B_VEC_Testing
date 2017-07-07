package cs.b2b.mapping.scripts

import cs.b2b.core.mapping.bean.LocDT
import cs.b2b.core.mapping.bean.ct.*
import cs.b2b.core.mapping.util.XmlBeanParser
import groovy.xml.MarkupBuilder

import java.sql.Connection

class CUS_CS2CTXML_XML_WGLL {


    cs.b2b.core.mapping.util.MappingUtil util = new cs.b2b.core.mapping.util.MappingUtil();
    cs.b2b.core.mapping.util.MappingUtil_CT_O_Common ctUtil = new cs.b2b.core.mapping.util.MappingUtil_CT_O_Common(util);

    def appSessionId = null
    def sourceFileName = null
    def TP_ID = null
    def MSG_TYPE_ID = null
    def DIR_ID = null
    def MSG_REQ_ID = null
    Connection conn = null

    def xmlDateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss"
    def yyyyMMdd = "yyyyMMdd"
    def HHmm = 'HHmm'
    java.util.Date currentDate = new java.util.Date()


    public void generateBody(Body current_Body, Header ctHeader, MarkupBuilder outXml) {

    }

    String mapping(String inputXmlBody, String[] runtimeParams, Connection conn) {

        XmlBeanParser parser = new XmlBeanParser()
        ContainerMovement ct = parser.xmlParser(inputXmlBody, ContainerMovement.class)

        def writer = new StringWriter()
        def outXml = new MarkupBuilder(new IndentPrinter(new PrintWriter(writer), "", false));
        outXml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
        outXml.omitEmptyAttributes = true
        outXml.omitNullAttributes = true



        def bizKeyWriter = new StringWriter();
        def bizKeyXml = new MarkupBuilder(new IndentPrinter(new PrintWriter(bizKeyWriter), "", false));
        bizKeyXml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")

        def csuploadWriter = new StringWriter();
        def csuploadXml = new MarkupBuilder(new IndentPrinter(new PrintWriter(csuploadWriter), "", false));
        csuploadXml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")

        String result = "";
        result = writer?.toString()
        result+="\r\n<CargoTrackingXML>\r\n</CargoTrackingXML>"

        writer.close();
        csuploadWriter.close()
        bizKeyWriter.close()



        return result;
    }


}
