# springMvc文件上传和下载

## 文件上传
使用`MultipartFile`接收文件

大致步骤：
1. 获取文件
2. 进行文件和保存路径的操作
3. 判断文件是否存在，不存在就新建一个
4. 保存文件，并进行其他操作（存入数据库等）
5. 返回操作提示信息

```
    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file){
        //获取文件名
        String fileName=file.getOriginalFilename();
        //得到文件的格式
        String suffix=fileName.substring( fileName.lastIndexOf("."));
        //拼接文件要保存的绝对路径
        StringBuffer newPath=new StringBuffer();
        newPath.append(defaultPath);
        newPath.append(new Date().getTime());
        newPath.append(suffix);

        //一个服务器上的文件，newPath为服务器上保存的路径
        File saveFile=new File(newPath.toString());
        //判断服务器上的父文件夹是否存在，没有就新建一个
        if(!saveFile.getParentFile().exists()){

            saveFile.getParentFile().mkdir();
        }

        try {
                //文件传输
                file.transferTo(saveFile);
                return  "上传成功";
        } catch (IOException e) {
                e.printStackTrace();
        }

       return  "上传失败";
    }
```
> form 表单必须添加`enctype="multipart/form-data"`

## 文件下载
使用流操作
基本步骤：
1. 获取将要下载文件的路径
2. 根据路径，New一个File
3. 转换成文件输入流（FileInputStream）
4. 设置响应格式和信息
5. 通过response获得输出流
6. 读取文件输入流再输出。
7. 关闭流

```
 //创建一个File
        File file=new File(defaultPath+path);
        //把File转换成文件流
        FileInputStream fileInputStream=new FileInputStream(file);
        //设置下载的格式和信息
        response.setContentType("application/force-download");
        response.addHeader("Content-disposition", "attachment;fileName=" +path);
        //获取输出流
        OutputStream os=response.getOutputStream();
        //读取的长度
         byte[] buf = new byte[1024];
         //是否为空
        int len=fileInputStream.read(buf);
        if(len!=-1){
            //输出
            os.write(buf, 0, len);
        }
        //关闭流
        fileInputStream.close();
        os.close();
```

## 工具类的制作
创建一个FIleUtil类

```
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
}
```

# 多文件下载和上传

## 多文件下载
多文件直接打包成zip格式下载

大致步骤：
1. 获取要下载的文件，按文件名和文件路径的方式存储为List
2. 生成一个数组输出流(ByteArrayOutputStream)
3. 将数组输出流转为ZipOutputStream
4. 遍历获取List中的文件输入流，并写入zip输出流
5. 设置返回zip文件的名称和响应信息等

> 对第三第四步进行了封装成工具类方法
```
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

```

>Controller中的内容
```
@RequestMapping("/downLoadFiles")
    public ResponseEntity<byte[]> downLoadFiles() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        List<Map<String,String>> list=new ArrayList<>();
       //添加数据
        Map<String,String> map=new HashMap<>();
        map.put("fileName","考题答案_mysql.sql");
        map.put("filePath","D:/upload/1563849029848.sql");

        Map<String,String> map2=new HashMap<>();
        map2.put("fileName","common.txt");
        map2.put("filePath","d:/upload/1e4febe4e3f043798a9a430fed39a4f1.txt");
        list.add(map);
        list.add(map2);
        //写入流
        FileUtil.batchFileToZIP(list, byteArrayOutputStream);
        HttpHeaders headers = new HttpHeaders();
      
        String fileName = null;
        try {
            fileName = new String("附件.zip".getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
  
        headers.setContentDispositionFormData("attachment", fileName);// 文件名称

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), headers,
                HttpStatus.CREATED);

        return responseEntity;
     }

```

## 多文件上传
> 使用MultipartFile数组接收
```
@PostMapping("/uploads")
    public String uploadFiles(@RequestParam("files") MultipartFile[] files){
       if(files!=null&&files.length>0){
           Arrays.stream(files).forEach(f->{
               FileUtil.uploadFile(f,defaultPath);//将前面的当文件上传提取成工具类
           });
        }
       return "redirect:/downLoad";
    }
```
>注意：前端input的name记得一致




