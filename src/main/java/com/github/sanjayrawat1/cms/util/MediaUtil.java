package com.github.sanjayrawat1.cms.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sanjayrawat1.cms.enumeration.MediaType;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Sanjay Singh Rawat
 */
@Slf4j
public class MediaUtil {

    public static MediaType detectMediaType(MultipartFile file) {
        try {
            Metadata metadata = new Metadata();
            metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, file.getOriginalFilename());
            String mimeType = new Tika().detect(file.getInputStream(), metadata);
            if (org.springframework.http.MediaType.TEXT_PLAIN_VALUE.equals(mimeType)) {
                new ObjectMapper().readTree(file.getInputStream());
                mimeType = org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
            }
            return MediaType.getByValue(mimeType);
        } catch (IOException ex) {
            log.error("Unable to detect the mime type", ex);
        }
        return MediaType.UNKNOWN;
    }

    public static Dimension getDimension(MultipartFile file) throws IOException {
        MediaType mediaType = detectMediaType(file);
        return getDimension(file, mediaType);
    }

    private static Dimension getDimension(MultipartFile file, MediaType mediaType) throws IOException {
        return switch (mediaType) {
            case JPEG -> {
                ImageIcon imageIcon = new ImageIcon(file.getBytes());
                yield new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
            }
            case PNG -> {
                BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
                yield new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight());
            }
            default -> throw new IllegalStateException("Unexpected media type");
        };
    }
}
