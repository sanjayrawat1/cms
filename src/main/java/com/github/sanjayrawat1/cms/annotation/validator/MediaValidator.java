package com.github.sanjayrawat1.cms.annotation.validator;

import com.github.sanjayrawat1.cms.annotation.ValidMedia;
import com.github.sanjayrawat1.cms.enumeration.MediaType;
import com.github.sanjayrawat1.cms.util.MediaUtil;
import java.awt.*;
import java.io.IOException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.multipart.MultipartFile;

/**
 * Check that the media being uploaded is valid as per the constraint specified.
 *
 * @author Sanjay Singh Rawat
 */
public class MediaValidator implements ConstraintValidator<ValidMedia, MultipartFile> {

    private static final DataSize MAX_IMAGE_SIZE_IN_MB = DataSize.of(1, DataUnit.MEGABYTES);
    private static final DataSize MAX_VIDEO_SIZE_IN_MB = DataSize.of(100, DataUnit.MEGABYTES);
    private static final Dimension VALID_IMAGE_DIMENSION = new Dimension(178, 48);
    private static final String MEDIA_NOT_NULL_MESSAGE = "MEDIA MUST NOT BE NULL";
    private static final String INVALID_MEDIA_SIZE_MESSAGE = "";
    private static final String INVALID_IMAGE_DIMENSION_MESSAGE = "";
    private static final String IMAGE_DIMENSION_DETECTION_FAILED_MESSAGE = "";

    @Override
    public void initialize(ValidMedia constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (isFileEmpty(file, context)) {
            return false;
        }
        MediaType mediaType = MediaUtil.detectMediaType(file);
        if (!isMediaTypeSupported(mediaType, context)) {
            return false;
        }
        if (!isFileSizeAcceptable(file, mediaType, context)) {
            return false;
        }
        return isFileDimensionAcceptable(file, mediaType, context);
    }

    private boolean isFileEmpty(MultipartFile file, ConstraintValidatorContext context) {
        if (file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(MEDIA_NOT_NULL_MESSAGE).addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isMediaTypeSupported(MediaType mediaType, ConstraintValidatorContext context) {
        if (MediaType.UNKNOWN == mediaType) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(MediaType.UNKNOWN.getValue()).addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isFileSizeAcceptable(MultipartFile file, MediaType mediaType, ConstraintValidatorContext context) {
        if (MediaType.JPEG == mediaType || MediaType.PNG == mediaType) {
            if (file.getSize() > MAX_IMAGE_SIZE_IN_MB.toBytes()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(INVALID_MEDIA_SIZE_MESSAGE).addConstraintViolation();
                return false;
            }
        }
        if (MediaType.MP4 == mediaType) {
            if (file.getSize() > MAX_VIDEO_SIZE_IN_MB.toBytes()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(INVALID_MEDIA_SIZE_MESSAGE).addConstraintViolation();
                return false;
            }
        }
        return true;
    }

    private boolean isFileDimensionAcceptable(MultipartFile file, MediaType mediaType, ConstraintValidatorContext context) {
        if (MediaType.JPEG == mediaType || MediaType.PNG == mediaType) {
            Dimension dimension;
            try {
                dimension = MediaUtil.getDimension(file);
            } catch (IOException ex) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(IMAGE_DIMENSION_DETECTION_FAILED_MESSAGE).addConstraintViolation();
                return false;
            }
            if (!isValidDimension(VALID_IMAGE_DIMENSION, dimension)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(INVALID_IMAGE_DIMENSION_MESSAGE).addConstraintViolation();
                return false;
            }
        }
        return true;
    }

    private boolean isValidDimension(Dimension expected, Dimension actual) {
        return (
            actual != null &&
            !(actual.getWidth() <= 0) &&
            !(actual.getWidth() > expected.getWidth()) &&
            !(actual.getHeight() <= 0) &&
            !(actual.getHeight() > expected.getHeight())
        );
    }
}
