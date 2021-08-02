package net.timxekhach.operation.data.mapped.abstracts;

import net.timxekhach.security.model.SecurityResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

@MappedSuperclass
public abstract class XeEntity implements Serializable {
    @Transient
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    public String buildProfileImagePath() {
        return String.format("%s/profile.jpg", this.buildUniquePath());
    }

    public String getProfileImageUrl() {
        if(new File(this.buildProfileImagePath()).exists()) {
            return String.format("%s/profile.jpg", this.buildUniqueUrl());
        }
        return String.format("http://robohash.org/xekhach/%s", this.buildRelativePath());
    }

    private String buildUniquePath() {
        return String.format("%s%s", SecurityResource.instance.get().getResourcePath(), this.buildRelativePath());
    }

    private String buildUniqueUrl() {
        return String.format("%s%s", SecurityResource.instance.get().getResourceUrl(), this.buildRelativePath());
    }

    private String buildRelativePath() {
        return String.format("%s/%s", this.getLowerName(), this.getIncrementId());
    }

    abstract protected Long getIncrementId();

    @Transient
    String lowerName;
    String getLowerName() {
        if(lowerName == null) {
            lowerName = this.getClass().getSimpleName().toLowerCase();
        }
        return this.lowerName;
    }

    protected void deleteProfileImage() {
        File file = new File(this.buildProfileImagePath());
        if(file.exists()) {
            file.delete();
        }
    }

    public void saveProfileImage(MultipartFile profileImage) throws IOException {
        File file = new File(this.buildProfileImagePath());
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        profileImage.transferTo(file);
    }
}
