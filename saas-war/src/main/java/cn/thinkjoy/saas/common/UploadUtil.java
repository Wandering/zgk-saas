package cn.thinkjoy.saas.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by liusven on 2016/11/1.
 */
public class UploadUtil
{
    public static String uploadFile(HttpServletRequest request) throws IOException
    {
        String filePath = "";
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
            request.getSession().getServletContext());
        if(multipartResolver.isMultipart(request))
        {
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            Iterator iter=multiRequest.getFileNames();
            while(iter.hasNext())
            {
                MultipartFile uploadFile=multiRequest.getFile(iter.next().toString());
                if(uploadFile!=null)
                {
                    String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
                    path=path.substring(1, path.indexOf("classes")).replaceFirst("ile:","");
                    File distFile = new File(path + uploadFile.getOriginalFilename());
                    uploadFile.transferTo(distFile);
                    MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
                    FileSystemResource resource = new FileSystemResource(distFile);
                    RestTemplate template = new RestTemplate();
                    param.add("file", resource);
                    param.add("productCode", "gk360");
                    param.add("bizSystem", "gk360");
                    param.add("spaceName", "gk360");
                    param.add("userId", "gk360");
                    param.add("dirId", "0");
                    template.getMessageConverters().add(new FastJsonHttpMessageConverter());
                    String returnJson =
                        template.postForObject("http://cs-dev.thinkjoy.com.cn/rest/v1/uploadFile", param, String.class);
                    UploadFileReturn uploadFileReturn = JSON.parseObject(returnJson, UploadFileReturn.class);
                    if (uploadFileReturn != null && "0000000".equals(uploadFileReturn.getRtnCode()))
                    {
                        filePath = uploadFileReturn.getBizData().getFile().getFileUrl();
                        boolean delete = distFile.delete();
                        while (!delete)
                        {
                            delete = distFile.delete();
                        }
                    }
                }
            }
        }
        return filePath;
    }
}
