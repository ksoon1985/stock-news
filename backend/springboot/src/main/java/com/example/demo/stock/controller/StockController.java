package com.example.demo.stock.controller;

import com.example.demo.security.jwt.SecurityUser;
import com.example.demo.stock.dto.SearchResDTO;
import com.example.demo.stock.dto.StockSummaryResDTO;
import com.example.demo.stock.model.StockKeyword;
import com.example.demo.stock.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stock")
@Api("주식에 관련된 데이터를 전송하는 컨트롤러")
public class StockController {
    private final StockService stockService;

    @Operation(summary = "주식 종목 검색 요청",description = "종목 코드, 종목 이름, 종목 마켓 을 응답합니다.")
    @GetMapping("/stocks/{keyword}")
    public ResponseEntity stockSearchByKeyword(@PathVariable String keyword, HttpServletRequest request){

        log.info("stockSearchByKeyword, Client IP : {} , PORT : {}",request.getRemoteAddr(),request.getRemotePort());

        return ResponseEntity.ok().body(stockService.stockSearchByKeyword(keyword));
    }

    @Operation(summary = "일별 주가 정보 요청", description = "주식 차트에 사용할 주가 데이터를 응답합니다.")
    @GetMapping("/stock-price/{stockCode}")
    public ResponseEntity getStockPrice(@PathVariable String stockCode, HttpServletRequest request){

        log.info("Client IP : {} , PORT : {}",request.getRemoteAddr(),request.getRemotePort());

        if(stockCode.equals("") || stockCode == null){
            return ResponseEntity.badRequest().body("종목 코드 값이 없습니다.");
        }



        return ResponseEntity.ok().body(stockService.selectStockPrice(stockCode));
    }

    @Operation(summary = "주식 정보와 가격정보 요청", description = "종목 코드, 종목 이름, 종목 마켓, 주식 차트에 사용할 주가 데이터를 응답합니다.")
    @GetMapping("/stock-infoAndPrice/{stockCode}")
    public ResponseEntity getStockInfoAndPrice(@PathVariable String stockCode, HttpServletRequest request){

        log.info("Client IP : {} , PORT : {}",request.getRemoteAddr(),request.getRemotePort());

        if(stockCode.equals("") || stockCode == null){
            return ResponseEntity.badRequest().body("종목 코드 값이 없습니다.");
        }
        return ResponseEntity.ok().body(stockService.selectStockPrice(stockCode));
    }

    @Operation(summary = "종목 요약정보 요청", description = "종목에 관한 요약 정보를 응답합니다.")
    @GetMapping("/stock-summary/{stockCode}")
    public ResponseEntity getStockSummary(@PathVariable String stockCode, HttpServletRequest request){
        log.info("Client IP : {} , PORT : {}",request.getRemoteAddr(),request.getRemotePort());

        return ResponseEntity.ok().body(stockService.selectStockSummary(stockCode));
    }

    @Operation(summary = "종목 요약정보 - 대표키워드 요청")
    @GetMapping("/stock-themeKeyword/{stockCode}")
    public ResponseEntity getStockThemeKeyword(@PathVariable String stockCode){
        if(stockCode.equals("") || stockCode == null){
            return ResponseEntity.badRequest().body("종목 코드 값이 없습니다.");
        }

        StockSummaryResDTO summaryResDTO = stockService.selectStockSummary(stockCode);

        return ResponseEntity.ok().body(summaryResDTO.getThemeKeywords());
    }

    @Operation(summary = "종목 연간 재무정보 요청",description = "종목에 해당하는 연간 재무정보 데이터를 응답합니다.")
    @GetMapping("/stock-finance/a/{stockCode}")
    public ResponseEntity getStockAnnualFinance(@PathVariable String stockCode ,HttpServletRequest request){
        log.info("Client IP : {} , PORT : {}",request.getRemoteAddr(),request.getRemotePort());

        return ResponseEntity.ok().body(stockService.selectStockAnnualFinance(stockCode));
    }

    @Operation(summary = "종목 분기별 재무정보 요청",description = "종목에 해당하는 분기별 재무정보 데이터를 응답합니다.")
    @GetMapping("/stock-finance/q/{stockCode}")
    public ResponseEntity getStockQuarterFinance(@PathVariable String stockCode ,HttpServletRequest request){
        log.info("Client IP : {} , PORT : {}",request.getRemoteAddr(),request.getRemotePort());

        return ResponseEntity.ok().body(stockService.selectStockQuarterFinance(stockCode));
    }

    // @AuthenticationPrincipal : 로그인한 유저 (UserDetails)
    @Operation(summary = "주식 종목 즐겨찾기 요청", description = "로그인 인증을 받은 유저의 주식 종목 즐겨찾기 요청입니다.")
    @GetMapping("/stock-like/{stockCode}")
    public ResponseEntity likeStock(@PathVariable String stockCode, @ApiIgnore @AuthenticationPrincipal SecurityUser member){

        // 인증이 안된 요청임
        if(member == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }

        stockService.likeStock(stockCode,member.getUsername());

        ArrayList<SearchResDTO> wishList = getLikeStocksHandler(member);

        return ResponseEntity.ok().body(wishList);
    }

    @Operation(summary = "주식 종목 즐겨찾기 해제 요청" , description = "로그인 인증을 받은 유저의 주식 종목 즐겨찾기 해제 요청입니다.")
    @GetMapping("/stock-dislike/{stockCode}")
    public ResponseEntity dislikeStock(@PathVariable String stockCode, @ApiIgnore @AuthenticationPrincipal SecurityUser member){

        // 인증이 안된 요청임
        if(member == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }

        stockService.dislikeStock(stockCode,member.getUsername());

        ArrayList<SearchResDTO> wishList = getLikeStocksHandler(member);

        return ResponseEntity.ok().body(wishList);
    }

    @Operation(summary = "즐겨찾기한 목록 요청" , description = "로그인 인증을 받은 유저의 즐겨찾기 목록을 요청합니다.")
    @GetMapping("/stocks/likes")
    public ResponseEntity getLikeStocks(@ApiIgnore @AuthenticationPrincipal SecurityUser member){
        // 인증이 안된 요청임
        if(member == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }

        ArrayList<SearchResDTO> wishList = getLikeStocksHandler(member);

        return ResponseEntity.ok().body(wishList);
    }

    public ArrayList<SearchResDTO> getLikeStocksHandler(@ApiIgnore @AuthenticationPrincipal SecurityUser member){

        ArrayList<SearchResDTO> wishList = stockService.stockLikeList(member.getUsername());

        return wishList;
    }

    @Operation(summary = "키워드 관심 요청")
    @GetMapping("/keyword-like/{themeKeyword}")
    public ResponseEntity likeKeyword(@PathVariable String themeKeyword, @ApiIgnore @AuthenticationPrincipal SecurityUser member){
        // 인증이 안된 요청임
        if(member == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }
        stockService.likeKeyword(themeKeyword,member.getUsername());

        ArrayList<String> wishList = getLikeKeywordsHandler(member.getUsername());

        return ResponseEntity.ok().body(wishList);
    }

    @Operation(summary = "키워드 관심 해제 요청")
    @GetMapping("/keyword-dislike/{themeKeyword}")
    public ResponseEntity dislikeKeyword(@PathVariable String themeKeyword, @ApiIgnore @AuthenticationPrincipal SecurityUser member){
        // 인증이 안된 요청임
        if(member == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }
        stockService.dislikeKeyword(themeKeyword,member.getUsername());

        ArrayList<String> wishList = getLikeKeywordsHandler(member.getUsername());

        return ResponseEntity.ok().body(wishList);
    }

    @Operation(summary = "관심중인 키워드 목록 요청")
    @GetMapping("/keywords/likes")
    public ResponseEntity getLikeKeywords(@ApiIgnore @AuthenticationPrincipal SecurityUser member){
        // 인증이 안된 요청임
        if(member == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
        }
        ArrayList<String> wishList = getLikeKeywordsHandler(member.getUsername());

        return ResponseEntity.ok().body(wishList);
    }

    public ArrayList<String> getLikeKeywordsHandler(String email){

        ArrayList<StockKeyword> wishList = stockService.keywordLikeList(email);
        ArrayList<String> keywordArr = new ArrayList<>();
        for (StockKeyword stockKeyword : wishList) {
            keywordArr.add(stockKeyword.getKeyword());
        }
        return keywordArr;
    }
}
