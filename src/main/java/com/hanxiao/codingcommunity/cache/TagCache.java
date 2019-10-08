package com.hanxiao.codingcommunity.cache;

import com.hanxiao.codingcommunity.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOs = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js",
                "php", "css", "html", "java", "node.js", "python",
                "html5", "c++", "c", "golang", "objective-c",
                "typescript", "shell", "swift", "c#", "sass",
                "ruby", "bash", "less", "asp.net", "lua", "scala", "perl"));

        tagDTOs.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("struts", "spring",
                "spring mvc", "mybatis", "hibernate", "spring boot",
                "spring cloud", "jquery", "vue", "bootstrap"));
        tagDTOs.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "apache", "CentOS",
                "ubuntu", "tomcat", "hadoop", "docker"));
        tagDTOs.add(server);

        TagDTO database = new TagDTO();
        database.setCategoryName("数据库");
        database.setTags(Arrays.asList("mysql", "oracle", "mongodb",
                "sqlserver", "redis"));
        tagDTOs.add(database);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "vim",
                "visual-studio-code", "IntelliJ IDEA",
                "eclipse", "maven", "svn", "sublime-text"));
        tagDTOs.add(tool);
        return tagDTOs;
    }

    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOs = get();
        List<String> tagList = tagDTOs.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));

        return invalid;
    }

}
