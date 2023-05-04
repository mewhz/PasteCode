<template>
  <div>
  <pc-header></pc-header>
    <div class="list"
         v-infinite-scroll="infinite"
         :infinite-scroll-disabled="disabled">
<!--      <ul class="infinite-list">-->
<!--        <li v-for="i in count" class="infinite-list-item">{{ i }}</li>-->
<!--      </ul>-->
      <el-card v-for="code in codeList" style="margin-bottom: 50px" :key="code.codeId">
        <span>{{ code.codeTitle }}</span>
        <div>
          <pc-code :code="code"></pc-code>
        </div>
  <!--      <el-button>展开</el-button>-->
      </el-card>
      <p v-if="loading">加载中...</p>
      <p v-if="noMore">没有更多了</p>
    </div>
    <el-backtop target=".list"></el-backtop>
  </div>
</template>

<script>
import PcHeader from "@/components/pc-header";
import PcCode from "@/components/pc-code";
export default {
  name: "List",
  components: {PcCode, PcHeader},
  data() {
    return {
      total: 0,
      codeList: [],
      current: 1,
      size: 3,
      loading: false,
    }
  },
  computed: {
    noMore () {
      return (this.current * this.size) >= this.total
    },
    disabled () {
      console.log(this.loading || this.noMore)
      return this.loading || this.noMore
    }

  },
  methods : {

    infinite() {
      // this.load();
      this.current ++;
      this.loading = true;
      setTimeout(() => {
        this.load();
        this.loading = false;
      }, 800);
      console.log(new Date().getTime());
    },

    load() {
      this.$axios({
        url: `${this.$url}/code/pageList`,
        method: 'get',
        params: {
          "current": this.current - 1,
        }
      }).then((response) => {
        console.log(response.data.data.records);
        this.codeList = this.codeList.concat(response.data.data.records);
        this.total = response.data.data.count;
        // this.codeList = response.data.data;
      });
    }
  },
  created() {
    this.load();
  }
}
</script>

<style scoped>
>>> .el-card__body {
  padding: 0;
}
.list {
  height: 90vh;
  overflow: auto;
}


.infinite-list .infinite-list-item {
  /* 设置弹性布局 */
  display: flex;
  /* 垂直居中 */
  align-items: center;
  /* 水平居中 */
  justify-content: center;
  height: 50px;
  background: #e8f3fe;
  margin: 10px;
  color: #e43333
}
</style>