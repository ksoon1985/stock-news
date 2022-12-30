package com.example.demo.stock.service;

import com.example.demo.elasticsearch.dto.json.NewsClusteredReqDTO;
import com.example.demo.member.model.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.stock.dto.*;
import com.example.demo.stock.model.*;
import com.example.demo.stock.repository.*;
import com.example.demo.util.CustomDateUtil;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.json.JsonObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final StockPriceRepository stockPriceRepository;
    private final StockInfoRepository stockInfoRepository;
    private final StockLikeRepository stockLikeRepository;
    private final StockKeywordRepository stockKeywordRepository;
    private final StockStatisticsRepository stockStatisticsRepository;
    private final MemberRepository memberRepository;
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

        for (SearchResDTO searchResDTO : searchResult) {
            searchResDTO.setLogoPath(""+searchResDTO.getCode());
        }

        return searchResult;
    }

    /**
     * 종목코드로 종목 이름 검색
     */
    public String getStockNameByStockCode(String stockCode){

        StockPriceModel stockPriceModel = stockPriceRepository.findByCode(stockCode);

        return stockPriceModel.getName();
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

        // 갯수 부분
        KeywordLikeCount keywordLike = mongoTemplate.findOne(Query.query(Criteria.where("keyword").is(keyword)), KeywordLikeCount.class);
        // db에 키워드가 없다면
        if(keywordLike == null){
            keywordLike = new KeywordLikeCount();
            keywordLike.setKeyword(keyword);
            keywordLike.setCount(1);
            mongoTemplate.insert(keywordLike,"keyword_like_count");

        //db에 키워드가 이미 있다면
        }else{
            keywordLike.setCount(keywordLike.getCount() + 1);
            mongoTemplate.save(keywordLike,"keyword_like_count");
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

        // 갯수 부분
        KeywordLikeCount keywordLike = mongoTemplate.findOne(Query.query(Criteria.where("keyword").is(keyword)), KeywordLikeCount.class);
        // db에 키워드가 있다면
        if(keywordLike != null) {
            keywordLike.setCount(keywordLike.getCount() - 1);
            mongoTemplate.save(keywordLike,"keyword_like_count");
        }
    }

    /**
     * 키워드 관심 목록 가져오기
     */
    public ArrayList<StockKeyword> keywordLikeList(String email){
        return stockKeywordRepository.findByEmail(email);
    }


    // ==================================================통계용=================================================

    /**
     * 종목 클릭할 때
     * 종목 통계 로그 추가
     */
    public void addStockLog(String stockCode,String email){

        StockPriceModel stockModel = stockPriceRepository.findByCode(stockCode);
        String stockName = stockModel.getName();

        StockStatistics stockStatistics = new StockStatistics();
        stockStatistics.setStockCode(stockCode); // 주식 종목
        stockStatistics.setStockName(stockName); // 주식 이름

        // 로그인한 사용자
        if(!email.equals("")){
            Member member = memberRepository.findByEmail(email);
            String gender = member.getGender();
            String birthDay = member.getBirthDay();
            stockStatistics.setEmail(email); // 유저 이메일
            stockStatistics.setGender(gender); // 유저 성별
            stockStatistics.setBirthDay(birthDay); // 유저 생년월일
        }

        // 현재 날짜/시간
        LocalDateTime now = LocalDateTime.now().plusHours(9);
        stockStatistics.setSearchDate(now);

        stockStatisticsRepository.insert(stockStatistics);
    }

    /**
     * 종목 최근 1주
     * 클릭량 추이 통계
     */
    public ClickCountProgressResDTO getClickCountProgress(String stockCode){

        // 1주 전 ~ 현재 날짜 리스트
        //ArrayList<String> recent1WeekDateList = CustomDateUtil.getRecent1WeekDateList();
        ClickCountProgressResDTO clickCountProgressResDTO = new ClickCountProgressResDTO();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> countList = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        for(int i=6; i>=0;i--){

            Calendar calFrom = Calendar.getInstance();
            calFrom.add(Calendar.DATE,-i);

            Calendar calTo = Calendar.getInstance();
            calTo.add(Calendar.DATE,-i+1);

            Query query = new Query(
                    Criteria.where("stockCode").is(stockCode)
                            .andOperator(
                                    Criteria.where("searchDate").gte(calFrom.getTime()),
                                    Criteria.where("searchDate").lt((calTo.getTime()))));

            List<StockStatistics> stockStatistics = mongoTemplate.find(query, StockStatistics.class);

            // date setting
            dateList.add(format.format(calFrom.getTime()));
            // count setting
            countList.add(stockStatistics.size());
        }

        clickCountProgressResDTO.setDateList(dateList);
        clickCountProgressResDTO.setCountList(countList);

        return clickCountProgressResDTO;
    }

    /**
     * 종목 성별 클릭량 통계
     */
    public ArrayList<ArrayList<Object>> getClickCountGender(String stockCode){

        ArrayList<ArrayList<Object>> resultList = new ArrayList<>();

        Query maleQuery = new Query(
                Criteria.where("stockCode").is(stockCode)
                        .andOperator(Criteria.where("gender").is("M")));

        Query femaleQuery = new Query(
                Criteria.where("stockCode").is(stockCode)
                        .andOperator(Criteria.where("gender").is("F")));


        List<StockStatistics> maleStatistics = mongoTemplate.find(maleQuery, StockStatistics.class);
        List<StockStatistics> femaleStatistics = mongoTemplate.find(femaleQuery, StockStatistics.class);

        double maleSize = maleStatistics.size();
        double femaleSize = femaleStatistics.size();

        double sumSize = maleSize + femaleSize;

        double malePer = Double.parseDouble(String.format("%.2f",(maleSize/sumSize) * 100));
        double femalePer = Double.parseDouble(String.format("%.2f",(femaleSize/sumSize) * 100)) ;

        ArrayList<Object> maleData = new ArrayList<>();
        maleData.add("남성");
        maleData.add(malePer);

        ArrayList<Object> femaleData = new ArrayList<>();
        femaleData.add("여성");
        femaleData.add(femalePer);

        resultList.add(maleData);
        resultList.add(femaleData);

        return resultList;
    }

    /**
     * 종목 성별 클릭량 통계
     */
    public int [] getClickCountAgeGroup(String stockCode){

        int [] ageGroupList = new int[] {0,0,0,0,0,0,0,0};

        Query query = new Query(
                Criteria.where("stockCode").is(stockCode)
                        .andOperator(Criteria.where("birthDay").exists(true)));

        List<StockStatistics> stockStatistics = mongoTemplate.find(query, StockStatistics.class);

        LocalDate now = LocalDate.now();
        int currYear = now.getYear();
        int currMonth = now.getMonthValue();
        int currDay = now.getDayOfMonth();

        for (StockStatistics stockStatistic : stockStatistics) {
            String birthDate = stockStatistic.getBirthDay();
            String[] birthDateSplit = birthDate.split("-");

            int birthYear = Integer.parseInt(birthDateSplit[0]);
            int birthMonth = Integer.parseInt(birthDateSplit[1]);
            int birthDay = Integer.parseInt(birthDateSplit[2]);

            // 만나이 계산
            int age = currYear - birthYear;
            if(birthMonth * 100 + birthDay > currMonth * 100 + currDay) age--;

            // 나이 대
            int ages = (age/10);

            if(ages > 0 && ages < 9){
                ageGroupList[ages -1] = ageGroupList[ages -1] + 1;
            }

        }

        return ageGroupList;
    }


}
