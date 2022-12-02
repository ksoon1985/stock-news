package com.example.demo.community.controller;

import com.example.demo.community.model.Comment;
import com.example.demo.community.service.CommunityService;
import com.example.demo.security.jwt.SecurityUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public ResponseEntity addComment(@Valid @RequestBody Comment comment, BindingResult result, @AuthenticationPrincipal SecurityUser member){

        if(member == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }

        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(false);
        }

        comment.setMember(member.getMember());
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

        return ResponseEntity.ok().body(comments);

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



}
