package examapi.contentservice.domain.dto;

import examapi.contentservice.domain.dto.PostDto;

import java.util.List;

public class AllPostsDto {

    private List<PostDto> all;


    public AllPostsDto() {
    }

    public AllPostsDto(List<PostDto> posts) {
        this.all = posts;
    }


    public List<PostDto> getAll() {
        return all;
    }

    public void setAll(List<PostDto> all) {
        this.all = all;
    }
}
