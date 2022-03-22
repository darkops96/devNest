import java.io.IOException;
import java.sql.Blob;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Document;

import org.apache.tomcat.util.http.parser.MediaType;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import es.urjc.dad.devNestInternalService.Internal_Services.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class FileController {
    
    @Autowired
    private FileService fileService;

    @GetMapping("/download-videogame/{id}")
    public ResponseEntity getVideogame(@PathVariable long id) throws Exception
    {
        if(fileService.isDownloable(id)){
            return fileService.download(id);
        }
        else{       
            return (ResponseEntity) ResponseEntity.notFound();
        }
    }
    
    /*
    @GetMapping(value = "/zip-download", produces="application/zip")
    public void zipDownload(@RequestParam String filePath, HttpServletResponse response) throws IOException{
        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
        //Esto sirve para meter el archivo con filePath en un ZIP aunque ya debería ser un ZIP por lo que sobraría
            FileSystemResource resource = new FileSystemResource(filePath);
            ZipEntry zipEntry = new ZipEntry(resource.getFilename());
            zipEntry.setSize((resource.contentLength()));
            zipOut.putNextEntry(zipEntry);
            StreamUtils.copy(resource.getInputStream(),zipOut);
            zipOut.closeEntry();
            zipOut.finish();
            zipOut.close();
        ///////////
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filePath + "\"");
    }
*/
  
}
