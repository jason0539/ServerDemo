package com.daoming.tvseries.controller;

import com.daoming.tvseries.dto.TvSeriesDto;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/tvseries")
public class TvSeriesController {
    private static final Log log = LogFactory.getLog(TvSeriesController.class);

//    @GetMapping
//    public Map<String, Object> sayHello() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("message", "hello,world");
//        return map;
//    }

    //curl http://127.0.0.1:8080/tvseries/
    @GetMapping
    public List<TvSeriesDto> getAll() {
        if (log.isDebugEnabled()) {
            log.debug("TvSeriesController.getAll被调用了");
        }
        List<TvSeriesDto> list = new ArrayList<>();
        list.add(createPoi());
        list.add(createWestWorld());
        return list;
    }

    //curl http://127.0.0.1:8080/tvseries/101
    @GetMapping("/{id}")
    public TvSeriesDto getOne(@PathVariable int id) {
        if (log.isDebugEnabled()) {
            log.debug("getOne " + id);
        }
        if (id == 101) {
            return createPoi();
        } else if (id == 102) {
            return createWestWorld();
        } else {
            throw new ResourceNotFoundException();
        }
    }


    //curl -H "Content-Type:application/json" -X POST --data '{"name":"西部世界","seasonCount":1,"originalRelease":"2016-10-2"}' http://127.0.0.1:8080/tvseries
    @PostMapping
    public TvSeriesDto insertOne(@RequestBody TvSeriesDto tvSeriesDto) {
        if (log.isDebugEnabled()) {
            log.debug("insertOne " + tvSeriesDto + "，这里应该写插入到数据库的代码");
        }
        tvSeriesDto.setId(9999);
        return tvSeriesDto;
    }

    //curl -F "photo=@your_photo_path.jpg" http://127.0.0.1:8080/tvseries/102/photos
    @PostMapping(value = "/{id}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addPhoto(@PathVariable int id, @RequestParam("photo") MultipartFile imgFile) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("addPhoto " + id + ",文件：" + imgFile.getOriginalFilename());
        }
        FileOutputStream fos = new FileOutputStream("target/" + imgFile.getOriginalFilename());
        IOUtils.copy(imgFile.getInputStream(),fos);
        fos.close();
    }

    //http://localhost:8080/tvseries/101/icon
    @GetMapping(value = "/{id}/icon", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getIcon(@PathVariable int id) throws Exception{
        if (log.isDebugEnabled()){
            log.debug("getIcon " + id);
        }
        String iconFile = "src/test/resources/icon.jpeg";
        InputStream inputStream = new FileInputStream(iconFile);
        return IOUtils.toByteArray(inputStream);
    }

    //curl -H "Content-Type:application/json" -X PUT --data '{"name":"西部世界","seasonCount":1,"originalRelease":"2016-10-2"}' http://127.0.0.1:8080/tvseries/101
    @PutMapping("/{id}")
    public TvSeriesDto updateOne(@PathVariable int id, @RequestBody TvSeriesDto tvSeriesDto) {
        if (log.isDebugEnabled()) {
            log.debug("updateOne " + id + ",这里应该写更新到数据库的代码");
        }
        if (id == 101 || id == 102) {
            return createWestWorld();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    //curl -X DELETE http://127.0.0.1:8080/tvseries/101\?delete_reason\=duplicated
    @DeleteMapping("/{id}")
    public Map<String, String> deleteOne(@PathVariable int id, HttpServletRequest request,
                                         @RequestParam(value = "delete_reason", required = false) String deleteReason) {
        if (log.isDebugEnabled()) {
            log.debug("deleteOne id = " + id);
        }
        Map result = new HashMap();
        if (id == 101) {
            result.put("message", "#101被" + request.getRemoteAddr() + "删除，原因：" + deleteReason);
        } else if (id == 102) {
            //AccessDeniedException更合适
            throw new RuntimeException("#102不能被删除");
        } else {
            throw new ResourceNotFoundException();
        }
        return result;
    }

    private TvSeriesDto createPoi() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.OCTOBER, 2, 0, 0);
        return new TvSeriesDto(101, "Person of interest", 1, calendar.getTime());
    }

    private TvSeriesDto createWestWorld() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.OCTOBER, 2, 0, 0);
        return new TvSeriesDto(102, "WestWorld", 1, calendar.getTime());
    }
}
