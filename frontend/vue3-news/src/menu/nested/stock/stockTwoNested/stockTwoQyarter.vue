<template>
  <table class="financial-information-table">
    <tbody>
      <td class="name-table">
        <tr class="contents"></tr>
        <tr class="contents"></tr>
        <tr class="contents"></tr>
        <tr class="contents">
          매출액(억)
        </tr>
        <tr class="contents">
          영업이익(억)
        </tr>
        <tr class="contents">
          영업이익률(%)
        </tr>
        <tr class="contents">
          당기순이익(억)
        </tr>
        <tr class="contents">
          당기순이익(지배)(억)
        </tr>
        <tr class="contents">
          순이익률(%)
        </tr>
        <tr class="contents">
          자산총계(억)
        </tr>
        <tr class="contents">
          부채총계(억)
        </tr>
        <tr class="contents">
          자본총계(억)
        </tr>
        <tr class="contents">
          부채비율(%)
        </tr>
        <tr class="contents">
          유동비율
        </tr>
        <tr class="contents">
          자본금(억)
        </tr>
        <tr class="contents">
          영업활동현금흐름(억)
        </tr>
        <tr class="contents">
          투자활동현금흐름(억)
        </tr>
        <tr class="contents">
          재무활동현금흐름(억)
        </tr>
        <tr class="contents">
          EPS(원)
        </tr>
        <tr class="contents">
          PER(배)
        </tr>
        <tr class="contents">
          PBR(배)
        </tr>
        <tr class="contents">
          ROE(%)
        </tr>
        <tr class="contents">
          ROA(%)
        </tr>
      </td>
      <td
        class="value"
        v-for="(item, index) in stockInformationDataQyarter"
        :key="index"
      >
        <tr class="data-contents">
          2021/09
        </tr>
        <tr class="contents">
          {{
            item.totalPrice
          }}
        </tr>
        <tr class="contents">
          {{
            item.operationIncome
          }}
        </tr>
        <tr class="contents">
          {{
            item.operationMargins
          }}
        </tr>
        <tr class="contents">
          {{
            item.netIncome
          }}
        </tr>
        <tr class="contents">
          {{
            item.netIncome2
          }}
        </tr>
        <tr class="contents">
          {{
            item.profitMargins
          }}
        </tr>
        <tr class="contents">
          {{
            item.totalAssets
          }}
        </tr>
        <tr class="contents">
          {{
            item.totalDept
          }}
        </tr>
        <tr class="contents">
          {{
            item.totalEquity
          }}
        </tr>
        <tr class="contents">
          {{
            item.deptRatio
          }}
        </tr>
        <tr class="contents">
          {{
            item.currentRatio
          }}
        </tr>
        <tr class="contents">
          {{
            item.capitalStock
          }}
        </tr>
        <tr class="contents">
          {{
            item.CFO
          }}
        </tr>
        <tr class="contents">
          {{
            item.CFI
          }}
        </tr>
        <tr class="contents">
          {{
            item.CFF
          }}
        </tr>
        <tr class="contents">
          {{
            item.EPS
          }}
        </tr>
        <tr class="contents">
          {{
            item.PER
          }}
        </tr>
        <tr class="contents">
          {{
            item.PBR
          }}
        </tr>
        <tr class="contents">
          {{
            item.ROE
          }}
        </tr>
        <tr class="contents">
          {{
            item.ROA
          }}
        </tr>
      </td>
      <td
        class="value"
        v-for="(item, index) in stockInformationDataQyarterTwo"
        :key="index"
      >
        <tr class="data-contents">
          2021/12
        </tr>
        <tr class="contents">
          {{
            item.totalPrice
          }}
        </tr>
        <tr class="contents">
          {{
            item.operationIncome
          }}
        </tr>
        <tr class="contents">
          {{
            item.operationMargins
          }}
        </tr>
        <tr class="contents">
          {{
            item.netIncome
          }}
        </tr>
        <tr class="contents">
          {{
            item.netIncome2
          }}
        </tr>
        <tr class="contents">
          {{
            item.profitMargins
          }}
        </tr>
        <tr class="contents">
          {{
            item.totalAssets
          }}
        </tr>
        <tr class="contents">
          {{
            item.totalDept
          }}
        </tr>
        <tr class="contents">
          {{
            item.totalEquity
          }}
        </tr>
        <tr class="contents">
          {{
            item.deptRatio
          }}
        </tr>
        <tr class="contents">
          {{
            item.currentRatio
          }}
        </tr>
        <tr class="contents">
          {{
            item.capitalStock
          }}
        </tr>
        <tr class="contents">
          {{
            item.CFO
          }}
        </tr>
        <tr class="contents">
          {{
            item.CFI
          }}
        </tr>
        <tr class="contents">
          {{
            item.CFF
          }}
        </tr>
        <tr class="contents">
          {{
            item.EPS
          }}
        </tr>
        <tr class="contents">
          {{
            item.PER
          }}
        </tr>
        <tr class="contents">
          {{
            item.PBR
          }}
        </tr>
        <tr class="contents">
          {{
            item.ROE
          }}
        </tr>
        <tr class="contents">
          {{
            item.ROA
          }}
        </tr>
      </td>
      <td
        class="value"
        v-for="(item, index) in stockInformationDataQyarterThree"
        :key="index"
      >
        <tr class="data-contents">
          2022/03
        </tr>
        <tr class="contents">
          {{
            item.totalPrice
          }}
        </tr>
        <tr class="contents">
          {{
            item.operationIncome
          }}
        </tr>
        <tr class="contents">
          {{
            item.operationMargins
          }}
        </tr>
        <tr class="contents">
          {{
            item.netIncome
          }}
        </tr>
        <tr class="contents">
          {{
            item.netIncome2
          }}
        </tr>
        <tr class="contents">
          {{
            item.profitMargins
          }}
        </tr>
        <tr class="contents">
          {{
            item.totalAssets
          }}
        </tr>
        <tr class="contents">
          {{
            item.totalDept
          }}
        </tr>
        <tr class="contents">
          {{
            item.totalEquity
          }}
        </tr>
        <tr class="contents">
          {{
            item.deptRatio
          }}
        </tr>
        <tr class="contents">
          {{
            item.currentRatio
          }}
        </tr>
        <tr class="contents">
          {{
            item.capitalStock
          }}
        </tr>
        <tr class="contents">
          {{
            item.CFO
          }}
        </tr>
        <tr class="contents">
          {{
            item.CFI
          }}
        </tr>
        <tr class="contents">
          {{
            item.CFF
          }}
        </tr>
        <tr class="contents">
          {{
            item.EPS
          }}
        </tr>
        <tr class="contents">
          {{
            item.PER
          }}
        </tr>
        <tr class="contents">
          {{
            item.PBR
          }}
        </tr>
        <tr class="contents">
          {{
            item.ROE
          }}
        </tr>
        <tr class="contents">
          {{
            item.ROA
          }}
        </tr>
      </td>
      <td
        class="value"
        v-for="(item, index) in stockInformationDataQyarterFour"
        :key="index"
      >
        <tr class="data-contents">
          2022/06
        </tr>
        <tr class="contents">
          {{
            item.totalPrice
          }}
        </tr>
        <tr class="contents">
          {{
            item.operationIncome
          }}
        </tr>
        <tr class="contents">
          {{
            item.operationMargins
          }}
        </tr>
        <tr class="contents">
          {{
            item.netIncome
          }}
        </tr>
        <tr class="contents">
          {{
            item.netIncome2
          }}
        </tr>
        <tr class="contents">
          {{
            item.profitMargins
          }}
        </tr>
        <tr class="contents">
          {{
            item.totalAssets
          }}
        </tr>
        <tr class="contents">
          {{
            item.totalDept
          }}
        </tr>
        <tr class="contents">
          {{
            item.totalEquity
          }}
        </tr>
        <tr class="contents">
          {{
            item.deptRatio
          }}
        </tr>
        <tr class="contents">
          {{
            item.currentRatio
          }}
        </tr>
        <tr class="contents">
          {{
            item.capitalStock
          }}
        </tr>
        <tr class="contents">
          {{
            item.CFO
          }}
        </tr>
        <tr class="contents">
          {{
            item.CFI
          }}
        </tr>
        <tr class="contents">
          {{
            item.CFF
          }}
        </tr>
        <tr class="contents">
          {{
            item.EPS
          }}
        </tr>
        <tr class="contents">
          {{
            item.PER
          }}
        </tr>
        <tr class="contents">
          {{
            item.PBR
          }}
        </tr>
        <tr class="contents">
          {{
            item.ROE
          }}
        </tr>
        <tr class="contents">
          {{
            item.ROA
          }}
        </tr>
      </td>
    </tbody>
  </table>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";

export default {
  setup() {
    const store = useStockStore();

    let {
      stockCode,
      listCode,
      stockInformationDataQyarter,
      stockInformationDataQyarterTwo,
      stockInformationDataQyarterThree,
      stockInformationDataQyarterFour,
    } = storeToRefs(store);

    return {
      listCode,
      stockCode,
      stockInformationDataQyarter,
      stockInformationDataQyarterTwo,
      stockInformationDataQyarterThree,
      stockInformationDataQyarterFour,
    };
  },
};
</script>

<style>
.financial-information-table {
  width: 100%;
}

.name-table {
  width: 25rem;
  color: #999999;
}

.value {
  width: 9.2rem;
}

.contents {
  font-size: 1.1rem;
}

table {
  border-collapse: separate;
  border-spacing: 0 10px;
}

.data-contents {
  color: #999999;
}
</style>
