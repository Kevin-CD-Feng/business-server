package com.xtxk.cn.controller.minio;

import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.configurer.minio.MinioConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Optional;

@RestController
@RequestMapping("/pc")
@AspectLogger
public class MinioController {

    @Autowired
    private MinioConfig minioConfig;

    @GetMapping("/event/{day}/{fileName:.*}")
    public void previewFile(@PathVariable String day, @PathVariable String fileName, HttpServletResponse response) throws Exception {
        String prefix="pc/event/";
        String previewFile = day + "/" + fileName;
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(previewFile, "UTF-8"));
        InputStream inputStream = minioConfig.downLoad(previewFile);
        Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(previewFile);
        if (mediaType.isPresent()) {
            MediaType type = mediaType.get();
            response.setContentType(type.toString());
        } else {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        }
        IOUtils.copy(inputStream, response.getOutputStream());
    }
}