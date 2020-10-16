package examapi.contentservice.domain.dto;

import examapi.contentservice.domain.dto.CommentDto;

import java.util.List;

public class AllCommentsDto {

    List<CommentDto> all;

    public AllCommentsDto() {
    }

    public AllCommentsDto(List<CommentDto> comments) {
        this.all = comments;
    }


    public List<CommentDto> getAll() {
        return all;
    }

    public void setAll(List<CommentDto> all) {
        this.all = all;
    }
}
