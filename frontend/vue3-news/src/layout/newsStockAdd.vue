<template>
  <div class="left-sidebar-menu">
    <div class="header">
      <div class="stock-list"><h3>관심목록</h3></div>
      <div class="stock-add">
        <button type="button" class="btn-menu">
          <div class="plus-icon">
            <svg
              width="20"
              height="20"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              style="display: block"
            >
              <image
                href="@/assets/svg/plus-svgrepo-com.svg"
                width="20"
                height="20"
              />
            </svg>
          </div>
          <span class="btn-text">종목추가</span>
        </button>
      </div>
      <div></div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
/* 관심목록  */
.stock-list {
  border-bottom: 1px solid #e0e0e0;
  margin-bottom: 10px;
}

/* 종목추가 버튼 div */
.stock-add {
  border-bottom: 1px solid #e0e0e0;
  margin-bottom: 10px;
}

/* 종목추가 버튼 */
.btn-menu {
  border: 0;
  cursor: pointer;
  display: flex;
  align-items: center;
  color: #1c1c1c;
  margin-bottom: 10px;
  background: white;
}

/* 플러스 아이콘  */
.plus-icon {
  margin-right: 6px;
}

/* 종목 추가 글자 */
.btn-text {
  font-size: 1rem;
  color: #999999;
}

/* 종목 추가 글자 호버 */
.btn-text:hover {
  color: #000000;
}
</style>
