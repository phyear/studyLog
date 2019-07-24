package com.example.testmybatis.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    /**
     * 上传文件
     * @param uploadFile
     * @param prefix
     * @return 上传后路径
     */
    public static String  uploadFile(MultipartFile uploadFile,String prefix){
         String filename=uploadFile.getOriginalFilename();
         String suffix=filename.substring(filename.lastIndexOf("."));
         String  name=UUID.randomUUID().toString().replace("-", "");
         StringBuffer path=new StringBuffer();
         path.append(prefix);
         path.append(name);
         path.append(suffix);

         File file=new File(path.toString());

         if(!file.getParentFile().exists()){
              file.getParentFile().mkdir();
               }
        try {
            uploadFile.transferTo(file);
            return path.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

     }

    /**
     * 下载文件
     * @param response
     * @param prefix
     * @param filename
     * @return
     */

    public static int downFile(HttpServletResponse response,String prefix,String filename){
        File file=new File(prefix+filename);
        FileInputStream fis=null;
        OutputStream os=null;
        try {
            fis=new FileInputStream(file);
            response.setContentType("application/force-download");
            response.addHeader("Content-disposition", "attachment;fileName=" +filename);
            os=response.getOutputStream();
            byte[] buf = new byte[1024];
            int len=fis.read(buf);
            if(len!=-1){
              os.write(buf,0,len);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        finally {
            try {
                fis.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return 1;
    }

    public static void batchFileToZIP(List<Map<String, String>> parms, ByteArrayOutputStream byteOutPutStream) {

        ZipOutputStream zipOut = new ZipOutputStream(byteOutPutStream);

        FileInputStream inputStream = null;
        try {
            for (Map<String, String> value : parms) {
                // 文件路径
                inputStream = new FileInputStream(new File(value.get("filePath")));
                // 使用指定名称创建新的 ZIP 条目 （通俗点就是文件名）
                ZipEntry zipEntry = new ZipEntry(value.get("fileName"));
                // 开始写入新的 ZIP 文件条目并将流定位到条目数据的开始处
                zipOut.putNextEntry(zipEntry);

                byte[] b = new byte[1024];

                int length = 0;

                while ((length = inputStream.read(b)) != -1) {

                    zipOut.write(b, 0, length);

                }
                zipOut.closeEntry();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                zipOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
