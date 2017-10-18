package app;

import com.mongodb.gridfs.GridFSDBFile;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileController {
    private final GridFsTemplate gridFs;

    @Autowired
    public FileController(GridFsTemplate gridFs) {
        this.gridFs = gridFs;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<File> listFiles() {
        final List<GridFSDBFile> list = gridFs.find(null);
        final List<File> listFiles = new ArrayList<>();
        list.forEach(gridFSDBFile -> listFiles.add(convertToFile(gridFSDBFile)));

        return listFiles;
    }

    private File convertToFile(final GridFSDBFile file) {
        return new File(file.getFilename());
    }

    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public void handleFileDownload(@RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response) {
        final GridFSDBFile file = gridFs.findOne(new Query().addCriteria(Criteria.where("filename").is(name)));

        if (file != null) {
            try {
                response.setContentType(file.getContentType());
                response.setContentLength((new Long(file.getLength()).intValue()));
                response.setHeader("content-Disposition", "attachment; filename=" + file.getFilename());
                IOUtils.copyLarge(file.getInputStream(), response.getOutputStream());
            } catch (IOException ignored) {

            }
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String handleFileDelete(@RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response) {
        final GridFSDBFile file = gridFs.findOne(new Query().addCriteria(Criteria.where("filename").is(name)));

        if (file == null) return "File not found.";

        gridFs.delete(new Query().addCriteria(Criteria.where("filename").is(name)));
        return "File " + file.getFilename() + " was deleted.";
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty())
            return "You failed to upload " + name + " because the file was empty.";

        try {
            gridFs.store(file.getInputStream(), name);

            return "You successfully uploaded " + name + "!";
        } catch (Exception e) {
            return "You failed to upload " + name + " => " + e.getMessage();
        }
    }

}
