package com.xtxk.cn.service.alarm;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class DocumentManager {
    public HashMap<String, String> generatorPDF(String docTemplatePath, Map<String, Object> map) {
        LocalDateTime time = LocalDateTime.now();
        HashMap<String, String> retMap = new HashMap<>();
        String localTime = time.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String fileName = time.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String wordPath = docTemplatePath + File.separator + localTime + File.separator + fileName + ".docx";
        retMap.put("wordPath", wordPath);
        String pdfPath = docTemplatePath + File.separator + localTime + File.separator + fileName + ".pdf";
        boolean result = this.convertToWord(docTemplatePath, wordPath, map);
        if (result) {
            if (this.WordToPDF(wordPath, pdfPath)) {
                retMap.put("pdfPath", pdfPath);
            }
        }
        return retMap;
    }
    private boolean convertToWord(String fileDir, String outWordFile, Map<String, Object> tableData) {
        Configuration configuration=new Configuration();
        File oriFile =new File(fileDir);
        configuration.setDefaultEncoding("UTF-8");
        try {
            configuration.setDirectoryForTemplateLoading(oriFile);
            Template template = configuration.getTemplate("eventReport.ftl","UTF-8");
            mkdirs(outWordFile);
            File outFile = new File(outWordFile);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8));
            template.process(tableData,out);
        } catch (Exception e) {
            log.error("Convert To Word fire error:{}",e.getMessage());
            return false;
        }
        return true;
    }
    private boolean WordToPDF(String wordPath, String pdfPath) {
        boolean result = true;
        File file = new File(pdfPath);
        FileOutputStream outputStream = null;
        try {
            // 授权去除pdf顶部水印
            if(!getLicenseDoc()){
                return false;
            }
            outputStream = new FileOutputStream(file);
            Document doc = new Document(wordPath);
            doc.save(outputStream, SaveFormat.PDF);
            outputStream.flush();
            outputStream.close();
        } catch (Exception ex) {
            //LogUtil.LOG.error(ex.getMessage());
            log.error("WordToPDF fires errors:{}",ex.getMessage());
            result = false;
        } finally {
            IOUtils.closeQuietly(outputStream);
            //IOUtils.closeQuietly(file);
        }
        return result;
    }
    private boolean getLicenseDoc() {
        String licenseXml = "<License>\n" +
                "    <Data>\n" +
                "        <Products>\n" +
                "            <Product>Aspose.Total for Java</Product>\n" +
                "            <Product>Aspose.Words for Java</Product>\n" +
                "        </Products>\n" +
                "        <EditionType>Enterprise</EditionType>\n" +
                "        <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                "        <LicenseExpiry>20991231</LicenseExpiry>\n" +
                "        <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                "    </Data>\n" +
                "    <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=\n" +
                "    </Signature>\n" +
                "</License>";
        License license = new License();
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(licenseXml.getBytes(StandardCharsets.UTF_8));
            license.setLicense(inputStream);
            return true;
        } catch (Exception ex) {
            //LogUtil.LOG.error(ex.getMessage());
        }
        return false;
    }
    private void mkdirs(String path) {
        File desc = new File(path);
        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
    }
}