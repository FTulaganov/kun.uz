package com.example.service;

import com.example.DTO.attach.AttachDTO;
import com.example.entity.AttachEntity;
import com.example.exps.ItemNotFoundExeption;
import com.example.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;

    public byte[] loadImage(String fileName) {
        byte[] imageInByte;

        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(new File("attaches/" + fileName));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }
/*

    public byte[] open_general2(String attachName) {
        // 20f0f915-93ec-4099-97e3-c1cb7a95151f.jpg
        int lastIndex = attachName.lastIndexOf(".");
        String id = attachName.substring(0,lastIndex);
        AttachEntity attachEntity = get(id);
        byte[] data;
        try {                                                     // attaches/2023/4/25/20f0f915-93ec-4099-97e3-c1cb7a95151f.jpg
            Path file = Paths.get("attaches/" + attachEntity.getPath() + "/" + attachName);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
*/

    public byte[] open_general(String attachName) {
        String[] lastIndex = attachName.split("\\.");
        Optional<AttachEntity> photo = attachRepository.findById(lastIndex[0]);
        byte[] data;
        if (photo.isEmpty()){
            return new byte[0];
        }

        try {
            Path file = Paths.get("attaches/" + photo.get().getPath() + "/" + attachName);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundExeption("Attach not Found");
        });
    }


    public Resource download(String fileName) {
        try {
            int lastIndex = fileName.lastIndexOf(".");
            String id = fileName.substring(0, lastIndex);
            AttachEntity attachEntity = get(id);

            Path file = Paths.get("attaches/" + attachEntity.getPath() + "/" + fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public String saveToSystem3(MultipartFile file) {
        try {
            String pathFolder = getYmDString(); // 2022/05/03
            File folder = new File("attaches/" + pathFolder);  // attaches/2023/05/3
            if (!folder.exists()) {
                folder.mkdirs();
            }
            byte[] bytes = file.getBytes();
            String extension = getExtension(file.getOriginalFilename());

            AttachEntity attachEntity = new AttachEntity();
            attachEntity.setCreatedData(LocalDateTime.now());
            attachEntity.setExtension(extension);
            attachEntity.setSize(file.getSize());
            attachEntity.setPath(pathFolder);
            attachEntity.setOriginalName(file.getOriginalFilename());
            attachRepository.save(attachEntity);

            Path path = Paths.get("attaches/" + pathFolder + "/" + attachEntity.getId() + "." + extension);
            // attaches/2023/04/26/uuid().jpg
            Files.write(path, bytes);
            return attachEntity.getId() + "." + extension;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Page<AttachDTO> pagingtion(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdData");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<AttachEntity> pageObj = attachRepository.findAll(pageable);
        Long totalCount = pageObj.getTotalElements();
        List<AttachEntity> entityList = pageObj.getContent();
        List<AttachDTO> dtoList = new LinkedList<>();
        for (AttachEntity entity : entityList) {
            AttachDTO dto = new AttachDTO();
            dto.setId(entity.getId());
            dto.setOriginalName(entity.getOriginalName());
            dto.setSize(entity.getSize());
            dto.setExtension(entity.getExtension());
            dto.setCreatedDate(entity.getCreatedData());
            dtoList.add(dto);
        }
        Page<AttachDTO> response = new PageImpl<AttachDTO>(dtoList, pageable, totalCount);
        return response;
    }
    public Boolean delete(String id) {
        AttachEntity attachEntity = attachRepository.findById(id).get();
        if (attachEntity==null){
            throw new ItemNotFoundExeption("Attach not found");
        }
        File myObj = new File("attaches/"  + "/" + attachEntity.getPath()+"/" +attachEntity.getId()+ "." + attachEntity.getExtension());
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
        attachRepository.deleteById(id);
        return true;
    }
}
