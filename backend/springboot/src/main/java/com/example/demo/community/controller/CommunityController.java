package com.example.demo.community.controller;

import com.example.demo.community.dto.StockKeywordResDTO;
import com.example.demo.community.model.Comment;
import com.example.demo.community.model.KeywordComment;
import com.example.demo.community.service.CommunityService;
import com.example.demo.security.jwt.SecurityUser;
import com.example.demo.stock.model.KeywordLikeCount;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityService communityService;
    @Operation(summary = "커뮤니티 글 등록 요청")
    @PostMapping("/addComment")
    public ResponseEntity addComment(@Valid @RequestBody Comment comment, BindingResult result,@ApiIgnore @AuthenticationPrincipal SecurityUser member){

        if(member == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }

        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(false);
        }

        // 대댓글 등록 요청 시 부모 댓글의 subCount 수 ++ (update)
        // comment 에 parentId 값이 있을 때
        if(!comment.getParentId().trim().equals("") ){
            communityService.increaseSubCount(comment.getParentId());
        }

        comment.setEmail(member.getMember().getEmail());
        comment.setNickName(member.getMember().getNickName());
        comment.setRegDate(LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        communityService.insertComment(comment);
        return ResponseEntity.ok().body("저장 완료");
    }

    @Operation(summary = "종목에 맞는 커뮤니티 글 목록 요청")
    @GetMapping("/comments/{stockCode}")
    public ResponseEntity comments(@PathVariable String stockCode){

        if(stockCode.trim().equals("") || stockCode == null){
            return ResponseEntity.badRequest().body("종목 코드 값이 없습니다.");
        }

        List<Comment> comments = communityService.getCommentsByStockCode(stockCode);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("comments",comments);
        resultMap.put("totalCount",comments.size());
        return ResponseEntity.ok().body(resultMap);

    }

    @Operation(summary = "특정 코멘트에 대한 코멘트 목록 요청 (대댓글)")
    @PostMapping("/comments/{stockCode}")
    public ResponseEntity commentsByParentId(@RequestBody Map<String, String> idMap){

        List<Comment> comments = new ArrayList<>();

        try{
            String commentId = idMap.get("id");

            if(commentId.trim().equals("") || commentId == null){
                throw new NullPointerException();
            }

            comments = communityService.getCommentsByParentId(commentId);

        }catch (NullPointerException e){

            return ResponseEntity.badRequest().body("id 값이 없습니다.");
        }

        return ResponseEntity.ok(comments);

    }

    @Operation(summary = "고유 id 값으로 커뮤니티 글 삭제 요청")
    @PostMapping("/delComment")
    public ResponseEntity delComment(@RequestBody Map<String, String> idMap){

        try{
            String commentId = idMap.get("id");

            if(commentId.trim().equals("") || commentId == null){
                throw new NullPointerException();
            }

            communityService.delComment(commentId);

        }catch (NullPointerException e){

            return ResponseEntity.badRequest().body("id 값이 없습니다.");
        }

        return ResponseEntity.ok("글 삭제 완료");
    }

    //주제 토론 부분 ===================================================
    @Operation(summary = "키워드 관심 많은 순으로 0~20개 목록 가져오기")
    @GetMapping("/getKeywordsByRanking")
    public ResponseEntity getKeywordsByRanking(){
        List<KeywordLikeCount> rankList = communityService.getKeywordsByRanking();

        ArrayList<StockKeywordResDTO> rankResDTO = new ArrayList<>();

        for (KeywordLikeCount keywordLikeCount : rankList) {
            StockKeywordResDTO stockKeywordResDTO = new StockKeywordResDTO();

            List<KeywordComment> keywordComments =
                    communityService.getKeywordCommentsByKeyword(keywordLikeCount.getKeyword());

            stockKeywordResDTO.setKeyword(keywordLikeCount.getKeyword());
            stockKeywordResDTO.setLikeCount(keywordLikeCount.getCount());
            stockKeywordResDTO.setCommentCount(keywordComments.size());

            rankResDTO.add(stockKeywordResDTO);
        }

        return ResponseEntity.ok().body(rankResDTO);
    }

    @Operation(summary = "커뮤니티 키워드 글 등록 요청")
    @PostMapping("/addKeywordComment")
    public ResponseEntity addKeywordComment(@Valid @RequestBody KeywordComment keywordComment, BindingResult result, @ApiIgnore @AuthenticationPrincipal SecurityUser member) {

        if(member == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }

        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(false);
        }

        // 대댓글 등록 요청 시 부모 댓글의 subCount 수 ++ (update)
        // keyword comment 에 parentId 값이 있을 때
        if(!keywordComment.getParentId().trim().equals("") ){
            communityService.increaseKeywordSubCount(keywordComment.getParentId());
        }

        keywordComment.setEmail(member.getMember().getEmail());
        keywordComment.setNickName(member.getMember().getNickName());
        keywordComment.setRegDate(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        communityService.insertKeywordComment(keywordComment);
        return ResponseEntity.ok().body("저장 완료");
    }

    @Operation(summary = "키워드에 맞는 커뮤니티 키워드 글 목록 요청")
    @GetMapping("/keyword-comments/{keyword}")
    public ResponseEntity keywordComments(@PathVariable String keyword){

        if(keyword.trim().equals("") || keyword == null){
            return ResponseEntity.badRequest().body("종목 키워드 값이 없습니다.");
        }

        // 응답해줄 결과 res dto
        StockKeywordResDTO keywordCommentsResDTO = new StockKeywordResDTO();

        // 키워드에 맞는 글 목록
        List<KeywordComment> keywordComments = communityService.getKeywordCommentsByKeyword(keyword);
        // 키워드 이름
        keywordCommentsResDTO.setKeyword(keyword);
        // 글 목록
        keywordCommentsResDTO.setKeywordComments(keywordComments);
        // 글 개수
        keywordCommentsResDTO.setCommentCount(keywordComments.size());
        // 키워드 관심 개수
        keywordCommentsResDTO.setLikeCount(communityService.getKeywordLikeCount(keyword));

        return ResponseEntity.ok().body(keywordCommentsResDTO);

    }
}
