package com.example.demo.stock.service;

import com.example.demo.stock.dto.*;
import com.example.demo.stock.model.StockInfoModel;
import com.example.demo.stock.model.StockKeyword;
import com.example.demo.stock.model.StockLikeModel;
import com.example.demo.stock.model.StockPriceModel;
import com.example.demo.stock.repository.StockInfoRepository;
import com.example.demo.stock.repository.StockKeywordRepository;
import com.example.demo.stock.repository.StockLikeRepository;
import com.example.demo.stock.repository.StockPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.json.JsonObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final StockPriceRepository stockPriceRepository;
    private final StockInfoRepository stockInfoRepository;
    private final StockLikeRepository stockLikeRepository;
    private final StockKeywordRepository stockKeywordRepository;
    private final MongoTemplate mongoTemplate;

    /**
     * 종목 검색
     * - 종목 이름으로 검색
     * - 종목 코드로 검색
     */
    public List<SearchResDTO> stockSearchByKeyword(String keyword){

        /**
         * keyword 에 문자가 하나라도 포함되어 있다면 이름(name)으로 검색
         * 세글자 이상에서 모두 숫자라면 종목 코드로 검색
         */
        boolean isCodeSearch = false;

        if(keyword.length() > 2){
            if(keyword.chars().allMatch(Character::isDigit)){
                isCodeSearch = true;
            }
        }

        Query query = new Query();

        /**
         * true : 종목 코드로 검색
         * false : 종목 명으로 검색
         */
        if(isCodeSearch){
            //query.addCriteria(Criteria.where("code").regex("^"+keyword))
            query.addCriteria(Criteria.where("code").regex(".*"+keyword+".*"))
                    .with(Sort.by(Sort.Direction.ASC, "code")).limit(15);
        }else{
            query.addCriteria(Criteria.where("name").regex(".*"+ keyword +".*","i"))
                    .with(Sort.by(Sort.Direction.ASC, "name")).limit(15);
        }

        List<SearchResDTO> searchResult = mongoTemplate.find(query,SearchResDTO.class,"stock_price");

        return searchResult;
    }

    /**
     * 종목 차트 - 일별 가격 데이터 요청
     */
    public ArrayList<ArrayList> selectStockPrice(String stockCode){

        StockPriceModel stockPriceModel = stockPriceRepository.findByCode(stockCode);

        return stockPriceModel.getPriceByDate();
    }

    /**
     * 종목 정보 - 종목 요약정보 요청
     */
    public StockSummaryResDTO selectStockSummary(String stockCode){

        Query query = new Query();
        query.addCriteria(Criteria.where("code").is(stockCode));

        StockSummaryResDTO summaryResDTO = mongoTemplate.findOne(query, StockSummaryResDTO.class,"stock_info");

        return summaryResDTO;
    }

    /**
     * 종목 정보 - 재무 정보 연간 데이터 요청
     */
    public ArrayList<Object> selectStockAnnualFinance(String stockCode){

        StockInfoModel stockInfoModel = stockInfoRepository.findByCode(stockCode);

        return stockInfoModel.getAnnualFinanceInfo();
    }

    /**
     * 종목 정보 - 재무 정보 분기별 데이터 요청
     */
    public ArrayList<Object> selectStockQuarterFinance(String stockCode){

        StockInfoModel stockInfoModel = stockInfoRepository.findByCode(stockCode);

        return stockInfoModel.getQuarterFinanceInfo();
    }

    /**
     * 종목 즐겨찾기 요청
     */
    public void likeStock(String stockCode, String email){

        // 즐겨찾기로 되어있는지 확인
        StockLikeModel stockLikeModel = stockLikeRepository.findByCodeAndEmail(stockCode, email);

        // 없으면 즐겨찾기 추가
        if(stockLikeModel == null){
            stockLikeModel = new StockLikeModel(stockCode,email);
            stockLikeRepository.insert(stockLikeModel);
        }
    }


    /**
     * 종목 즐겨찾기 해제 요청
     */
    public void dislikeStock(String stockCode, String email){

        // 즐겨찾기로 되어있는지 확인
        StockLikeModel stockLikeModel = stockLikeRepository.findByCodeAndEmail(stockCode, email);

        // 있으면 즐겨찾기 삭제
        if(stockLikeModel != null){
            stockLikeRepository.delete(stockLikeModel);
        }
    }

    /**
     * 즐겨찾기한 목록 가져오기
     */
    public ArrayList<SearchResDTO> stockLikeList(String email){
        ArrayList<StockLikeModel> wishlist = stockLikeRepository.findByEmail(email);

        Query query = new Query();
        query.addCriteria(Criteria.where("code").in(wishlist.stream().map(c->c.getCode()).collect(Collectors.toList()) ));

        ArrayList<SearchResDTO> result = (ArrayList)mongoTemplate.find(query, SearchResDTO.class, "stock_price");

        return result;
    }

    /**
     * 키워드 관심 요청
     */
    public void likeKeyword(String keyword, String email){

        // 관심중으로 되어있는지 확인
        StockKeyword stockKeyword = stockKeywordRepository.findByKeywordAndEmail(keyword, email);

        // 없으면 추가
        if(stockKeyword == null){
            stockKeyword = new StockKeyword(keyword,email);
            stockKeywordRepository.insert(stockKeyword);
        }
    }

    /**
     * 키워드 관심 해제 요청
     */
    public void dislikeKeyword(String keyword, String email){

        // 관심중으로 되어있는지 확인
        StockKeyword stockKeyword = stockKeywordRepository.findByKeywordAndEmail(keyword, email);

        // 있으면 관심 삭제
        if(stockKeyword != null){
            stockKeywordRepository.delete(stockKeyword);
        }
    }

    /**
     * 키워드 관심 목록 가져오기
     */
    public ArrayList<StockKeyword> keywordLikeList(String email){
        return stockKeywordRepository.findByEmail(email);
    }
}
